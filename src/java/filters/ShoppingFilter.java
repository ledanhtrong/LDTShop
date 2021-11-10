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
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import table.product.ProductDTO;

/**
 *
 * @author wifil
 */
public class ShoppingFilter implements Filter {

    private static final boolean DEBUG = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public ShoppingFilter() {
    }

    private final String SEARCH_PRODUCT_CONTROLLER = "searchProduct";
    private final String ADD_TO_CART_CONTROLLER = "addToCart";
    private final String VIEW_CART_CONTROLLER = "viewCart";
    private final String REMOVE_ITEM_CONTROLLER = "removeItem";
    private final String PROCESS_CHECKOUT_PAGE = "checkoutJSP";
    private final String PROCESS_CHECKOUT_CONTROLLER = "processCheckout";
    private final String PROCESS_ORDER_CONTROLLER = "processOrder";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String uri = httpRequest.getRequestURI();
        String resource = uri.substring(uri.lastIndexOf("/") + 1);

        String button = request.getParameter("btAction");
        String url = SEARCH_PRODUCT_CONTROLLER;
        try {
            if (resource.equals("shopping")) {
                if (button != null) {
                    switch (button) {
                        case "Apply":
                            url = SEARCH_PRODUCT_CONTROLLER;
                            break;
                        case "Add To Cart":
                            url = ADD_TO_CART_CONTROLLER;
                            break;
                        case "View Cart":
                            url = VIEW_CART_CONTROLLER;
                            break;
                        case "Remove":
                            url = REMOVE_ITEM_CONTROLLER;
                            break;
                        case "Process checkout":
                            url = PROCESS_CHECKOUT_PAGE;
                            break;
                        case "Delivery to this address":
                            url = PROCESS_CHECKOUT_CONTROLLER;
                            break;
                        case "Process Order":
                            url = PROCESS_ORDER_CONTROLLER;
                            break;
                        default:
                            break;
                    }
                }
            }

            boolean isDoChain = false;
            if (resource.equals("shopping.jsp")) {
                List<ProductDTO> searchResult = (List<ProductDTO>) request.getAttribute("SEARCH_RESULT");
                if(searchResult != null) {
                    isDoChain = true;
                }
            }
            
            if (!isDoChain) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                dispatcher.forward(request, response);   
            }
            
            if (isDoChain) {
                chain.doFilter(request, response);
            }
        } catch (ServletException e) {
            log("ShoppingFilter ServletException: " + e.getMessage());
        } catch (IOException e) {
            log("ShoppingFilter IOException: " + e.getMessage());
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
                log("ShoppingFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("ShoppingFilter()");
        }
        StringBuilder sb = new StringBuilder("ShoppingFilter(");
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
