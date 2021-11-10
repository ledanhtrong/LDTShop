package table.product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import table.orderDetails.OrderDetailsDTO;
import utils.DBOTools;
import utils.GeneralDAO;

public class ProductDAO extends GeneralDAO implements Serializable {

    public ProductDAO() {
        super();
    }

    @Override
    protected Connection openConnection() throws NamingException, SQLException {
        return DBOTools.getConnectToASMDatabase();
    }

    private List<ProductDTO> productList;

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void search(double min, double max)
            throws SQLException, NamingException {
        try {
            //1. Open connection
            con = openConnection();
            if (con != null) {
                //2. Create query string
                String query = "SELECT ID, NAME, PRICE, UNIT, QUANTITY, IMAGE, DESCRIPTION "
                        + "FROM PRODUCT "
                        + "WHERE (PRICE >= ?) AND (PRICE <= ?) AND (QUANTITY > 0)";
                //3. Prepare statement
                stm = con.prepareStatement(query);
                stm.setDouble(1, min);
                stm.setDouble(2, max);
                //4. Execute
                rs = stm.executeQuery();
                //5. Process result
                if (productList == null) {
                    productList = new ArrayList<>();
                }
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    String unit = rs.getString("unit");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    productList.add(new ProductDTO(id, name, price, unit, quantity, image, description));
                }
            }//end if connection is openned.
        } finally {
            //6. Close objects
            closeConnection();
        }
    }

    public void search(String typeId, double min, double max)
            throws SQLException, NamingException {
        try {
            //1. Open connection
            con = openConnection();
            if (con != null) {
                //2. Create query string
                String query = "SELECT ID, NAME, PRICE, UNIT, QUANTITY, IMAGE, DESCRIPTION "
                        + "FROM PRODUCT "
                        + "WHERE (PRICE >= ?) AND (PRICE <= ?) AND (TYPEID = ?) AND (QUANTITY > 0)";
                //3. Prepare statement
                stm = con.prepareStatement(query);
                stm.setDouble(1, min);
                stm.setDouble(2, max);
                stm.setString(3, typeId);
                //4. Execute
                rs = stm.executeQuery();
                //5. Process result
                if (productList == null) {
                    productList = new ArrayList<>();
                }
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    String unit = rs.getString("unit");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    productList.add(new ProductDTO(id, name, price, unit, quantity, image, description));
                }
            }//end if connection is openned.
        } finally {
            //6. Close objects
            closeConnection();
        }
    }

    public boolean checkExistProduct(String id) throws SQLException, NamingException {
        try {
            //1. Open connection
            con = openConnection();
            if (con != null) {
                //2. Create query string
                String query = "SELECT NAME "
                        + "FROM PRODUCT "
                        + "WHERE ID = ? and QUANTITY > 0";
                //3. Prepare statement
                stm = con.prepareStatement(query);
                stm.setString(1, id);
                //4. Execute
                rs = stm.executeQuery();
                //5. Process result
                if (rs.next()) {
                    return true;
                }
            }//end if connection is openned.
        } finally {
            //6. Close objects
            closeConnection();
        }
        return false;
    }

    public List<ProductDTO> getAllProducts()
            throws NamingException, SQLException {
        List<ProductDTO> list = null;
        try {
            con = openConnection();
            if (con != null) {
                String query = "SELECT ID, NAME, PRICE, UNIT, QUANTITY, IMAGE, DESCRIPTION "
                        + "FROM PRODUCT ";

                stm = con.prepareStatement(query);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    double price = rs.getDouble("price");
                    String unit = rs.getString("unit");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    String description = rs.getString("description");
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(new ProductDTO(id, name, price, unit, quantity, image, description));
                }
            }
        } finally {
            closeConnection();
        }
        return list;
    }

    public boolean updateAfterPayment(List<OrderDetailsDTO> details)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "UPDATE PRODUCT "
                        + "SET QUANTITY = QUANTITY - ? "
                        + "WHERE ID = ?";

                stm = con.prepareStatement(query);

                int count = 0;
                for (OrderDetailsDTO orderDetail : details) {
                    stm.setInt(1, orderDetail.getQuantity());
                    stm.setString(2, orderDetail.getId());
                    //4. Add to batch
                    stm.addBatch();
                    count++;

                    //The lenght of an statement is limit
                    if (count >= 100) {
                        int[] results = stm.executeBatch();
                        for (int result : results) {
                            if (result < 1) {
                                return false;
                            }
                        }
                        count = 0;
                    }
                }

                if (count > 0) {
                    int[] results = stm.executeBatch();
                    for (int result : results) {
                        if (result < 1) {
                            return false;
                        }
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return true;
    }
}
