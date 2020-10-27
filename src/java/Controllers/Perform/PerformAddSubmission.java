/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Gradedcomponent;
import Models.Submission;
import Util.Errors;
import Util.Quick;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformAddSubmission", urlPatterns = {"/PerformAddSubmission"})
public class PerformAddSubmission extends HttpServlet {

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

        // Objects
        Models.Class classroom = new Models.Class();
        Models.Classparticipant cpa = new Models.Classparticipant();
        Models.Gradedcomponent assignment = new Models.Gradedcomponent();
        Submission submission = new Submission();

        String classId = "";
        String id = "";
        String comment = "";

        try {
            // Get field data
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "id":
                            id = item.getString();
                            break;
                        case "code":
                            classId = item.getString();
                            break;
                        case "comment":
                            comment = item.getString();
                            break;
                        case "deadline":
                            comment = item.getString();
                            break;
                    }
                }
            }
            

            try {
                // Get class participant
                cpa = (Models.Classparticipant) em.createNativeQuery("select cpa.* from classparticipant cpa, participant p where p.userid = ? and p.participantid = cpa.participantid and cpa.classid = ?", Models.Classparticipant.class).setParameter(1, user.getUserid()).setParameter(2, classId).getSingleResult();
                classroom = cpa.getClassid();

                // Get assignment
                assignment = (Gradedcomponent) em.createNativeQuery("select * from gradedcomponent where componentid = ? and classid = ?", Models.Gradedcomponent.class).setParameter(1, id).setParameter(2, classId).getSingleResult();

            } catch (Exception ex) {
                ex.printStackTrace();
                servlet.toServlet("Dashboard");
                return;
            }

            // Has this user submitted ?
            try {
                submission = (Submission) em.createNativeQuery("select s.* from submission s, gradedcomponent g where g.componentid = s.componentid and g.componentid = ? and s.classparticipantid = ?", Submission.class).setParameter(1, id).setParameter(2, cpa.getClassparticipantid()).getSingleResult();
                System.out.println("Assignment already submitted");

                // Since already submitted, redirect back
                servlet.toServlet("AssignmentDetails?id=" + id + "&code=" + classId);
            } catch (NoResultException ex) {
                // No error
            }

            // Create new submission
            submission.setSubmissionid(Quick.generateID(em, utx, Submission.class, "submissionid"));
            submission.setComment(comment);
            submission.setMarks(null); // Since not marked yet
            submission.setDatesubmitted(new DateTime().toDate());
            submission.setClassparticipantid(cpa);
            submission.setComponentid(assignment);

            // Upload attachments
            // File path for uploading
            String filePath = "/ScaffoldData/Class/" + classId + "/Assignments/Submissions";

            String fileUrl = Quick.uploadFile(filePath, items, request, servlet); // Use Quick's uploadFile() method

            // Check for error
            if (fileUrl != null && fileUrl.equalsIgnoreCase("error")) {
                servlet.toServlet("AddSubmission?id=" + id + "&code=" + classId);
                return;
            }

            submission.setFileurl(fileUrl);
            
            // Insert into db
            db.insert(submission);
            
            // Redirect
            servlet.toServlet("SubmissionDetails?id=" + id + "&code=" + classId);
            
        } catch (FileUploadException ex) {
            Logger.getLogger(PerformAddSubmission.class.getName()).log(Level.SEVERE, null, ex);
            Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("AddSubmission?id=" + id + "&code=" + classId);
            return;
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
