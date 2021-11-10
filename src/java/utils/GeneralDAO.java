/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author wifil
 */
public abstract class GeneralDAO implements Serializable{
    protected Connection con;
    protected PreparedStatement stm;
    protected ResultSet rs;

    public GeneralDAO() {
    }
    
    protected abstract Connection openConnection() throws Exception;
    
    protected void closeConnection() 
            throws SQLException{
        if(rs != null) rs.close();
        if(stm != null) stm.close();
        if(con != null) con.close();
    }
}
