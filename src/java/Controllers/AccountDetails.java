/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import Models.*;
import Util.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mast3
 */
@WebServlet(name = "AccountDetails", urlPatterns = {"/AccountDetails"})
public class AccountDetails extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        /*if(!Server.isLoggedIn(request.getSession(false), response))
            return;
        
            
        //Get the latest details from the database
        Users user = new DB(em, utx).getSingleResult("userid", Server.getUser(request, response).getUserid(), Users.class);
        
        request.setAttribute("name", user.getName());
        request.setAttribute("dateOfBirth", user.getDateofbirth() == null ? null : (new SimpleDateFormat("dd/MM/yyyy")).format(user.getDateofbirth()));
        request.setAttribute("address", user.getAddress());
        request.setAttribute("email", user.getEmail());
        System.out.println(user.getEmail());*/
        // Get user data
        Users user = Server.getUser(request, response);

        Quick.putInJsp(request, "name", user.getName());
        Quick.putInJsp(request, "email", user.getEmail());
        Quick.putInJsp(request, "imageUrl", user.getImageurl());
        Quick.putInJsp(request, "address", user.getAddress());

        if (user.getDateofbirth() != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(user.getDateofbirth());
            int day = cal.get(Calendar.DAY_OF_MONTH);
            Quick.putInJsp(request, "dateOfBirth", String.format("%04d", cal.get(Calendar.YEAR)) + "-" + String.format("%02d", (cal.get(Calendar.MONTH) + 1)) + "-" + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
        }

        request.getRequestDispatcher("WEB-INF/accountDetails.jsp").forward(request, response);

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
