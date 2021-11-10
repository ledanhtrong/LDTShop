/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import table.account.AccountDAO;
import java.io.IOException;
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

/**
 *
 * @author wifil
 */
@WebServlet(name = "UpdateAccountServlet", urlPatterns = {"/UpdateAccountServlet"})
public class UpdateAccountServlet extends HttpServlet {

    private final String DASHBOARD = "dashboardJSP";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String searchValue = request.getParameter("txtSearchValue");
        String username = (String) request.getAttribute("USERNAME");
        String password = (String) request.getAttribute("PASSWORD");
        String fullname = (String) request.getAttribute("FULLNAME");
        String phone = (String) request.getAttribute("PHONE");
        String address = (String) request.getAttribute("ADDRESS");
        String role = (String) request.getAttribute("ROLE");

        String url = DASHBOARD;
        boolean isUpdated = false;
        String message = "";
        try {
            //Get role of the use whole update this
            HttpSession session = request.getSession();
            Integer adminRole = (Integer) session.getAttribute("CURRENT_USER_ROLE");

            //Call dao
            AccountDAO dao = new AccountDAO();

            if (username != null) { // it's null if someone trick by urlRewriting mechanism
                //Process update
                if (password != null) {
                    isUpdated = dao.updatePassword(username, password, adminRole);
                    if (!isUpdated) {
                        message += "password ";
                    }
                }

                if (fullname != null) {
                    isUpdated = dao.updateFullname(username, fullname, adminRole);
                    if (!isUpdated) {
                        message += " fullname:" + fullname;
                    }
                }

                if (phone != null) {
                    isUpdated = dao.updatePhone(username, phone, adminRole);
                    if (!isUpdated) {
                        message += " phone:" + phone;
                    }
                }

                if (address != null) {
                    isUpdated = dao.updateAddress(username, address, adminRole);
                    if (!isUpdated) {
                        message += " address:" + address;
                    }
                }

                if (role != null) {
                    isUpdated = dao.updateRole(username, role, adminRole);
                    if (!isUpdated) {
                        message += " role:" + role;
                    }
                }
            }

            //Back to previous action
            if(searchValue == null) {
                searchValue = "";
            }
            if(username == null) {
                username = "";
            }
            searchValue = URLEncoder.encode(searchValue, StandardCharsets.UTF_8.toString());
            username = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());
            url = "dashboard"
                    + "?btAction=Process+update"
                    + "&txtUser=" + username
                    + "&txtSearchValue=" + searchValue;
            
        } catch (SQLException e) {
            log("UpdateAccountServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("UpdateAccountServlet Naming: " + e.getMessage());
            url = ERROR_PAGE;
        } finally {
            if(!isUpdated) {
                log(message);
            }
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
