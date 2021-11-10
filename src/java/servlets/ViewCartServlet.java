/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import table.cart.Cart;
import table.product.ProductDAO;
import table.product.ProductDTO;

/**
 *
 * @author wifil
 */
@WebServlet(name = "ViewCartServlet", urlPatterns = {"/ViewCartServlet"})
public class ViewCartServlet extends HttpServlet {

    private final String VIEW_CART_PAGE = "viewCartJSP";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = VIEW_CART_PAGE;
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null) {
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        //Call dao
                        ProductDAO dao = new ProductDAO();
                        List<ProductDTO> products = dao.getAllProducts();
                        List<ProductDTO> productsInCart = processItems(items, products);
                        if (productsInCart != null) {
                            request.setAttribute("CART_PRODUCT_LIST", productsInCart);
                        }
                    }// end if cart has products
                }// end if cart exist
            }
        } catch (SQLException e) {
            log("ViewCartServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("ViewCartServlet Naming: " + e.getMessage());
            url = ERROR_PAGE;
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
    }

    private List<ProductDTO> processItems(Map<String, Integer> items, List<ProductDTO> products) {
        List<ProductDTO> productsInCart = null;
        for (ProductDTO product : products) {
            String id = product.getId();
            if (items.containsKey(id)) {
                int quantity = items.get(id);
                product.setQuantity(quantity);
                if (productsInCart == null) {
                    productsInCart = new ArrayList<>();
                }
                productsInCart.add(product);
            }
        }
        return productsInCart;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
