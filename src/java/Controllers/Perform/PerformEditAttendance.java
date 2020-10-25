/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Attendance;
import Models.Classparticipant;
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
@WebServlet(name = "PerformEditAttendance", urlPatterns = {"/PerformEditAttendance"})
public class PerformEditAttendance extends HttpServlet {

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
        Attendance attendance = new Attendance();
        DB db = new DB(em, utx);

        // Get session id
        String attendanceId = servlet.getQueryStr("id");
        String status = servlet.getQueryStr("status");

        try {
            cpa = (Classparticipant) em.createNativeQuery("select cpa.* from classparticipant cpa, attendance a where cpa.classparticipantid = a.classparticipantid and a.attendanceid = ?", Classparticipant.class).setParameter(1, attendanceId).getSingleResult();
        
            // Get the class
            classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa, attendance a where c.classid = cpa.classid and cpa.classparticipantid = a.classparticipantid and a.attendanceid = ?", Models.Class.class).setParameter(1, attendanceId).getSingleResult();

            // Validate user
            em.createNativeQuery("select cpa.* from classparticipant cpa, participant p where cpa.classid = ? and cpa.participantid = p.participantid and p.userid= ?").setParameter(1, classroom.getClassid()).setParameter(2, user.getUserid()).getSingleResult();
            
            // Get attendance
            attendance = (Attendance) em.createNativeQuery("select * from attendance where attendanceid = ?", Attendance.class).setParameter(1, attendanceId).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            // Error means no result, redirect to dashboard
            servlet.toServlet("Dashboard");
            return;
        }
        
        // Update the attendance status
        if(status.equalsIgnoreCase("late")){
            attendance.setStatus("late");
        } else if(status.equalsIgnoreCase("present")){
            attendance.setStatus("present");
        } else{
            attendance.setStatus("absent");
        }
        
        // Update in the db
        db.update(attendance);
        
        // Redirect
        servlet.toServlet("AttendanceList?id="+classroom.getClassid()+"&code="+ attendance.getSessionid().getSessionid());
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
