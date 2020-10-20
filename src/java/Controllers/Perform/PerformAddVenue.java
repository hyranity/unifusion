/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Classparticipant;
import Models.Institution;
import Models.Venue;
import Util.Errors;
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
@WebServlet(name = "PerformAddVenue", urlPatterns = {"/PerformAddVenue"})
public class PerformAddVenue extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Util.Servlet servlet = null;

        String institutionCode = "";
        try {
            
            // Util objects
            servlet = new Util.Servlet(request, response);
            Util.DB db = new Util.DB(em, utx);
            Models.Users user = Server.getUser(request, response);
            Models.Institution institution = new Institution();

            // Get query strings
            institutionCode = servlet.getQueryStr("id");
            String name = servlet.getQueryStr("id");
            String location = servlet.getQueryStr("location");
            int capacity = Integer.parseInt(servlet.getQueryStr("capacity"));
            boolean isActive = servlet.getQueryStr("isActive") != null;

            // Validation goes here
            if (institutionCode == null || name == null || location == null || servlet.getQueryStr("capacity") == null || institutionCode.trim().isEmpty() || name.trim().isEmpty() || location.trim().isEmpty() || servlet.getQueryStr("capacity").trim().isEmpty()) {
                // Has null data
                System.out.println("Null fields!");
                Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
                servlet.toServlet("AddVenue?id=" + institutionCode);
                return;
            }

            // Note: Venue only works for institution.
            try {
                // Find the class' institution
                institution = (Models.Institution) em.createNativeQuery("select * from institution where institutioncode = ?", Models.Institution.class).setParameter(1, institutionCode).getSingleResult();

            } catch (NoResultException e) {
                System.out.println("No institution found");
                servlet.toServlet("Dashboard");
                return;
            }
            
            // Add new venue
            Venue venue = new Venue();
            venue.setVenueid(Quick.generateID(em, utx, Venue.class, "venueid"));
            venue.setCapacity(capacity);
            venue.setInstitutioncode(institution);
            venue.setIsactive(isActive);
            venue.setLocation(location);
            venue.setTitle(name);
            
            // Insert in db
            db.insert(venue);

            // Put in JSP
            servlet.putInJsp("id", institution.getInstitutioncode());

        } catch (NumberFormatException numberFormatException) {
            System.out.println("Capacity is invalid!");
            Errors.respondSimple(request.getSession(), "Capacity has to be a number");
            servlet.toServlet("AddVenue?id=" + institutionCode);
            return;
        }

        // Redirect
        servlet.toServlet("Venues");

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
