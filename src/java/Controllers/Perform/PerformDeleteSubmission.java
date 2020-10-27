/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Gradedcomponent;
import Models.Submission;
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

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformDeleteSubmission", urlPatterns = {"/PerformDeleteSubmission"})
public class PerformDeleteSubmission extends HttpServlet {
    
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
        String submissionId = servlet.getQueryStr("submission");
        Models.Users user = Server.getUser(request, response);

        // Objects
        Models.Class classroom = new Models.Class();
        Models.Classparticipant cpa = new Models.Classparticipant();
        Models.Gradedcomponent assignment = new Models.Gradedcomponent();
        Submission submission = new Submission();
        
        try {
            // Get class participant
            cpa = (Models.Classparticipant) em.createNativeQuery("select cpa.* from classparticipant cpa, participant p where p.userid = ? and p.participantid = cpa.participantid and cpa.classid = ?", Models.Classparticipant.class).setParameter(1, user.getUserid()).setParameter(2, classId).getSingleResult();
            classroom = cpa.getClassid();

            // Get submission made by THIS USER with THIS SUBMISSION ID and THIS ASSIGNMENT ID
            submission = (Submission) em.createNativeQuery("select * from submission where componentid = ? and classparticipantid = ? and submissionid = ?", Submission.class).setParameter(1, id).setParameter(2, cpa.getClassparticipantid()).setParameter(3, submissionId).getSingleResult();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            servlet.toServlet("Dashboard");
            return;
        }

        // Delete the submission's uploaded files
        if (submission.getFileurl() != null) {
            String[] files = submission.getFileurl().split("\\|");
            
            for (String file : files) {
                String name = file.substring(file.indexOf("\\") + 1, file.length());
                
                Quick.deleteFile(file);
            }
        }

        // Delete it
        db.delete(submission);

        // Redirect
        servlet.toServlet("Assignments?id=" + classId);
        
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
