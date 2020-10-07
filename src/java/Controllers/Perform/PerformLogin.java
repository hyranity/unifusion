/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

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
import Util.*;
import Models.*;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformLogin", urlPatterns = {"/PerformLogin"})
public class PerformLogin extends HttpServlet {
    
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
        
        
        
        //Get credentials
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        //See if user exists in DB based on their email
        Users user = new DB(em, utx).getSingleResult("email", email, Users.class);
        
        if(user == null){
            // User does not exist
            System.out.println("User does not exist");
            Errors.respondSimple(request.getSession(), "User does not exist");
            response.sendRedirect("Login");
            return;
        }
        
        //Compare password
        if(!Hasher.comparePassword(user.getPassword(), password, user.getPasswordsalt())){
            // Password is incorrect
            System.out.println("Password is incorrect");
            Errors.respondSimple(request.getSession(), "Incorrect password");
            response.sendRedirect("Login");
            return;
        }
        
        //Login successful
        HttpSession session = request.getSession();
        session.setAttribute("user", user); 
        System.out.println("LOGIN SUCCESS");
        response.sendRedirect("Dashboard");
        
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
