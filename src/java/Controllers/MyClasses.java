/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.*;
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
import Util.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author mast3
 */
@WebServlet(name = "MyClasses", urlPatterns = {"/MyClasses"})
public class MyClasses extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Servlet serve = new Servlet(request, response);
        DB db = new DB(em, utx);
        Users user = Server.getUser(request, response);

        String output = "";

        // To get all classes
        Query query = em.createNativeQuery("select distinct c.* from Class c, Users u, Classparticipant cpa, Participant p where cpa.classid = c.classid and u.userid = p.userid and p.participantid = cpa.participantid and u.userid = ?", Models.Class.class);
        
        query.setParameter(1, user.getUserid());
        ArrayList<Models.Class> classList = db.getList(Models.Class.class, query);
        
        System.out.println(classList.size());
        for (int i = 0; i < classList.size(); i++) {

            if (i == 0) {
                // Start with new row
                output += "<div class='row'>";
            }

            if (i % 2 == 0 && i > 0) {
                // Break new row for every 2 classes
                output += "</div>";
                output += "<div class='row'>";
            }

            // Get the teacher
          Users teacher =  db.getList(Models.Users.class,  em.createNativeQuery("select u.* from Class c, Classparticipant cpa, Participant p, Users u where c.classid = cpa.classid and cpa.role = ? and p.participantid = cpa.participantid and u.userid = p.userid", Models.Users.class).setParameter(1, "teacher")).get(0);
            
            output += "<div class='class' id='orange' onclick=\"location.href = 'ClassDetails?class=" + classList.get(i).getClassid() +"';\">"
                    + "<img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>"
                    + "<div class='details'>"
                    + "<div class='top-details'>"
                    + "<a class='id'>" + classList.get(i).getClassid() + "</a>"
                    + "<a class='tutor'>" + teacher.getName() + "</a>"
                    + "</div>"
                    + " <a class='name'>" + classList.get(i).getClasstitle() + "</a>"
                    + "<a class='description'>" + (classList.get(i).getDescription() == null ? "No description" : classList.get(i).getDescription()) + "</a>"
                    + "</div>"
                    + " </div>";

        }

        // For every participating class/course/institution/programme
//        for (Participant participant : user.getParticipantCollection()) {
//
//            Models.Class classroom[] = new Models.Class[participant.getClassparticipantCollection().size()];
//
//            int classCount = 0;
//
//            // For every class 
//            for (Classparticipant cpa : participant.getClassparticipantCollection()) {
//                classList.add(cpa.getClassid());
//                
//            }
//
//            
//        }
        // TODO: If a parameter of course/institution/programme is given, use that to display all classes within
        serve.putInJsp("output", output);
        serve.servletToJsp("myClasses.jsp");
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
