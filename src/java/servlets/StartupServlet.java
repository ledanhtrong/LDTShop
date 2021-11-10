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
@WebServlet(name = "StartupServlet", urlPatterns = {"/StartupServlet"})
public class StartupServlet extends HttpServlet {

    private final String HOME_PAGE = "front";
    private final String PROCESS_USER_CART = "processUserCart";
    private final String ERROR_PAGE = "errorJSP";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = HOME_PAGE;
        try {
            //1. Get cookies 
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                //2. Call dao
                AccountDAO dao = new AccountDAO();

                //3. Traverse the cookies
                for (Cookie cookie : cookies) {
                    String username = cookie.getName();
                    String password = cookie.getValue();

                    boolean isSuccessful = dao.checkLogin(username, password);
                    if (isSuccessful) {
                        //4. Get / create session
                        HttpSession session = request.getSession(true);

                        //5. Store user information in session scope
                        AccountDTO user = dao.getLoginUser();
                        session.setAttribute("CURRENT_USER_USERNAME", user.getUsername());
                        session.setAttribute("CURRENT_USER_FULLNAME", user.getFullname());
                        session.setAttribute("CURRENT_USER_ROLE"    , user.getRole());
                        session.setAttribute("CURRENT_USER_PHONE"   , user.getPhone());
                        session.setAttribute("CURRENT_USER_ADDRESS" , user.getAddress());
                        
                        url = PROCESS_USER_CART;
                        break;
                    }// end if login succeed
                }
            }// end if cookies exist
        } catch (SQLException e) {
            log("StartupServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("StartupServlet Naming: " + e.getMessage());
            url = ERROR_PAGE;
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
