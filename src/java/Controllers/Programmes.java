/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.*;
import Util.DB;
import Util.Quick;
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
@WebServlet(name = "Programme", urlPatterns = {"/Programme"})
public class Programmes extends HttpServlet {

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

        // Get course data
        String programmeCode = servlet.getQueryStr("id");
        Query query = em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ? and ppa.programmecode = pg.programmecode and ppa.participantid = p.participantid and p.userid = ?", Programme.class).setParameter(1, programmeCode).setParameter(2, currentUser.getUserid());

        // If no results
        if (query.getResultList().isEmpty()) {
            servlet.toServlet("Dashboard");
            return;
        }

        Programme programme = (Programme) query.getSingleResult();

        if (programme == null) {
            // Programme not found
            System.out.println("PROGRAMME NOT FOUND");
            servlet.toServlet("Dashboard");
        } else {
            // Programme is found
            System.out.println(programme.getTitle());

            // Get list of tutors
            ArrayList<Users> tutorList = db.getList(Users.class, em.createNativeQuery("select u.* from programmeparticipant ppa, programme pg, users u, participant p where pg.programmecode = ? and ppa.role = 'teacher' and u.userid = p.userid and p.participantid = ppa.participantid and pg.programmecode = ppa.programmecode", Models.Users.class).setParameter(1, programmeCode));

            // Get list of students
            ArrayList<Users> studentList = db.getList(Users.class, em.createNativeQuery("select u.* from programmeparticipant ppa, programme pg, users u, participant p where pg.programmecode = ? and ppa.role = 'student' and u.userid = p.userid and p.participantid = ppa.participantid and pg.programmecode = ppa.programmecode", Models.Users.class).setParameter(1, programmeCode));

            // Get creator
            Users creator = db.getList(Users.class, em.createNativeQuery("select u.* from programmeparticipant ppa, programme pg, users u, participant p where pg.programmecode = ? and ppa.role = 'teacher' and u.userid = p.userid and p.participantid = ppa.participantid and ppa.iscreator = true and pg.programmecode = ppa.programmecode", Models.Users.class).setParameter(1, programmeCode)).get(0);

            // Displaying Members box
            String youBox = "", moreStr = "", editBt = "<a class='more' href='#'>Click to view more ></a>";
            int moreCount = tutorList.size() + studentList.size();

            // if current user == creator
            if (currentUser.getUserid().equals(creator.getUserid())) {
                editBt = "<a class='more' href='ProgrammeDetails?programme=" + programmeCode + "'>Click to edit ></a>";
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
            for (Models.Announcement announcement : programme.getAnnouncementCollection()) {
                // Get top 3 only
                if (counter > 2) {
                    break;
                }

                // Build UI
                announcementUI += "<div class='announcement'>\n"
                        + "                  <a class='time'>" + Quick.timeSince(announcement.getDateannounced()) +"</a>\n"
                        + "                  <img class='icon' src='" + Quick.getIcon(announcement.getPosterid().getUserid().getImageurl() )+ "'>\n"
                        + "                  <div class='text'>\n"
                        + "                    <a class='message'><span>" +  announcement.getTitle() +"</span></a>\n"
                        + "                    <a class='item'>"+ announcement.getPosterid().getUserid().getName() +"</a>\n"
                        + "                  </div>\n"
                        + "                </div>";
            }
            
            // Get  announcement count
            Query weeklyQuery = em.createNativeQuery("select count(*) from announcement where programmecode = ? and current_date = date(dateannounced)").setParameter(1, programme.getProgrammecode());
            servlet.putInJsp("todayAnnounced", weeklyQuery.getSingleResult());
            servlet.putInJsp("announcementCount", programme.getAnnouncementCollection().size());
            
            


            // Put data in JSP
            servlet.putInJsp("announcementUI", announcementUI);
            servlet.putInJsp("programme", programme);
            servlet.putInJsp("tutorList", tutorList);
            servlet.putInJsp("studentList", studentList);
            servlet.putInJsp("youBox", youBox);
            servlet.putInJsp("moreStr", moreStr);
            servlet.putInJsp("creator", creator);
            servlet.putInJsp("editBt", editBt);
        }

        servlet.servletToJsp("programme.jsp");
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
