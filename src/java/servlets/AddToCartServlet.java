/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import table.cart.Cart;
import table.cart.CartDAO;
import table.product.ProductDAO;

/**
 *
 * @author wifil
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private final String SHOPPING_PAGE = "shoppingJSP";
    private final String ERROR_PAGE = "errorJSP";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String id = request.getParameter("txtId");
        String typeId = request.getParameter("txtTypeId");
        String inputMin = request.getParameter("txtMin");
        String inputMax = request.getParameter("txtMax");

        String url = SHOPPING_PAGE;
        try {
            //Get - Create session
            HttpSession session = request.getSession(true);
            String username = (String) session.getAttribute("CURRENT_USER_USERNAME");

            //1. Get cart (create if not exist)
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart == null) {
                cart = new Cart();
                cart.setUsername(username);
            }

            //2. Call product dao - Check the existence of product
            ProductDAO productDao = new ProductDAO();
            boolean isExistProduct = productDao.checkExistProduct(id);
            if (isExistProduct) {
                //3. Add to cart
                cart.addToCart(id);
                //4. Remove order if exists, because cart is updated
                session.removeAttribute("CURRENT_USER_ORDER");
                
                //5.. Check customer logged in or not
                if (username != null) {
                    //6. Store to database
                    CartDAO cartDao = new CartDAO();
                    boolean isExistItem = cartDao.isExistItem(username, id);

                    if (isExistItem) {
                        boolean isUpdated = cartDao.updateItem(username, id, 1);
                        if (!isUpdated) {
                            log("AddToCartServlet Cannot update: " + username + " " + id);
                        }
                    }

                    if (!isExistItem) {
                        boolean isInserted = cartDao.addItem(username, id, 1);
                        if (!isInserted) {
                            log("AddToCartServlet Cannot insert: " + username + " " + id);
                        }
                    }
                }
            }

            //Update cart to session
            session.setAttribute("CART", cart);
            session.setAttribute("NUMBER_PRODUCTS_IN_CART", cart.getTotalQuantity());

            //Prepare search parameters
            typeId = URLEncoder.encode(typeId, StandardCharsets.UTF_8.toString());
            inputMin = URLEncoder.encode(inputMin, StandardCharsets.UTF_8.toString());
            inputMax = URLEncoder.encode(inputMax, StandardCharsets.UTF_8.toString());
            //Back to previous action
            url = "shopping"
                    + "?btAction=Apply"
                    + "&txtTypeId=" + typeId
                    + "&txtMin=" + inputMin
                    + "&txtMax=" + inputMax;

        } catch (UnsupportedEncodingException e) {
            log("AddToCartServlet Encode: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (SQLException e) {
            log("AddToCartServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("AddToCartServlet Naming: " + e.getMessage());
            url = ERROR_PAGE;
        } finally {
            response.sendRedirect(url);
        }
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
