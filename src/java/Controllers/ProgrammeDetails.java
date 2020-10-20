/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;
import Models.*;
import Util.DB;
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
@WebServlet(name = "ProgrammeDetails", urlPatterns = {"/ProgrammeDetails"})
public class ProgrammeDetails extends HttpServlet {

    @PersistenceContext
    EntityManager em;
    
    @Resource
    private UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Important utility classes
        Servlet servlet = new Servlet(request, response);
        DB db = new DB(em, utx);
        
        // Get the programme from DB
        String programmeCode = servlet.getQueryStr("programme");
        
        // Get the programme from DB where this person is participating inside it and is a programme leader
        Query query = em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where p.userid = ? and pg.programmecode = ? and pg.programmecode = ppa.programmecode and ppa.participantid = p.participantid and (p.educatorrole = 'programmeLeader' or p.educatorrole = 'institutionAdmin')", Models.Programme.class);
        query.setParameter(1, Server.getUser(request, response).getUserid());
        query.setParameter(2, programmeCode);
        
        
        // If invalid course code
        if(query.getResultList().size() == 0) { 
            System.out.println("Invalid programme code");
           servlet.toServlet("Dashboard");
            return;
        } else{
            Models.Programme programme = (Programme) query.getSingleResult();
            
            // Course is valid, display data
            servlet.putInJsp("programme", programme);
            servlet.servletToJsp("programmeDetails.jsp");
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
