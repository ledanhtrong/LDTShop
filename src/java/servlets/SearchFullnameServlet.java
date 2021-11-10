/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import table.account.AccountDAO;
import table.account.AccountDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wifil
 */
@WebServlet(name = "SearchFullnameServlet", urlPatterns = {"/SearchFullnameServlet"})
public class SearchFullnameServlet extends HttpServlet {

    private final String DASHBOARD_PAGE = "dashboardJSP";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String searchValue = (String) request.getAttribute("SEARCH_VALUE");
        String url = DASHBOARD_PAGE;
        try {
            boolean isValid = true;
            if (searchValue == null) {
                isValid = false;
            }
            
            if (searchValue != null) {
                searchValue = searchValue.trim();
                if (searchValue.isEmpty()) {
                    isValid = false;
                }
                if (searchValue.matches("^[%]+$")) {
                    isValid = false;
                }
                if (searchValue.length() > 50) {// max full name length
                    isValid = false;
                }
                if (searchValue.equals("All Accounts")) {
                    isValid = true;
                    searchValue = "%";
                }
            }

            if (isValid) {
                //1.Call dao
                AccountDAO dao = new AccountDAO();
                dao.search(searchValue);

                //2. Store result in request scope
                List<AccountDTO> accountList = dao.getAccountList();
                if (accountList != null) {
                    request.setAttribute("SEARCH_RESULT", accountList);
                }
            }

        } catch (SQLException e) {
            log("SearchFullnameServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("SearchFullnameServlet Naming: " + e.getMessage());
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
