/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wifil
 */
public class DashboardFilter implements Filter {

    private static final boolean DEBUG = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public DashboardFilter() {
    }

    private final String DASHBOARD_PAGE = "dashboardJSP";
    private final String SEARCH_FULLNAME = "searchFullname";
    private final String PREPARE_UPDATED_ACCOUNT = "prepareUpdatedAccount";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        //Wrap request
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String url = DASHBOARD_PAGE;
        try {
            HttpSession session = httpRequest.getSession(false); // session is checked in SessionFilter
            String username = (String) session.getAttribute("CURRENT_USER_USERNAME");
            if (username != null) {
                String button = request.getParameter("btAction");
                if (button == null) {
                    request.setAttribute("DASHBOARD_KEY", "1");
                }

                if (button != null) {
                    if (button.equals("Account Information")) {
                        request.setAttribute("DASHBOARD_KEY", "1");
                    }

                    if (button.equals("Account Management")) {
                        request.setAttribute("DASHBOARD_KEY", "2");
                    }

                    if (button.equals("Product Information")) {
                        request.setAttribute("DASHBOARD_KEY", "3");
                    }

                    if (button.equals("Process update")) {
                        request.setAttribute("DASHBOARD_KEY", "2");
                        url = PREPARE_UPDATED_ACCOUNT;
                    }
                    if (button.equals("All Accounts")) {
                        request.setAttribute("DASHBOARD_KEY", "2");
                        request.setAttribute("SEARCH_VALUE", "All Accounts");
                        url = SEARCH_FULLNAME;
                    }

                    if (button.equals("Search")) {
                        request.setAttribute("DASHBOARD_KEY", "2");

                        String searchValue = request.getParameter("txtSearchValue");
                        request.setAttribute("SEARCH_VALUE", searchValue);
                        url = SEARCH_FULLNAME;
                    }// end if search
                }//end if button param exists
            }// end if has login user

            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
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
                log("DashboardFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("DashboardFilter()");
        }
        StringBuilder sb = new StringBuilder("DashboardFilter(");
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
