/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wifil
 */
public class AuthorizationFilter implements Filter {

    private static final boolean DEBUG = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AuthorizationFilter() {
    }

    private final String HOME_PAGE = "front";
    private final String LOGIN_PAGE = "loginJSP";

    private final int USER_ROLE = 0;
    private final int ADMIN_ROLE = 1;
    private final int SUPER_ADMIN_ROLE = 2;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) {
        //Wrap request to HttpServletRequest
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        ServletContext context = httpRequest.getServletContext();

        List<String> userPermissions = null;
        Integer role = null;
        try {
            //Try to get session
            HttpSession session = httpRequest.getSession(false);
            if (session != null) {
                role = (Integer) session.getAttribute("CURRENT_USER_ROLE");
            }

            if (role == null) {
                userPermissions = (List<String>) context.getAttribute("GENERAL_PERMISSIONS");
            } else if (role == USER_ROLE) {
                userPermissions = (List<String>) context.getAttribute("DEFAULT_USER_PERMISSIONS");
            } else if (role == ADMIN_ROLE || role == SUPER_ADMIN_ROLE) {
                userPermissions = (List<String>) context.getAttribute("DEFAULT_ADMIN_PERMISSIONS");
            }

            //Get uri
            String uri = httpRequest.getRequestURI();
            //Get resource 
            String resource = uri.substring(uri.lastIndexOf("/") + 1);

            boolean isAccessible = false;
            if (uri.contains("/assets")) {
                isAccessible = true;
            }

            if (userPermissions.contains(resource)) {
                isAccessible = true;
            }

            //Log
            request.getServletContext().log("Authorization " + resource);

            //Check 
            if (!isAccessible) {
                String url = HOME_PAGE;
                if (role == null) {
                    String errorMessage = "Please login to access.";
                    request.setAttribute("ERROR_MESSAGE", errorMessage);
                    url = LOGIN_PAGE;
                }

                RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
            
            if (isAccessible) {
                chain.doFilter(request, response);
            }

        } catch (ServletException e) {
            log("AuthorizationFilter ServletException: " + e.getMessage());
        } catch (IOException e) {
            log("AuthorizationFilter IOException: " + e.getMessage());
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
                log("AuthorizationFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthorizationFilter()");
        }
        StringBuilder sb = new StringBuilder("AuthorizationFilter(");
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
