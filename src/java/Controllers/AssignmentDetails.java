/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Gradedcomponent;
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
@WebServlet(name = "AssignmentDetails", urlPatterns = {"/AssignmentDetails"})
public class AssignmentDetails extends HttpServlet {

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

        // Get deadline
        DateTimeFormatter dateFmt = DateTimeFormat.forPattern("d MMM YYYY");
        String deadline = new DateTime(assignment.getDeadline()).toString(dateFmt);

        // Buttons to show teacher only
        String editBt = "";
        String viewSubmissionsBt = "";
        String deleteBt = "";
        if (cpa.getRole().equalsIgnoreCase("teacher")) {
            editBt = "<a href='EditButton?id=" + assignment.getComponentid() + "&code=" + assignment.getClassid().getClassid() + "' id='edit-button' class='button'>Edit</a>";
            viewSubmissionsBt = "<a href='ViewSubmissions?id=" + assignment.getComponentid() + "&code=" + assignment.getClassid().getClassid() + "' id='viewSubmissions-button' class='button'>View Submissions</a>";
            deleteBt = "<a href='DeleteAssignment?id=" + assignment.getComponentid() + "&code=" + assignment.getClassid().getClassid() + "' id='remove-button' class='button'>Delete</a>";
        }

        // Buttons to show student only
        String viewMySubmission = "";
        String submitBt = "";
        if (cpa.getRole().equalsIgnoreCase("student")) {
            viewMySubmission = "<a href='SubmitAssignment?id=" + assignment.getComponentid() + "&code=" + assignment.getClassid().getClassid() + "' id='makeSubmission-button' class='button'>Make Submission</a>";
            submitBt = "<a href='MySubmission?id=" + assignment.getComponentid() + "&code=" + assignment.getClassid().getClassid() + "' id='viewMySubmission-button' class='button'>View My Submission</a>";
        }
        
        // Display attachments
         String fileStr = "";

            // Get each file
            if (assignment.getFileurl() != null) {
                String[] files = assignment.getFileurl().split("\\|");

                for (String file : files) {
                    String name = file.substring(file.indexOf("\\") + 1, file.length());

                    fileStr += "<div class='attachment'>\n"
                            + "            <img class='icon' src='https://icons.iconarchive.com/icons/pelfusion/flat-file-type/512/doc-icon.png'>\n"
                            + "            <a href='AssignmentFile?id=" + id + "&code=" + classId + "&file=" + file + "' class='name'>" + name + "</a>\n"
                            + "          </div>";
                }

                servlet.putInJsp("fileStr", fileStr);
            }

        // Put in JSP
        servlet.putInJsp("assignment", assignment);
        servlet.putInJsp("deadline", deadline);
        servlet.putInJsp("editBt", editBt);
        servlet.putInJsp("viewMySubmission", viewMySubmission);
        servlet.putInJsp("submitBt", submitBt);
        servlet.putInJsp("deleteBt", deleteBt);
        servlet.putInJsp("viewSubmissionsBt", viewSubmissionsBt);
        servlet.putInJsp("subheading", classroom.getClassid() + " - " + classroom.getClasstitle() + " (Class)");
        servlet.putInJsp("icon", Quick.getIcon(classroom.getIconurl()));

        // Redirect
        servlet.servletToJsp("assignmentDetails.jsp");

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
