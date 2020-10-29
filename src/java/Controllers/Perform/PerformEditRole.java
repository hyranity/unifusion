/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Participant;
import Util.Server;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
@WebServlet(name = "PerformEditRole", urlPatterns = {"/PerformEditRole"})
public class PerformEditRole extends HttpServlet {
    
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

        // Objects
        Participant self = new Participant();
        Participant staff = new Participant();

        // Get query strings
        String role = servlet.getQueryStr("role");
        String id = servlet.getQueryStr("id");
        String institutionId = servlet.getQueryStr("institution");
        
        try {
            self = (Participant) em.createNativeQuery("select p.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ?", Participant.class).setParameter(1, institutionId).setParameter(2, user.getUserid()).getSingleResult();
            staff = (Participant) em.createNativeQuery("select p.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.participantid = ?", Participant.class).setParameter(1, institutionId).setParameter(2, id).getSingleResult();
            
        } catch (NoResultException ex) {
            ;
            servlet.toServlet("Dashboard");
            return;
        }

        // Update role
        if (role.equalsIgnoreCase("classTeacher")) {
            staff.setEducatorrole("classTeacher");
        } else if (role.equalsIgnoreCase("courseLeader")) {
            staff.setEducatorrole("courseLeader");
        } else if (role.equalsIgnoreCase("programmeLeader")) {
            staff.setEducatorrole("programmeLeader");
        } else if (role.equalsIgnoreCase("institutionAdmin")) {
            staff.setEducatorrole("institutionAdmin");
        } else {
            // Invalid role
            servlet.toServlet("Dashboard");
            return;
        }

        // Update in db
        db.update(staff);

        // Redirect
        servlet.toServlet("MemberList?type=institution&id=" + institutionId);
        
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
