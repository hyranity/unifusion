/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Programme;
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
@WebServlet(name = "PerformEditProgramme", urlPatterns = {"/PerformEditProgramme"})
public class PerformEditProgramme extends HttpServlet {

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
        String programmeCode = servlet.getQueryStr("programmeCode"); // WILL NEVER BE EDITED
        String programmeTitle = servlet.getQueryStr("programmeTitle");
        String institutionCode = servlet.getQueryStr("institutionCode"); // WILL NEVER BE EDITED
        String description = servlet.getQueryStr("description");
        //String bannerURL = servlet.getQueryStr("bannerURL");
        //String colourTheme = servlet.getQueryStr("colourTheme");
        String iconURL = servlet.getQueryStr("iconURL");
        boolean hasInstitution = servlet.getQueryStr("hasInstitution") != null;  // WILL NEVER BE EDITED
        boolean isPublic = servlet.getQueryStr("isPublic") != null;

        // Validations go here
        if (programmeCode == null || programmeTitle == null || description == null || programmeCode.trim().isEmpty() || programmeTitle.trim().isEmpty() || description.trim().isEmpty()) {
            // Has null data
            System.out.println("Null fields!");
            Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("ProgrammeDetails?programme=" + programmeCode);
            return;
        }

        // Get the programme from DB where this person is participating inside it and is a programme leader
        Query query = em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where p.userid = ? and pg.programmecode = ? and pg.programmecode = ppa.programmecode and ppa.participantid = p.participantid and ppa.isCreator='true'", Models.Programme.class);
        query.setParameter(1, Server.getUser(request, response).getUserid());
        query.setParameter(2, programmeCode);

        // If invalid course code
        if (query.getResultList().size() == 0) {
            System.out.println("Invalid programme code");
            servlet.toServlet("Dashboard");
            return;
        } else {
            Models.Programme programme = (Programme) query.getSingleResult();

            // Update programme fields
            programme.setTitle(programmeTitle);
            programme.setDescription(description);
            programme.setIspublic(isPublic);
            //programme.setColourtheme(colourTheme);
            //programme.setBannerurl(bannerURL);
            programme.setIconurl(iconURL);

            // Update in db
            db.update(programme);

            // Redirect
            servlet.toServlet("Programme?id=" + programmeCode);
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
