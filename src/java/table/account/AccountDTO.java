/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table.account;

import java.io.Serializable;

/**
 *
 * @author wifil
 */
public class AccountDTO implements Serializable {

    private String username;
    private String password;
    private String fullname;
    private Integer role;
    private String phone;
    private String address;

    public AccountDTO() {
    }

    public AccountDTO(String username, String password, String fullname, Integer role, String phone, String address) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
        this.phone = phone;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
}
