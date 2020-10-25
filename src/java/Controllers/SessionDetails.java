/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Attendance;
import Models.Session;
import Util.Quick;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author mast3
 */
@WebServlet(name = "SessionDetails", urlPatterns = {"/SessionDetails"})
public class SessionDetails extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Objects
        Servlet servlet = new Servlet(request, response);
        Models.Users user = Server.getUser(request, response);
        Models.Class classroom = new Models.Class();
        Models.Institution institution = new Models.Institution();
        Models.Classparticipant cpa = new Models.Classparticipant();
        Models.Session session = new Models.Session();

        // Get class id and session id
        String classId = servlet.getQueryStr("id");
        String code = servlet.getQueryStr("code");

        // Venue URL
        String venueUrl = "";

        // Fetch from db
        try {
            // Get class
            classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa,participant p where c.classid = ? and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ?", Models.Class.class).setParameter(1, classId).setParameter(2, user.getUserid()).getSingleResult();

            // Get current user class participant
            cpa = (Models.Classparticipant) em.createNativeQuery("select cpa.* from class c, classparticipant cpa, participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ?", Models.Classparticipant.class).setParameter(1, classId).setParameter(2, user.getUserid()).getSingleResult();

            // Get session
            session = (Session) em.createNativeQuery("select * from session where sessionid = ? and classid = ?", Models.Session.class).setParameter(1, code).setParameter(2, classId).getSingleResult();

            // Find the class' institution
            Query institutionQuery = em.createNativeQuery("select i.* from institution i, programme p, course c, class cl where cl.classid = ? and cl.coursecode = c.coursecode and c.programmecode = p.programmecode and p.institutioncode = i.institutioncode", Models.Institution.class).setParameter(1, classId);
            if (institutionQuery.getResultList().size() > 0) {
                institution = (Models.Institution) institutionQuery.getSingleResult();

                // Get venue URL if venueID is supplied in session
                if (session.getVenueid() != null) {
                    venueUrl = "VenueDetails?id=" + institution.getInstitutioncode() + "&code=" + session.getVenueid().getVenueid();
                }

            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            // Error means no result, redirect to classroom page
            servlet.toServlet("Class?id=" + classId);
            return;
        }

        // Show ID to teacher only
        if (cpa.getRole().equalsIgnoreCase("teacher")) {
            servlet.putInJsp("id", session.getSessionid());
        }

        // Get date
        DateTimeFormatter dateFmt = DateTimeFormat.forPattern("d MMM YYYY");
        String date = new DateTime(session.getStarttime()).toString(dateFmt);

        // Get time range
        DateTimeFormatter rangeFmt = DateTimeFormat.forPattern("h'.'mm a");
        String range = new DateTime(session.getStarttime()).toString(rangeFmt) + " - " + new DateTime(session.getEndtime()).toString(rangeFmt);

        // Get venue
        // Get tempVenue if VenueID is null
        String venue = session.getVenueid() == null ? session.getTempvenuename() : session.getVenueid().getTitle();

        // Get attendance
        Query attendanceQuery = em.createNativeQuery("select * from attendance where classparticipantid = ? and sessionid = ? and (status='late' or status='present')", Models.Attendance.class).setParameter(1, cpa.getClassparticipantid()).setParameter(2, session.getSessionid());

        String attendedStatus = "";

        // If attended
        if (attendanceQuery.getResultList().size() > 0) {
            Attendance attendance = (Models.Attendance) attendanceQuery.getSingleResult();
            servlet.putInJsp("attendance", attendance);
            servlet.putInJsp("attendedStr", "Marked");
            

            attendedStatus = "<div class='detail' id='checked'>\n"
                    + "                                <a class='label'>ON TIME</a>\n"
                    + "                                <a class='value'>" + new DateTime(attendance.getDateattended()).toString(rangeFmt)+ "</a>\n"
                    + "                            </div>";
            
            servlet.putInJsp("attendedStatus", attendedStatus);

        } else {
            servlet.putInJsp("attendedStr", "Unmarked");
            servlet.putInJsp("markBt", "<a id='button' href='TakeAttendance'>Mark your attendance</a>");
        }

        // Put in JSP
        servlet.putInJsp("subheading", classroom.getClassid() + " - " + classroom.getClasstitle() + " (Class)");
        servlet.putInJsp("classroom", classroom);
        servlet.putInJsp("venue", venue);
        servlet.putInJsp("venueUrl", venueUrl);
        servlet.putInJsp("range", range);
         servlet.putInJsp("date", date);
        servlet.putInJsp("session", session);
        servlet.putInJsp("teacher", session.getCreatorid().getParticipantid().getUserid());
        servlet.putInJsp("teacherIcon", Quick.getIcon(session.getCreatorid().getParticipantid().getUserid().getImageurl()));
        servlet.putInJsp("icon", Quick.getIcon(classroom.getIconurl()));
        servlet.putInJsp("hideIfTeacher", cpa.getRole().equalsIgnoreCase("teacher") ? "style=\"display:none;\"" : "");
        servlet.putInJsp("hideIfStudent", cpa.getRole().equalsIgnoreCase("student") ? "style=\"display:none;\"" : "");

        // Redirect
        servlet.servletToJsp("sessionDetails.jsp");
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
