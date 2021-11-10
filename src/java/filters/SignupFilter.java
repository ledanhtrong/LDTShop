/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import table.account.AccountSignupError;
import table.account.AccountValidation;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import utils.HashTools;

/**
 *
 * @author wifil
 */
public class SignupFilter implements Filter {

    private static final boolean DEBUG = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public SignupFilter() {
    }

    private final String SIGN_UP_FAILED = "signupJSP";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        
        
        String username = httpRequest.getParameter("txtUser");
        String password = httpRequest.getParameter("txtPass");
        String confirm  = httpRequest.getParameter("txtConfirm");
        String fullname = httpRequest.getParameter("txtFullname");
        String phone    = httpRequest.getParameter("txtPhone");
        String address  = httpRequest.getParameter("txtAddress");
        String role     = httpRequest.getParameter("txtRole");

        AccountValidation validator = new AccountValidation();
        AccountSignupError errors = new AccountSignupError();
        boolean isError = false;

        String url = SIGN_UP_FAILED;
        try {
            //1. Check user errors
            if (!validator.isValidUsernameLength(username)) {
                isError = true;
                errors.setUsernameLengthError(AccountValidation.FULLNAME_LENGTH_ERR_MESSAGE);
            } else if (!validator.isValidUsernameFormat(username)) {
                isError = true;
                errors.setUsernameFormatError(AccountValidation.USERNAME_FORMAT_ERR_MESSAGE);
            }

            if (!validator.isValidPasswordLength(password)) {
                isError = true;
                errors.setPasswordLengthError(AccountValidation.PASSWORD_LENGTH_ERR_MESSAGE);
            } else if (!validator.isValidPasswordFormat(password)) {
                isError = true;
                errors.setPasswordFormatError(AccountValidation.PASSWORD_FORMAT_ERR_MESSAGE);
            } else if (!validator.isValidConfirm(password, confirm)) {
                isError = true;
                errors.setConfirmNotMatched(AccountValidation.CONFIRM_NOT_MATCH_ERR_MESSAGE);
            }

            if (!validator.isValidFullnameLength(fullname.trim())) {
                isError = true;
                errors.setFullnameLengthError(AccountValidation.FULLNAME_LENGTH_ERR_MESSAGE);
            }
            
            if(!validator.isValidPhoneFormat(phone.trim())) {
                isError = true;
                errors.setPhoneFormatErr(AccountValidation.PHONE_FORMAT_ERROR_MESSAGE);
            }
            
            if(!validator.isValidAddressLength(address.trim())) {
                isError = true;
                errors.setAddressLengthErr(AccountValidation.ADDRESS_LENGTH_ERROR_MESSAGE);
            }

            //2. Process
            if (isError) {
                request.setAttribute("ERRORS", errors);
                RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
            if (!isError) {
                String hashedPassword = HashTools.hashSHA256(password);                
                
                request.setAttribute("USERNAME", username.trim().toLowerCase());
                request.setAttribute("HASHED_PASSWORD", hashedPassword);
                request.setAttribute("FULLNAME", fullname.trim());
                request.setAttribute("PHONE", phone.trim());
                request.setAttribute("ADDRESS", address.trim());
                if(role != null) {
                    request.setAttribute("ROLE", role);
                }
                chain.doFilter(request, response);
            }
        } catch (NoSuchAlgorithmException e) {
            log("SignupFilter NoAlgorithm: " + e.getMessage());
        } catch (ServletException e) {
            log("SignupFilter ServletException: " + e.getMessage());
        } catch (IOException e) {
            log("SignupFilter IOException: " + e.getMessage());
        }
    }

    /**
     * Return the filter configuration object for this filter.
     * @return 
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (DEBUG) {
                log("SignupFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SignupFilter()");
        }
        StringBuilder sb = new StringBuilder("SignupFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
