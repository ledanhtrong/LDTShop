/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import table.orderDetails.OrderDetailsDTO;
import table.orders.OrdersDTO;
import table.product.ProductDAO;
import table.product.ProductDTO;

/**
 *
 * @author wifil
 */
@WebServlet(name = "ProcessOrderServlet", urlPatterns = {"/ProcessOrderServlet"})
public class ProcessOrderServlet extends HttpServlet {

    private final String PAYMENT_PAGE = "paymentJSP";
    private final String VIEW_CART = "viewCart";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String address = request.getParameter("txtAddress");
        String receiver = request.getParameter("txtReceiver");
        String phone = request.getParameter("txtPhone");
        String url = VIEW_CART;

        try {
            HttpSession session = request.getSession(false);                            // has checked by session filter
            String username = (String) session.getAttribute("CURRENT_USER_USERNAME");   // Checked log in authorization filter
            String buyer = (String) session.getAttribute("CURRENT_USER_FULLNAME");      // Checked log in authorization filter
            Cart cart = (Cart) session.getAttribute("CART");                            // has checked by checkout filter
            Map<String, Integer> items = cart.getItems();                               // has checked by checkout filter

            //Call dao
            ProductDAO productDao = new ProductDAO();

            //Process order details list
            List<ProductDTO> products = productDao.getAllProducts();
            List<OrderDetailsDTO> details = processDetails(items, products);

            if (details != null) {
                // Create order 
                Timestamp paidDay = null; // need buyer confirm
                url = PAYMENT_PAGE;
                
                OrdersDTO order = new OrdersDTO(username, buyer, receiver, address, phone, paidDay, details);
                session.setAttribute("CURRENT_USER_ORDER", order);
            }

            if (details == null) {
                /*
                * New feature: check items in cart again.
                * Maybe products is sold out, then remove from
                * cart and notice to buyer.
                 */
            }

        } catch (SQLException e) {
            log("RemoveFromCartServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("RemoveFromCartServlet Naming: " + e.getMessage());
            url = ERROR_PAGE;
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }

    }

    private List<OrderDetailsDTO> processDetails(Map<String, Integer> items, List<ProductDTO> products) {
        List<OrderDetailsDTO> details = null;

        for (int i = 0; i < products.size(); i++) {
            String id = products.get(i).getId();
            if (items.containsKey(id)) {
                String name = products.get(i).getName();
                String unit = products.get(i).getUnit();
                double price = products.get(i).getPrice();
                int quantity = items.get(id);
                if (details == null) {
                    details = new ArrayList<>();
                }
                details.add(new OrderDetailsDTO(id, name, quantity, unit, price));
            }
        }

        return details;
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
