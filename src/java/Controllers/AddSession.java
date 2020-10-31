/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Class;
import Models.Institution;
import Models.Venue;
import Util.Quick;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "AddSession", urlPatterns = {"/AddSession"})
public class AddSession extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Objects
        Servlet servlet = new Servlet(request, response);
        Models.Users user = Server.getUser(request, response);
        Models.Class classroom = new Models.Class();

        // Get class code
        String classid = servlet.getQueryStr("id");
        
         // Get possible queryStr
        servlet.putInJsp("date", servlet.getQueryStr("date"));

        // Fetch from db
        try {
            classroom = (Class) em.createNativeQuery("select c.* from class c, classparticipant cpa,participant p where c.classid = ? and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role = 'teacher'", Models.Class.class).setParameter(1, classid).setParameter(2, user.getUserid()).getSingleResult();
        } catch (Exception ex) {
            // Error means no result, redirect to classroom page
            servlet.toServlet("Class?id=" + classid);
            return;
        }

        // Get class' institution
        Query institutionQuery = em.createNativeQuery("select i.* from class cl, course c, programme p, institution i where cl.classid = ? and cl.coursecode = c.COURSECODE and c.PROGRAMMECODE = p.PROGRAMMECODE and p.INSTITUTIONCODE = i.INSTITUTIONCODE", Models.Institution.class).setParameter(1, classid);

        // Show VenueList only if class has an institution
        String hideVenueList = "";
        String venueUI = "";
        String tempChecked = "";
        String tempVenueStr = "Temporary venue (If none in list)";
        if (institutionQuery.getResultList().size() == 0) {
            hideVenueList = "display:none;";
            tempChecked = "checked";
            tempVenueStr = "Venue";
        } else {
            // If has institution, load all venues
            for (Venue venue : ((Institution) institutionQuery.getSingleResult()).getVenueCollection()) {

                // Only load active ones
                if (venue.getIsactive()) {
                    venueUI += " <div class='venue' id='venue-" + venue.getVenueid() + "' onclick='selectVenue(\"" + venue.getVenueid() + "\")'>\n"
                            + "                  <a class='id'>" + venue.getVenueid() + "</a>\n"
                            + "                  <a class='name'>" + venue.getTitle() + "</a>\n"
                            + "                  <a class='location'>" + venue.getLocation() + "</a>\n"
                            + "                </div>";
                }
            }
        }

        // Put in jsp
        servlet.putInJsp("subheading", classroom.getClassid() + " - " + classroom.getClasstitle() + " (Class)");
        servlet.putInJsp("id", classid);
        servlet.putInJsp("hideVenueList", hideVenueList);
        servlet.putInJsp("tempChecked", tempChecked);
        servlet.putInJsp("venueUI", venueUI);
        servlet.putInJsp("tempVenueStr", tempVenueStr);
        servlet.putInJsp("icon", Quick.getIcon(classroom.getIconurl()));

        // Redirect
        servlet.servletToJsp("addSession.jsp");
        return;
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
