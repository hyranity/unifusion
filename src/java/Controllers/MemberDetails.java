/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.*;
import Util.Quick;
import Util.Server;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "MemberDetails", urlPatterns = {"/MemberDetails"})
public class MemberDetails extends HttpServlet {

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
        Classparticipant cpa = new Classparticipant();
        Courseparticipant coursepa = new Courseparticipant();
        Programmeparticipant ppa = new Programmeparticipant();
        Institutionparticipant ipa = new Institutionparticipant();

        // Output string
        String tutorStr = "";
        String studentStr = "";

        // Get query strings
        String type = servlet.getQueryStr("type");
        String id = servlet.getQueryStr("id");
        String memberId = servlet.getQueryStr("memberId");

        Query query;

        try {
            if ("class".equalsIgnoreCase(type)) {
                // Get the class
                Models.Class classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ?", Models.Class.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Verify this person is a teacher
                em.createNativeQuery("select cpa.* from class c, classparticipant cpa,  participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and cpa.role = 'teacher' and p.userid=?", Models.Classparticipant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get member
                cpa = (Classparticipant) em.createNativeQuery("select cpa.* from class c, classparticipant cpa,  participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid  and p.participantid = ?", Models.Classparticipant.class).setParameter(1, id).setParameter(2, memberId).getSingleResult();

                // Put into JSP
                servlet.putInJsp("subheading", classroom.getClassid() + " - " + classroom.getClasstitle() + " (Class)");
                servlet.putInJsp("icon", Quick.getIcon(classroom.getIconurl()));
                servlet.putInJsp("userIcon", Quick.getIcon(cpa.getParticipantid().getUserid().getImageurl()));
                servlet.putInJsp("member", cpa);
                servlet.putInJsp("member", cpa);

            } else if ("course".equalsIgnoreCase(type)) {
                // Get the course
                Models.Course course = (Models.Course) em.createNativeQuery("select c.* from course c, courseparticipant cpa, participant p where c.coursecode = ? and cpa.coursecode = c.coursecode and cpa.participantid = p.participantid and p.userid = ?", Course.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Verify this person is a teacher
                em.createNativeQuery("select cpa.* from course c, courseparticipant cpa, participant p where c.coursecode = ? and cpa.participantid = p.participantid  and cpa.coursecode = c.coursecode and cpa.role = 'teacher' and p.userid = ?", Models.Courseparticipant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get member
                coursepa = (Courseparticipant) em.createNativeQuery("select cpa.* from course c, courseparticipant cpa, participant p  where c.coursecode = ? and cpa.participantid = p.participantid and cpa.coursecode = c.coursecode and p.participantid = ?", Models.Courseparticipant.class).setParameter(1, id).setParameter(2, memberId).getSingleResult();

                // Put into JSP
                servlet.putInJsp("subheading", course.getCoursecode() + " - " + course.getTitle() + " (Course)");
                servlet.putInJsp("userIcon", Quick.getIcon(coursepa.getParticipantid().getUserid().getImageurl()));
                servlet.putInJsp("icon", Quick.getIcon(course.getIconurl()));
                servlet.putInJsp("member", coursepa);
            } else if ("programme".equalsIgnoreCase(type)) {
                // Get the programme
                Models.Programme programme = (Models.Programme) em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ? and ppa.programmecode = pg.programmecode and ppa.participantid = p.participantid and p.userid = ?", Programme.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Verify this person is a teacher
                em.createNativeQuery("select ppa.* from programme pg, programmeparticipant ppa, participant p  where pg.programmecode = ? and ppa.programmecode = pg.programmecode and p.participantid = ppa.participantid and ppa.role = 'teacher'  and p.userid = ?", Models.Programmeparticipant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get member
                ppa = (Programmeparticipant) em.createNativeQuery("select ppa.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ? and ppa.programmecode = pg.programmecode and p.participantid = ppa.participantid and p.participantid = ?", Models.Programmeparticipant.class).setParameter(1, id).setParameter(2, memberId).getSingleResult();

                // Put into JSP
                servlet.putInJsp("subheading", programme.getProgrammecode() + " - " + programme.getTitle() + " (Programme)");
                servlet.putInJsp("userIcon", Quick.getIcon(ppa.getParticipantid().getUserid().getImageurl()));
                servlet.putInJsp("icon", Quick.getIcon(programme.getIconurl()));
                servlet.putInJsp("member", ppa);
            } else if ("institution".equalsIgnoreCase(type)) {
                // Get the institution
                Models.Institution institution = (Models.Institution) em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ?", Institution.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Verify this person is a teacher
                em.createNativeQuery("select ipa.* from institution i, institutionparticipant  ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and ipa.role = 'teacher' and p.userid = ?", Models.Institutionparticipant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get member
                ipa = (Institutionparticipant) em.createNativeQuery("select ipa.* from institution i, institutionparticipant ipa, participant p  where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.participantid = ?", Models.Institutionparticipant.class).setParameter(1, id).setParameter(2, memberId).getSingleResult();

                // Put into JSP
                servlet.putInJsp("subheading", institution.getInstitutioncode() + " - " + institution.getName() + " (Institution)");
                servlet.putInJsp("userIcon", Quick.getIcon(ipa.getParticipantid().getUserid().getImageurl()));
                servlet.putInJsp("member", ipa);
                servlet.putInJsp("icon", Quick.getIcon(institution.getIconurl()));
                
                // Hide save button since institutionparticipant does not have cgpa/grade fields
                servlet.putInJsp("hideIfInstitution", "style='display:none;'");
            } else {
                // Incorrect type
                System.out.println("Type is incorrect");
                return;
            }
        } catch (NoResultException e) {
            System.out.println("No data found");
            servlet.toServlet("MemberList?id=" + id + "&type=" + type);
            return;
        }

        // Only show CGPA to programme, institution
        // Only show grade to classes and courses
        // Only show submissions to classes
        if (type.equalsIgnoreCase("programme") && ppa.getRole().equalsIgnoreCase("student")) {
            String cgpa = "<div class='detail'>\n"
                    + "              <a class='label'>CGPA</a>\n"
                    + "              <a class='value'>" + (ppa.getCgpa() == null ? "N/A" : ppa.getCgpa()) + "</a>\n"
                    + "            </div>";

            String cgpaForm = "<a class='label' style='margin-left: -180px;'>CGPA</a>\n"
                    + "            <input class='textbox' type='text' name='cgpa' placeholder='eg. 4.0000' value='" + (ppa.getCgpa() == null ? "" : ppa.getCgpa()) + "' required>";

            servlet.putInJsp("cgpaForm", cgpaForm);
            servlet.putInJsp("cgpa", cgpa);
        }
        if (type.equalsIgnoreCase("course") && coursepa.getRole().equalsIgnoreCase("student")) {
            String cgpa = "<div class='detail'>\n"
                    + "              <a class='label'>CGPA</a>\n"
                    + "              <a class='value'>" + (coursepa.getCgpa() == null ? "N/A" : coursepa.getCgpa()) + "</a>\n"
                    + "            </div>";

            String grade = "<div class='detail'>\n"
                    + "              <a class='label'>Grade</a>\n"
                    + "              <a class='value'>" + (coursepa.getGrade() == null ? "N/A" : coursepa.getGrade()) + "</a>\n"
                    + "            </div>";

            String cgpaForm = "<a class='label' style='margin-left: -180px;'>CGPA</a>\n"
                    + "            <input class='textbox' type='text' name='cgpa' placeholder='eg. 4.0000' value='" + (coursepa.getCgpa() == null ? "" : coursepa.getCgpa()) + "' required>";

            String gradeForm = "<a class='label' style='margin-left: -180px;'>Grade</a>\n"
                    + "            <input class='textbox' type='text' name='grade' placeholder='eg. A' value='" + (coursepa.getGrade() == null ? "" : coursepa.getGrade()) + "'  required>";

            servlet.putInJsp("gradeForm", gradeForm);
            servlet.putInJsp("cgpaForm", cgpaForm);
            servlet.putInJsp("grade", grade);
            servlet.putInJsp("cgpa", cgpa);
        }

        if (type.equalsIgnoreCase("class") && cpa.getRole().equalsIgnoreCase("student")) {
            String grade = "<div class='detail'>\n"
                    + "              <a class='label'>Grade</a>\n"
                    + "              <a class='value'>" + (cpa.getGrade() == null ? "N/A" : cpa.getGrade()) + "</a>\n"
                    + "            </div>";

            String submissions = "<div class='detail' onclick='location.href=\"#\"'>\n"
                    + "              <a class='label'>Submissions</a>\n"
                    + "              <a class='value'>" + cpa.getSubmissionCollection().size() + "</a>\n"
                    + "            </div>";

            String gradeForm = "<a class='label' style='margin-left: -180px;'>Grade</a>\n"
                    + "            <input class='textbox' type='text' name='grade' placeholder='eg. A' value='" + (cpa.getGrade() == null ? "" : cpa.getGrade()) + "'  required>";

            servlet.putInJsp("gradeForm", gradeForm);
            servlet.putInJsp("submissions", submissions);
            servlet.putInJsp("grade", grade);
        }

        
        servlet.putInJsp("type", type);
        servlet.putInJsp("id", id);
        servlet.putInJsp("memberId", memberId);

        servlet.servletToJsp("memberDetails.jsp");

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
