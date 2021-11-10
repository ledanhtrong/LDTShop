/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import table.account.AccountSignupError;
import table.account.AccountDAO;
import java.io.IOException;
import java.sql.SQLException;
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
@WebServlet(name = "SignupServlet", urlPatterns = {"/SignupServlet"})
public class SignupServlet extends HttpServlet {

    private final String SIGN_UP_FAILED = "signupJSP";
    private final String LOGIN_PAGE = "loginHTML";
    private final String ERROR_PAGE = "errorHTML";

    private final int DEFAULT_ROLE = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String username = (String) request.getAttribute("USERNAME");
        String hashedPassword = (String) request.getAttribute("HASHED_PASSWORD");
        String fullname = (String) request.getAttribute("FULLNAME");
        String phone    = (String) request.getAttribute("PHONE");
        String address  = (String) request.getAttribute("ADDRESS");
        int role        = DEFAULT_ROLE;
        try {
            String temp = (String) request.getAttribute("ROLE");
            if (temp != null) {
                role = Integer.parseInt(temp);
            }
        } catch (NumberFormatException e) {
            role = DEFAULT_ROLE;
        }
        String url = SIGN_UP_FAILED;
        try {

            //1. Call dao
            AccountDAO dao = new AccountDAO();
            //2. Creat
            boolean isCreated = dao.createAccount(username, hashedPassword, fullname, role, phone, address);
            if (isCreated) {
                url = LOGIN_PAGE;
            }

        } catch (NamingException e) {
            log("SignupServlet Naming:" + e.getMessage());
            url = ERROR_PAGE;
        } catch (SQLException e) {
            String message = e.getMessage();
            log("SignupServlet SQL:" + message);
            url = ERROR_PAGE;

            if (message.contains("duplicate")) {
                AccountSignupError errors = new AccountSignupError();
                errors.setUsernameIsExisted("Username: " + username + " already exist.");
                request.setAttribute("ERRORS", errors);
                url = SIGN_UP_FAILED;
            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
