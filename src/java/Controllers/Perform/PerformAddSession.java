/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Session;
import Util.DB;
import Util.Errors;
import Util.Quick;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformAddSession", urlPatterns = {"/PerformAddSession"})
public class PerformAddSession extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Objects
        Servlet servlet = new Servlet(request, response);
        Models.Users user = Server.getUser(request, response);
        Models.Class classroom = new Models.Class();
        Models.Classparticipant classparticipant = new Models.Classparticipant();
        DB db = new DB(em, utx);

        // Get class code
        String classid = servlet.getQueryStr("id");

        // Fetch from db
        try {
            classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa,participant p where c.classid = ? and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role = 'teacher'", Models.Class.class).setParameter(1, classid).setParameter(2, user.getUserid()).getSingleResult();
            classparticipant = (Models.Classparticipant) em.createNativeQuery("select cpa.* from class c, classparticipant cpa,participant p where c.classid = ? and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role = 'teacher'", Models.Classparticipant.class).setParameter(1, classid).setParameter(2, user.getUserid()).getSingleResult();

        } catch (Exception ex) {
            // Error means no result, redirect to classroom page
            servlet.toServlet("Class?id=" + classid);
        }

        // Validations go here
        if (servlet.getQueryStr("venue") == null || servlet.getQueryStr("date") == null || servlet.getQueryStr("startTime") == null || servlet.getQueryStr("endTime") == null || servlet.getQueryStr("venue").trim().isEmpty() || servlet.getQueryStr("date").trim().isEmpty() || servlet.getQueryStr("startTime").trim().isEmpty() || servlet.getQueryStr("endTime").trim().isEmpty()) {
            // Has null data
            System.out.println("Null fields!");
            Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("AddSession?id=" + classid);
            return;
        }

        // Get from form
        String venue = servlet.getQueryStr("venue");
        DateTime startDate = DateTime.parse(servlet.getQueryStr("date"));
        DateTime endDate = DateTime.parse(servlet.getQueryStr("date"));
        String startTime = servlet.getQueryStr("startTime");
        String endTime = servlet.getQueryStr("endTime");

        // Getting startTime
        int startHour = Integer.parseInt(startTime.split(":")[0]);
        int startMinute = Integer.parseInt(startTime.split(":")[1]);

        // Getting endTime
        int endHour = Integer.parseInt(endTime.split(":")[0]);
        int endMinute = Integer.parseInt(endTime.split(":")[1]);

        // Configure startDate and endDate with the times
        startDate = startDate.withHourOfDay(startHour).withMinuteOfHour(startMinute);
        endDate = endDate.withHourOfDay(endHour).withMinuteOfHour(endMinute);

        // Check for invalid dates
        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
            System.out.println("Invalid dates!");
            Errors.respondSimple(request.getSession(), "Starting time must be before the ending time.");
            servlet.toServlet("AddSession?id=" + classid);
            return;
        }

        // For debugging purposes
        DateTimeFormatter fmt = DateTimeFormat.forPattern("d/MM/YYYY H:m");
        System.out.println(startDate.toString(fmt) + " until " + endDate.toString(fmt));

        // Check to see if this time and place already booked
        //NOTE: need to check venue id also
        Query query = null;

        // If this is a standalone class, prevent double booking from this class' scope only
        if (classroom.getCoursecode() == null) {
            query = em.createNativeQuery("select s.* from session s where  (s.startTime between ? and ? ) or ( s.endTime between ? and ? ) and s.classid = ?");
            query.setParameter(1, startDate.toDate());
            query.setParameter(2, endDate.toDate());
            query.setParameter(3, startDate.toDate());
            query.setParameter(4, endDate.toDate());
            query.setParameter(5, classid);
        } else {
            // Check double booking based on venue ID
        }

        if (query.getResultList().size() > 0) {
            System.out.println("This session already booked!");
            Errors.respondSimple(request.getSession(), "The specified venue and/or time slot is occupied.");
            servlet.toServlet("AddSession?id=" + classid);
            return;
        } else {
            // No errors, create new Session
            Session session = new Session();
            session.setSessionid(Quick.generateID(em, utx, Session.class, "sessionid"));
            session.setIsreplacement(false);
            session.setStarttime(startDate.toDate());
            session.setEndtime(endDate.toDate());
            session.setClassid(classroom);
            session.setCreatorid(classparticipant);

            // Put in db
            db.insert(session);

            System.out.println("New session created");

            servlet.toServlet("Sessions?id=" + classid);
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
