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
import java.util.Calendar;
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

        // Objects to be utilized
        Users user = Server.getUser(request, response);
        Course course = new Course();
        Programme programme = new Programme();
        Semester semester = new Semester();
        Courseparticipant cpa = new Courseparticipant();
        Participant participant = new Participant();

        // Obtain field data
        String courseCode = servlet.getQueryStr("courseCode");
        String courseName = servlet.getQueryStr("courseName");
        String programmeCode = servlet.getQueryStr("programmeCode");
        String semesterCode = servlet.getQueryStr("semesterCode");
        String description = servlet.getQueryStr("description");
        boolean hasProgramme = servlet.getQueryStr("hasProgramme") != null;
        boolean hasSemester = servlet.getQueryStr("hasSemester") != null;
        boolean isPublic = servlet.getQueryStr("isPublic") != null;

        // Validations go here
        // Set course object
        course.setCoursecode(courseCode);
        course.setDescription(description);
        course.setTitle(courseName);
        course.setIspublic(isPublic);

        // If a programme is specified
        if (hasProgramme) {
            // Load programme from DB

            // Check whether user participates in the programme 
            // Check permission level
            // if valid
            if (true) {
                // Set course into the programme
                //  course.setProgrammecode(programme);

                // Get the existing user from programme
                // If a semester is specified
                if (hasSemester) {
                    // Load semester from DB

                    // Check whether user participates in the programme 
                    // Check permission level
                    // If valid,
                    if (true) {

                        // Set semester into the course
                        //course.setSemestercode(semester);
                    } // If invalid semester, show error message
                    else {

                    }
                }
            } // If invalid programme, show error message
            else {

            }

        } // If no programme is specified
        else {

            // Create a new participant since it's not related to any programme/institution
            participant.setDateadded(Calendar.getInstance().getTime());
            participant.setEducatorrole("courseLeader");
            participant.setStatus("active");
            participant.setParticipantid(Quick.generateID(em, utx, Participant.class, "Participantid"));
            participant.setUserid(user);
            db.insert(participant);

        }

        // Create a course participant to link this creator to this course
        cpa.setParticipantid(participant);
        cpa.setIscreator(true);
        cpa.setRole("teacher");
        cpa.setStatus("active");
        cpa.setCoursecode(course);
        cpa.setCourseparticipantid(Quick.generateID(em, utx, Courseparticipant.class, "Courseparticipantid"));

        // insert into database 
        db.insert(course);
        db.insert(cpa);

        System.out.println("Successfully added a course!");

        servlet.toServlet("AddCourse");

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