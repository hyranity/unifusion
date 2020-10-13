/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Courseparticipant;
import Models.Participant;
import Models.Programmeparticipant;
import Models.Users;
import Util.DB;
import Util.Quick;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
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
@WebServlet(name = "PerformJoinProgramme", urlPatterns = {"/PerformJoinProgramme"})
public class PerformJoinProgramme extends HttpServlet {

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

        // Current user
        Users user = Server.getUser(request, response);

        // Obtain form data
        String programmeCode = servlet.getQueryStr("programmeCode");

        // Fetch from db
        Models.Programme programme = db.getSingleResult("programmecode", programmeCode, Models.Programme.class);

        // If no class is found
        if (programme == null) {
            System.out.println("No Programme is found");
            servlet.toServlet("JoinProgramme");
            return;
        }

        // If this person already joined
        Query joinQuery = em.createNativeQuery("select p.* from participant p, programmeparticipant ppa where p.userid = ? and p.participantid = ppa.participantid and ppa.programmecode = ?").setParameter(1, user.getUserid()).setParameter(2, programme.getProgrammecode());
        if (joinQuery.getResultList().size() > 0) {
            System.out.println("Already joined this programme");
            servlet.toServlet("JoinProgramme");
            return;
        }

        // Is this programme associated with a institution?
        if (programme.getInstitutioncode() != null) {

            // If this programme associated with an institution? He must be in the programme first before joining the course
            Query query = em.createNativeQuery("select p.* from participant p, institutionparticipant ipa where p.userid = ? and p.participantid = ipa.participantid and ipa.institutioncode = ?").setParameter(1, user.getUserid()).setParameter(2, programme.getInstitutioncode().getInstitutioncode());

            // If yes
            if (query.getResultList().size() > 0) {
                // Use the existing participant object and add him into the class
                Participant participant = (Participant) query.getSingleResult();

                // Create new programme participant
                Programmeparticipant programmePart = new Programmeparticipant();
                programmePart.setIscreator(true);
                programmePart.setRole("student");
                programmePart.setStatus("active");
                programmePart.setProgrammecode(programme);
                programmePart.setProgrammeparticipantid(Quick.generateID(em, utx, Programmeparticipant.class, "Programmeparticipantid"));
                programmePart.setParticipantid(participant); // Reuse existing participant

                //Insert into db
                db.insert(programmePart);
            } // If no
            else {
                // Reject
                System.out.println("Not participating in the institution");
                servlet.toServlet("JoinProgramme");
                return;
            }
        } // Not associated with an institution
        else {
            // Since not associated with a programme, create new participant object
            Participant participant = new Participant();
            participant.setDateadded(Calendar.getInstance().getTime());
            participant.setEducatorrole("student");
            participant.setStatus("active");
            participant.setParticipantid(Quick.generateID(em, utx, Participant.class, "Participantid"));
            participant.setUserid(user);

            db.insert(participant);

            // Create new programme participant
            Programmeparticipant programmePart = new Programmeparticipant();
            programmePart.setIscreator(true);
            programmePart.setRole("student");
            programmePart.setStatus("active");
            programmePart.setProgrammecode(programme);
            programmePart.setProgrammeparticipantid(Quick.generateID(em, utx, Programmeparticipant.class, "Programmeparticipantid"));
            programmePart.setParticipantid(participant); // Use the newly created participant
            db.insert(programmePart);
            
            System.out.println("Programme successfully joined");
            servlet.toServlet("Programme?id=" + programme.getProgrammecode());
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
