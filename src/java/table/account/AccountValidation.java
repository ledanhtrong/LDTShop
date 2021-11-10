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
public class AccountValidation implements Serializable{

    public static final String USERNAME_LENGTH_ERR_MESSAGE = 
            "Username is required from 6 to 30 characters.";
    
    public static final String USERNAME_FORMAT_ERR_MESSAGE = 
            "Username only accepts number 0-9, alphabets, "
            + "and underline '_'";
    
    public static final String PASSWORD_LENGTH_ERR_MESSAGE = 
            "Password is required from 6 to 30 characters.";
    
    public static final String PASSWORD_FORMAT_ERR_MESSAGE = 
            "Password only accepts numbers, alphabet, "
            + "and these special characters: *.!@#$%^&?~_";
    
    public static final String CONFIRM_NOT_MATCH_ERR_MESSAGE = 
            "Confirm must match with password";
    
    public static final String FULLNAME_LENGTH_ERR_MESSAGE = 
            "Full name is required from 2 to 50 characters.";
    
    public final static String ADDRESS_LENGTH_ERROR_MESSAGE = 
            "We need address to delivery for you. Require 5 to 500 characters";
    
    public final static String PHONE_FORMAT_ERROR_MESSAGE = 
            "Wrong phone number.";
    
    public AccountValidation() {
    }
    
    public boolean isValidUsernameLength(String username) {
        int length = username.length();
        return 6 <= length && length <= 30;
    }
    
    public boolean isValidUsernameFormat(String username) {
        return username.matches("[0-9a-zA-Z_]+");
    }
    
    public boolean isValidPasswordLength(String password) {
        int length = password.length();
        return 6 <= length && length <= 30;
    }
    
    public boolean isValidPasswordFormat(String password) {
        return password.matches("[0-9a-zA-Z*.!@#$%^&?~_]+");
    }
    
    public boolean isValidConfirm(String password, String confirm) {
        return confirm.trim().equals(password.trim());
    }
    
    public boolean isValidFullnameLength(String fullname) {
        int length = fullname.length();
        return 2 <= length && length <= 50;
    }
    
    public boolean isValidAddressLength(String address) {
        int length = address.length();
        return length >= 5 && length <= 500;
    }
    
    public boolean isValidPhoneFormat(String phone) {
        return phone.matches("^([+][0-9]+)?[0-9]+$");
    }
}
