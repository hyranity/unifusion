/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Util.Errors;
import Util.Server;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "PerformEditAnnouncement", urlPatterns = {"/PerformEditAnnouncement"})
public class PerformEditAnnouncement extends HttpServlet {

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

        // Get form data
        String title = servlet.getQueryStr("title");
        String message = servlet.getQueryStr("message");
        String id = servlet.getQueryStr("id");
        String code = servlet.getQueryStr("code");
        String type = servlet.getQueryStr("type");
        
         // Validate null fields
        if (title == null || message == null  || type == null || id == null  || code == null  || title.trim().isEmpty() || message.trim().isEmpty()  || code.trim().isEmpty()  || id.trim().isEmpty()  || type.trim().isEmpty()) {
            // Has null data
            System.out.println("Null fields!");
            Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("EditAnnouncement?id=" + id + "&code=" + code + "&type=" + type +"");
            return;
        }
        
        // Get announcement
       Models.Announcement announcement = db.getSingleResult("announcementid", code, Models.Announcement.class);
       
       if(announcement == null){
           // Announcement doesn't exist
            System.out.println("Announcement doesn't exist");
            Errors.respondSimple(request.getSession(), "Announcement doesn't exist");
            servlet.toServlet("EditAnnouncement?id=" + id + "&code=" + code + "&type=" + type +"");
            return;
       }
       
       // Allow only poster to edit
       if(!announcement.getPosterid().getUserid().getUserid().equals(user.getUserid())){
            System.out.println("Unauthorized user");
            Errors.respondSimple(request.getSession(), "You are not allowed to edit this.");
            servlet.toServlet("EditAnnouncement?id=" + id + "&code=" + code + "&type=" + type +"");
            return;
       }
       
       // Perform update
       announcement.setTitle(title);
       announcement.setMessage(message);
       
       
       db.update(announcement);
       
        System.out.println("Announcement updated successfully");
        servlet.toServlet("AnnouncementDetails?id=" + id + "&code=" + code + "&type=" + type +"");
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
