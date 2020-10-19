/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Util.Errors;
import Util.Quick;
import Util.Server;
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
@WebServlet(name = "PerformDeleteAnnouncement", urlPatterns = {"/PerformDeleteAnnouncement"})
public class PerformDeleteAnnouncement extends HttpServlet {
    
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

        // Announcement object
        Models.Announcement announcement = new Models.Announcement();

        // Get query strings
        String type = servlet.getQueryStr("type");
        String id = servlet.getQueryStr("id");
        String code = servlet.getQueryStr("code"); // announcement id

        // Get announcement
        announcement = db.getSingleResult("announcementid", code, Models.Announcement.class);

        // Allow only poster to edit
        if (!announcement.getPosterid().getUserid().getUserid().equals(user.getUserid())) {
            System.out.println("Unauthorized user");
            Errors.respondSimple(request.getSession(), "You are not allowed to edit this.");
            servlet.toServlet("Announcementdetails?id=" + id + "&code=" + code + "&type=" + type + "");
            return;
        }

        // Delete the uploaded files
        if (announcement.getFileurl() != null) {
            String[] files = announcement.getFileurl().split("\\|");
            
            for (String file : files) {
                String name = file.substring(file.indexOf("\\") + 1, file.length());
                
                Quick.deleteFile(file);
            }
        }
        
        // Delete the actual announcement
        db.delete(announcement);
        
        // Redirect to announcement page
        servlet.toServlet("Announcement?type=" + type +"&id=" + id);
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
