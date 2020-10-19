/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Course;
import Models.Institution;
import Models.Programme;
import Util.Errors;
import Util.Quick;
import Util.Server;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.nio.file.Files;
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
@WebServlet(name = "AnnouncementFile", urlPatterns = {"/AnnouncementFile"})
public class AnnouncementFile extends HttpServlet {

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
        
        // Announcement object
        Models.Announcement announcement = new Models.Announcement();

        // Get query strings
        String type = servlet.getQueryStr("type");
        String id = servlet.getQueryStr("id");
        String code = servlet.getQueryStr("code"); // announcement id

        System.out.println(code);
        
        Query query;
        
        try {
            if ("class".equalsIgnoreCase(type)) {
                // Get the class
                Models.Class classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ?", Models.Class.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get the announcement
                announcement = (Models.Announcement) em.createNativeQuery("select a.* from announcement a where a.classid = ? and a.announcementid = ?", Models.Announcement.class).setParameter(1, classroom.getClassid()).setParameter(2, code).getSingleResult();

            } else if ("course".equalsIgnoreCase(type)) {
                // Get the course
                Models.Course course = (Models.Course) em.createNativeQuery("select c.* from course c, courseparticipant cpa, participant p where c.coursecode = ? and cpa.coursecode = c.coursecode and cpa.participantid = p.participantid and p.userid = ?", Course.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get the announcement
                announcement = (Models.Announcement) em.createNativeQuery("select a.* from announcement a where a.coursecode = ? and a.announcementid = ?", Models.Announcement.class).setParameter(1, course.getCoursecode()).setParameter(2, code).getSingleResult();

            } else if ("programme".equalsIgnoreCase(type)) {
                // Get the programme
                Models.Programme programme = (Models.Programme) em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ? and ppa.programmecode = pg.programmecode and ppa.participantid = p.participantid and p.userid = ?", Programme.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get the announcement
                announcement = (Models.Announcement) em.createNativeQuery("select a.* from announcement a where a.programmecode = ? and a.announcementid = ?", Models.Announcement.class).setParameter(1, programme.getProgrammecode()).setParameter(2, code).getSingleResult();

            } else if ("institution".equalsIgnoreCase(type)) {
                // Get the course
                Models.Institution institution = (Models.Institution) em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ?", Institution.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get the announcement
                announcement = (Models.Announcement) em.createNativeQuery("select a.* from announcement a where a.institutioncode = ? and a.announcementid = ?", Models.Announcement.class).setParameter(1, institution.getInstitutioncode()).setParameter(2, code).getSingleResult();

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
        }
        
        String filename = servlet.getQueryStr("file");
        
        Quick.displayFile(filename, getServletContext(), request, response, servlet, "AnnouncementDetails?id=" + id + "&code=" + code + "&type=" + type);
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
