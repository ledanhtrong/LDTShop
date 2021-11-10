/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

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
import utils.HashTools;

/**
 *
 * @author wifil
 */
public class LoginFilter implements Filter {

    private static final boolean DEBUG = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public LoginFilter() {
    }

    
    private final String LOGIN_PAGE = "loginJSP";
    private final String INVALID_MESSAGE = "Your username or password are incorrect.";
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        String username = request.getParameter("txtUser");
        String password = request.getParameter("txtPass");

        String url = LOGIN_PAGE;
        boolean isValid = true;
        try {
            //1. Prepare input
            if (username == null || username.trim().isEmpty()) {
                isValid = false;
            }
            if (password == null || password.trim().isEmpty()) {
                isValid = false;
            }

            if(!isValid) {
                request.setAttribute("LOGIN_FAILED", INVALID_MESSAGE);
                
                RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
            
            if (isValid) {
                username = username.trim().toLowerCase();
                String hashPassword = HashTools.hashSHA256(password);
                request.setAttribute("USERNAME", username);
                request.setAttribute("HASHED_PASSWORD", hashPassword);

                chain.doFilter(request, response);
            }
        } catch (NoSuchAlgorithmException e) {
            log("LoginServlet NoAlgorithm: " + e.getMessage());
        } catch (ServletException e) {
            log("LoginFilter ServletException: " + e.getMessage());
        } catch (IOException e) {
            log("LoginFilter IOException: " + e.getMessage());
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
                log("LoginFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("LoginFilter()");
        }
        StringBuilder sb = new StringBuilder("LoginFilter(");
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
