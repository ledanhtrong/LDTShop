/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import table.orders.OrdersCheckOutError;
import table.orders.OrdersValidation;
import utils.URLTools;

/**
 *
 * @author wifil
 */
@WebServlet(name = "ProcessCheckOutServlet", urlPatterns = {"/ProcessCheckOutServlet"})
public class ProcessCheckOutServlet extends HttpServlet {

    private final String CHECK_OUT_PAGE = "checkoutJSP";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String address = request.getParameter("txtAddress");
        String receiver = request.getParameter("txtReceiver");
        String phone = request.getParameter("txtPhone");

        OrdersValidation validator = new OrdersValidation();
        OrdersCheckOutError errors = new OrdersCheckOutError();
        boolean isError = false;

        String url = CHECK_OUT_PAGE;
        try {
            HttpSession session = request.getSession(false); // has check by filter
            if (address == null || receiver == null || phone == null) {// happen when user write invalid url
                isError = true;
            }

            if (!isError) {
                if (address.isEmpty()) {
                    address = (String) session.getAttribute("CURRENT_USER_ADDRESS");
                } else if (!validator.isValidAddressLength(address.trim())) {
                    isError = true;
                    errors.setAddressLengthErr(OrdersValidation.ADDRESS_LENGTH_ERROR_MESSAGE);
                }

                if (receiver.isEmpty()) {
                    receiver = (String) session.getAttribute("CURRENT_USER_FULLNAME");
                } else if (!validator.isValidFullnameLength(receiver.trim())) {
                    isError = true;
                    errors.setFullnameLengthErr(OrdersValidation.FULLNAME_LENGTH_ERROR_MESSAGE);
                }

                if (phone.isEmpty()) {
                    phone = (String) session.getAttribute("CURRENT_USER_PHONE");
                } else if (!validator.isValidPhoneFormat(phone.trim())) {
                    isError = true;
                    errors.setPhoneFormatErr(OrdersValidation.PHONE_FORMAT_ERROR_MESSAGE);
                }
            }

            if (isError) {
                url = CHECK_OUT_PAGE;
                request.setAttribute("ERRORS", errors);
            }

            if (!isError) {
                address = URLTools.URLEncode(address.trim());
                receiver = URLTools.URLEncode(receiver.trim());
                phone = URLTools.URLEncode(phone.trim());
                url = "shopping"
                        + "?btAction=Process+Order"
                        + "&txtAddress=" + address
                        + "&txtReceiver=" + receiver
                        + "&txtPhone=" + phone;
            }

        } catch (UnsupportedEncodingException e) {
            log("ProcessCheckOutServlet NotSupport: " + e.getMessage());
            url = ERROR_PAGE;
        } finally {
            if (isError) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
            if(!isError) {
                response.sendRedirect(url);
            }
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
