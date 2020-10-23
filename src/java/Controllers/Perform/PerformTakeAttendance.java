/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Attendance;
import Models.Classparticipant;
import Models.Session;
import Util.Errors;
import Util.Quick;
import Util.Server;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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
import org.joda.time.DateTime;

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformTakeAttendance", urlPatterns = {"/PerformTakeAttendance"})
public class PerformTakeAttendance extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Important objects
        Util.Servlet servlet = new Util.Servlet(request, response);
        Util.DB db = new Util.DB(em, utx);
        Models.Users user = Server.getUser(request, response);
        Classparticipant cpa = new Classparticipant();

        // Get attendance code
        String code = servlet.getQueryStr("code");

        // Find db for session  with this code
        Models.Session session = db.getSingleResult("sessionid", code, Models.Session.class);

        if (session == null) {
            // Incorrect session code
            System.out.println("Attendance code is incorrect");
            Errors.respondSimple(request.getSession(), "Attendance code is incorrect.");
            servlet.toServlet("TakeAttendance");
        } else {
            // Correct session code

            // Check to ensure that is within class start and end time
            DateTime startTime = new DateTime(session.getStarttime());
            DateTime endTime = new DateTime(session.getEndtime());
            DateTime now = new DateTime();

            // If before class starts
            if (now.isBefore(startTime)) {
                Errors.respondSimple(request.getSession(), "Class has not yet begun.");
                servlet.toServlet("TakeAttendance");
            } // If after class ended 
            else if (now.isAfter(endTime)) {
                Errors.respondSimple(request.getSession(), "Class has already ended.");
                servlet.toServlet("TakeAttendance");
                return;
            } // Within class time
            else if (now.isAfter(startTime) && now.isBefore(endTime)) {

                // Find the class participant linked to this class and this user
                Query cpaQuery = em.createNativeQuery("select cpa.* from classparticipant cpa, class c, participant p, session s where s.sessionid = ? and s.classid = c.classid and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ?", Classparticipant.class);
                cpaQuery.setParameter(1, code);
                cpaQuery.setParameter(2, user.getUserid());

                try {
                    cpa = (Classparticipant) cpaQuery.getSingleResult();
                } catch (Exception ex) {
                    // Classparticipant not found
                    System.out.println("Class participant not found");
                }

                // Check if attendance already marked
                Query attendanceQuery = em.createNativeQuery("select * from attendance where sessionid = ? and classparticipantid = ?", Classparticipant.class);
                attendanceQuery.setParameter(1, code);
                attendanceQuery.setParameter(2, cpa.getClassparticipantid());

                // If marked
                if (attendanceQuery.getResultList().size() > 0) {
                    Errors.respondSimple(request.getSession(), "Attendance already marked");
                    servlet.toServlet("TakeAttendance");
                }

                // Create new attendance object
                Attendance attendance = new Attendance();
                attendance.setAttendanceid(Quick.generateID(em, utx, Attendance.class, "attendanceid"));
                attendance.setClassparticipantid(cpa);
                attendance.setDateattended(DateTime.now().toDate());
                attendance.setSessionid(session);

                // Put in db
                db.insert(attendance);

                System.out.println("Successfully marked attendance");
                servlet.servletToJsp("takeAttendance.jsp");
            } else {

            }

            servlet.servletToJsp("takeAttendance.jsp");
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
