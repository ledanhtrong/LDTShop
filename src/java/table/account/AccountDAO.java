/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table.account;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import utils.GeneralDAO;
import utils.DBOTools;

/**
 *
 * @author wifil
 */
public class AccountDAO extends GeneralDAO implements Serializable {

    @Override
    protected Connection openConnection() throws NamingException, SQLException {
        return DBOTools.getConnectToASMDatabase();
    }

    private AccountDTO loginUser;

    public AccountDTO getLoginUser() {
        return loginUser;
    }
    
    public boolean checkLogin(String username, String password)
            throws NamingException, SQLException {
        try {
            //1. Open connection
            con = openConnection();
            if (con != null) {
                //2. Create query string
                String query = "SELECT FULLNAME, ROLE, PHONE, ADDRESS "
                        + "FROM ACCOUNT "
                        + "WHERE USERNAME=? AND PASSWORD=?";
                //3. Prepare statement
                stm = con.prepareStatement(query);
                stm.setString(1, username);
                stm.setString(2, password);
                //4. Execute
                rs = stm.executeQuery();
                //5. Process result
                if (rs.next()) {
                    String fullname = rs.getString("fullname");
                    Integer role    = rs.getInt("role");
                    String phone    = rs.getString("phone");
                    String address  = rs.getString("address");
                    loginUser = new AccountDTO(username, password, fullname, role, phone, address);
                    return true;
                }
            }
        } finally {
            //6. Close objects
            closeConnection();
        }
        return false;
    }

    private List<AccountDTO> accountList;

    public List<AccountDTO> getAccountList() {
        return accountList;
    }

