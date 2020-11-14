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

@WebServlet(name = "EducationList", urlPatterns = {"/EducationList"})
public class EducationList extends HttpServlet {

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
        
        /*
        type course : show all classes in the course
        type programme : show all courses in the programme
        type institution : show all programmes in the institution
        */
        
        switch(type) {
            case "course":
                List<Models.Class> classes = (List<Models.Class>) em.createNativeQuery("select c.* from class c "
                        + "where c.coursecode = ?", Models.Class.class).setParameter(1, id).getResultList();
                
                for (Models.Class individualClass : classes) {
                    
                    String description = (individualClass.getDescription().length() > 50)
                            ? individualClass.getDescription().substring(0, Math.min(individualClass.getDescription().length(), 50)) + "..."
                            : individualClass.getDescription();
                    
                    output += "<div class='class' onclick=\"location.href='Class?id=" + individualClass.getClassid() + "'\">\n" +
                    "            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + individualClass.getClassid() + "</a>\n" +
                    "              <a class='title'>" + individualClass.getClasstitle() + "</a>\n" +
                    "              <a class='desc'>" + description + "</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                }
                
                Course currentCourse = (Course) em.createNativeQuery("select c.* from course c "
                        + "where c.coursecode = ?", Models.Course.class).setParameter(1, id).getSingleResult();
                
                servlet.putInJsp("id", id);
                servlet.putInJsp("name", currentCourse.getTitle());
                servlet.putInJsp("output", output);
                servlet.servletToJsp("classes.jsp");
                
                break;
                
            case "programme":
                List<Course> courses = (List<Course>) em.createNativeQuery("select c.* from course c "
                    + "where c.programmecode = ?", Course.class).setParameter(1, id).getResultList();
                
                for (Models.Course course : courses) {
                    
                    String description = (course.getDescription().length() > 50)
                            ? course.getDescription().substring(0, Math.min(course.getDescription().length(), 50)) + "..."
                            : course.getDescription();
                    
                    output += "<div class='course' onclick=\"location.href='Course?id=" + course.getCoursecode() + "'\">\n" +
                    "            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + course.getCoursecode() + "</a>\n" +
                    "              <a class='title'>" + course.getTitle() + "</a>\n" +
                    "              <a class='desc'>" + description + "</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                }
                
                Programme currentProgramme = (Programme) em.createNativeQuery("select p.* from programme p "
                        + "where p.programmecode = ?", Models.Programme.class).setParameter(1, id).getSingleResult();
                
                servlet.putInJsp("id", id);
                servlet.putInJsp("name", currentProgramme.getTitle());
                servlet.putInJsp("output", output);
                servlet.servletToJsp("courses.jsp");
                
                break;
                
            case "institution":
                List<Programme> programmes = (List<Programme>) em.createNativeQuery("select p.* from programme p "
                    + "where p.institutioncode = ?", Programme.class).setParameter(1, id).getResultList();
                
                for (Models.Programme programme : programmes) {
                    
                    String description = (programme.getDescription().length() > 50)
                            ? programme.getDescription().substring(0, Math.min(programme.getDescription().length(), 50)) + "..."
                            : programme.getDescription();
                    
                    output += "<div class='programme' onclick=\"location.href='Programme?id=" + programme.getProgrammecode() + "'\">\n" +
                    "            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>\n" +
                    "            <div class='text'>\n" +
                    "              <a class='id'>" + programme.getProgrammecode() + "</a>\n" +
                    "              <a class='title'>" + programme.getTitle() + "</a>\n" +
                    "              <a class='desc'>" + description + "</a>\n" +
                    "            </div>\n" +
                    "          </div>";
                }
                
                Institution currentInstitution = (Institution) em.createNativeQuery("select i.* from institution i "
                        + "where i.institutioncode = ?", Models.Institution.class).setParameter(1, id).getSingleResult();
                
                servlet.putInJsp("id", id);
                servlet.putInJsp("name", currentInstitution.getName());
                servlet.putInJsp("output", output);
                servlet.servletToJsp("programmes.jsp");
                
                break;
               
            default:
                servlet.servletToJsp("dashboard.jsp");
        }
        
        
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
