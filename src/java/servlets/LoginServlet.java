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
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wifil
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String LOGIN_FAILED = "loginJSP";
    private final String LOGIN_SUCCEED = "processUserCart";
    private final String ERROR_PAGE = "errorHTML";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String username = (String) request.getAttribute("USERNAME");              // has checked
        String hashedPassword = (String) request.getAttribute("HASHED_PASSWORD"); // has checked

        String url = LOGIN_FAILED;
        try {
            //Get / open session
            HttpSession session = request.getSession(true);

            //Call dao
            AccountDAO dao = new AccountDAO();
            boolean isSuccessful = dao.checkLogin(username, hashedPassword);

            if (isSuccessful) {
                //Store user information to session scope
                AccountDTO user = dao.getLoginUser();
                session.setAttribute("CURRENT_USER_USERNAME", user.getUsername());
                session.setAttribute("CURRENT_USER_FULLNAME", user.getFullname());
                session.setAttribute("CURRENT_USER_ROLE", user.getRole());
                session.setAttribute("CURRENT_USER_PHONE", user.getPhone());
                session.setAttribute("CURRENT_USER_ADDRESS", user.getAddress());

                //Store user login infor to cookie
                Cookie cookie = new Cookie(username, hashedPassword);
                cookie.setMaxAge(60 * 30);
                response.addCookie(cookie);

                url = LOGIN_SUCCEED;
            }// end if login success

            if (!isSuccessful) {
                // Send an error message for login failed
                String errMessage = "Your username or password are incorrect.";
                request.setAttribute("LOGIN_FAILED", errMessage);
                url = LOGIN_FAILED;
            }

        } catch (SQLException e) {
            log("LoginServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("LoginServlet Naming: " + e.getMessage());
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
