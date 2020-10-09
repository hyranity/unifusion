/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Util.DB;
import Util.Servlet;
import Models.*;
import Util.Quick;
import Util.Server;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
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

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformAddProgramme", urlPatterns = {"/PerformAddProgramme"})
public class PerformAddProgramme extends HttpServlet {

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
        Programme programme = new Programme();
        Programmeparticipant ppa = new Programmeparticipant();
        Participant participant = new Participant();

        // Obtain field data
        String programmeCode = servlet.getQueryStr("programmeCode");
        String programmeTitle = servlet.getQueryStr("programmeCode");
        String institutionCode = servlet.getQueryStr("institutionCode");
        String description = servlet.getQueryStr("description");
        boolean hasInstitution = servlet.getQueryStr("hasInstitution") != null;
        boolean isPublic = servlet.getQueryStr("isPublic") != null;

        // Validations go here
        // Create new programme
        programme.setProgrammecode(programmeCode);
        programme.setTitle(programmeTitle);
        programme.setIspublic(isPublic);
        programme.setDescription(description);

        // If an institution is specified
        if (hasInstitution) {
            // Load institution from DB
            // Check whether user participates in the institution 
            // Check permission level
            Query query = em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p, users u where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.PARTICIPANTID = p.PARTICIPANTID and p.USERID = ? and p.educatorrole = 'programmeLeader'", Models.Institution.class);
            query.setParameter(1, institutionCode);
            query.setParameter(2, user.getUserid());

            // Execute the query
            List<Models.Institution> results = query.getResultList();

            // if valid
            if (results.size() > 0) {
                // Set programme into the institution
                programme.setInstitutioncode(results.get(0));

                // Get the existing participant from programme
                Query participantQuery = em.createNativeQuery("select p.* from institution i, institutionparticipant ipa, participant p, users u where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.PARTICIPANTID = p.PARTICIPANTID and p.USERID = ? and p.educatorrole = 'programmeLeader'", Models.Participant.class);
                participantQuery.setParameter(1, institutionCode);
                participantQuery.setParameter(2, user.getUserid());

                // Execute the query
                List<Models.Participant> participantResults = participantQuery.getResultList();
                participant = participantResults.get(0);

            } // If invalid programme, show error message
            else {
                System.out.println("Programme code incorrect");
                servlet.toServlet("AddProgramme");
                return;
            }

        } // If no institution is specified
        else {

            // Create a new participant since it's not related to any programme/institution
            participant.setDateadded(Calendar.getInstance().getTime());
            participant.setEducatorrole("programmeLeader");
            participant.setStatus("active");
            participant.setParticipantid(Quick.generateID(em, utx, Participant.class, "Participantid"));
            participant.setUserid(user);
            db.insert(participant);

        }

        // Insert the programme
        db.insert(programme);

        // Create the programme participant
        ppa.setParticipantid(participant); 
        ppa.setIscreator(true);
        ppa.setRole("teacher");
        ppa.setStatus("active");
        ppa.setProgrammecode(programme);
        ppa.setProgrammeparticipantid(Quick.generateID(em, utx, Courseparticipant.class, "Programmeparticipantid"));

        // Insert the programme
        db.insert(ppa);

        // Redirect
        System.out.println("Programme successfullly created");
        servlet.toServlet("AddProgramme");
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
