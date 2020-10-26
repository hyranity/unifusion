/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Session;
import Util.Quick;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
@WebServlet(name = "Sessions", urlPatterns = {"/Sessions"})
public class Sessions extends HttpServlet {

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

        // Get class code
        String classid = servlet.getQueryStr("id");

        // Fetch from db
        try {
            classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa,participant p where c.classid = ? and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ?", Models.Class.class).setParameter(1, classid).setParameter(2, user.getUserid()).getSingleResult();
            // Get current user class participant
            cpa = (Models.Classparticipant) em.createNativeQuery("select cpa.* from class c, classparticipant cpa, participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ?", Models.Classparticipant.class).setParameter(1, classid).setParameter(2, user.getUserid()).getSingleResult();

        } catch (Exception ex) {
            // Error means no result, redirect to classroom page
            servlet.toServlet("Class?id=" + classid);
        }

        String sessionUI = "";

        for (Session session : classroom.getSessionCollection()) {
            boolean isPast = false;
            String status = "id='upcoming'";
            String countdown = "";

            // If end time already passed, it is in the past
            if (new DateTime(session.getEndtime()).isBefore(DateTime.now())) {
                status = "id='past'";
                isPast = true;
            }

            if (isPast) {
                countdown = Quick.timeSince(session.getStarttime());
            } else {
                countdown = Quick.timeTo(session.getStarttime());
            }

            DateTimeFormatter date = DateTimeFormat.forPattern("MMM d',' h:mm a");

            // Show session ID  button  only if creator
            String sessId = "";

            if (cpa.getRole().equalsIgnoreCase("teacher")) {
                sessId += "<a class='sessionId'>" + session.getSessionid() + "</a>";
            }

            sessionUI += "<div class='session' " + status + " onclick='window.location.href=\"SessionDetails?id=" + classid + "&code=" + session.getSessionid() + "\"'>\n"
                    + "            <a class='time'>" + countdown + "</a>\n"
                    + "            <img class='tutorIcon' src='" + Quick.getIcon(session.getCreatorid().getParticipantid().getUserid().getImageurl()) + "'>\n"
                    + "            <div class='text'>\n"
                    + sessId
                    + "              <a class='message'>" + new DateTime(session.getStarttime()).toString(date) + "</a>\n"
                    + "              <a class='tutor'>" + session.getCreatorid().getParticipantid().getUserid().getName() + "</a>\n"
                    + "            </div>\n"
                    + "          </div>";

           
        }

        // Get institution to display "add venue" button
        String addVenueBt = "";
        try {
            // Find the class' institution
            institution = (Models.Institution) em.createNativeQuery("select i.* from institution i, programme p, course c, class cl where cl.classid = ? and cl.coursecode = c.coursecode and c.programmecode = p.programmecode and p.institutioncode = i.institutioncode", Models.Institution.class).setParameter(1, classid).getSingleResult();

            // Add the button
            addVenueBt = "";
        } catch (NoResultException e) {
            // Not in an institution, no need to add the button
            System.out.println("Not in an institution");

        }

        // Show "create session" to only teachers
        String addSessionBt = "";
        if (cpa.getRole().equalsIgnoreCase("teacher")) {
            addSessionBt = "<a href='AddSession?id=" + classid + "' id='create-button' class='button'>Create a Session</a>";
        }

        // Put in jsp
        servlet.putInJsp("subheading", classroom.getClassid() + " - " + classroom.getClasstitle() + " (Class)");
        servlet.putInJsp("id", classid);
        servlet.putInJsp("addSessionBt", addSessionBt);
        servlet.putInJsp("icon", Quick.getIcon(classroom.getIconurl()));
        servlet.putInJsp("sessionUI", sessionUI);

        // Redirect
        servlet.servletToJsp("sessions.jsp");
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
