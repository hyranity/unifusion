/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.*;
import Util.DB;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "Course", urlPatterns = {"/Course"})
public class Courses extends HttpServlet {

   @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
         // Declare variables
        Servlet servlet = new Servlet(request, response);
        DB db = new DB(em, utx);

        // Get class data
        String courseCode = servlet.getQueryStr("id");
        Course course  = db.getSingleResult("coursecode", courseCode, Course.class);
        

        if (course == null) {
            // Classroom not found
            System.out.println("COURSE NOT FOUND");
        } else {
            // Course is found
            System.out.println(course.getTitle());

            // Get list of tutors
            ArrayList<Users> tutorList = db.getList(Users.class, em.createNativeQuery("select u.* from courseparticipant cpa, course c, users u, participant p where c.coursecode = ? and cpa.role = 'teacher' and u.userid = p.userid and p.participantid = cpa.participantid", Models.Users.class).setParameter(1, courseCode));

            // Get list of students
            ArrayList<Users> studentList = db.getList(Users.class, em.createNativeQuery("select u.* from courseparticipant cpa, course c, users u, participant p where c.coursecode = ? and cpa.role = 'student' and u.userid = p.userid and p.participantid = cpa.participantid", Models.Users.class).setParameter(1, courseCode));

            // Get creator
            Users creator = db.getList(Users.class, em.createNativeQuery("select u.* from courseparticipant cpa, course c, users u, participant p where c.coursecode = ? and cpa.role = 'teacher' and u.userid = p.userid and p.participantid = cpa.participantid and cpa.iscreator = true", Models.Users.class).setParameter(1, courseCode)).get(0);

            // Get currentUser
            Users currentUser = Server.getUser(request, response);

            // Displaying Members box
            String youBox = "", moreStr = "", editBt = "<a class='more' href='#'>Click to view more ></a>";
            int moreCount = tutorList.size() + studentList.size();

            // if current user != creator
            if (currentUser.getUserid().equals(creator.getUserid())) {
                editBt = "<a class='more' href='CourseDetails?course=" + courseCode + "'>Click to edit ></a>";
                moreCount -= 1;
            } else {
                youBox = "<div class='box' id='you'>"
                        + "<img class='icon' src='" + ((Models.Users) request.getAttribute("currentUser")).getImageurl() + "'>"
                        + "<a class='name'>You</a>"
                        + "</div>";
                moreCount -= 2;
            }

            // Displaying "and xx more..."
            if (moreCount > 0) {
                moreStr = "<a id='noOfMembers'>and " + moreCount + " more...</a>";
            }

            // Put data in JSP
            servlet.putInJsp("course", course);
            servlet.putInJsp("tutorList", tutorList);
            servlet.putInJsp("studentList", studentList);
            servlet.putInJsp("youBox", youBox);
            servlet.putInJsp("moreStr", moreStr);
            servlet.putInJsp("creator", creator);
            servlet.putInJsp("editBt", editBt);
        }

        // Redirect
        servlet.servletToJsp("course.jsp");
        
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
