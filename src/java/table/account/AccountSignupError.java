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
public class AccountSignupError implements Serializable {

    private String usernameLengthError;
    private String usernameFormatError;

    private String passwordLengthError;
    private String passwordFormatError;

    
    private String fullnameLengthError;
    private String confirmNotMatched;
    private String phoneFormatErr;
    private String addressLengthErr;
    private String usernameIsExisted;

    public AccountSignupError() {
    }

    public String getUsernameLengthError() {
        return usernameLengthError;
    }

    public void setUsernameLengthError(String usernameLengthError) {
        this.usernameLengthError = usernameLengthError;
    }

    public String getUsernameFormatError() {
        return usernameFormatError;
    }

    public void setUsernameFormatError(String usernameFormatError) {
        this.usernameFormatError = usernameFormatError;
    }

    public String getPasswordLengthError() {
        return passwordLengthError;
    }

    public void setPasswordLengthError(String passwordLengthError) {
        this.passwordLengthError = passwordLengthError;
    }

    public String getPasswordFormatError() {
        return passwordFormatError;
    }

    public void setPasswordFormatError(String passwordFormatError) {
        this.passwordFormatError = passwordFormatError;
    }

    public String getFullnameLengthError() {
        return fullnameLengthError;
    }

    public void setFullnameLengthError(String fullnameLengthError) {
        this.fullnameLengthError = fullnameLengthError;
    }

    public String getConfirmNotMatched() {
        return confirmNotMatched;
    }

    public void setConfirmNotMatched(String confirmNotMatched) {
        this.confirmNotMatched = confirmNotMatched;
    }

    public String getPhoneFormatErr() {
        return phoneFormatErr;
    }

    public void setPhoneFormatErr(String phoneFormatErr) {
        this.phoneFormatErr = phoneFormatErr;
    }

    public String getAddressLengthErr() {
        return addressLengthErr;
    }

    public void setAddressLengthErr(String addressLengthErr) {
        this.addressLengthErr = addressLengthErr;
    }

    public String getUsernameIsExisted() {
        return usernameIsExisted;
    }

    public void setUsernameIsExisted(String usernameIsExisted) {
        this.usernameIsExisted = usernameIsExisted;
    }

    
}
