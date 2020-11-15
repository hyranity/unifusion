/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Util.*;
import Models.*;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformJoinCourse", urlPatterns = {"/PerformJoinCourse"})
public class PerformJoinCourse extends HttpServlet {

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

        // Current user
        Users user = Server.getUser(request, response);

        // Obtain form data
        String courseCode = servlet.getQueryStr("courseCode");

        // Fetch from db
        Models.Course course = db.getSingleResult("coursecode", courseCode, Models.Course.class);

        // If no class is found
        if (course == null) {
            Errors.respondSimple(request.getSession(), "That course does not exist");
            System.out.println("No course is found");
            servlet.toServlet("JoinCourse");
            return;
        }

        // If this person already joined
        Query joinQuery = em.createNativeQuery("select p.* from participant p, courseparticipant cpa where p.userid = ? and p.participantid = cpa.participantid and cpa.coursecode = ?", Models.Participant.class).setParameter(1, user.getUserid()).setParameter(2, course.getCoursecode());
        if (joinQuery.getResultList().size() > 0) {
            System.out.println("Already joined this course");
            Errors.respondSimple(request.getSession(), "You've already joined!");
            servlet.toServlet("JoinCourse");
            return;
        }

        // Is this course associated with a programme?
        if (course.getProgrammecode() != null) {

            // Is this user participating in the programme? He must be in the programme first before joining the course
            Query query = em.createNativeQuery("select p.* from participant p, programmeparticipant ppa where p.userid = ? and p.participantid = ppa.participantid and ppa.programmecode = ?", Models.Participant.class).setParameter(1, user.getUserid()).setParameter(2, course.getProgrammecode().getProgrammecode());

            // If yes
            if (query.getResultList().size() > 0) {
                // Use the existing participant object and add him into the class
                Participant participant = (Participant) query.getSingleResult();

                // Create new course participant
                Courseparticipant coursePart = new Courseparticipant();
                coursePart.setIscreator(true);
                coursePart.setRole("student");
                coursePart.setStatus("active");
                coursePart.setCoursecode(course);
                coursePart.setCourseparticipantid(Quick.generateID(em, utx, Courseparticipant.class, "courseparticipantid"));
                coursePart.setParticipantid(participant); // Reuse existing participant

                //Insert into db
                db.insert(coursePart);

                System.out.println("Course successfully joined");
                servlet.toServlet("Course?id=" + course.getCoursecode());

            } // If no
            else {
                // Reject
                Errors.respondSimple(request.getSession(), "That course does not exist");
                System.out.println("Not participating in the programme");
                servlet.toServlet("JoinCourse");
                return;
            }

        } // not associated with a programme
        else {
            // Since not associated with a programme, create new participant object
            Participant participant = new Participant();
            participant.setDateadded(Calendar.getInstance().getTime());
            participant.setEducatorrole("student");
            participant.setStatus("active");
            participant.setParticipantid(Quick.generateID(em, utx, Participant.class, "Participantid"));
            participant.setUserid(user);

            db.insert(participant);

            // Create new course participant
            Courseparticipant coursePart = new Courseparticipant();
            coursePart.setIscreator(true);
            coursePart.setRole("student");
            coursePart.setStatus("active");
            coursePart.setCoursecode(course);
            coursePart.setCourseparticipantid(Quick.generateID(em, utx, Courseparticipant.class, "Courseparticipantid"));
            coursePart.setParticipantid(participant); // Use the newly created participant

            db.insert(coursePart);

            // Add the user to every class within that course
//            for (Models.Class classroom : course.getClassCollection()) {
//                // Create new class participant for each class
//                Classparticipant classPart = new Classparticipant();
//                classPart.setIscreator(true);
//                classPart.setRole("student");
//                classPart.setStatus("active");
//                classPart.setClassid(classroom);
//                classPart.setClassparticipantid(Quick.generateID(em, utx, Classparticipant.class, "Classparticipantid"));
//                classPart.setParticipantid(participant); // Use the newly created participant
//                db.insert(classPart);
//            }
            System.out.println("Course successfully joined");
            servlet.toServlet("Course?id=" + course.getCoursecode());
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
