/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author wifil
 */
public class HashTools implements Serializable {

    public static String hashSHA256(String originalString) 
            throws NoSuchAlgorithmException{
            //1. Create an MessageDigest instance for SHA-256 
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            //2. Convert string to byte array in UTF_8 standard
            byte[] array = originalString.getBytes(StandardCharsets.UTF_8);
            
            //3. Do hash
            byte[] hash = digest.digest(array);
            
            //4. Convert hashed array to string in hexadecimal
            String hashString = DatatypeConverter.printHexBinary(hash);
        
        return hashString;
    }

}
