/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
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

/**
 *
 * @author wifil
 */
@WebServlet(name = "RemoveFromCartServlet", urlPatterns = {"/RemoveFromCartServlet"})
public class RemoveFromCartServlet extends HttpServlet {

    private final String VIEW_CART = "viewCart";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = VIEW_CART;
        try {
            //Go cart place and get cart
            HttpSession session = request.getSession(false); //session is checked
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart != null) {
                String id = request.getParameter("txtId");
                if (id != null) {
                    //Remove item in cart
                    cart.removeItem(id);
                    
                    //Remove item in cart table if exist
                    String username = cart.getUsername();
                    if (username != null) {
                        CartDAO dao = new CartDAO();
                        dao.deleteItem(username, id);
                        
                        //Remove order if it already exists, because cart is updated
                        session.removeAttribute("CURRENT_USER_ORDER");
                    }
                }
                //Update cart to session
                session.setAttribute("CART", cart);
            }

            //Update number of products in cart
            int numberOfProducts = 0;
            if (cart != null) {
                numberOfProducts = cart.getTotalQuantity();
            }
            session.setAttribute("NUMBER_PRODUCTS_IN_CART", numberOfProducts);
        } catch (SQLException e) {
            log("RemoveFromCartServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("RemoveFromCartServlet Naming: " + e.getMessage());
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
