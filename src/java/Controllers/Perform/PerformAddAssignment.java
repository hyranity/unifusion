/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Announcement;
import Models.Gradedcomponent;
import Util.Errors;
import Util.Quick;
import Util.Server;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
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
@WebServlet(name = "PerformAddAssignment", urlPatterns = {"/PerformAddAssignment"})
public class PerformAddAssignment extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Util objects
        Util.Servlet servlet = new Util.Servlet(request, response);
        Util.DB db = new Util.DB(em, utx);
        Models.Users user = Server.getUser(request, response);

        // Form fields
        String title = "";
        String details = "";
        String id = "";
        String deadlineDate = "";
        String deadlineTime = "";
        double marks = 0;
        boolean isForMarksOnly = false;

        try {
            // Get field data
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "title":
                            title = item.getString();
                            break;
                        case "details":
                            details = item.getString();
                            break;
                        case "id":
                            id = item.getString();
                            break;
                        case "deadlineDate":
                            deadlineDate = item.getString();
                            break;
                        case "deadlineTime":
                            deadlineTime = item.getString();
                            break;
                        case "isForMarksOnly":
                            isForMarksOnly = true;
                            break;
                        case "marks":
                            marks = Integer.parseInt(item.getString());
                            break;
                    }
                }
            }
            // Validation goes here
            if (title == null || details == null || deadlineDate == null || id == null || deadlineTime == null || deadlineTime.trim().isEmpty() || title.trim().isEmpty() || details.trim().isEmpty() || deadlineDate.trim().isEmpty() || id.trim().isEmpty()) {
                // Has null data
                System.out.println("Null fields!");
                Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
                servlet.toServlet("AddAssignment?id=" + id);
                return;
            }

            // Get class
            Models.Class classroom = new Models.Class();
            try {
                classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = ? and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role='teacher'", Models.Class.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
            } catch (Exception ex) {
                System.out.println("Cannot get classroom");
                servlet.toServlet("Dashboard");
                return;
            }

            // Getting deadlineTime
            int hour = Integer.parseInt(deadlineTime.split(":")[0]);
            int min = Integer.parseInt(deadlineTime.split(":")[1]);
            DateTime deadline = DateTime.parse(deadlineDate);
            deadline = deadline.withHourOfDay(hour).withMinuteOfHour(min);

            // Create new Assignment
            Gradedcomponent assignment = new Gradedcomponent();
            assignment.setClassid(classroom);
            assignment.setDeadline(deadline.toDate());
            assignment.setIssueddate(new DateTime().toDate());
            assignment.setTotalmarks(marks);
            assignment.setIstoshowmarksonly(isForMarksOnly);
            assignment.setDetails(details);
            assignment.setTitle(title);
            assignment.setComponentid(Quick.generateID(em, utx, Gradedcomponent.class, "componentid"));

            // File path for uploading
            String filePath = "/ScaffoldData/Class/" + id + "/Assignments";

            String fileUrl = Quick.uploadFile(filePath, items, request, servlet); // Use Quick's uploadFile() method

            // Check for error
            if (fileUrl != null && fileUrl.equalsIgnoreCase("error")) {
                servlet.toServlet("AddAssignment?id=" + id);
                return;
            }

            assignment.setFileurl(fileUrl);

            // Upload in db
            db.insert(assignment);

            // Redirect
            servlet.toServlet("Assignments?id=" + id);

        } catch (FileUploadException ex) {
            Logger.getLogger(PerformAddAssignment.class.getName()).log(Level.SEVERE, null, ex);
            Errors.respondSimple(request.getSession(), "File could not be uploaded");
            servlet.toServlet("AddAssignment?id=" + id);
            return;
        } catch (NumberFormatException ex) {
            Errors.respondSimple(request.getSession(), "Marks must be specified correctly.");
            servlet.toServlet("AddAssignment?id=" + id);
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
