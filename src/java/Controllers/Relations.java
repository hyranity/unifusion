
package Controllers;

import Models.*;
import Util.DB;
import Util.Quick;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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

@WebServlet(name = "Relations", urlPatterns = {"/Relations"})
public class Relations extends HttpServlet {

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
        Models.Users user = Server.getUser(request, response);
        
        String type = servlet.getQueryStr("type");
        String id = servlet.getQueryStr("id");
        
        String output = "";
        
        Models.Class currentClass = null;
        Course course = null;
        Programme programme = null;
        Institution institution = null;
        
        switch(type) {
            case "class":
                // get current class
                currentClass = (Models.Class) em.createNativeQuery("select c.* from class c "
                    + "where c.classid = ?", Models.Class.class)
                        .setParameter(1, id).getSingleResult();
                
                // find course if it exists
                if (currentClass.getCoursecode() != null) {
                    course = (Course) em.createNativeQuery("select c.* from course c "
                        + "where c.coursecode = ?", Course.class)
                            .setParameter(1, currentClass.getCoursecode().getCoursecode()).getSingleResult();
                }
                
                // find course's programme if it exists
                if (course != null) {
                    if (course.getProgrammecode() != null) {
                        programme = (Programme) em.createNativeQuery("select p.* from programme p "
                            + "where p.programmecode = ?", Programme.class)
                                .setParameter(1, course.getProgrammecode().getProgrammecode()).getSingleResult();
                    }
                }
                
                // find programme's institution if it exists
                if (programme != null) {
                    if (programme.getInstitutioncode() != null) {
                        institution = (Institution) em.createNativeQuery("select i.* from institution i "
                            + "where i.institutioncode = ?", Institution.class)
                                .setParameter(1, programme.getInstitutioncode().getInstitutioncode()).getSingleResult();
                    }
                }
                
                if (institution != null) {
                    output += "<div class='panel' onclick=\"location.href='Institution?id=" + institution.getInstitutioncode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + institution.getInstitutioncode() + "</a>\n" +
                    "              <a class='title'>" + institution.getName() + "</a>\n" +
                    "              <a class='desc'>Insitution</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                    
                    output += "<a class='divider'>^</a>";
                }
                
                if (programme != null) {
                    output += "<div class='panel' onclick=\"location.href='Programme?id=" + programme.getProgrammecode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + programme.getProgrammecode() + "</a>\n" +
                    "              <a class='title'>" + programme.getTitle() + "</a>\n" +
                    "              <a class='desc'>Programme</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                    
                    output += "<a class='divider'>^</a>";
                }
                
                if (course != null) {
                    output += "<div class='panel' onclick=\"location.href='Course?id=" + course.getCoursecode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + course.getCoursecode() + "</a>\n" +
                    "              <a class='title'>" + course.getTitle() + "</a>\n" +
                    "              <a class='desc'>Course</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                    
                    output += "<a class='divider'>^</a>";
                }
                
                if (currentClass != null) {
                    output += "<div class='panel' id='current' onclick=\"location.href='Class?id=" + currentClass.getClassid() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + currentClass.getClassid() + "</a>\n" +
                    "              <a class='title'>" + currentClass.getClasstitle() + "</a>\n" +
                    "              <a class='desc'>Class</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                }
                
                break;
                
            case "course":
                // get current course
                course = (Course) em.createNativeQuery("select c.* from course c "
                    + "where c.coursecode = ?", Course.class)
                        .setParameter(1, id).getSingleResult();
                
                // get all classes under course
                List<Models.Class> classes = (List<Models.Class>) em.createNativeQuery("select c.* from class c "
                    + "where c.coursecode = ?", Models.Class.class)
                        .setParameter(1, id).getResultList();
                
                // find course's programme if it exists
                if (course.getProgrammecode() != null) {
                    if (course.getProgrammecode() != null) {
                        programme = (Programme) em.createNativeQuery("select p.* from programme p "
                            + "where p.programmecode = ?", Programme.class)
                                .setParameter(1, course.getProgrammecode().getProgrammecode()).getSingleResult();
                    }
                }
                
                // find programme's institution if it exists
                if (programme != null) {
                    if (programme.getInstitutioncode() != null) {
                        institution = (Institution) em.createNativeQuery("select i.* from institution i "
                            + "where i.institutioncode = ?", Institution.class)
                                .setParameter(1, programme.getInstitutioncode().getInstitutioncode()).getSingleResult();
                    }
                }
                
                if (institution != null) {
                    output += "<div class='panel' onclick=\"location.href='Institution?id=" + institution.getInstitutioncode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + institution.getInstitutioncode() + "</a>\n" +
                    "              <a class='title'>" + institution.getName() + "</a>\n" +
                    "              <a class='desc'>Insitution</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                    
                    output += "<a class='divider'>^</a>";
                }
                
                if (programme != null) {
                    output += "<div class='panel' onclick=\"location.href='Programme?id=" + programme.getProgrammecode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + programme.getProgrammecode() + "</a>\n" +
                    "              <a class='title'>" + programme.getTitle() + "</a>\n" +
                    "              <a class='desc'>Programme</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                    
                    output += "<a class='divider'>^</a>";
                }
                
                if (course != null) {
                    output += "<div class='panel' id='current' onclick=\"location.href='Course?id=" + course.getCoursecode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + course.getCoursecode() + "</a>\n" +
                    "              <a class='title'>" + course.getTitle() + "</a>\n" +
                    "              <a class='desc'>Course</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                    
                    output += "<a class='divider'>v</a>";
                }
                
                if (classes != null) {
                    output += "<div class='panel' onclick=\"location.href='EducationList?type=course&id=" + course.getCoursecode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='title'>" + classes.size() + " Classes...</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                }
                
                break;
              
            case "programme":
                // get current programme
                programme = (Programme) em.createNativeQuery("select p.* from programme p "
                    + "where p.programmecode = ?", Programme.class)
                        .setParameter(1, id).getSingleResult();
                
                // get all courses under programme
                List<Course> courses = (List<Course>) em.createNativeQuery("select c.* from course c "
                    + "where c.programmecode = ?", Course.class)
                        .setParameter(1, id).getResultList();
                
                // find programme's institution if it exists
                if (programme != null) {
                    if (programme.getInstitutioncode() != null) {
                        institution = (Institution) em.createNativeQuery("select i.* from institution i "
                            + "where i.institutioncode = ?", Institution.class)
                                .setParameter(1, programme.getInstitutioncode().getInstitutioncode()).getSingleResult();
                    }
                }
                
                if (institution != null) {
                    output += "<div class='panel' onclick=\"location.href='Institution?id=" + institution.getInstitutioncode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + institution.getInstitutioncode() + "</a>\n" +
                    "              <a class='title'>" + institution.getName() + "</a>\n" +
                    "              <a class='desc'>Insitution</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                    
                    output += "<a class='divider'>^</a>";
                }
                
                if (programme != null) {
                    output += "<div class='panel' id='current' onclick=\"location.href='Programme?id=" + programme.getProgrammecode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + programme.getProgrammecode() + "</a>\n" +
                    "              <a class='title'>" + programme.getTitle() + "</a>\n" +
                    "              <a class='desc'>Programme</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                    
                    output += "<a class='divider'>v</a>";
                }
                
                if (courses != null) {
                    output += "<div class='panel' onclick=\"location.href='EducationList?type=programme&id=" + programme.getProgrammecode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='title'>" + courses.size() + " Courses...</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                }
                break;
              
            case "institution":
                // get current institution
                institution = (Institution) em.createNativeQuery("select i.* from institution i "
                    + "where i.institutioncode = ?", Institution.class)
                        .setParameter(1, id).getSingleResult();
                
                // get all programmes under institution
                List<Programme> programmes = (List<Programme>) em.createNativeQuery("select p.* from programme p "
                    + "where p.institutioncode = ?", Programme.class)
                        .setParameter(1, id).getResultList();
                
                if (institution != null) {
                    output += "<div class='panel' id='current' onclick=\"location.href='Institution?id=" + institution.getInstitutioncode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + institution.getInstitutioncode() + "</a>\n" +
                    "              <a class='title'>" + institution.getName() + "</a>\n" +
                    "              <a class='desc'>Insitution</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                    
                    output += "<a class='divider'>v</a>";
                }
                
                if (programmes != null) {
                    output += "<div class='panel' onclick=\"location.href='EducationList?type=institution&id=" + institution.getInstitutioncode() + "'\">\n" +
                    "            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='title'>" + programmes.size() + " Programmes...</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                }
                break;
            
            default:
                servlet.servletToJsp("dashboard.jsp");
        }
        
        servlet.putInJsp("output", output);
        servlet.servletToJsp("relations.jsp");
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
