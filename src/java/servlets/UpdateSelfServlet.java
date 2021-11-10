/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import table.account.AccountDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
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
@WebServlet(name = "UpdateSelfServlet", urlPatterns = {"/UpdateSelfServlet"})
public class UpdateSelfServlet extends HttpServlet {

    private final String DASHBOARD_PAGE = "dashboardJSP";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String username = (String) request.getAttribute("USERNAME");
        String hashedPassword = (String) request.getAttribute("HASHED_PASSWORD");
        String fullname = (String) request.getAttribute("FULLNAME");
        String phone    = (String) request.getAttribute("PHONE");
        String address  = (String) request.getAttribute("ADDRESS");

        String url = DASHBOARD_PAGE;
        String message = "Cannot update: ";
        boolean isUpdated = false;
        try {
            //1. Call dao
            AccountDAO dao = new AccountDAO();
            if (hashedPassword != null) {
                isUpdated = dao.updatePassword(username, hashedPassword);
                if (!isUpdated) {
                    message += "password ";
                }
            }
            if (fullname != null) {
                isUpdated = dao.updateFullname(username, fullname);
                if (isUpdated) {
                    HttpSession session = request.getSession();
                    session.setAttribute("CURRENT_USER_FULLNAME", fullname);
                }

                if (!isUpdated) {
                    message += " fullname:" + fullname;
                }
            }

            if (phone != null) {
                isUpdated = dao.updatePhone(username, phone);
                if (isUpdated) {
                    HttpSession session = request.getSession();
                    session.setAttribute("CURRENT_USER_PHONE", phone);
                }

                if (!isUpdated) {
                    message += " phone:" +phone;
                }
            }

            if (address != null) {
                isUpdated = dao.updateAddress(username, address);
                if (isUpdated) {
                    HttpSession session = request.getSession();
                    session.setAttribute("CURRENT_USER_ADDRESS", address);
                }

                if (!isUpdated) {
                    message += " address:" +address;
                }
            }

            if (!isUpdated) {
                log(message);
            }
        } catch (SQLException e) {
            log("StartupServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("StartupServlet Naming: " + e.getMessage());
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
