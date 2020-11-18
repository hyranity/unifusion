/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Course;
import Util.DB;
import Util.Errors;
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

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformEditCourse", urlPatterns = {"/PerformEditCourse"})
public class PerformEditCourse extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Utility objects
        Servlet servlet = new Servlet(request, response);
        DB db = new DB(em, utx);

        // Obtain field data
        String courseCode = servlet.getQueryStr("courseCode"); // WILL NEVER BE EDITED
        String courseTitle = servlet.getQueryStr("courseTitle");
        String programmeCode = servlet.getQueryStr("programmeCode");  // WILL NEVER BE EDITED
        String semesterCode = servlet.getQueryStr("semesterCode");  // WILL NEVER BE EDITED
        String description = servlet.getQueryStr("description");
        String bannerURL = servlet.getQueryStr("bannerURL");
        String colourTheme = servlet.getQueryStr("colourTheme");
        String iconURL = servlet.getQueryStr("iconURL");
        boolean hasProgramme = servlet.getQueryStr("hasProgramme") != null;  // WILL NEVER BE EDITED
        boolean hasSemester = servlet.getQueryStr("hasSemester") != null;  // WILL NEVER BE EDITED
        boolean isPublic = servlet.getQueryStr("isPublic") != null;

        // Validations go here
        // Validate null fields (important fields only)
        if (courseCode == null || courseTitle == null || description == null || courseCode.trim().isEmpty() || courseTitle.trim().isEmpty() || description.trim().isEmpty()) {
            // Has null data
            System.out.println("Null fields!");
            Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("CourseDetails?course="+courseCode);
            return;
        }

        // Get course from DB
        Models.Course course = db.getSingleResult("coursecode", courseCode, Models.Course.class);

        // Get course from db where this person is the creator
        // Only allow creator to edit
        Query query = em.createNativeQuery("select c.* from course c, courseparticipant cpa, participant p where p.userid = ? and c.coursecode = ? and c.coursecode = cpa.coursecode and cpa.participantid = p.participantid and cpa.role='teacher'", Models.Course.class);
        query.setParameter(1, Server.getUser(request, response).getUserid());
        query.setParameter(2, courseCode);
        
        // If invalid course code
        if (query.getResultList().size() == 0) {
            System.out.println("Invalid course code");
            servlet.toServlet("Dashboard");
        } else {
            // Update course fields
            course.setTitle(courseTitle);
            course.setDescription(description);
            course.setIspublic(isPublic);
            course.setBannerurl(bannerURL);
            course.setColourtheme(colourTheme);
            course.setIconurl(iconURL);

            // Update db
            db.update(course);

            // Redirect
            servlet.toServlet("Course?id=" + courseCode);
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
