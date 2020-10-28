/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Gradedcomponent;
import Models.Submission;
import Util.Errors;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
@WebServlet(name = "PerformGradeSubmission", urlPatterns = {"/PerformGradeSubmission"})
public class PerformGradeSubmission extends HttpServlet {

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
        Models.Users user = Server.getUser(request, response);

        // Get field data
        String classId = servlet.getQueryStr("code");
        String id = servlet.getQueryStr("id");
        String submissionId = servlet.getQueryStr("submission");
        String marksStr = servlet.getQueryStr("marks");
        String remarks = servlet.getQueryStr("remarks"); // Optional
        double marks = 0;

        // Objects
        Models.Class classroom = new Models.Class();
        Models.Classparticipant cpa = new Models.Classparticipant();
        Models.Gradedcomponent assignment = new Models.Gradedcomponent();
        Submission submission = new Submission();

        // Marks cannot be null
        try {
            marks = Double.parseDouble(marksStr);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid marks");
            Errors.respondSimple(request.getSession(), "Marks must be specified correctly.");
            servlet.toServlet("GradeSubmission?id=" + id + "&code="+ classId + "&submission="+ submissionId);
            return;
        }

        try {
            // Get class participant
            cpa = (Models.Classparticipant) em.createNativeQuery("select cpa.* from classparticipant cpa, participant p where p.userid = ? and p.participantid = cpa.participantid and cpa.classid = ? and cpa.role='teacher'", Models.Classparticipant.class).setParameter(1, user.getUserid()).setParameter(2, classId).getSingleResult();
            classroom = cpa.getClassid();

            // Get assignment
            assignment = (Gradedcomponent) em.createNativeQuery("select * from gradedcomponent where componentid = ? and classid = ?", Models.Gradedcomponent.class).setParameter(1, id).setParameter(2, classId).getSingleResult();

        } catch (Exception ex) {
            ex.printStackTrace();
            servlet.toServlet("Dashboard");
            return;
        }

        // Get assignment
        try {
            // Get the submission 
            submission = (Submission) em.createNativeQuery("select * from submission where componentid = ? and submissionid= ?", Submission.class).setParameter(1, id).setParameter(2, submissionId).getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No submission found");
            servlet.toServlet("AssignmentDetails?id=" + id + "&code=" + classId);
            return;
        }

        // Update the marks
        submission.setMarks(marks);
        
        // Update the remarks
         submission.setRemarks(remarks);
        
        // Update in db
        db.update(submission);
        
        // Redirect
        servlet.toServlet("GradeSubmission?id=" + id + "&code="+ classId + "&submission="+ submissionId);
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
