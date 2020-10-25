/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Attendance;
import Models.Session;
import Util.DB;
import Util.Quick;
import Util.Server;
import Util.Servlet;
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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author mast3
 */
@WebServlet(name = "AttendanceList", urlPatterns = {"/AttendanceList"})
public class AttendanceList extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Objects
        Session session = new Session();
        Servlet servlet = new Servlet(request, response);
        Models.Users user = Server.getUser(request, response);
        Models.Class classroom = new Models.Class();
        Models.Classparticipant cpa = new Models.Classparticipant();
        DB db = new DB(em, utx);

        // Get class id and session id
        String classId = servlet.getQueryStr("id");
        String code = servlet.getQueryStr("code");

        // Fetch from db
        try {
            // Get class
            classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa,participant p where c.classid = ? and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role='teacher'", Models.Class.class).setParameter(1, classId).setParameter(2, user.getUserid()).getSingleResult();

            // Get current user class participant
            cpa = (Models.Classparticipant) em.createNativeQuery("select cpa.* from class c, classparticipant cpa, participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role='teacher'", Models.Classparticipant.class).setParameter(1, classId).setParameter(2, user.getUserid()).getSingleResult();

            // Get session
            session = (Session) em.createNativeQuery("select * from session where sessionid = ? and classid = ?", Models.Session.class).setParameter(1, code).setParameter(2, classId).getSingleResult();

        } catch (Exception ex) {
            // Error means no result, redirect to classroom page
            servlet.toServlet("Class?id=" + classId);
            return;
        }

        // Attendance UI
        String attendanceUI = "";

        for (Attendance attendance : session.getAttendanceCollection()) {

            // Print status
            String status = attendance.getStatus() == null ? "ABSENT" : attendance.getStatus();

            // Print only if absent
            String absentClass = status.equalsIgnoreCase("absent") ? "absent" : "";
            
            // Print role
            String role = attendance.getClassparticipantid().getRole().equalsIgnoreCase("teacher") ? "TUTOR" : "MEMBER";
            String tutorClass = attendance.getClassparticipantid().getRole().equalsIgnoreCase("teacher") ? "tutor" : "";

            // Print each option
            String absentOption = status.equalsIgnoreCase("absent") ? "selected" : "";
            String presentOption = status.equalsIgnoreCase("present") ? "selected" : "";
            String lateOption = status.equalsIgnoreCase("late") ? "selected" : "";

            // Print time attended
            DateTimeFormatter time = DateTimeFormat.forPattern("h'.'mm a");
            String timeAttended = status.equalsIgnoreCase("absent") ? "-" : new DateTime(attendance.getDateattended()).toString(time);

            // Print the actual attendance UI
            attendanceUI += "<div class='member " + absentClass + " " + tutorClass + "'>\n"
                    + "            <a class='info'>" + role + "</a>\n"
                    + "            <div class='buttons'>\n"
                    + "              <select class='dropdown' onchange='editAttendance(\"" + attendance.getAttendanceid() + "\")'>\n"
                    + "                <option value='present' " + presentOption + ">Present</option>\n"
                    + "                <option value='late' " + lateOption + ">Late</option>\n"
                    + "                <option value='absent' " + absentOption + ">Absent</option>\n"
                    + "              </select>\n"
                    + "              <a class='dropdownLabel'>v</a>\n"
                    + "              <a class='save-button' id='save-button_" + attendance.getAttendanceid() + "' href='PerformEditAttendance?id=" + attendance.getAttendanceid() + "&status=present'>></a>\n"
                    + "            </div>\n"
                    + "            <img class='icon' src='" + Quick.getIcon(attendance.getClassparticipantid().getParticipantid().getUserid().getImageurl()) + "'>\n"
                    + "            <div class='details'>\n"
                    + "              <div class='top-details'>\n"
                    + "                <a class='id'>002</a>\n"
                    + "                <a class='name'>" + attendance.getClassparticipantid().getParticipantid().getUserid().getName() + "</a>\n"
                    + "              </div>\n"
                    + "              <div class='time-details'>\n"
                    + "                <a class='time-label'>" + status.toUpperCase() + "</a>\n"
                    + "                <a class='time-checked'>" + timeAttended + "</a>\n"
                    + "              </div>\n"
                    + "            </div>\n"
                    + "          </div>";
        }

        // Get date
        DateTimeFormatter dateFmt = DateTimeFormat.forPattern("d MMM YYYY");
        String date = new DateTime(session.getStarttime()).toString(dateFmt);

        // Get time range
        DateTimeFormatter rangeFmt = DateTimeFormat.forPattern("h'.'mm a");
        String range = new DateTime(session.getStarttime()).toString(rangeFmt) + " - " + new DateTime(session.getEndtime()).toString(rangeFmt);

        // Get total counts
        int total = em.createNativeQuery("select * from attendance where sessionid = ?").setParameter(1, code).getResultList().size();
        int present = em.createNativeQuery("select * from attendance where sessionid = ? and status='present'").setParameter(1, code).getResultList().size();
        int late = em.createNativeQuery("select * from attendance where sessionid = ? and status='late'").setParameter(1, code).getResultList().size();
        int absent = em.createNativeQuery("select * from attendance where sessionid = ? and status='absent'").setParameter(1, code).getResultList().size();

        // Put in JSP
        servlet.putInJsp("subheading", classroom.getClassid() + " - " + classroom.getClasstitle() + " (Class)");
        servlet.putInJsp("icon", Quick.getIcon(classroom.getIconurl()));
        servlet.putInJsp("attendanceUI", attendanceUI);
        servlet.putInJsp("classroom", classroom);
        servlet.putInJsp("session", session);
        servlet.putInJsp("total", total);
        servlet.putInJsp("present", present);
        servlet.putInJsp("late", late);
        servlet.putInJsp("absent", absent);
        servlet.putInJsp("range", range);
        servlet.putInJsp("date", date);

        // Redirect
        servlet.servletToJsp("attendanceList.jsp");

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
