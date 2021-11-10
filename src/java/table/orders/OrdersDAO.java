/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table.orders;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import utils.DBOTools;
import utils.GeneralDAO;

/**
 *
 * @author wifil
 */
public class OrdersDAO extends GeneralDAO implements Serializable{

    @Override
    protected Connection openConnection() throws NamingException, SQLException {
        return DBOTools.getConnectToASMDatabase();
    }
    
    public void saveOrder(OrdersDTO order) 
            throws NamingException, SQLException{
        try {
            con = openConnection();
            if(con != null) {
                String query = "INSERT INTO "
                        + "ORDERS(USERNAME, BUYER, RECEIVER, ADDRESS, PHONE, PAIDDAY, SUBTOTAL) "
                        + "VALUES (?,?,?,?,?,?,?)";
                
                stm = con.prepareStatement(query);
                stm.setString(1, order.getUsername());
                stm.setString(2, order.getBuyer());
                stm.setString(3, order.getReceiver());
                stm.setString(4, order.getAddress());
                stm.setString(5, order.getPhone());
                stm.setTimestamp(6, order.getPaidDay());
                stm.setDouble(7, order.getSubtotal());
                
                stm.executeUpdate();
            }
        } finally {
            closeConnection();
        }
    }
    
    public Integer getOrderId(String username, Timestamp paidDay)
            throws NamingException, SQLException{
        try {
            con = openConnection();
            if(con != null) {
                String query = "SELECT ORDERID "
                        + "FROM ORDERS "
                        + "WHERE (USERNAME = ?) "
                        + "AND "
                        + "DATEADD(ms, -DATEPART(ms, PAIDDAY), PAIDDAY) =  DATEADD(ms, -DATEPART(ms, ?), ?)";
                
                stm = con.prepareStatement(query);
                stm.setString(1, username);
                stm.setTimestamp(2, paidDay);
                stm.setTimestamp(3, paidDay);
                
                rs = stm.executeQuery();
                if(rs.next()) {
                    Integer orderId = rs.getInt("orderid");
                    return orderId;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }
    
}