    public void search(String searchValue)
            throws NamingException, SQLException {
        try {
            //1. Open connection
            con = openConnection();
            if (con != null) {
                //2. Create query string
                String query = "SELECT USERNAME, PASSWORD, FULLNAME, ROLE, PHONE, ADDRESS "
                        + "FROM ACCOUNT "
                        + "WHERE FULLNAME LIKE ? AND ROLE <= 1";
                //3. Prepare statement
                stm = con.prepareStatement(query);
                stm.setString(1, "%"+searchValue+"%");
                //4. Execute
                rs = stm.executeQuery();
                //5. Process result
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullname = rs.getString("fullname");
                    Integer role = rs.getInt("role");
                    String phone    = rs.getString("phone");
                    String address  = rs.getString("address");
                    
                    if (accountList == null) {
                        accountList = new ArrayList<>();
                    }
                    accountList.add(new AccountDTO(username, password, fullname, role, phone, address));
                }
            }
        } finally {
            //6. Close objects
            closeConnection();
        }
    }

    public boolean deleteAccount(String username, int role)
            throws NamingException, SQLException {
        try {
            //1. Open connection
            con = DBOTools.getConnectToASMDatabase();
            if (con != null) {
                //2. Create query String
                String query = "DELETE FROM ACCOUNT "
                        + "WHERE USERNAME = ? AND ROLE < ?";
                //3. Prepare statement
                stm = con.prepareStatement(query);
                stm.setString(1, username);
                stm.setInt(2, role);
                //4. Execute
                int result = stm.executeUpdate();
                //5. Process result
                if (result > 0) {
                    return true;
                }
            }
        } finally {
            //6. Close connection
            closeConnection();
        }
        return false;
    }

    public boolean updatePassword(String username, String password)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "UPDATE ACCOUNT "
                        + "SET PASSWORD=? "
                        + "WHERE USERNAME=?";

                stm = con.prepareStatement(query);
                stm.setString(1, password);
                stm.setString(2, username);

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
    
    public boolean updatePassword(String username, String password, Integer adminRole)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "UPDATE ACCOUNT "
                        + "SET PASSWORD = ? "
                        + "WHERE USERNAME = ? AND ROLE < ?";

                stm = con.prepareStatement(query);
                stm.setString(1, password);
                stm.setString(2, username);
                stm.setInt(3, adminRole);

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
    
    public boolean updateFullname(String username, String fullname)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "UPDATE ACCOUNT "
                        + "SET FULLNAME=? "
                        + "WHERE USERNAME=?";

                stm = con.prepareStatement(query);
                stm.setString(1, fullname);
                stm.setString(2, username);

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
    

    public boolean updateFullname(String username, String fullname, Integer adminRole)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "UPDATE ACCOUNT "
                        + "SET FULLNAME = ? "
                        + "WHERE USERNAME = ? AND ROLE < ?";

                stm = con.prepareStatement(query);
                stm.setString(1, fullname);
                stm.setString(2, username);
                stm.setInt(3, adminRole);

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
    
    public boolean updateRole(String username, String role, Integer adminRole)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "UPDATE ACCOUNT "
                        + "SET ROLE = ? "
                        + "WHERE USERNAME = ? AND ROLE < ?";

                stm = con.prepareStatement(query);
                stm.setString(1, role);
                stm.setString(2, username);
                stm.setInt(3, adminRole);

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
    
    public boolean updatePhone(String username, String phone)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "UPDATE ACCOUNT "
                        + "SET PHONE = ? "
                        + "WHERE USERNAME = ?";

                stm = con.prepareStatement(query);
                stm.setString(1, phone);
                stm.setString(2, username);

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
    
    public boolean updatePhone(String username, String phone, Integer adminRole)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "UPDATE ACCOUNT "
                        + "SET PHONE = ? "
                        + "WHERE USERNAME = ? AND ROLE < ?";

                stm = con.prepareStatement(query);
                stm.setString(1, phone);
                stm.setString(2, username);
                stm.setInt(3, adminRole);

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
    
    public boolean updateAddress(String username, String address)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "UPDATE ACCOUNT "
                        + "SET ADDRESS = ? "
                        + "WHERE USERNAME = ?";

                stm = con.prepareStatement(query);
                stm.setString(1, address);
                stm.setString(2, username);

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
    
    public boolean updateAddress(String username, String address, Integer adminRole)
            throws NamingException, SQLException {
        try {
            con = openConnection();
            if (con != null) {
                String query = "UPDATE ACCOUNT "
                        + "SET ADDRESS = ? "
                        + "WHERE USERNAME = ? AND ROLE < ?";

                stm = con.prepareStatement(query);
                stm.setString(1, address);
                stm.setString(2, username);
                stm.setInt(3, adminRole);

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
    
    
    public boolean createAccount(String username, String password,
            String fullname, int role, String phone, String address) throws NamingException, SQLException {
        try {
            con = DBOTools.getConnectToASMDatabase();
            if (con != null) {
                String query = "INSERT "
                        + "INTO ACCOUNT(USERNAME, PASSWORD, FULLNAME, ROLE, PHONE, ADDRESS) "
                        + "VALUES (?, ?, ?, ?, ?, ?)";

                stm = con.prepareStatement(query);
                stm.setString(1, username);
                stm.setString(2, password);
                stm.setString(3, fullname);
                stm.setInt(4, role);
                stm.setString(5, phone);
                stm.setString(6, address);
                
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

    
    public AccountDTO getAccount(String username, int adminRole)
            throws NamingException, SQLException {
        AccountDTO account = null;
        try {
            //1. Open connection
            con = openConnection();
            if (con != null) {
                //2. Create query string
                String query = "SELECT FULLNAME, ROLE, PHONE, ADDRESS "
                        + "FROM ACCOUNT "
                        + "WHERE USERNAME=? AND ROLE < ?";
                //3. Prepare statement
                stm = con.prepareStatement(query);
                stm.setString(1, username);
                stm.setInt(2, adminRole);
                //4. Execute
                rs = stm.executeQuery();
                //5. Process result
                if (rs.next()) {
                    String fullname = rs.getString("fullname");
                    Integer role    = rs.getInt("role");
                    String phone    = rs.getString("phone");
                    String address  = rs.getString("address");
                    account = new AccountDTO(username, "", fullname, role, phone, address);
                }
            }
        } finally {
            //6. Close objects
            closeConnection();
        }
        return account;
    }
}
