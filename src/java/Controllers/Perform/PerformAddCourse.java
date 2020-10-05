/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Util.*;
import Models.*;
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

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformAddCourse", urlPatterns = {"/PerformAddCourse"})
public class PerformAddCourse extends HttpServlet {

   @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Utility objects
        Servlet servlet = new Servlet(request, response);
        DB db = new DB(em, utx);
        
        // Obtain field data
        String courseCode = servlet.getQueryStr("courseCode");
        String courseName  = servlet.getQueryStr("courseName");
        String programmeCode  = servlet.getQueryStr("programmeCode");
        String semesterCode  = servlet.getQueryStr("semesterCode");
        String description  = servlet.getQueryStr("description");
        boolean hasProgramme = servlet.getQueryStr("hasProgramme") != null;
        boolean hasSemester = servlet.getQueryStr("hasSemester") != null;
        
        // Set course object
        Course course = new Course();
        course.setCoursecode(courseCode);
        course.setDescription(description);
        course.setTitle(courseName);
        
        // If a programme is specified
        if(hasProgramme){ 
            // Load programme
            // Check whether user participates in the programme 
            // Check permission level
        }
        
        // If a semester is specified
        // Must be under a programme to work
        if(hasSemester){
            // Load semester
             // Check whether user participates in the programme 
            // Check permission level
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
