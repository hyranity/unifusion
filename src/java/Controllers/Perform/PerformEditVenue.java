/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Institution;
import Util.Errors;
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
@WebServlet(name = "PerformEditVenue", urlPatterns = {"/PerformEditVenue"})
public class PerformEditVenue extends HttpServlet {

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
        Models.Institution institution = new Institution();
        Models.Venue venue = new Models.Venue();
        
        String institutionCode = "";
        String venueId = "";

        try {
            // Get query string
             institutionCode = servlet.getQueryStr("id");
             venueId = servlet.getQueryStr("code");
            String name = servlet.getQueryStr("name");
            String location = servlet.getQueryStr("location");
            int capacity = Integer.parseInt(servlet.getQueryStr("capacity"));
            boolean isActive = servlet.getQueryStr("isActive") != null;

            // Validation goes here
            if (institutionCode == null || name == null || location == null || servlet.getQueryStr("capacity") == null || institutionCode.trim().isEmpty() || name.trim().isEmpty() || location.trim().isEmpty() || servlet.getQueryStr("capacity").trim().isEmpty()) {
                // Has null data
                System.out.println("Null fields!");
                Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
                servlet.toServlet("EditVenue?id=" + institutionCode+"&code="+venueId);
                return;
            }

            // Get institution while doing authorization check
            try {
                institution = (Models.Institution) em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and i.institutioncode = ipa.institutioncode and ipa.participantid = p.participantid and p.educatorrole='institutionAdmin' and p.userid = ?", Models.Institution.class).setParameter(1, institutionCode).setParameter(2, user.getUserid()).getSingleResult();

            } catch (NoResultException e) {
                System.out.println("No institution found");
                servlet.toServlet("Dashboard");
                return;
            }

            // Get venue; no need authorization since already done above
            try {
                venue = (Models.Venue) em.createNativeQuery("select * from venue where institutioncode = ? and venueid = ?", Models.Venue.class).setParameter(1, institutionCode).setParameter(2, venueId).getSingleResult();

            } catch (NoResultException e) {
                System.out.println("No venue found");
                servlet.toServlet("Dashboard");
                return;
            }
            
            // Update data
            venue.setTitle(name);
            venue.setCapacity(capacity);
            venue.setLocation(location);
            venue.setIsactive(isActive);
            
            // Update in db
            db.update(venue);
            
            // Redirect 
            servlet.toServlet("VenueDetails?id=" + institutionCode+"&code="+venueId);
            
        } catch (NumberFormatException numberFormatException) {
            System.out.println("Capacity is invalid!");
            Errors.respondSimple(request.getSession(), "Capacity has to be a number");
            servlet.toServlet("EditVenue?id=" + institutionCode+"&code="+venueId);
            return;
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
