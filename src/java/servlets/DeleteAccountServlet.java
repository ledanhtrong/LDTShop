/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import table.account.AccountDAO;
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

/**
 *
 * @author wifil
 */
@WebServlet(name = "DeleteAccountServlet", urlPatterns = {"/DeleteAccountServlet"})
public class DeleteAccountServlet extends HttpServlet {

    private final String DASH_BOARD = "dashboard";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String searchValue = request.getParameter("txtSearchValue");
        String username = request.getParameter("txtUser");
        
        String url = DASH_BOARD;
        try {
            //1. Get current user role
            HttpSession session = request.getSession();
            int role = (Integer) session.getAttribute("CURRENT_USER_ROLE");

            //2. Call dao
            AccountDAO dao = new AccountDAO();
            boolean isDeleted = dao.deleteAccount(username, role);
            if(!isDeleted) {
                log("DeleteAccountServlet Cannot delete user: " + username);
            }

            //3. Back to previous action
            if(searchValue == null) {
                searchValue = "";
            }
            searchValue = URLEncoder.encode(searchValue, StandardCharsets.UTF_8.toString());
            url = "dashboard"
                    + "?btAction=Search"
                    + "&txtSearchValue=" + searchValue;

        } catch (UnsupportedEncodingException e) {
            log("DeleteAccountServlet Encode: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (SQLException e) {
            log("DeleteAccountServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("DeleteAccountServlet Naming: " + e.getMessage());
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
