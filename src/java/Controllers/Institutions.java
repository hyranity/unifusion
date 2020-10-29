/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Institution;
import Models.Participant;
import Models.Programme;
import Models.Users;
import Util.DB;
import Util.Quick;
import Util.Server;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
@WebServlet(name = "Institution", urlPatterns = {"/Institution"})
public class Institutions extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Redirect
        Util.Servlet servlet = new Util.Servlet(request, response);
        DB db = new DB(em, utx);

        // Get currentUser
        Users currentUser = Server.getUser(request, response);
        Participant self = new Participant();

        // Get course data
        String institutionCode = servlet.getQueryStr("id");
        Institution institution = null;
        
        try {
            institution = (Institution) em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ?", Institution.class).setParameter(1, institutionCode).setParameter(2, currentUser.getUserid()).getSingleResult();
            self = (Participant) em.createNativeQuery("select p.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ?", Participant.class).setParameter(1, institutionCode).setParameter(2, currentUser.getUserid()).getSingleResult();

        } catch (NoResultException ex) {
            servlet.toServlet("Dashboard");
            return;
        }

        if (institution == null) {
            // Programme not found
            System.out.println("INSTITUTION NOT FOUND");
            servlet.toServlet("Dashboard");
        } else {
            // Programme is found

            // Get list of tutors
            ArrayList<Users> tutorList = db.getList(Users.class, em.createNativeQuery("select u.* from institutionparticipant ipa, institution i, users u, participant p where i.institutioncode = ? and ipa.role = 'teacher' and u.userid = p.userid and p.participantid = ipa.participantid and i.institutioncode = ipa.institutioncode", Models.Users.class).setParameter(1, institutionCode));

            // Get list of students
            ArrayList<Users> studentList = db.getList(Users.class, em.createNativeQuery("select u.* from institutionparticipant ipa, institution i, users u, participant p where i.institutioncode = ? and ipa.role = 'student' and u.userid = p.userid and p.participantid = ipa.participantid and i.institutioncode = ipa.institutioncode", Models.Users.class).setParameter(1, institutionCode));

            // Get creator
            Users creator = db.getList(Users.class, em.createNativeQuery("select u.* from institutionparticipant ipa, institution i, users u, participant p where i.institutioncode = ? and ipa.role = 'teacher' and u.userid = p.userid and p.participantid = ipa.participantid and ipa.iscreator = true and i.institutioncode = ipa.institutioncode", Models.Users.class).setParameter(1, institutionCode)).get(0);

            if(self.getEducatorrole().equalsIgnoreCase("institutionAdmin")){
                String authCode = "<a id='authorisationCode'><span>Staff authorisation code: </span>" + institution.getAuthcode() + "</a>";
                servlet.putInJsp("authCode", authCode);
            }

            // Displaying Members box
            String youBox = "", moreStr = "", editBt = "<a class='more' href='#'>Click to view more ></a>";
            int moreCount = tutorList.size() + studentList.size();

            // if current user == creator
            if (currentUser.getUserid().equals(creator.getUserid())) {
                editBt = "<a class='more' href='InstitutionDetails?institution=" + institutionCode + "'>Click to edit ></a>";
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
            for (Models.Announcement announcement : institution.getAnnouncementCollection()) {
                // Get top 3 only
                if (counter > 2) {
                    break;
                }

                // Build UI
                announcementUI += "<div class='announcement' onclick='window.location.href=\"AnnouncementDetails?type=institution&id=" + institution.getInstitutioncode() + "&code=" + announcement.getAnnouncementid() + "\"'>\n"
                        + "                  <a class='time'>" + Quick.timeSince(announcement.getDateannounced()) + "</a>\n"
                        + "                  <img class='icon' src='" + Quick.getIcon(announcement.getPosterid().getUserid().getImageurl()) + "'>\n"
                        + "                  <div class='text'>\n"
                        + "                    <a class='message'><span>" + announcement.getTitle() + "</span></a>\n"
                        + "                    <a class='item'>" + announcement.getPosterid().getUserid().getName() + "</a>\n"
                        + "                  </div>\n"
                        + "                </div>";
            }

            // Get  announcement count
            Query weeklyQuery = em.createNativeQuery("select count(*) from announcement where institutioncode = ? and current_date = date(dateannounced)").setParameter(1, institution.getInstitutioncode());
            servlet.putInJsp("todayAnnounced", weeklyQuery.getSingleResult());
            servlet.putInJsp("announcementCount", institution.getAnnouncementCollection().size());

            // Put data in JSP
            servlet.putInJsp("institution", institution);
            servlet.putInJsp("tutorList", tutorList);
            servlet.putInJsp("studentList", studentList);
            servlet.putInJsp("youBox", youBox);
            servlet.putInJsp("moreStr", moreStr);
            servlet.putInJsp("creator", creator);
            servlet.putInJsp("editBt", editBt);
            servlet.putInJsp("announcementUI", announcementUI);
        }

        servlet.servletToJsp("institution.jsp");
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
