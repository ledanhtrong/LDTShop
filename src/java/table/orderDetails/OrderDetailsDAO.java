package table.orderDetails;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import utils.DBOTools;
import utils.GeneralDAO;

/**
 *
 * @author wifil
 */
public class OrderDetailsDAO extends GeneralDAO implements Serializable {

    @Override
    protected Connection openConnection() throws NamingException, SQLException {
        return DBOTools.getConnectToASMDatabase();
    }

    public boolean saveOrderDetails(Integer orderId, List<OrderDetailsDTO> details)
            throws SQLException, NamingException {
        try {
            //1. Open connection
            con = openConnection();
            if (con != null) {
                //2. Create query string
                String query = "INSERT INTO "
                        + "ORDER_DETAILS(ORDERID, PRODUCTID, NAME, QUANTITY, PRICE, TOTAL) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";
                //3. Prepare statement
                stm = con.prepareStatement(query);
                int count = 0;
                for (OrderDetailsDTO orderDetail : details) {
                    stm.setInt(1, orderId);
                    stm.setString(2, orderDetail.getId());
                    stm.setString(3, orderDetail.getName());
                    stm.setInt(4, orderDetail.getQuantity());
                    stm.setDouble(5, orderDetail.getPrice());
                    stm.setDouble(6, orderDetail.getTotal());
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
            }//end if connection is openned
        } finally {
            closeConnection();
        }

        return true;
    }

}
