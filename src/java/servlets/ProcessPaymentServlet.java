/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import table.cart.CartDAO;
import table.orderDetails.OrderDetailsDAO;
import table.orders.OrdersDAO;
import table.orders.OrdersDTO;
import table.product.ProductDAO;

/**
 *
 * @author wifil
 */
@WebServlet(name = "ProcessPaymentServlet", urlPatterns = {"/ProcessPaymentServlet"})
public class ProcessPaymentServlet extends HttpServlet {

    private final String PAYMENT_PAGE = "paymentJSP";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = PAYMENT_PAGE;
        boolean isSuccessfulPayment = false;
        try {
            HttpSession session = request.getSession(false);
            OrdersDTO order = (OrdersDTO) session.getAttribute("CURRENT_USER_ORDER"); // has checked by PaymentFilter
            //Set paid day before process
            Calendar cal = Calendar.getInstance();
            Timestamp paidDay = new Timestamp(cal.getTimeInMillis());
            order.setPaidDay(paidDay);

            //1. Save order infor
            OrdersDAO orderDao = new OrdersDAO();
            orderDao.saveOrder(order);

            //2. Get order identity
            Integer orderId = orderDao.getOrderId(order.getUsername(), order.getPaidDay());
            if (orderId != null) {
                //3. Save order details
                OrderDetailsDAO detailDao = new OrderDetailsDAO();
                boolean detailsIsSaved = detailDao.saveOrderDetails(orderId, order.getDetails());
                if (detailsIsSaved) {
                    //4. Remove cart (In database)
                    CartDAO cartDao = new CartDAO();
                    boolean cartIsDeleted = cartDao.deleteCart(order.getUsername());
                    if (cartIsDeleted) {
                        //5. Remove cart, order in session
                        session.removeAttribute("CART");
                        session.removeAttribute("NUMBER_PRODUCTS_IN_CART");
                        session.removeAttribute("CURRENT_USER_ORDER");
                        isSuccessfulPayment = true;

                        //6. Update quantity inproduct
                        ProductDAO productDao = new ProductDAO();
                        boolean isUpdated = productDao.updateAfterPayment(order.getDetails());

                        if (!isUpdated) {
                            log("ProcessPaymentServlet Cannot update product quantity:" + order.getUsername());
                        }
                    }

                    if (!cartIsDeleted) {
                        url = ERROR_PAGE;
                        log("ProcessPaymentServlet Cannot delete cart in database:" + order.getUsername());
                    }
                }

                if (!detailsIsSaved) {
                    url = ERROR_PAGE;
                    log("ProcessPaymentServlet Cannot save detail:" + orderId);
                }
            }

            if (orderId == null) {
                url = ERROR_PAGE;
                log("ProcessPaymentServlet Cannot get orderId:" + order.getUsername() + " | " + order.getPaidDay());
            }

            request.setAttribute("IS_SUCCESSFUL_PAYMENT", isSuccessfulPayment);
        } catch (SQLException e) {
            log("ProcessPaymentServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("ProcessPaymentServlet Naming: " + e.getMessage());
            url = ERROR_PAGE;
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
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
