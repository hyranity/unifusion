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
import Util.Quick;
import java.util.Calendar;
import java.util.Date;
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
        Servlet serve = new Servlet(request, response);
        Users user = Server.getUser(request, response);
        DB db = new DB(em, utx);

        // Create classroom
        Models.Class classroom = new Models.Class();
        classroom.setClasstitle(serve.getQueryStr("className"));
        classroom.setClassid(serve.getQueryStr("classCode"));
        classroom.setIspublic(true);
        classroom.setDescription(serve.getQueryStr("description"));
        classroom.setClasstype(serve.getQueryStr("classType"));

        // Check duplicate ID
        if (db.getSingleResult("classid", classroom.getClassid(), Models.Class.class) != null) {
            // There is a duplicate ID
            System.out.println("Duplicate class code!");
            serve.toServlet("AddClass");
            return;
        }

        db.insert(classroom);

        // Add user as participant as classroom creator
        // Models.Participant participant = new Models.Participant();
        // If classroom is part of a course, and this course is part of a programme from an institution
        if (serve.getQueryStr("courseCode") != null && serve.getQueryStr("courseCodeEnabled") == "true") {  

            // If course exists
            // Check if course exists
            if (db.getSingleResult("coursecode", serve.getQueryStr("courseCode"), Models.Course.class) == null) {
                // Course does not exist
                System.out.println("Course does not exist");
                serve.toServlet("AddClass");
                return;
            }

            // Check if educator  is an existing participant (only applicable for courses, programmes, and institutions with multiple educators)
            // Note: Participant is usually created when user joins a course/programme/institution.
            // Check if educator is participating in the same course AND authorized; if so, use that same participant
            // Check if educator is participating in the same programme AND authorized; if so, use that same participant
            // Check if educator is participating in the same institution AND authorized; if so, use that same participant
            // Else, deny access
            //db.getSingleResult("participantID", value, classType)
        } else {
            // This educator will only teach this class, create a new participant AND class participant

            // Participant
            Participant participant = new Participant();
            participant.setDateadded(Calendar.getInstance().getTime());
            participant.setEducatorrole("classTeacher");
            participant.setStatus("active");
            participant.setParticipantid(Quick.generateID(em, utx, Participant.class, "Participantid"));
            participant.setUserid(user);
            db.insert(participant);

            // Class participant
            Classparticipant classPart = new Classparticipant();
            classPart.setParticipantid(participant);
            classPart.setIscreator(true);
            classPart.setRole("teacher");
            classPart.setStatus("active");
            classPart.setClassid(classroom);
            classPart.setClassparticipantid(Quick.generateID(em, utx, Classparticipant.class, "Classparticipantid"));
            db.insert(classPart);

        }

        // Successful
        serve.toServlet("MyClasses");
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
