/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Institutionparticipant;
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
@WebServlet(name = "PerformJoinInstitution", urlPatterns = {"/PerformJoinInstitution"})
public class PerformJoinInstitution extends HttpServlet {

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
        String institutionCode = servlet.getQueryStr("institutionCode");

        // Fetch from db
        Models.Institution institution= db.getSingleResult("institutioncode", institutionCode, Models.Institution.class);
        
          // If no institution is found
        if (institution == null) {
            System.out.println("No Institution is found");
            servlet.toServlet("JoinInstitution");
            return;
        }
        
         // Since not associated with a programme, create new participant object
            Participant participant = new Participant();
            participant.setDateadded(Calendar.getInstance().getTime());
            participant.setEducatorrole("student");
            participant.setStatus("active");
            participant.setParticipantid(Quick.generateID(em, utx, Participant.class, "Participantid"));
            participant.setUserid(user);

            db.insert(participant);

            // Create new programme participant
            Institutionparticipant institutionPart = new Institutionparticipant();
            institutionPart.setIscreator(true);
            institutionPart.setRole("student");
            institutionPart.setStatus("active");
            institutionPart.setInstitutioncode(institution);
            institutionPart.setInstitutionparticipantid(Quick.generateID(em, utx, Institutionparticipant.class, "Institutionparticipantid"));
            institutionPart.setParticipantid(participant); // Use the newly created participant
            db.insert(institutionPart);
            
            System.out.println("Institution successfully joined");
            servlet.toServlet("Institution?id=" + institution.getInstitutioncode());
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
