/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Course;
import Models.Institution;
import Models.Programme;
import Util.Quick;
import Util.Server;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "Announcement", urlPatterns = {"/Announcement"})
public class Announcement extends HttpServlet {

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

        // Get query strings
        String type = servlet.getQueryStr("type");
        String id = servlet.getQueryStr("id");

        Query query;

        String announcementUI = "";

        try {
            if ("class".equalsIgnoreCase(type)) {
                // Get the class
                Models.Class classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role = 'teacher'", Models.Class.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Generate announcement UI
                for (Models.Announcement announcement : classroom.getAnnouncementCollection()) {
                    
                    String icon = Quick.getIcon(announcement.getPosterid().getUserid().getImageurl());
                    
                    announcementUI += "<div class='announcement' onclick='window.location.href=\"AnnouncementDetails?code=" + announcement.getAnnouncementid() + "&id=" + classroom.getClassid() + "&type=class\"'>\n"
                            + "            <a class='time'>" + Quick.timeSince(announcement.getDateannounced()) +"</a>\n"
                            + "            <img class='icon' src='" + icon + "'>\n"
                            + "            <div class='text'>\n"
                          + "              <a class='message'><span>" + announcement.getTitle() + "</span></a>\n"
                            + "              <a class='item'>" + announcement.getPosterid().getUserid().getName() + "</a>\n"
                            + "            </div>\n"
                            + "          </div>";
                }

                // Put into JSP
                servlet.putInJsp("subheading", classroom.getClassid() + " - " + classroom.getClasstitle() + " (Class)");
                servlet.putInJsp("icon", Quick.getIcon(classroom.getIconurl()));
                servlet.putInJsp("id", classroom.getClassid());
                servlet.putInJsp("type", "class");
                servlet.putInJsp("postURL", "PostAnnouncement?id="+id+"&type="+type);
                servlet.putInJsp("announcementUI", announcementUI);

            } else if ("course".equalsIgnoreCase(type)) {
                // Get the course
                Models.Course course = (Models.Course) em.createNativeQuery("select c.* from course c, courseparticipant cpa, participant p where c.coursecode = ? and cpa.coursecode = c.coursecode and cpa.participantid = p.participantid and p.userid = ? and cpa.role = 'teacher'", Course.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                  // Generate announcement UI
                for (Models.Announcement announcement : course.getAnnouncementCollection()) {
                    
                    String icon = Quick.getIcon(announcement.getPosterid().getUserid().getImageurl());
                    
                    announcementUI += "<div class='announcement' onclick='window.location.href=\"AnnouncementDetails?code=" + announcement.getAnnouncementid() + "&id=" + course.getCoursecode() + "&type=course\"'>\n"
                            + "            <a class='time'>" + Quick.timeSince(announcement.getDateannounced()) +"</a>\n"
                            + "            <img class='icon' src='" + icon + "'>\n"
                            + "            <div class='text'>\n"
                            + "              <a class='message'><span>" + announcement.getTitle() + "</span></a>\n"
                            + "              <a class='item'>" + announcement.getPosterid().getUserid().getName() + "</a>\n"
                            + "            </div>\n"
                            + "          </div>";
                }
                
                // Put into JSP
                servlet.putInJsp("subheading", course.getCoursecode() + " - " + course.getTitle() + " (Course)");
                servlet.putInJsp("icon", Quick.getIcon(course.getIconurl()));
                servlet.putInJsp("id", course.getCoursecode());
                servlet.putInJsp("type", "course");
                servlet.putInJsp("postURL", "PostAnnouncement?id="+id+"&type="+type);
                servlet.putInJsp("announcementUI", announcementUI);
            } else if ("programme".equalsIgnoreCase(type)) {
                // Get the course
                Models.Programme programme = (Models.Programme) em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ? and ppa.programmecode = pg.programmecode and ppa.participantid = p.participantid and p.userid = ? and ppa.role = 'teacher'", Programme.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                  // Generate announcement UI
                for (Models.Announcement announcement : programme.getAnnouncementCollection()) {
                    
                    String icon = Quick.getIcon(announcement.getPosterid().getUserid().getImageurl());
                    
                    announcementUI += "<div class='announcement' onclick='window.location.href=\"AnnouncementDetails?code=" + announcement.getAnnouncementid() + "&id=" + programme.getProgrammecode() + "&type=programme\"'>\n"
                            + "            <a class='time'>" + Quick.timeSince(announcement.getDateannounced()) +"</a>\n"
                            + "            <img class='icon' src='" + icon + "'>\n"
                            + "            <div class='text'>\n"
                          + "              <a class='message'><span>" + announcement.getTitle() + "</span></a>\n"
                            + "              <a class='item'>" + announcement.getPosterid().getUserid().getName() + "</a>\n"
                            + "            </div>\n"
                            + "          </div>";
                }
                
                // Put into JSP
                servlet.putInJsp("subheading", programme.getProgrammecode() + " - " + programme.getTitle() + " (Programme)");
                servlet.putInJsp("icon", Quick.getIcon(programme.getIconurl()));
                servlet.putInJsp("id", programme.getProgrammecode());
                servlet.putInJsp("type", "programme");
                servlet.putInJsp("postURL", "PostAnnouncement?id="+id+"&type="+type);
                servlet.putInJsp("announcementUI", announcementUI);
            } else if ("institution".equalsIgnoreCase(type)) {
                // Get the course
                Models.Institution institution = (Models.Institution) em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ? and ipa.role = 'teacher'", Institution.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                  // Generate announcement UI
                for (Models.Announcement announcement : institution.getAnnouncementCollection()) {
                    
                    String icon = Quick.getIcon(announcement.getPosterid().getUserid().getImageurl());
                    
                    announcementUI += "<div class='announcement' onclick='window.location.href=\"AnnouncementDetails?code=" + announcement.getAnnouncementid() + "&id=" + institution.getInstitutioncode() + "&type=institution\"'>\n"
                            + "            <a class='time'>" + Quick.timeSince(announcement.getDateannounced()) +"</a>\n"
                            + "            <img class='icon' src='" + icon + "'>\n"
                            + "            <div class='text'>\n"
                          + "              <a class='message'><span>" + announcement.getTitle() + "</span></a>\n"
                            + "              <a class='item'>" + announcement.getPosterid().getUserid().getName() + "</a>\n"
                            + "            </div>\n"
                            + "          </div>";
                }
                
                // Put into JSP
                servlet.putInJsp("subheading", institution.getInstitutioncode() + " - " + institution.getName() + " (Institution)");
                servlet.putInJsp("icon", Quick.getIcon(institution.getIconurl()));
                servlet.putInJsp("id", institution.getInstitutioncode());
                servlet.putInJsp("type", "institution");
                servlet.putInJsp("postURL", "PostAnnouncement?id="+id+"&type="+type);
                servlet.putInJsp("announcementUI", announcementUI);
            } else {
                // Incorrect type
                System.out.println("Type is incorrect");
                servlet.toServlet("Dashboard");
                return;
            }
        } catch (NoResultException e) {
            System.out.println("No data found");
            servlet.toServlet("Dashboard");
            return;
        } catch (Exception ex){
            servlet.toServlet("Home");
            return;
        }

        servlet.servletToJsp("announcements.jsp");
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
