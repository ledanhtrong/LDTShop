/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table.productType;

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
public class ProductTypeDAO extends GeneralDAO implements Serializable{

    @Override
    protected Connection openConnection() throws NamingException, SQLException {
        return DBOTools.getConnectToASMDatabase();
    }
    
    private List<ProductTypeDTO> typeList;

    public List<ProductTypeDTO> getTypeList() {
        return typeList;
    }
    
    public void getAllProductType() throws NamingException, SQLException{
        try {
            con = openConnection();
            if(con != null) {
                String query = "SELECT ID, NAME "
                        + "FROM PRODUCT_TYPE ";
                stm = con.prepareStatement(query);
                rs = stm.executeQuery();
                while(rs.next()) {
                    String id   = rs.getString("id");
                    String name = rs.getString("name");
                    if(typeList == null) {
                        typeList = new ArrayList<>();
                    }
                    typeList.add(new ProductTypeDTO(id, name));
                }
            }
        } finally {
            closeConnection();
        }
    }
    
}
