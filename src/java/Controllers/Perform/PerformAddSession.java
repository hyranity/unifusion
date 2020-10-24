/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Session;
import Models.Venue;
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
        Session session = new Session();
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
            return;
        }

        // Validations go here
        if (((servlet.getQueryStr("tempVenue") == null && servlet.getQueryStr("venueId") == null) || servlet.getQueryStr("date") == null || servlet.getQueryStr("startTime") == null || servlet.getQueryStr("endTime") == null) || (servlet.getQueryStr("tempVenue").trim().isEmpty() && servlet.getQueryStr("venueId").trim().isEmpty()) || servlet.getQueryStr("date").trim().isEmpty() || servlet.getQueryStr("startTime").trim().isEmpty() || servlet.getQueryStr("endTime").trim().isEmpty()) {
            // Has null data
            System.out.println("Null fields!");
            Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
            servlet.toServlet("AddSession?id=" + classid);
            return;
        }

        // Get from form
        String tempVenue = servlet.getQueryStr("tempVenue");
        String venueId = servlet.getQueryStr("venueId");
        DateTime startDate = DateTime.parse(servlet.getQueryStr("date"));
        DateTime endDate = DateTime.parse(servlet.getQueryStr("date"));
        String startTime = servlet.getQueryStr("startTime");
        String endTime = servlet.getQueryStr("endTime");
        //  isVirtualVenue = servlet.getQueryStr("isVirtualVenue") != null;

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
        

        // Get class' institution
        Query institutionQuery = em.createNativeQuery("select i.* from class cl, course c, programme p, institution i where cl.classid = ? and cl.coursecode = c.COURSECODE and c.PROGRAMMECODE = p.PROGRAMMECODE and p.INSTITUTIONCODE = i.INSTITUTIONCODE", Models.Institution.class).setParameter(1, classid);
        
        // If a venue is provided AND class belongs in an institution 
        if (((venueId != null && !venueId.trim().isEmpty()) || tempVenue.trim().isEmpty()) && institutionQuery.getResultList().size() > 0) {

            // Get venue by Id
            Venue venue = db.getSingleResult("venueid", venueId, Venue.class);

            // If venue == null, show error
            if (venue == null) {
                System.out.println("Invalid venue ID!");
                Errors.respondSimple(request.getSession(), "Selected venue does not exist.");
                servlet.toServlet("AddSession?id=" + classid);
                return;
            }

            // Check double booking based on venue ID
            System.out.println("Performing algorithm to prevent double booking based on venue ID");
            query = em.createNativeQuery("select s.* from session s where  ((s.startTime between ? and ? ) or ( s.endTime between ? and ? )) and s.classid = ? and s.venueid = ?");
            query.setParameter(1, startDate.toDate());
            query.setParameter(2, endDate.toDate());
            query.setParameter(3, startDate.toDate());
            query.setParameter(4, endDate.toDate());
            query.setParameter(5, classid);
            query.setParameter(6, venue.getVenueid());
            
            // Redirect if session ady booked
            if (query.getResultList().size() > 0) {
                System.out.println("This venue already booked!");
                Errors.respondSimple(request.getSession(), "The specified venue is occupied.");
                servlet.toServlet("AddSession?id=" + classid);
                return;
            }
           
            // Set specified venue into the session
            session.setVenueid(venue);
        } // If this class does NOT have institution AND have temp venue, prevent double booking from this class' scope only
        else if ((tempVenue != null &&  !tempVenue.trim().isEmpty()) || institutionQuery.getResultList().size() == 0) {
            query = em.createNativeQuery("select s.* from session s where  (s.startTime between ? and ? ) or ( s.endTime between ? and ? ) and s.classid = ?");
            query.setParameter(1, startDate.toDate());
            query.setParameter(2, endDate.toDate());
            query.setParameter(3, startDate.toDate());
            query.setParameter(4, endDate.toDate());
            query.setParameter(5, classid);

            // Redirect if session ady booked
            if (query.getResultList().size() > 0) {
                System.out.println("This session already booked!");
                Errors.respondSimple(request.getSession(), "The specified time slot is occupied.");
                servlet.toServlet("AddSession?id=" + classid);
                return;
            }

            // Since tempVenue is provided, use that
            session.setTempvenuename(tempVenue);
        } else {
            // It must be either two choices above; else, there is an error
            System.out.println("Could not process venue");
            Errors.respondSimple(request.getSession(), "Something went wrong.");
            servlet.toServlet("AddSession?id=" + classid);
            return;
        }

        // No errors, create new Session
        session.setIsreplacement(false);
        session.setStarttime(startDate.toDate());
        session.setEndtime(endDate.toDate());
        session.setClassid(classroom);
        session.setCreatorid(classparticipant);

        // Generate unique ID
        String sessionId = Quick.generateStr(6); // Generate random string

        // Check for duplicate, reroll if found
        while (em.createNativeQuery("select * from session where sessionid = ?").setParameter(1, sessionId).getResultList().size() > 0) {
            sessionId = Quick.generateStr(6); // Regenerate random string
        }

        // Set ID
        session.setSessionid(sessionId);

        // Put in db
        db.insert(session);

        System.out.println("New session created");

        servlet.toServlet("Sessions?id=" + classid);

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
