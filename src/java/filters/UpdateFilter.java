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
import javax.servlet.http.HttpSession;
import utils.HashTools;

/**
 *
 * @author wifil
 */
public class UpdateFilter implements Filter {

    private static final boolean DEBUG = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public UpdateFilter() {
    }

    private final String DASHBOARD_PAGE = "dashboardJSP";
    private final String PREVIOUS_FEATURE = "prepareUpdatedAccount";
    private final String UPDATE_SELF_CONTROLLER = "updateSelf";
    private final String UPDATE_ACCOUNT_CONTROLLER = "updateAccount";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        //Wrap request
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        //Get param
        String username = request.getParameter("txtUser");
        String password = request.getParameter("txtPass");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");
        String phone = request.getParameter("txtPhone");
        String address = request.getParameter("txtAddress");
        String role = request.getParameter("txtRole");
        String button = request.getParameter("btAction");

        AccountValidation validator = new AccountValidation();
        AccountSignupError errors = new AccountSignupError();
        boolean isError = false;

        String url = DASHBOARD_PAGE;
        try {
            //Check valid password
            if (password != null) {
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
            }

            //Check valid fullname
            if (fullname != null) {
                if (!validator.isValidFullnameLength(fullname.trim())) {
                    isError = true;
                    errors.setFullnameLengthError(AccountValidation.FULLNAME_LENGTH_ERR_MESSAGE);
                }

            }

            //Check valid phone
            if (phone != null) {
                if (!validator.isValidPhoneFormat(phone.trim())) {
                    isError = true;
                    errors.setPhoneFormatErr(AccountValidation.PHONE_FORMAT_ERROR_MESSAGE);
                }
            }

            //Check valid address
            if (address != null) {
                if (!validator.isValidAddressLength(address)) {
                    isError = true;
                    errors.setAddressLengthErr(AccountValidation.ADDRESS_LENGTH_ERROR_MESSAGE);
                }
            }

            // Process errors
            if (isError) {
                if (button.equals("Update")) {
                    request.setAttribute("DASHBOARD_KEY", "2");
                    url = PREVIOUS_FEATURE;
                }
                request.setAttribute("ERRORS", errors);
            }// end if input is valid

            if (!isError) {
                //process url
                if (button.equals("Save change")) {
                    HttpSession session = httpRequest.getSession(); // session has checked
                    username = (String) session.getAttribute("CURRENT_USER_USERNAME");
                    url = UPDATE_SELF_CONTROLLER;
                }

                if (button.equals("Update")) {
                    url = UPDATE_ACCOUNT_CONTROLLER;
                }

                //Process metarials for update controllers
                if (username != null) {
                    request.setAttribute("USERNAME", username.trim());
                }

                if (password != null) {
                    String hashedPassword = HashTools.hashSHA256(password.trim());
                    request.setAttribute("HASHED_PASSWORD", hashedPassword);
                }//end if update password

                if (fullname != null) {
                    request.setAttribute("FULLNAME", fullname.trim());
                }// end if update fullname

                if (phone != null) {
                    request.setAttribute("PHONE", phone.trim());
                }// end if update phone

                if (address != null) {
                    request.setAttribute("ADDRESS", address.trim());
                }

                if (role != null) {
                    request.setAttribute("ROLE", role);
                }// end if update role

            }// end if input not valid

            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
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
     *
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
     *
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (DEBUG) {
                log("UpdateFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("UpdateFilter()");
        }
        StringBuilder sb = new StringBuilder("UpdateFilter(");
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
