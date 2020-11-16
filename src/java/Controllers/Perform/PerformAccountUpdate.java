/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Users;
import Util.DB;
import Util.Errors;
import Util.Quick;
import Util.Server;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolationException;
import org.joda.time.DateTime;

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformAccountUpdate", urlPatterns = {"/PerformAccountUpdate"})
public class PerformAccountUpdate extends HttpServlet {
    
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
        try {
            response.setContentType("text/html;charset=UTF-8");
          
            Users user = Server.getUser(request, response);
            Util.DB db = new Util.DB(em, utx);
            
            //Update user
            user.setName(request.getParameter("name"));
            user.setAddress(request.getParameter("address"));
            user.setImageurl(request.getParameter("imageUrl"));
            
            //If user exists
            Users checkUser = db.getSingleResult("email", request.getParameter("email"), Users.class);
            if(checkUser != null && !checkUser.getUserid().equals(user.getUserid())){
                Errors.respondSimple(request.getSession(), "This email already exists on another account");
                response.sendRedirect("AccountDetails");
                return;
            }
            
            user.setEmail(request.getParameter("email"));
            
            
            if(request.getParameter("dateOfBirth").length() > 0)
                user.setDateofbirth(new DateTime(request.getParameter("dateOfBirth")).toDate());
            
            user.setName(request.getParameter("name"));
            
         
            
            new DB(em, utx).update(user);
            
            //Redirect back to same page
            response.sendRedirect("AccountDetails");
            return;
        } catch(ConstraintViolationException ex){
            System.out.println(ex.getConstraintViolations());
        }catch(Exception ex){
            Errors.respondSimple(request.getSession(), "Something went wrong");
            ex.printStackTrace();
        }
        response.sendRedirect("AccountDetails");
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
