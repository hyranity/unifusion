/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Gradedcomponent;
import Util.Errors;
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

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformEditAssignment", urlPatterns = {"/PerformEditAssignment"})
public class PerformEditAssignment extends HttpServlet {
    
    @PersistenceContext
    EntityManager em;
    
    @Resource
    private UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Utility objects
        Servlet servlet = new Servlet(request, response);
        Util.DB db = new Util.DB(em, utx);

        // Get class code and assignment id
        String classId = servlet.getQueryStr("code");
        String id = servlet.getQueryStr("id");
        Models.Users user = Server.getUser(request, response);

        // Objects
        Models.Class classroom = new Models.Class();
        Models.Classparticipant cpa = new Models.Classparticipant();
        Models.Gradedcomponent assignment = new Models.Gradedcomponent();

        // Get data from field
        String title = servlet.getQueryStr("title");
        String details = servlet.getQueryStr("details");
        String date = servlet.getQueryStr("deadline");
        boolean isToShowMarksOnly = servlet.getQueryStr("isForMarksOnly") != null;
        double totalMarks = 0;

        // Validate null fields
        if ((title == null || details == null || date == null) || (title.trim().isEmpty() || details.trim().isEmpty() || date.trim().isEmpty())) {
            System.out.println("Edit Assignment has null fields");
            Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("EditAssignment?id=" + id + "&code=" + classId);
            return;
        }
        
        try {
            totalMarks = Double.parseDouble(servlet.getQueryStr("marks"));
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Couldn't parse total marks");
            servlet.toServlet("Dashboard");
            return;
        }
        
        try {
            // Get class participant
            cpa = (Models.Classparticipant) em.createNativeQuery("select cpa.* from classparticipant cpa, participant p where p.userid = ? and p.participantid = cpa.participantid and cpa.classid = ? and cpa.role='teacher'", Models.Classparticipant.class).setParameter(1, user.getUserid()).setParameter(2, classId).getSingleResult();
            classroom = cpa.getClassid();

            // Get assignment
            assignment = (Gradedcomponent) em.createNativeQuery("select * from gradedcomponent where componentid = ? and classid = ?", Models.Gradedcomponent.class).setParameter(1, id).setParameter(2, classId).getSingleResult();
            
        } catch (Exception ex) {
            System.out.println("No data found");
            servlet.toServlet("Dashboard");
            return;
        }

        // Update the assignment
        assignment.setTitle(title);
        assignment.setDeadline(new DateTime(date).toDate());
        assignment.setTotalmarks(totalMarks);
        assignment.setIstoshowmarksonly(isToShowMarksOnly);
        assignment.setDetails(details);

        // Update in db
        db.update(assignment);

        // Redirect
        servlet.toServlet("AssignmentDetails?id=" + id + "&code=" + classId);
        
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
