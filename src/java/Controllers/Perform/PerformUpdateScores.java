/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Classparticipant;
import Models.Course;
import Models.Courseparticipant;
import Models.Institution;
import Models.Institutionparticipant;
import Models.Participant;
import Models.Programme;
import Models.Programmeparticipant;
import Util.Errors;
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
@WebServlet(name = "PerformUpdateScores", urlPatterns = {"/PerformUpdateScores"})
public class PerformUpdateScores extends HttpServlet {

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
        String cgpa = servlet.getQueryStr("cgpa") == null ? "" : servlet.getQueryStr("cgpa");
        String grade = servlet.getQueryStr("grade") == null ? "" : servlet.getQueryStr("grade");
        
        grade = grade.trim().isEmpty() ? "NULL" : grade;

        Query query;
        
        // Grade and CGPA cannot be both null
        if((cgpa.trim().isEmpty() && grade.trim().isEmpty())){
             Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("MemberDetails?id=" + id + "&type=" + type + "&memberId=" + memberId);
            return;
        }
        
        // Grade should only contain up to 2 letters
        if(!grade.trim().isEmpty() && grade.length()>2){
             Errors.respondSimple(request.getSession(), "Grade should only contain 2 characters and cannot be empty.");
            servlet.toServlet("MemberDetails?id=" + id + "&type=" + type + "&memberId=" + memberId);
            return;
        }

        try {
            if ("class".equalsIgnoreCase(type)) {
                // Get the class
                Models.Class classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ?", Models.Class.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Verify this person is a teacher
                em.createNativeQuery("select cpa.* from class c, classparticipant cpa,  participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and cpa.role = 'teacher' and p.userid=?", Models.Classparticipant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get member
                cpa = (Classparticipant) em.createNativeQuery("select cpa.* from class c, classparticipant cpa,  participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid  and p.participantid = ?", Models.Classparticipant.class).setParameter(1, id).setParameter(2, memberId).getSingleResult();

                // Update grade
                cpa.setGrade(grade);
                
                // Update in DB
                db.update(cpa);
            } else if ("course".equalsIgnoreCase(type)) {
                // Get the course
                Models.Course course = (Models.Course) em.createNativeQuery("select c.* from course c, courseparticipant cpa, participant p where c.coursecode = ? and cpa.coursecode = c.coursecode and cpa.participantid = p.participantid and p.userid = ?", Course.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Verify this person is a teacher
                em.createNativeQuery("select cpa.* from course c, courseparticipant cpa, participant p where c.coursecode = ? and cpa.participantid = p.participantid  and cpa.coursecode = c.coursecode and cpa.role = 'teacher' and p.userid = ?", Models.Courseparticipant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get member
                coursepa = (Courseparticipant) em.createNativeQuery("select cpa.* from course c, courseparticipant cpa, participant p  where c.coursecode = ? and cpa.participantid = p.participantid and cpa.coursecode = c.coursecode and p.participantid = ?", Models.Courseparticipant.class).setParameter(1, id).setParameter(2, memberId).getSingleResult();

                  // Update grade
                coursepa.setGrade(grade);
                
                // Update cgpa
                coursepa.setCgpa(Double.parseDouble(cgpa));
                
                // Update in DB
                db.update(coursepa);
            } else if ("programme".equalsIgnoreCase(type)) {
                // Get the programme
                Models.Programme programme = (Models.Programme) em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ? and ppa.programmecode = pg.programmecode and ppa.participantid = p.participantid and p.userid = ?", Programme.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Verify this person is a teacher
                em.createNativeQuery("select ppa.* from programme pg, programmeparticipant ppa, participant p  where pg.programmecode = ? and ppa.programmecode = pg.programmecode and p.participantid = ppa.participantid and ppa.role = 'teacher'  and p.userid = ?", Models.Programmeparticipant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get member
                ppa = (Programmeparticipant) em.createNativeQuery("select ppa.* from programme pg, programmeparticipant ppa, participant p  where pg.programmecode = ? and ppa.programmecode = pg.programmecode and p.participantid = ppa.participantid and p.participantid = ?", Models.Programmeparticipant.class).setParameter(1, id).setParameter(2, memberId).getSingleResult();

                 // Update cgpa
                ppa.setCgpa(Double.parseDouble(cgpa));
                
                // Update in DB
                db.update(ppa);
            } else if ("institution".equalsIgnoreCase(type)) {
                // Get the institution
                Models.Institution institution = (Models.Institution) em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ?", Institution.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Verify this person is a teacher
                em.createNativeQuery("select ipa.* from institution i, institutionparticipant  ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and ipa.role = 'teacher' and p.userid = ?", Models.Classparticipant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();

                // Get member
                ipa = (Institutionparticipant) em.createNativeQuery("select ipa.* from institution i, institutionparticipant ipa, participant p  where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.participantid = ?", Models.Classparticipant.class).setParameter(1, id).setParameter(2, memberId).getSingleResult();

                // Don't do anything
            } else {
                // Incorrect type
                System.out.println("Type is incorrect");
                return;
            }
        } catch (NoResultException e) {
            System.out.println("No data found");
            servlet.toServlet("MemberList?id=" + id + "&type=" + type);
            return;
        } catch(NumberFormatException ex){
             Errors.respondSimple(request.getSession(), "CGPA must be a number");
            servlet.toServlet("MemberDetails?id=" + id + "&type=" + type + "&memberId=" + memberId);
            return;
        }
        
        servlet.toServlet("MemberDetails?id=" + id + "&type=" + type + "&memberId=" + memberId);
        
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
