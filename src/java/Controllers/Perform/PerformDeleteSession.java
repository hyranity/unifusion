/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Attendance;
import Models.Session;
import Util.DB;
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

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformDeleteSession", urlPatterns = {"/PerformDeleteSession"})
public class PerformDeleteSession extends HttpServlet {

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
        Models.Classparticipant classparticipant = new Models.Classparticipant();
        DB db = new DB(em, utx);

        // Get class code
        String classid = servlet.getQueryStr("id");
        String sessionId = servlet.getQueryStr("code");

        // Fetch from db
        try {
            // Get classroom WITH authorization
            classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa,participant p where c.classid = ? and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role = 'teacher'", Models.Class.class).setParameter(1, classid).setParameter(2, user.getUserid()).getSingleResult();
            classparticipant = (Models.Classparticipant) em.createNativeQuery("select cpa.* from class c, classparticipant cpa,participant p where c.classid = ? and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role = 'teacher'", Models.Classparticipant.class).setParameter(1, classid).setParameter(2, user.getUserid()).getSingleResult();

            // Get session (no need authorization as it's done above)
            session = (Session) em.createNativeQuery("select * from session where sessionid = ?", Models.Session.class).setParameter(1, sessionId).getSingleResult();

        } catch (Exception ex) {
            // Error means no result, redirect to classroom page
            System.out.println(ex.getMessage());
            servlet.toServlet("Class?id=" + classid);
            return;
        }

        // Perform deletion on attendees
        for (Attendance attendance : session.getAttendanceCollection()) {
            db.delete(attendance);
        }

        // Fetch updated session
        session = (Session) em.createNativeQuery("select * from session where sessionid = ?", Models.Session.class).setParameter(1, sessionId).getSingleResult();

        // Perform deletion on session
        db.delete(session);

        servlet.toServlet("Sessions?id=" + classid);
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
