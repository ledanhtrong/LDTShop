/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

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
import table.product.ProductDAO;
import table.product.ProductDTO;
import table.productType.ProductTypeDAO;
import table.productType.ProductTypeDTO;

/**
 *
 * @author wifil
 */
@WebServlet(name = "SearchProductServlet", urlPatterns = {"/SearchProductServlet"})
public class SearchProductServlet extends HttpServlet {

    private final String SHOPPING_PAGE = "shoppingJSP";
    private final String ERROR_PAGE = "errorHTML";

    private final String DEFAULT_TYPEID = "All";
    private final double DEFAULT_MIN = 0.0;
    private final double DEFAULT_MAX = 1e10;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String typeId = request.getParameter("txtTypeId");
        String inputMin = request.getParameter("txtMin");
        String inputMax = request.getParameter("txtMax");

        String url = SHOPPING_PAGE;
        try {
            typeId     = processTypeId(typeId);
            double min = processMin(inputMin);
            double max = processMax(inputMax);

            //Call dao and search
            ProductDAO productDao = new ProductDAO();
            if (typeId.equals(DEFAULT_TYPEID)) {
                productDao.search(min, max);
            }
            if (!typeId.equals(DEFAULT_TYPEID)) {
                productDao.search(typeId, min, max);
            }
            //Get result
            List<ProductDTO> searchResult = productDao.getProductList();
            request.setAttribute("SEARCH_RESULT", searchResult);
            request.setAttribute("MIN", min);
            request.setAttribute("MAX", max);

            //Get type list
            ProductTypeDAO typeDao = new ProductTypeDAO();
            typeDao.getAllProductType();
            List<ProductTypeDTO> typeList = typeDao.getTypeList();
            request.setAttribute("TYPES", typeList);

        } catch (NumberFormatException e) {
            log("SearchProductServlet NumberFormat: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (SQLException e) {
            log("SearchProductServlet SQl: " + e.getMessage());
            url = ERROR_PAGE;
        } catch (NamingException e) {
            log("SearchProductServlet Naming: " + e.getMessage());
            url = ERROR_PAGE;
        } finally {
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        }
    }

    private String processTypeId(String typeId) {
        if (typeId == null || typeId.isEmpty()) {
            typeId = DEFAULT_TYPEID;
        }
        return typeId;
    }

    private double processMin(String inputMin) {
        double min = DEFAULT_MIN;
        if (inputMin != null) {
            inputMin = inputMin.trim();
            inputMin = inputMin.replaceFirst("^[0]+", "");
            if (inputMin.matches("[1-9][0-9]{0,9}([.][0-9]+)?")) {
                min = Double.parseDouble(inputMin);
            }
        }
        return min;
    }

    private double processMax(String inputMax) {
        double max = DEFAULT_MAX;
        if (inputMax != null) {
            inputMax = inputMax.trim();
            inputMax = inputMax.replaceFirst("^[0]+", "");
            if (inputMax.matches("[1-9][0-9]{0,9}([.][0-9]+)?")) {
                max = Double.parseDouble(inputMax);
            }
        }
        return max;
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
