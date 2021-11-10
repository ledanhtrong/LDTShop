/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author wifil
 */
public class DBOTools implements Serializable{
    
    private final static String ASM_DATABASE_NAME = "ASM";
    
    public static Connection getConnectToASMDatabase() 
            throws NamingException, SQLException{
        //1. Get current context
        Context context = new InitialContext();
        //2. Get tomcat context
        Context tomcat = (Context) context.lookup("java:comp/env");
        //3. Get data source
        DataSource ds = (DataSource) tomcat.lookup(ASM_DATABASE_NAME);
        //4. Open connection
        Connection con = ds.getConnection();
        
        return con;
    }
}
