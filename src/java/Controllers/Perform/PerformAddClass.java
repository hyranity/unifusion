/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Util.DB;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import Models.*;
import Util.Errors;
import Util.Quick;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
@WebServlet(name = "PerformAddClass", urlPatterns = {"/PerformAddClass"})
public class PerformAddClass extends HttpServlet {

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

        // Utility objects
        Servlet servlet = new Servlet(request, response);
        Users user = Server.getUser(request, response);
        DB db = new DB(em, utx);

        // Objects to be utilized
        Participant participant = new Participant();
        Classparticipant classPart = new Classparticipant();
        Models.Class classroom = new Models.Class();

        // Get form data
        String className = servlet.getQueryStr("className");
        String classCode = servlet.getQueryStr("classCode");
        String description = servlet.getQueryStr("description");
        String classType = servlet.getQueryStr("classType");
        boolean hasCourse = servlet.getQueryStr("hasCourse") != null;
        boolean isPublic = servlet.getQueryStr("isPublic") != null;
        
        // Validation goes here
        if(className == null || classCode == null || description == null || classType == null  || className.trim().isEmpty() || classCode.trim().isEmpty() || description.trim().isEmpty() || classType.trim().isEmpty()){
           // Has null data
            System.out.println("Null fields!");
            Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("AddClass");
            return;
        }

        // Create classroom
        classroom.setClasstitle(className);
        classroom.setClassid(classCode);
        classroom.setIspublic(isPublic);
        classroom.setDescription(description);
        classroom.setClasstype(classType);

        // Check duplicate ID
        if (db.getSingleResult("classid", classroom.getClassid(), Models.Class.class) != null) {
            // There is a duplicate ID
            System.out.println("Duplicate class code!");
            Errors.respondSimple(request.getSession(), "Another class with this code already exists");
            servlet.toServlet("AddClass");
            return;
        }

        // If classroom is part of a course, and this course is part of a programme from an institution
        if (hasCourse) {

            // Check if course exists
            if (db.getSingleResult("coursecode", servlet.getQueryStr("courseCode"), Models.Course.class) == null) {
                // Course does not exist
                System.out.println("Course does not exist");
                Errors.respondSimple(request.getSession(), "The specified course does not exist.");
                servlet.toServlet("AddClass");
                return;
            }

            // Note: Participant is usually created when user joins a class/course/programme/institution OR creates a class/course/programme/institution for the FIRST time.
            // Check if educator is participating in the same course AND authorized; if so, use that same participant
            // Get the course in which this person is a teacher
            Query query = em.createNativeQuery("select c.* from course c, courseparticipant cpa, participant p, users u where c.COURSECODE = ? and cpa.COURSECODE = c.COURSECODE and cpa.PARTICIPANTID = p.PARTICIPANTID and p.USERID = ? and (p.educatorrole = 'classTeacher'  or p.educatorrole = 'courseLeader'  or p.educatorrole = 'programmeLeader' or p.educatorrole = 'institutionAdmin')", Models.Course.class);
            query.setParameter(1, servlet.getQueryStr("courseCode"));
            query.setParameter(2, user.getUserid());

            // Execute the query
            List<Models.Course> results = db.getList(Models.Course.class, query);

            // If no results
            if (results.size() == 0) {
                System.out.println("User does not have authority to add a class to this course");
            } // If course exists
            else {
                // Put this class into that course
                classroom.setCoursecode(results.get(0));

                // Get the existing participant by:
                // Getting the participant of the course in which this person is a teacher
                Query participantQuery = em.createNativeQuery("select p.* from course c, courseparticipant cpa, participant p, users u where c.COURSECODE = ? and cpa.COURSECODE = c.COURSECODE and cpa.PARTICIPANTID = p.PARTICIPANTID and p.USERID = ? and cpa.\"ROLE\" = 'teacher'", Models.Participant.class);
                participantQuery.setParameter(1, servlet.getQueryStr("courseCode"));
                participantQuery.setParameter(2, user.getUserid());

                // Execute the query
                List<Models.Participant> participantResults = db.getList(Models.Participant.class, participantQuery);

                // If no results (highly unlikely to happen after the previous filter)
                if (participantResults.size() == 0) {
                    System.out.println("Participant not in the course!");
                } // If participant is found 
                else {
                    // Use the existing participant
                    participant = participantResults.get(0);
                }

            }

            // Only check for that since a class without a course cannot be related to programme/institution
            // Else, deny access
            //db.getSingleResult("participantID", value, classType)
        } else {
            // This educator will only teach this class, create a new participant AND class participant

            // Participant
            participant.setDateadded(Calendar.getInstance().getTime());
            participant.setEducatorrole("classTeacher");
            participant.setStatus("active");
            participant.setParticipantid(Quick.generateID(em, utx, Participant.class, "Participantid"));
            participant.setUserid(user);
            db.insert(participant);

        }

        // Insert class
        db.insert(classroom);

        // Class participant
        classPart.setParticipantid(participant);
        classPart.setIscreator(true);
        classPart.setRole("teacher");
        classPart.setStatus("active");
        classPart.setClassid(classroom);
        classPart.setClassparticipantid(Quick.generateID(em, utx, Classparticipant.class, "Classparticipantid"));
        db.insert(classPart);

        // Successful
        servlet.toServlet("Class?id="+classCode );
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
