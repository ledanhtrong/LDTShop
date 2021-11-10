/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table.orders;

/**
 *
 * @author wifil
 */
public class OrdersValidation {
    public final static String ADDRESS_LENGTH_ERROR_MESSAGE = 
            "We need your address to delivery. ";
    public final static String FULLNAME_LENGTH_ERROR_MESSAGE = 
            "Accept a name lenght in 2 to 50 characters only.";
    public final static String PHONE_FORMAT_ERROR_MESSAGE = 
            "Wrong phone number.";
    
    public boolean isValidAddressLength(String address) {
        int length = address.length();
        return length >= 5 && length <= 500;
    }
    
    public boolean isValidFullnameLength(String fullname) {
        int length = fullname.length();
        return length >= 2 && length <= 50;
    }
    
    public boolean isValidPhoneFormat(String phone) {
        return phone.matches("^([+][0-9]+)?[0-9]+$");
    }
    
    
}
