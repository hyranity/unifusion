/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.*;
import Util.DB;
import Util.Quick;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
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
@WebServlet(name = "PerformJoinClass", urlPatterns = {"/PerformJoinClass"})
public class PerformJoinClass extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        boolean canJoin = false;

        // Utility objects
        Servlet servlet = new Servlet(request, response);
        DB db = new DB(em, utx);

        // Current user
        Users user = Server.getUser(request, response);

        // Obtain form data
        String classId = servlet.getQueryStr("classCode");

        // Fetch from db
        Models.Class classroom = db.getSingleResult("classid", classId, Models.Class.class);

        // If no class is found
        if (classroom == null) {
            System.out.println("No class is found");
            servlet.toServlet("JoinClass");
            return;
        }

        // Is this class associated with a course?
        if (classroom.getCoursecode() != null) {

            // Is this user participating in the course? He must be in the course first before joining the class
            Query query = em.createNativeQuery("select p.* from participant p, courseparticipant cpa where p.userid = ? and p.participantid = cpa.participantid and cpa.courseid = ?").setParameter(1, user.getUserid()).setParameter(2, classroom.getCoursecode().getCoursecode());

            // If yes
            if (query.getResultList().size() > 0) {
                // Use the existing participant object and add him into the class
                Participant participant = (Participant) query.getSingleResult();

                // Create new class participant
                Classparticipant classPart = new Classparticipant();
                classPart.setIscreator(true);
                classPart.setRole("student");
                classPart.setStatus("active");
                classPart.setClassid(classroom);
                classPart.setClassparticipantid(Quick.generateID(em, utx, Classparticipant.class, "Classparticipantid"));
                classPart.setParticipantid(participant); // Reuse existing participant

                //Insert into db
                db.insert(classPart);
            } // If no
            else {
                // Reject
                System.out.println("Not participating in the course");
                servlet.toServlet("JoinClass");
                return;
            }

        } // not associated with a course
        else {
            // Since not associated with a course, create new participant object
            Participant participant = new Participant();
            participant.setDateadded(Calendar.getInstance().getTime());
            participant.setEducatorrole("student");
            participant.setStatus("active");
            participant.setParticipantid(Quick.generateID(em, utx, Participant.class, "Participantid"));
            participant.setUserid(user);

            db.insert(participant);

            // Create new class participant
            Classparticipant classPart = new Classparticipant();
            classPart.setIscreator(true);
            classPart.setRole("student");
            classPart.setStatus("active");
            classPart.setClassid(classroom);
            classPart.setClassparticipantid(Quick.generateID(em, utx, Classparticipant.class, "Classparticipantid"));
            classPart.setParticipantid(participant); // Use the newly created participant

            db.insert(classPart);

            System.out.println("Class successfully joined");
            servlet.toServlet("Class?id=" + classroom.getClassid());
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
