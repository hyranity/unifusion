/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Institution;
import Models.Institutionparticipant;
import Models.Participant;
import Models.Users;
import Util.DB;
import Util.Errors;
import Util.Quick;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@WebServlet(name = "PerformAddInstitution", urlPatterns = {"/PerformAddInstitution"})
public class PerformAddInstitution extends HttpServlet {

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

        // Objects to be utilized
        Users user = Server.getUser(request, response);
        Institution institution = new Institution();
        Institutionparticipant ipa = new Institutionparticipant();
        Participant participant = new Participant();

        // Obtain field data
        String institutionCode = servlet.getQueryStr("institutionCode");
        String institutionName = servlet.getQueryStr("institutionName");
        String address = servlet.getQueryStr("address");
        String description = servlet.getQueryStr("description");
        boolean hasInstitution = servlet.getQueryStr("hasInstitution") != null;
        boolean isPublic = servlet.getQueryStr("isPublic") != null;

        // Validations go here
        if (institutionCode == null || institutionName == null || description == null || address == null || institutionCode.trim().isEmpty() || institutionName.trim().isEmpty() || description.trim().isEmpty() || address.trim().isEmpty()) {
            // Has null data
            System.out.println("Null fields!");
            Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("AddInstitution");
            return;
        }

        // Duplicate institution code
        if (db.getSingleResult("institutioncode", institutionCode, Institution.class) != null) {
            System.out.println("Has duplicate institution code!");
            Errors.respondSimple(request.getSession(), "That institution code is taken.");
            servlet.toServlet("AddInstitution");
            return;
        }

        // Create new institution
        institution.setInstitutioncode(institutionCode);
        institution.setName(institutionName);
        institution.setDescription(description);
        institution.setAddress(address);
        institution.setIspublic(isPublic);
        institution.setAuthcode(Quick.generateID(em, utx, Institution.class, "authcode"));
        db.insert(institution);

        // Create new participant, since this is the highest level
        participant.setDateadded(Calendar.getInstance().getTime());
        participant.setEducatorrole("institutionAdmin");
        participant.setStatus("active");
        participant.setParticipantid(Quick.generateID(em, utx, Participant.class, "participantid"));
        participant.setUserid(user);
        db.insert(participant);
        
        // Create a course participant to link this creator to this course
        ipa.setParticipantid(participant);
        ipa.setIscreator(true);
        ipa.setRole("teacher");
        ipa.setStatus("active");
        ipa.setInstitutioncode(institution);
        ipa.setInstitutionparticipantid(Quick.generateID(em, utx, Institutionparticipant.class, "institutionparticipantid"));
        db.insert(ipa);
        
        System.out.println("Successfully added an institution!");

        servlet.toServlet("Dashboard");
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
