/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Institution;
import Util.DB;
import Util.Errors;
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
@WebServlet(name = "PerformEditInstitution", urlPatterns = {"/PerformEditInstitution"})
public class PerformEditInstitution extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Utility objects
        Servlet servlet = new Servlet(request, response);
        DB db = new DB(em, utx);

        // Obtain field data
        String institutionCode = servlet.getQueryStr("institutionCode"); // WILL NEVER BE EDITED
        String institutionName = servlet.getQueryStr("institutionName");
        String address = servlet.getQueryStr("address");
        String description = servlet.getQueryStr("description");
        //String bannerURL = servlet.getQueryStr("bannerURL");
        //String colourTheme = servlet.getQueryStr("colourTheme");
        String iconURL = servlet.getQueryStr("iconURL");
        boolean isPublic = servlet.getQueryStr("isPublic") != null;

        // Validations go here
        if (institutionCode == null || institutionName == null || address == null || description == null || institutionCode.trim().isEmpty() || institutionName.trim().isEmpty() || address.trim().isEmpty() || description.trim().isEmpty()) {
            // Has null data
            System.out.println("Null fields!");
            Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("InstitutionDetails?institution=" + institutionCode);
            return;
        }

        // Get the programme from DB where this person is participating inside it and is the creator
        // Only allows creator to edit 
        Query query = em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where p.userid = ? and i.institutioncode = ? and i.institutioncode = ipa.institutioncode and ipa.participantid = p.participantid and ipa.isCreator = 'true'", Models.Institution.class);
        query.setParameter(1, Server.getUser(request, response).getUserid());
        query.setParameter(2, institutionCode);

        // If invalid institution code
        if (query.getResultList().size() == 0) {
            System.out.println("Invalid institution code");
            servlet.toServlet("Dashboard");
            return;
        } else {
             // Institution is valid
            Models.Institution institution = (Institution) query.getSingleResult();

            // Update fields
            institution.setInstitutioncode(institutionCode);
            institution.setName(institutionName);
            institution.setDescription(description);
            institution.setAddress(address);
            institution.setIspublic(isPublic);
            institution.setIconurl(iconURL);
            //institution.setBannerurl(bannerURL);
            //institution.setColourtheme(colourTheme);
            
            // Update
            db.update(institution);

            // Redirect
            servlet.toServlet("Institution?id=" + institutionCode);
           
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
