/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import table.cart.Cart;
import table.cart.CartDAO;
import table.cart.CartDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wifil
 */
@WebServlet(name = "ProcessUserCartServlet", urlPatterns = {"/ProcessUserCartServlet"})
public class ProcessUserCartServlet extends HttpServlet {

    private final String HOME_PAGE = "front";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        ServletContext context = request.getServletContext();
        Map<String, String> roadMap = (Map<String, String>) context.getAttribute("ROAD_MAP");

        String url = HOME_PAGE;
        try {
            HttpSession session = request.getSession(false); //Session has to exist
            if (session != null) {
                //MERGING USER CART
                String username = (String) session.getAttribute("CURRENT_USER_USERNAME");//username has to exist
                Cart cart = (Cart) session.getAttribute("CART");
                CartDAO dao = new CartDAO();
                List<CartDTO> list = dao.getItems(username);

                if (cart != null) {
                    Map<String, Integer> items = cart.getItems();
                    if (items != null) {
                        for (String id : items.keySet()) {
                            int quantity = items.get(id);

                            boolean isExistItem = dao.isExistItem(username, id);
                            if (isExistItem) {
                                boolean isUpdated = dao.updateItem(username, id, quantity);
                                if (!isUpdated) {
                                    log("AddToCartServlet Cannot update: " 
                                            + username + " " + id + " " + quantity);
                                }
                            }

                            if (!isExistItem) {
                                boolean isInserted = dao.addItem(username, id, quantity);
                                if (!isInserted) {
                                    log("AddToCartServlet Cannot insert: "
                                            + username + " " + id + " " + quantity);
                                }
                            }
                        }
                    }// end if cart not empty
                }// end if cart exists

                if (list != null) {
                    if (cart == null) {
                        cart = new Cart();
                    }
                    cart.addItems(list);
                }

                if (cart != null) {
                    cart.setUsername(username);
                }
                
                session.setAttribute("CART", cart);
                
                int numberOfProducts = 0;
                if(cart != null) {
                    numberOfProducts = cart.getTotalQuantity();
                }
                session.setAttribute("NUMBER_PRODUCTS_IN_CART", numberOfProducts);
            }
        } catch (SQLException e) {
            log("ProcessUserCartServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("ProcessUserCartServlet Naming: " + e.getMessage());
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
