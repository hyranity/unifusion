/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Classparticipant;
import Models.Gradedcomponent;
import Models.Users;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Util.*;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

/**
 *
 * @author mast3
 */
@WebServlet(name = "Class", urlPatterns = {"/Class"})
public class Class extends HttpServlet {

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

        // Get currentUser
        Users currentUser = Server.getUser(request, response);

        // Get class data
        String classId = servlet.getQueryStr("id");

        // Get class
        Query query = em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ?", Models.Class.class).setParameter(1, classId).setParameter(2, currentUser.getUserid());

        // If no results
        if (query.getResultList().isEmpty()) {
            servlet.toServlet("Dashboard");
            return;
        }

        Models.Class classroom = (Models.Class) query.getSingleResult();

        if (classroom == null) {
            // Classroom not found
            System.out.println("CLASS NOT FOUND");
            servlet.toServlet("Dashboard");
        } else {
            // Classroom is found

            // Get list of tutors
            ArrayList<Users> tutorList = db.getList(Users.class, em.createNativeQuery("select  u.* from classparticipant cpa, class c, users u, participant p where c.classid = ? and cpa.role = 'teacher' and u.userid = p.userid and p.participantid = cpa.participantid and c.classid = cpa.classid", Models.Users.class).setParameter(1, classId));

            // Get list of students
            ArrayList<Users> studentList = db.getList(Users.class, em.createNativeQuery("select  u.* from classparticipant cpa, class c, users u, participant p where c.classid = ? and cpa.role = 'student' and u.userid = p.userid and p.participantid = cpa.participantid and c.classid = cpa.classid", Models.Users.class).setParameter(1, classId));

            // Get creator
            Users creator = db.getList(Users.class, em.createNativeQuery("select u.* from classparticipant cpa, class c, users u, participant p where c.classid = ? and cpa.role = 'teacher' and u.userid = p.userid and p.participantid = cpa.participantid and cpa.iscreator = true  and c.classid = cpa.classid", Models.Users.class).setParameter(1, classId)).get(0);

            // Displaying Members box
            String youBox = "", moreStr = "", editBt = "";
            int moreCount = tutorList.size() + studentList.size();

            // if current user != creator
            if (currentUser.getUserid().equals(creator.getUserid())) {
                editBt = "<a class='more' href='ClassDetails?class=" + classId + "'>Click to edit ></a>";
                creator.setName("You");
                moreCount -= 1;
            } else {
                youBox = "<div class='box' id='you'>"
                        + "<img class='icon' src='" + Quick.getIcon(((Models.Users) request.getSession().getAttribute("user")).getImageurl()) + "'>"
                        + "<a class='name'>You</a>"
                        + "</div>";
                moreCount -= 2;
            }

            // Displaying "and xx more..."
            if (moreCount > 0) {
                moreStr = "<a id='noOfMembers'>and " + moreCount + " more...</a>";
            }

            // Get announcements
            String announcementUI = "";
            int counter = 0;
            for (Models.Announcement announcement : classroom.getAnnouncementCollection()) {
                // Get top 3 only
                counter++;
                if (counter > 3) {
                    break;
                }

                // Build UI
                announcementUI += "<div class='announcement' onclick='window.location.href=\"AnnouncementDetails?type=class&id=" + classroom.getClassid() + "&code=" + announcement.getAnnouncementid() + "\"'>\n"
                        + "                  <a class='time'>" + Quick.timeSince(announcement.getDateannounced()) + "</a>\n"
                        + "                  <img class='icon' src='" + Quick.getIcon(announcement.getPosterid().getUserid().getImageurl()) + "'>\n"
                        + "                  <div class='text'>\n"
                        + "                    <a class='message'><span>" + announcement.getTitle() + "</span></a>\n"
                        + "                    <a class='item'>" + announcement.getPosterid().getUserid().getName() + "</a>\n"
                        + "                  </div>\n"
                        + "                </div>";
            }
            
            // Get classwork
            String classworkUI = "";
            List<Gradedcomponent> classworkList =  em.createNativeQuery("select * from gradedcomponent where classid = ?  order by deadline asc fetch first 3 rows only", Gradedcomponent.class).setParameter(1, classroom.getClassid()).getResultList();
            for (Models.Gradedcomponent classwork : classworkList) {
                
                String href = "AssignmentDetails?id=" + classwork.getComponentid() +"&code=" + classroom.getClassid() +"";

                // Build UI
                classworkUI += "<div class='assignment' onclick=\"window.location.href='" + href +"'\">\n" +
"                              <a class='due'>1d</a>\n" +
"                              <a class='message'>" + classwork.getTitle() +"</a>\n" +
"                              <a class='slug'></a>\n" +
"                            </div>";
            }

            // Get today announcement count
            Query todayAnnouncements = em.createNativeQuery("select count(*) from announcement where classid = ? and current_date = date(dateannounced)").setParameter(1, classroom.getClassid());

            // Get today classwork
            Query todayClasswork = em.createNativeQuery("select count(*) from gradedcomponent where classid = ? and current_date = date(deadline)").setParameter(1, classroom.getClassid());

            // Get  session count
            Query todaySessions = em.createNativeQuery("select count(*) from session where classid = ? and current_date = date(starttime)").setParameter(1, classroom.getClassid());

            // Put data in JSP
            servlet.putInJsp("todayClasswork", todayClasswork.getSingleResult());
            servlet.putInJsp("classworkUI", classworkUI);
            servlet.putInJsp("todaySessions", todaySessions.getSingleResult());
            servlet.putInJsp("totalSessions", classroom.getSessionCollection().size());
            servlet.putInJsp("todayAnnounced", todayAnnouncements.getSingleResult());
            servlet.putInJsp("announcementCount", classroom.getAnnouncementCollection().size());
            servlet.putInJsp("classroom", classroom);
            servlet.putInJsp("tutorList", tutorList);
            servlet.putInJsp("studentList", studentList);
            servlet.putInJsp("youBox", youBox);
            servlet.putInJsp("moreStr", moreStr);
            servlet.putInJsp("creator", creator);
            servlet.putInJsp("editBt", editBt);
            servlet.putInJsp("announcementUI", announcementUI);
        }

        // Redirect
        servlet.servletToJsp("class.jsp");
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
