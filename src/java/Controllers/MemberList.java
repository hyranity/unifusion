/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Course;
import Models.Institution;
import Models.Participant;
import Models.Programme;
import Util.Quick;
import Util.Server;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.List;
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
@WebServlet(name = "MemberList", urlPatterns = {"/MemberList"})
public class MemberList extends HttpServlet {

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

        // Objects
        List<Participant> tutors;
        List<Participant> students;

        // Output string
        String tutorStr = "";
        String studentStr = "";

        // Get query strings
        String type = servlet.getQueryStr("type");
        String id = servlet.getQueryStr("id");

        Query query;

        try {
            if ("class".equalsIgnoreCase(type)) {
                // Get the class
                Models.Class classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ?", Models.Class.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                tutors = (List<Participant>) em.createNativeQuery("select p.* from class c, classparticipant cpa,  participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and cpa.role = 'teacher'", Models.Participant.class).setParameter(1, id).getResultList();
                students = (List<Participant>) em.createNativeQuery("select p.* from class c, classparticipant cpa,  participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid  and cpa.role = 'student'", Models.Participant.class).setParameter(1, id).getResultList();

                // Put into JSP
                servlet.putInJsp("subheading", classroom.getClassid() + " - " + classroom.getClasstitle() + " (Class)");
                servlet.putInJsp("icon", Quick.getIcon(classroom.getIconurl()));

            } else if ("course".equalsIgnoreCase(type)) {
                // Get the course
                Models.Course course = (Models.Course) em.createNativeQuery("select c.* from course c, courseparticipant cpa, participant p where c.coursecode = ? and cpa.coursecode = c.coursecode and cpa.participantid = p.participantid and p.userid = ?", Course.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                tutors = (List<Participant>) em.createNativeQuery("select p.* from course c, courseparticipant cpa, participant p where c.coursecode = ? and cpa.participantid = p.participantid  and cpa.coursecode = c.coursecode and cpa.role = 'teacher'", Models.Participant.class).setParameter(1, id).getResultList();
                students = (List<Participant>) em.createNativeQuery("select p.* from course c, courseparticipant cpa, participant p  where c.coursecode = ? and cpa.participantid = p.participantid and cpa.coursecode = c.coursecode and cpa.role = 'student'", Models.Participant.class).setParameter(1, id).getResultList();

                // Put into JSP
                servlet.putInJsp("subheading", course.getCoursecode() + " - " + course.getTitle() + " (Course)");
                servlet.putInJsp("icon", Quick.getIcon(course.getIconurl()));
            } else if ("programme".equalsIgnoreCase(type)) {
                // Get the course
                Models.Programme programme = (Models.Programme) em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ? and ppa.programmecode = pg.programmecode and ppa.participantid = p.participantid and p.userid = ?", Programme.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                tutors = (List<Participant>) em.createNativeQuery("select p.* from programme pg, programmeparticipant ppa, participant p  where pg.programmecode = ? and ppa.programmecode = pg.programmecode and p.participantid = ppa.participantid and ppa.role = 'teacher'", Models.Participant.class).setParameter(1, id).getResultList();
                students = (List<Participant>) em.createNativeQuery("select p.* from programme pg, programmeparticipant ppa, participant p  where pg.programmecode = ? and ppa.programmecode = pg.programmecode and p.participantid = ppa.participantid and ppa.role = 'student'", Models.Participant.class).setParameter(1, id).getResultList();

                // Put into JSP
                servlet.putInJsp("subheading", programme.getProgrammecode() + " - " + programme.getTitle() + " (Programme)");
                servlet.putInJsp("icon", Quick.getIcon(programme.getIconurl()));
            } else if ("institution".equalsIgnoreCase(type)) {
                // Get the course
                Models.Institution institution = (Models.Institution) em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ?", Institution.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                tutors = (List<Participant>) em.createNativeQuery("select p.* from institution i, institutionparticipant  ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and ipa.role = 'teacher'", Models.Participant.class).setParameter(1, id).getResultList();
                students = (List<Participant>) em.createNativeQuery("select p.* from institution i, institutionparticipant ipa, participant p  where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and ipa.role = 'student'", Models.Participant.class).setParameter(1, id).getResultList();

                // Put into JSP
                servlet.putInJsp("subheading", institution.getInstitutioncode() + " - " + institution.getName() + " (Institution)");
                servlet.putInJsp("icon", Quick.getIcon(institution.getIconurl()));
            } else {
                // Incorrect type
                System.out.println("Type is incorrect");
                return;
            }
        } catch (NoResultException e) {
            System.out.println("No data found");
            servlet.toServlet("Dashboard");
            return;
        }

        // Generate tutor list
        int counter = 0;

        for (Participant participant : tutors) {
            counter++;
            tutorStr += "   <div class='member' id='tutor' onclick=\"location.href='#';\">\n"
                    + "            <a class='info'>TUTOR</a>\n"
                    + "            <img class='icon' src='" + Quick.getIcon(participant.getUserid().getImageurl()) + "'>\n"
                    + "            <a class='id'>" + counter + "</a>\n"
                    + "            <a class='name'>" + participant.getUserid().getName() + "</a>\n"
                    + "          </div>";
        }

        // Generate student list
        for (Participant participant : students) {

            String memberDetails = !type.equalsIgnoreCase("institution") ? "onclick=\"location.href='MemberDetails?id=" + id + "&type=" + type + "&memberId=" + participant.getParticipantid() + "'\"" : "";

            counter++;
            studentStr += "  <div class='member' " + memberDetails + ">\n"
                    + "            <a class='info'>MEMBER</a>\n"
                    + "            <img class='icon' src='" + Quick.getIcon(participant.getUserid().getImageurl()) + "'>\n"
                    + "            <a class='id'>" + counter + "</a>\n"
                    + "            <a class='name'>" + participant.getUserid().getName() + "</a>\n"
                    + "          </div>";

        }

        // Put into JSP
        servlet.putInJsp("tutors", tutorStr);
        servlet.putInJsp("students", studentStr);

        servlet.servletToJsp("memberList.jsp");
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
