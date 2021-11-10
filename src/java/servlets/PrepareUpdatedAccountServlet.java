/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import table.account.AccountDAO;
import table.account.AccountDTO;

/**
 *
 * @author wifil
 */
@WebServlet(name = "PrepareUpdatedAccountServlet", urlPatterns = {"/PrepareUpdatedAccountServlet"})
public class PrepareUpdatedAccountServlet extends HttpServlet {

    private final String SEARCH_FULLNAME = "searchFullname";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("txtUser");
        

        String url = SEARCH_FULLNAME;
        try {
            HttpSession session = request.getSession(false); // session is checked
            int adminRole = (Integer) session.getAttribute("CURRENT_USER_ROLE");

            if (username != null) {
                AccountDAO dao = new AccountDAO();
                AccountDTO account = dao.getAccount(username, adminRole);

                if (account != null) {
                    request.setAttribute("PROCESSING_UPDATED_ACCOUNT", account);
                }
            }
            
            String searchValue = request.getParameter("txtSearchValue");
            request.setAttribute("SEARCH_VALUE", searchValue);
        } catch (SQLException e) {
            log("PrepareUpdatedAccountServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("PrepareUpdatedAccountServlet Naming: " + e.getMessage());
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
