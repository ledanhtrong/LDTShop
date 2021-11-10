/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table.cart;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import utils.DBOTools;
import utils.GeneralDAO;

/**
 *
 * @author wifil
 */
public class CartDAO extends GeneralDAO implements Serializable {

    @Override
    protected Connection openConnection() throws NamingException, SQLException {
        return DBOTools.getConnectToASMDatabase();
    }

    public boolean addItem(String username, String productId, int quantity)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "INSERT INTO CART(USERNAME, PRODUCTID, QUANTITY) "
                        + "VALUES (?, ?, ?)";

                stm = con.prepareStatement(query);
                stm.setString(1, username);
                stm.setString(2, productId);
                stm.setInt(3, quantity);

                int result = stm.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean isExistItem(String username, String productId)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "SELECT USERNAME "
                        + "FROM CART "
                        + "WHERE USERNAME = ? AND PRODUCTID = ?";

                stm = con.prepareStatement(query);
                stm.setString(1, username);
                stm.setString(2, productId);

                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean updateItem(String username, String productId, int quantity)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "UPDATE CART "
                        + "SET QUANTITY = QUANTITY + ? "
                        + "WHERE USERNAME = ? AND PRODUCTID = ? ";
                stm = con.prepareStatement(query);
                stm.setInt(1, quantity);
                stm.setString(2, username);
                stm.setString(3, productId);

                int result = stm.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }

    public List<CartDTO> getItems(String username)
            throws NamingException, SQLException {
        List<CartDTO> items = null;
        try {
            con = openConnection();
            if (con != null) {
                String query = "SELECT PRODUCTID, QUANTITY "
                        + "FROM CART "
                        + "WHERE USERNAME = ?";
                stm = con.prepareStatement(query);
                stm.setString(1, username);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String productId = rs.getString("productid");
                    int quantity = rs.getInt("quantity");

                    if (items == null) {
                        items = new ArrayList<>();
                    }
                    items.add(new CartDTO(username, productId, quantity));
                }
            }
        } finally {
            closeConnection();
        }
        return items;
    }

    public void deleteItem(String username, String productId)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "DELETE FROM CART "
                        + "WHERE USERNAME =? AND PRODUCTID = ?";
                stm = con.prepareStatement(query);
                stm.setString(1, username);
                stm.setString(2, productId);

                stm.executeUpdate();
            }
        } finally {
            closeConnection();
        }
    }

    public boolean deleteCart(String username)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "DELETE FROM CART "
                        + "WHERE USERNAME =?";
                stm = con.prepareStatement(query);
                stm.setString(1, username);

                int result = stm.executeUpdate();
                if (result > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
}
