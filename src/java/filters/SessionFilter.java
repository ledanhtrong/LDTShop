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
public class SessionFilter implements Filter {

    private static final boolean DEBUG = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public SessionFilter() {
    }

    private final String LOGIN_PAGE = "loginJSP";
    private final String SESSION_TIMEOUT_MESSAGE = "Session not found. Please login again.";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        //Wrap request, response
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        ServletContext context = request.getServletContext();
        List<String> notCheckSession = (List<String>) context.getAttribute("NOT_CHECK_SESSION");

        String url = LOGIN_PAGE;
        boolean needToCheckSession = true;
        try {
            //Check uri
            String uri = httpRequest.getRequestURI();
            if (uri.contains("/assets")) {
                needToCheckSession = false;
            }

            //Check resource 
            String resource = uri.substring(uri.lastIndexOf("/") + 1);
            if (notCheckSession.contains(resource)) {
                needToCheckSession = false;
            }

            //Log
            request.getServletContext().log("Session " + resource + ", uri " + uri);

            boolean isAccessible = false;
            if (needToCheckSession) {
                //Try to get session
                HttpSession session = httpRequest.getSession(false);
                if (session != null) {
                    isAccessible = true;
                }
            }
            if (!needToCheckSession) {
                isAccessible = true;
            }
            
            if(!isAccessible) {
                request.setAttribute("ERROR_MESSAGE", SESSION_TIMEOUT_MESSAGE);
                RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
            
            if(isAccessible) {
                chain.doFilter(request, response);
            }
        } catch (ServletException e) {
            log("SessionFilter ServletException: " + e.getMessage());
        } catch (IOException e) {
            log("SessionFilter IOException: " + e.getMessage());
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
                log("SessionFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("SessionFilter()");
        }
        StringBuilder sb = new StringBuilder("SessionFilter(");
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
