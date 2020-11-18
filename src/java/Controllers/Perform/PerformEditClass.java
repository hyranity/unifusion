/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Util.DB;
import Util.Errors;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "PerformEditClass", urlPatterns = {"/PerformEditClass"})
public class PerformEditClass extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Set utility objects
        Servlet servlet = new Servlet(request, response);
        DB db = new DB(em, utx);

        // Get form data
        String classTitle = servlet.getQueryStr("classTitle");
        String classCode = servlet.getQueryStr("classCode");  // WILL NEVER BE EDITED
        String description = servlet.getQueryStr("description");
        String classType = servlet.getQueryStr("classType");
        //String bannerURL = servlet.getQueryStr("bannerURL");
        //String colourTheme = servlet.getQueryStr("colourTheme");
        String courseCode = servlet.getQueryStr("courseCode");  // WILL NEVER BE EDITED
        String iconURL = servlet.getQueryStr("iconURL");
        boolean hasCourse = servlet.getQueryStr("hasCourse") != null;  // WILL NEVER BE EDITED
        boolean isPublic = servlet.getQueryStr("isPublic") != null;

        // Validation goes here
        // Validate important fields
        if (classTitle == null || classCode == null || description == null || classType == null || classTitle.trim().isEmpty() || classCode.trim().isEmpty() || description.trim().isEmpty() || classType.trim().isEmpty()) {
            // Has null data
            System.out.println("Null fields!");
            Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("ClassDetails?class=" + classCode);
            return;
        }

        // Get class from DB
        // Query only allows creator to edit class
        Query query = em.createNativeQuery("select * from class c, classparticipant cpa, participant p where c.classID = ? and cpa.classid = c.classID and cpa.participantid = p.participantid and p.userid = ? and cpa.isCreator='true'", Models.Class.class).setParameter(1, classCode).setParameter(2, Server.getUser(request, response).getUserid());
        ArrayList<Models.Class> result = db.getList(Models.Class.class, query);

        if (result.size() == 0) {
            // If no results
            System.out.println("Null class");
            servlet.toServlet("Dashboard");
            return;
        } else {
            // If a class is found

            // Get class from DB
            Models.Class classroom = db.getSingleResult("classid", classCode, Models.Class.class);

            // Update class data
            classroom.setClasstitle(classTitle);
            classroom.setDescription(description);
            //classroom.setBannerurl(bannerURL);
            classroom.setClasstype(classType);
            classroom.setIconurl(iconURL);
            classroom.setIspublic(isPublic);
            //classroom.setColourtheme(colourTheme);

            // Dont edit the course part
//            if (hasCourse) {
//                // If has a course
//
//                // Get course from db
//                Models.Course course = db.getSingleResult("courseid", courseCode, Models.Course.class);
//                classroom.setCoursecode(course);
//            } else {
//                // Remove any course
//                classroom.setCoursecode(null);
//            }

            // Update in DB
            db.update(classroom);

            // Redirect
            servlet.toServlet("Class?id=" + classCode);
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
