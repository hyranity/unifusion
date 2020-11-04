/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Util.Quick;
import Util.Server;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import java.util.regex.*;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;

/**
 *
 * @author mast3
 */
@WebServlet(name = "Chatbot", urlPatterns = {"/Chatbot"})
public class Chatbot extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    // Commands
    String retrieveCommand = "(show me|give me|display|show|get)( all)*.*";
    String questions = "(how many|what (time|is the time)).*";
    String create = "(create|make|new|develop)( a| an)? ";
    String createEducation = create + "(class|course|institution|programme).*";
    String createClassObjects = create + "(announcement|session|assignment).*";

    // Today
    String today = new DateTime().toString(DateTimeFormat.forPattern("MMM d', ' YYYY "));

    // Util objects
    Util.Servlet servlet;
    Util.DB db = new Util.DB(em, utx);
    Models.Users user;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        servlet = new Util.Servlet(request, response);
        user = Server.getUser(request, response);

        // Redirect guests
        if (user == null) {
            servlet.toServlet("Login");
            return;
        }

        // Read input
        String input = servlet.getQueryStr("query");

        if (input != null && !input.trim().isEmpty()) {
            // Process query

            input = input.replaceAll("\"", "\'");
            input(input);
            servlet.putInJsp("query", input);
            servlet.servletToJsp("chatbot.jsp?refresh=true");
            return;
        }

        // If no query
        if (input == null || input == "") {
            doBriefing();
        }

        // Redirect
        servlet.servletToJsp("chatbot.jsp");

    }
    
    // Daily briefing
     // Get today's classes
    public void doBriefing() {
        // Get all class sessions
        List<Models.Session> sessions = em.createNativeQuery("select s.* from session s, classparticipant cpa, participant p where s.classid = cpa.classid  and cpa.participantid = p.participantid and p.userid = ? and current_date = date(s.starttime) order by s.starttime asc", Models.Session.class).setParameter(1, user.getUserid()).getResultList();

        // If no data
        if (sessions.isEmpty()) {
            replyChat("It seems that you're free today!");
        }

        // To format dates
        DateTimeFormatter timeFmt = DateTimeFormat.forPattern("h:mm a'");
        DateTimeFormatter dateFmt = DateTimeFormat.forPattern("MMM d', ' YYYY");
        
        String output = addStats(new DateTime().toString(dateFmt), "Currently " + new DateTime().toString(timeFmt));

         output += addChat("Hey there! You have " + sessions.size() + " classes today");

        // For each session
        for (Models.Session session : sessions) {

            Models.Class classroom = session.getClassid();

            String startTime = new DateTime(session.getStarttime()).toString(timeFmt);
            String endTime = new DateTime(session.getEndtime()).toString(timeFmt);

            String status = "";
            // If already started
            if (new DateTime().isAfter(new DateTime(session.getStarttime()))) {

                // If already ended
                if (new DateTime().isAfter(new DateTime(session.getEndtime()))) {
                    status = "PAST";
                } // Havent ended
                else {
                    status = "ONGOING";
                }
            } // Havent started
            else {
                status = "UPCOMING";
            }

            output += "<div class='result display'  onclick=\"window.location.href='SessionDetails?id=" + classroom.getClassid() + "&code=" + session.getSessionid() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>" + status + "</a>\n"
                    + "                      <a class='name'>" + classroom.getClasstitle() + "</a>\n"
                    + "                      <a class='subname'>" + startTime + " - " + endTime + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);
    }

    // Show top 3 announcements
    public String showAnnouncements(String from) {
        String output = "";

        // Get
        List<Models.Announcement> fromInstitution = em.createNativeQuery("select a.* from announcement a, institution i, institutionparticipant ipa, participant p where a.institutioncode = ? and a.institutioncode = i.institutioncode and i.institutioncode = ipa.institutioncode and ipa.participantid = p.participantid and p.userid = ? order by dateannounced desc fetch first 3 rows only", Models.Announcement.class).setParameter(1, from).setParameter(2, user.getUserid()).getResultList();
        List<Models.Announcement> fromProgramme = em.createNativeQuery("select a.* from announcement a, programme pg, programmeparticipant ppa, participant p where a.programmecode = ? and a.programmecode = pg.programmecode and pg.programmecode = ppa.programmecode and ppa.participantid = p.participantid and p.userid = ? order by dateannounced desc fetch first 3 rows only", Models.Announcement.class).setParameter(1, from).setParameter(2, user.getUserid()).getResultList();
        List<Models.Announcement> fromCourse = em.createNativeQuery("select a.* from announcement a, course c, courseparticipant cpa, participant p where a.coursecode = ? and a.coursecode = c.coursecode and c.coursecode = cpa.coursecode and cpa.participantid = p.participantid and p.userid = ? order by dateannounced desc fetch first 3 rows only", Models.Announcement.class).setParameter(1, from).setParameter(2, user.getUserid()).getResultList();
        List<Models.Announcement> fromClass = em.createNativeQuery("select a.* from announcement a, class c, classparticipant cpa, participant p where a.classid = ? and a.classid = c.classid and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? order by dateannounced desc fetch first 3 rows only", Models.Announcement.class).setParameter(1, from).setParameter(2, user.getUserid()).getResultList();

        return displayAnnouncements(fromInstitution, fromProgramme, fromCourse, fromClass);
    }

    // Show top 3 announcements
    public String showAnnouncements() {
        String output = "";

        // Get
        List<Models.Announcement> fromInstitution = em.createNativeQuery("select a.* from announcement a, institution i, institutionparticipant ipa, participant p where a.institutioncode = i.institutioncode and  i.institutioncode = ipa.institutioncode and ipa.participantid = p.participantid and p.userid = ? order by dateannounced desc fetch first 3 rows only", Models.Announcement.class).setParameter(1, user.getUserid()).getResultList();
        List<Models.Announcement> fromProgramme = em.createNativeQuery("select a.* from announcement a, programme pg, programmeparticipant ppa, participant p where a.programmecode = pg.programmecode and pg.programmecode = ppa.programmecode and ppa.participantid = p.participantid and p.userid = ? order by dateannounced desc fetch first 3 rows only", Models.Announcement.class).setParameter(1, user.getUserid()).getResultList();
        List<Models.Announcement> fromCourse = em.createNativeQuery("select a.* from announcement a, course c, courseparticipant cpa, participant p where a.coursecode = c.coursecode and c.coursecode = cpa.coursecode and cpa.participantid = p.participantid and p.userid = ? order by dateannounced desc fetch first 3 rows only", Models.Announcement.class).setParameter(1, user.getUserid()).getResultList();
        List<Models.Announcement> fromClass = em.createNativeQuery("select a.* from announcement a, class c, classparticipant cpa, participant p where a.classid = c.classid and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? order by dateannounced desc fetch first 3 rows only", Models.Announcement.class).setParameter(1, user.getUserid()).getResultList();

        return displayAnnouncements(fromInstitution, fromProgramme, fromCourse, fromClass);
    }

    public String displayAnnouncements(List<Models.Announcement> fromInstitution, List<Models.Announcement> fromProgramme, List<Models.Announcement> fromCourse, List<Models.Announcement> fromClass) {
        String output = "";

        // Display in categories
        if (fromInstitution.size() > 0) {
            output += addChat("Institution announcements");

            for (Models.Announcement announcement : fromInstitution) {
                output += "<div class='result display'  onclick=\"window.location.href='AnnouncementDetails?id=" + announcement.getInstitutioncode().getInstitutioncode() + "&code=" + announcement.getAnnouncementid() + "&type=institution'\">\n"
                        + "                  <div class='top'>\n"
                        + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                        + "                    <div class='text'>\n"
                        + "                      <a class='type'>" + announcement.getInstitutioncode().getName() + "</a>\n"
                        + "                      <a class='name'>" + announcement.getTitle() + "</a>\n"
                        + "                      <a class='subname'>" + Quick.timeSince(announcement.getDateannounced()) + "</a>\n"
                        + "                    </div>\n"
                        + "                  </div>\n"
                        + "                </div>";
            }
        }
        if (fromProgramme.size() > 0) {
            output += addChat("Programme announcements");

            for (Models.Announcement announcement : fromProgramme) {
                output += "<div class='result display'  onclick=\"window.location.href='AnnouncementDetails?id=" + announcement.getProgrammecode().getProgrammecode() + "&code=" + announcement.getAnnouncementid() + "&type=programme'\">\n"
                        + "                  <div class='top'>\n"
                        + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                        + "                    <div class='text'>\n"
                        + "                      <a class='type'>" + announcement.getProgrammecode().getTitle() + "</a>\n"
                        + "                      <a class='name'>" + announcement.getTitle() + "</a>\n"
                        + "                      <a class='subname'>" + Quick.timeSince(announcement.getDateannounced()) + "</a>\n"
                        + "                    </div>\n"
                        + "                  </div>\n"
                        + "                </div>";
            }
        }
        if (fromCourse.size() > 0) {
            output += addChat("Course announcements");

            for (Models.Announcement announcement : fromCourse) {
                output += "<div class='result display'  onclick=\"window.location.href='AnnouncementDetails?id=" + announcement.getCoursecode().getCoursecode() + "&code=" + announcement.getAnnouncementid() + "&type=course'\">\n"
                        + "                  <div class='top'>\n"
                        + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                        + "                    <div class='text'>\n"
                        + "                      <a class='type'>" + announcement.getCoursecode().getTitle() + "</a>\n"
                        + "                      <a class='name'>" + announcement.getTitle() + "</a>\n"
                        + "                      <a class='subname'>" + Quick.timeSince(announcement.getDateannounced()) + "</a>\n"
                        + "                    </div>\n"
                        + "                  </div>\n"
                        + "                </div>";
            }
        }
        if (fromClass.size() > 0) {
            output += addChat("Class announcements");

            for (Models.Announcement announcement : fromClass) {
                output += "<div class='result display'  onclick=\"window.location.href='AnnouncementDetails?id=" + announcement.getClassid().getClassid() + "&code=" + announcement.getAnnouncementid() + "&type=class'\">\n"
                        + "                  <div class='top'>\n"
                        + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                        + "                    <div class='text'>\n"
                        + "                      <a class='type'>" + announcement.getClassid().getClasstitle() + "</a>\n"
                        + "                      <a class='name'>" + announcement.getTitle() + "</a>\n"
                        + "                      <a class='subname'>" + Quick.timeSince(announcement.getDateannounced()) + "</a>\n"
                        + "                    </div>\n"
                        + "                  </div>\n"
                        + "                </div>";
            }
        }

        // If no results
        if (output == "") {
            replyChat("Sorry, no announcements could be found");
            return "";
        }

        servlet.putInJsp("result", output);

        return output;
    }

    // Showing stats that can be added to other info
    public String addStats(String heading, String subheading) {
        String output = "<div class='result stat'>\n"
                + "                  <div class='top'>\n"
                + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/423/423786.svg'>\n"
                + "                    <div class='text'>\n"
                + "                      <a class='stat'>" + heading + "</a>\n"
                + "                      <a class='desc'>" + subheading + "</a>\n"
                + "                    </div>\n"
                + "                  </div>\n"
                + "                </div>";

        return output;
    }

    // Showing stats only
    public void replyStats(String heading, String subheading) {
        String output = "<div class='result stat'>\n"
                + "                  <div class='top'>\n"
                + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/423/423786.svg'>\n"
                + "                    <div class='text'>\n"
                + "                      <a class='stat'>" + heading + "</a>\n"
                + "                      <a class='desc'>" + subheading + "</a>\n"
                + "                    </div>\n"
                + "                  </div>\n"
                + "                </div>";

        servlet.putInJsp("result", output);
    }

    // Get today's classes
    public List<Models.Session> getTodayClasses() {
        // Get all class sessions
        List<Models.Session> sessions = em.createNativeQuery("select s.* from session s, classparticipant cpa, participant p where s.classid = cpa.classid  and cpa.participantid = p.participantid and p.userid = ? and current_date = date(s.starttime) order by s.starttime asc", Models.Session.class).setParameter(1, user.getUserid()).getResultList();

        // If no data
        if (sessions.isEmpty()) {
            replyChat("It seems that you're free today!");
            return new ArrayList<Models.Session>();
        }

        // To format dates
        DateTimeFormatter timeFmt = DateTimeFormat.forPattern("h:mm a'");
        DateTimeFormatter dateFmt = DateTimeFormat.forPattern("MMM d', ' YYYY ");

        String startDate = new DateTime().toString(dateFmt);

        String output = addChat("Here's today's schedule");

        output += addStats(sessions.size() + " classes", startDate);

        // For each session
        for (Models.Session session : sessions) {

            Models.Class classroom = session.getClassid();

            String startTime = new DateTime(session.getStarttime()).toString(timeFmt);
            String endTime = new DateTime(session.getEndtime()).toString(timeFmt);

            String status = "";
            // If already started
            if (new DateTime().isAfter(new DateTime(session.getStarttime()))) {

                // If already ended
                if (new DateTime().isAfter(new DateTime(session.getEndtime()))) {
                    status = "PAST";
                } // Havent ended
                else {
                    status = "ONGOING";
                }
            } // Havent started
            else {
                status = "UPCOMING";
            }

            output += "<div class='result display'  onclick=\"window.location.href='SessionDetails?id=" + classroom.getClassid() + "&code=" + session.getSessionid() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>" + status + "</a>\n"
                    + "                      <a class='name'>" + classroom.getClasstitle() + "</a>\n"
                    + "                      <a class='subname'>" + startTime + " - " + endTime + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);
        return sessions;
    }

    // Get all classes with FROM
    public List<Models.Class> showClasses(String from) {
        // Get from all levels
        List<Models.Class> fromInstitution = em.createNativeQuery("select c.* from class c, course cr, programme pg, institution i, institutionparticipant ipa, participant p where c.coursecode = cr.coursecode and cr.programmecode = pg.programmecode and pg.institutioncode = i.institutioncode and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ? and i.institutioncode = ?", Models.Class.class).setParameter(1, user.getUserid()).setParameter(2, from).getResultList();
        List<Models.Class> fromProgramme = em.createNativeQuery("select c.* from class c, course cr, programme pg,programmeparticipant ppa, participant p where c.coursecode = cr.coursecode and cr.programmecode = pg.programmecode and ppa.programmecode = pg.programmecode and ppa.participantid = p.participantid and p.userid = ? and pg.programmecode = ?", Models.Class.class).setParameter(1, user.getUserid()).setParameter(2, from).getResultList();
        List<Models.Class> fromCourse = em.createNativeQuery("select c.* from class c, course cr, courseparticipant cpa, participant p where c.coursecode = cr.coursecode and cpa.coursecode = cr.coursecode and cpa.participantid = p.participantid and p.userid = ? and cpa.coursecode = ?", Models.Class.class).setParameter(1, user.getUserid()).setParameter(2, from).getResultList();

        // Combine the results
        List<Models.Class> results = new ArrayList<Models.Class>();
        results.addAll(fromInstitution);
        results.addAll(fromProgramme);
        results.addAll(fromCourse);

        // Display the output
        String output = "";

        output = addChat(results.size() + " classes were found.");

        for (Models.Class classroom : results) {
            output += "<div class='result display'  onclick=\"window.location.href='Class?id=" + classroom.getClassid() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>CLASS</a>\n"
                    + "                      <a class='name'>" + classroom.getClasstitle() + "</a>\n"
                    + "                      <a class='subname'>" + classroom.getClassid() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);

        return results;
    }

    // Get all classes without FROM
    public List<Models.Class> showClasses() {
        // Get from all levels
        List<Models.Class> results = em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ?", Models.Class.class).setParameter(1, user.getUserid()).getResultList();

        // Display the output
        String output = "";

        output = addChat(results.size() + " classes were found.");

        for (Models.Class classroom : results) {
            output += "<div class='result display' onclick=\"window.location.href='Class?id=" + classroom.getClassid() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>CLASS</a>\n"
                    + "                      <a class='name'>" + classroom.getClasstitle() + "</a>\n"
                    + "                      <a class='subname'>" + classroom.getClassid() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);
        return results;
    }

    // Get all courses with FROM
    public List<Models.Course> showCourses(String from) {
        // Get from all levels
        List<Models.Course> fromInstitution = em.createNativeQuery("select cr.* from course cr, programme pg, institution i, institutionparticipant ipa, participant p where cr.programmecode = pg.programmecode and pg.institutioncode = i.institutioncode and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ? and i.institutioncode = ?", Models.Course.class).setParameter(1, user.getUserid()).setParameter(2, from).getResultList();
        List<Models.Course> fromProgramme = em.createNativeQuery("select cr.* from  course cr, programme pg,programmeparticipant ppa, participant p where cr.programmecode = pg.programmecode and ppa.programmecode = pg.programmecode and ppa.participantid = p.participantid and p.userid = ? and pg.programmecode = ?", Models.Course.class).setParameter(1, user.getUserid()).setParameter(2, from).getResultList();

        // Combine the results
        List<Models.Course> results = new ArrayList<Models.Course>();
        results.addAll(fromInstitution);
        results.addAll(fromProgramme);

        // Display the output
        String output = "";

        output = addChat(results.size() + " courses were found.");

        for (Models.Course course : results) {
            output += "<div class='result display'  onclick=\"window.location.href='Course?id=" + course.getCoursecode() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>COURSE</a>\n"
                    + "                      <a class='name'>" + course.getTitle() + "</a>\n"
                    + "                      <a class='subname'>" + course.getCoursecode() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);

        return results;
    }

    // Get all courses without FROM
    public List<Models.Course> showCourses() {
        List<Models.Course> results = em.createNativeQuery("select cr.* from course cr, courseparticipant cpa, participant p where cr.coursecode = cpa.coursecode and cpa.participantid = p.participantid and p.userid = ?", Models.Course.class).setParameter(1, user.getUserid()).getResultList();

        // Display the output
        String output = "";

        output = addChat(results.size() + " courses were found.");

        for (Models.Course course : results) {
            output += "<div class='result display' onclick=\"window.location.href='Course?id=" + course.getCoursecode() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>COURSE</a>\n"
                    + "                      <a class='name'>" + course.getTitle() + "</a>\n"
                    + "                      <a class='subname'>" + course.getCoursecode() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);
        return results;
    }

    // Get all programmes with FROM
    public List<Models.Programme> showProgrammes(String from) {
        // Get from all levels
        List<Models.Programme> results = em.createNativeQuery("select pg.* from programme pg, institution i, institutionparticipant ipa, participant p where pg.institutioncode = i.institutioncode and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ? and i.institutioncode = ?", Models.Programme.class).setParameter(1, user.getUserid()).setParameter(2, from).getResultList();

        // Display the output
        String output = "";

        output = addChat(results.size() + " programmes were found.");

        for (Models.Programme programme : results) {
            output += "<div class='result display'  onclick=\"window.location.href='Programme?id=" + programme.getProgrammecode() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>PROGRAMME</a>\n"
                    + "                      <a class='name'>" + programme.getTitle() + "</a>\n"
                    + "                      <a class='subname'>" + programme.getProgrammecode() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);
        return results;
    }

    // Get all programmes  without FROM
    public List<Models.Programme> showProgrammes() {
        List<Models.Programme> results = em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ppa.programmecode and ppa.participantid = p.participantid and p.userid = ?", Models.Programme.class).setParameter(1, user.getUserid()).getResultList();

        // Display the output
        String output = "";

        output = addChat(results.size() + " programmes were found.");

        for (Models.Programme programme : results) {
            output += "<div class='result display' onclick=\"window.location.href='Programme?id=" + programme.getProgrammecode() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>PROGRAMME</a>\n"
                    + "                      <a class='name'>" + programme.getTitle() + "</a>\n"
                    + "                      <a class='subname'>" + programme.getProgrammecode() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);
        return results;
    }

    // Get all institutions
    public List<Models.Institution> showInstitutions() {
        List<Models.Institution> results = em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ipa.institutioncode and ipa.participantid = p.participantid and p.userid = ?", Models.Institution.class).setParameter(1, user.getUserid()).getResultList();

        // Display the output
        String output = "";

        output = addChat(results.size() + " institutions were found.");

        for (Models.Institution institution : results) {
            output += "<div class='result display' onclick=\"window.location.href='Institution?id=" + institution.getInstitutioncode() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>INSTITUTION</a>\n"
                    + "                      <a class='name'>" + institution.getName() + "</a>\n"
                    + "                      <a class='subname'>" + institution.getInstitutioncode() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);
        return results;
    }

    // Getting single data
    public void getSingleClass(String id) {
        // Get from all levels
        List<Models.Class> results = em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and c.classid = ?", Models.Class.class).setParameter(1, user.getUserid()).setParameter(2, id).getResultList();

        // Display the output
        String output = "";

        if (results.size() > 0) {
            output = addChat("Here's your class");
        } else {
            output = addChat("Hmm... I couldn't find your class.");
        }

        for (Models.Class classroom : results) {
            output += "<div class='result display' onclick=\"window.location.href='Class?id=" + classroom.getClassid() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>CLASS</a>\n"
                    + "                      <a class='name'>" + classroom.getClasstitle() + "</a>\n"
                    + "                      <a class='subname'>" + classroom.getClassid() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);
    }

    public void getSingleCourse(String id) {
        List<Models.Course> results = em.createNativeQuery("select cr.* from course cr, courseparticipant cpa, participant p where cr.coursecode = cpa.coursecode and cpa.participantid = p.participantid and p.userid = ? and cr.coursecode = ?", Models.Course.class).setParameter(1, user.getUserid()).setParameter(2, id).getResultList();

        // Display the output
        String output = "";

        if (results.size() > 0) {
            output = addChat("Here's your course");
        } else {
            output = addChat("Hmm... I couldn't find your course.");
        }

        for (Models.Course course : results) {
            output += "<div class='result display' onclick=\"window.location.href='Class?id=" + course.getCoursecode() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>COURSE</a>\n"
                    + "                      <a class='name'>" + course.getTitle() + "</a>\n"
                    + "                      <a class='subname'>" + course.getCoursecode() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);
    }

    public void getSingleProgramme(String id) {
        List<Models.Programme> results = em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ppa.programmecode and ppa.participantid = p.participantid and p.userid = ? and pg.programmecode = ?", Models.Programme.class).setParameter(1, user.getUserid()).setParameter(2, id).getResultList();

        // Display the output
        String output = "";

        if (results.size() > 0) {
            output = addChat("Here's your programme");
        } else {
            output = addChat("Hmm... I couldn't find your programme.");
        }

        for (Models.Programme programme : results) {
            output += "<div class='result display' onclick=\"window.location.href='Programme?id=" + programme.getProgrammecode() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>PROGRAMME</a>\n"
                    + "                      <a class='name'>" + programme.getTitle() + "</a>\n"
                    + "                      <a class='subname'>" + programme.getProgrammecode() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);
    }

    public void getSingleInstitution(String id) {
        List<Models.Institution> results = em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ipa.institutioncode and ipa.participantid = p.participantid and p.userid = ? and i.institutioncode = ?", Models.Institution.class).setParameter(1, user.getUserid()).setParameter(2, id).getResultList();

        // Display the output
        String output = "";

        if (results.size() > 0) {
            output = addChat("Here's your institution");
        } else {
            output = addChat("Hmm... I couldn't find your institution.");
        }

        for (Models.Institution institution : results) {
            output += "<div class='result display' onclick=\"window.location.href='Institution?id=" + institution.getInstitutioncode() + "'\">\n"
                    + "                  <div class='top'>\n"
                    + "                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>\n"
                    + "                    <div class='text'>\n"
                    + "                      <a class='type'>INSTITUTION</a>\n"
                    + "                      <a class='name'>" + institution.getName() + "</a>\n"
                    + "                      <a class='subname'>" + institution.getInstitutioncode() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "                </div>";
        }

        servlet.putInJsp("result", output);
    }

    public String addChat(String text) {
        String output = " <div class='result chat'>\n"
                + "                  <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/1041/1041916.svg'>\n"
                + "                  <a class='text'>" + text + "</a>\n"
                + "                </div>";

        return output;
    }

    public void replyChat(String text) {
        String output = " <div class='result chat'>\n"
                + "                  <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/1041/1041916.svg'>\n"
                + "                  <a class='text'>" + text + "</a>\n"
                + "                </div>";

        servlet.putInJsp("result", output);
    }

    public void addCreateEducationComponent(String type, String id, String name, String addServletName) {
        if (id != null) {
            id = id.replaceAll("\"", "");
            id = id.replaceAll("\'", "");
        }
        String idHref = id == null ? "" : id.trim().isEmpty() ? "" : id;
        String titleHref = name == null ? "" : name.trim().isEmpty() ? "" : name;

        // Replaces apostrophe
        titleHref = titleHref.replaceAll("\'", "%27");

        String output = "  <div class='result action' onclick='window.location.href=\"" + addServletName + "?id=" + idHref + "&title=" + titleHref + "\"'>\n"
                + "            <div class='top'>\n"
                + "              <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/867/867855.svg'>\n"
                + "              <div class='text'>\n"
                + "                <a class='type'>ACTION</a>\n"
                + "                <a class='desc'>Create a <span>" + type + "</span></a>\n"
                + "              </div>\n"
                + "            </div>\n";

        if (id != null || name != null) {
            output += "     <div class='bottom'>\n"
                    + "              <a class='desc'>With the following details:</a>\n";

            if (id != null) {
                output += "      <div class='item'>\n"
                        + "                <a class='label'>ID</a>\n"
                        + "                <a class='value'>" + id + "</a>\n"
                        + "              </div>\n";
            }

            if (name != null) {
                output += "      <div class='item'>\n"
                        + "                <a class='label'>NAME</a>\n"
                        + "                <a class='value'>" + name + "</a>\n"
                        + "              </div>\n";
            }

            output += "          </div>";
        }

        output += "            </div>  \n";

        servlet.putInJsp("result", output);
    }

    public void addCreateAssignment(String classId, String title, String description) {
        // Get class results
        List<Models.Class> classList = em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = ? and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role='teacher'", Models.Class.class).setParameter(1, classId).setParameter(2, user.getUserid()).getResultList();

        // To hold output
        String output = "";

        // If empty
        if (classList.size() == 0) {
            replyChat("Seems like this class doesn't exist");
            return;
        }

        // A result for each
        for (Models.Class classroom : classList) {
            String titleHref = title == null ? "" : title.trim().isEmpty() ? "" : title;
            String descHref = description == null ? "" : description.trim().isEmpty() ? "" : description;

            output = "  <div class='result action' onclick='window.location.href=\"AddAssignment?title=" + titleHref + "&desc=" + descHref + "&id=" + classId + "\"'>\n"
                    + "            <div class='top'>\n"
                    + "              <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/3324/3324859.svg'>\n"
                    + "              <div class='text'>\n"
                    + "                <a class='type'>ACTION</a>\n"
                    + "                <a class='desc'>Create an assignment for <span>" + classId + "</span></a>\n"
                    + "              </div>\n"
                    + "            </div>\n";

            if (title != null || description != null) {
                output += "     <div class='bottom'>\n"
                        + "              <a class='desc'>With the following details:</a>\n";

                if (title != null) {
                    output += "      <div class='item'>\n"
                            + "                <a class='label'>TITLE</a>\n"
                            + "                <a class='value'>" + title + "</a>\n"
                            + "              </div>\n";
                }

                if (description != null) {
                    output += "      <div class='item'>\n"
                            + "                <a class='label'>DESCRIPTION</a>\n"
                            + "                <a class='value'>" + description + "</a>\n"
                            + "              </div>\n";
                }

                output += "          </div>";
            }

            output += "            </div>  \n";
        }

        servlet.putInJsp("result", output);
    }

    public void addCreateSession(String classId, String date, String time) {
        // Get class results
        List<Models.Class> classList = em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = ? and c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role='teacher'", Models.Class.class).setParameter(1, classId).setParameter(2, user.getUserid()).getResultList();

        // To hold output
        String output = "";

        // If empty
        if (classList.size() == 0) {
            replyChat("Seems like this class doesn't exist");
            return;
        }

        // A result for each
        for (Models.Class classroom : classList) {
            String dateHref = date == null ? "" : date.trim().isEmpty() ? "" : date;
            String timeHref = time == null ? "" : time.trim().isEmpty() ? "" : time;

            // For displaying
            DateTimeFormatter dateFmt = DateTimeFormat.forPattern("dd MMM YYYY");
            String dateDisplay = new DateTime(date).toString(dateFmt);

            output = "  <div class='result action' onclick='window.location.href=\"AddSession?id=" + classId + "&date=" + dateHref + "\"'>\n"
                    + "            <div class='top'>\n"
                    + "              <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/3324/3324859.svg'>\n"
                    + "              <div class='text'>\n"
                    + "                <a class='type'>ACTION</a>\n"
                    + "                <a class='desc'>Create a session for <span>" + classId + "</span></a>\n"
                    + "              </div>\n"
                    + "            </div>\n";

            if (date != null || time != null) {
                output += "     <div class='bottom'>\n"
                        + "              <a class='desc'>With the following details:</a>\n";

                if (date != null) {
                    output += "      <div class='item'>\n"
                            + "                <a class='label'>DATE</a>\n"
                            + "                <a class='value'>" + dateDisplay + "</a>\n"
                            + "              </div>\n";
                }

                if (time != null) {
                    output += "      <div class='item'>\n"
                            + "                <a class='label'>DESCRIPTION</a>\n"
                            + "                <a class='value'>" + time + "</a>\n"
                            + "              </div>\n";
                }

                output += "          </div>";
            }

            output += "            </div>  \n";
        }

        servlet.putInJsp("result", output);
    }

    // ---- QUERY  PARSING BELOW ----
    public void input(String input) {
        // Lowercase the first letter
        // Thanks to Rekin @
        // https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
        input = input.substring(0, 1).toLowerCase() + input.substring(1);

        // Check intention
        // If retrieve
        if (input.matches(retrieveCommand)) {
            retrieve(input);
        } // If asking a question
        else if (input.matches(questions)) {
            questions(input);
        } else if (input.matches(createEducation)) {
            createEducation(input);
        } else if (input.matches(createClassObjects)) {
            createClassObjects(input);
        } else {
            replyChat("Sorry, I don't understand.");
        }
    }

    // Create stuff in class (TEACHER ONLY!)
    public void createClassObjects(String input) {
        // Find target
        if (input.matches(".* (announcement|session|assignment) for.*")) {
            String target = substr(input, "for (\\S*)\\s?");

            // Obtain the class here
            // If no target is found
            if (target == null || target == "") {
                System.out.println("No class found");
                replyChat("This class does not exist.");
                return;
            }

            if (input.matches(".*(announcement).*")) {
                System.out.println("Create an announcement for " + target);
            } else if (input.matches(".*(session).*(today)")) {
                System.out.println("Create a session for " + target);

                // Get today's date
                input = input.trim();
                String date = "";
                boolean processDate = true;
                try {
                    // Convert date to a string that can be read by <input> tags
                    DateTimeFormatter dateFmt = DateTimeFormat.forPattern("YYYY-MM-dd");
                    date = new DateTime().toString(dateFmt);
                } catch (Exception e) {
                    System.out.println("Invalid date");
                    processDate = false;
                }

                if (processDate) {
                    addCreateSession(target, date, null);
                } else {
                    // No date
                    addCreateSession(target, null, null);
                }

                // Get optional time (must be a range)
                // if(input.matches(".*(between|from) (.*) (and|to|until) (.*)"))
            }  else if (input.matches(".*(session).*")) {
                System.out.println("Create a session for " + target);

                // Get optional date
                input = input.trim();
                String dateInput = substr(input, ".*on (.*)");
                dateInput = dateInput != null ? dateInput.trim() : "";
                String date = "";
                boolean processDate = true;
                try {
                    // Convert date to a string that can be read by <input> tags
                    DateTimeFormatter dateFmt = DateTimeFormat.forPattern("YYYY-MM-dd");
                    date = new DateTime(Quick.getDate(dateInput)).toString(dateFmt);
                } catch (Exception e) {
                    System.out.println("Invalid date");
                    processDate = false;
                }

                if (processDate) {
                    addCreateSession(target, date, null);
                } else {
                    // No date
                    addCreateSession(target, null, null);
                }
            } else if (input.matches(".*(assignment).*")) {

                // Detect ID and name
                String title = substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']") != null && !substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']").trim().isEmpty() ? substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']") : substr2(input, ".*(named|titled|name|title) (\\S*)\\s?");
                String description = substr2(input, ".*(message|description) [\"\']([^\']*)[\"\']") != null && !substr2(input, ".*(message|description) [\"\']([^\']*)[\"\']").trim().isEmpty() ? substr2(input, ".*(message|description) [\"\']([^\']*)[\"\']") : substr2(input, ".*(message|description) (\\S*)\\s?");

                System.out.println(title);

                System.out.println(title.trim().isEmpty());
                // If ID provided
                if (title != null && !title.trim().isEmpty()) {

                    // And name as well
                    if (description != null && !description.trim().isEmpty()) {
                        addCreateAssignment(target, title, description);
                    } else {
                        addCreateAssignment(target, title, null);
                    }
                } // If name only
                else if (description != null && !description.trim().isEmpty()) {
                    addCreateAssignment(target, null, description);
                } else {
                    addCreateAssignment(target, null, null);
                }
            } else {
                System.out.println("Sorry, I don't understand");
                replyChat("This class does not exist.");
            }
        }

    }

    // Create class/course/prog/institution
    public void createEducation(String input) {
        if (input.matches(".* (class).*")) {

            // Detect ID and name
            String id = substr2(input, ".*(id|ID) (\\S*)\\s?");
            String name = substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']") != null && !substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']").trim().isEmpty() ? substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']") : substr2(input, ".*(named|titled|name|title) (\\S*)\\s?");

            // If ID provided
            if (id != null && !id.trim().isEmpty()) {

                // And name as well
                if (name != null && !name.trim().isEmpty()) {
                    addCreateEducationComponent("class", id, name, "AddClass");
                } else {
                    addCreateEducationComponent("class", id, null, "AddClass");
                }
            } // If name only
            else if (name != null && !name.trim().isEmpty()) {
                addCreateEducationComponent("class", null, name, "AddClass");
            } else {
                addCreateEducationComponent("class", null, null, "AddClass");
            }
        } else if (input.matches(".* (course).*")) {
            // Detect ID and name
            String id = substr2(input, ".*(id|ID) (\\S*)\\s?");
            String name = substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']") != null && !substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']").trim().isEmpty() ? substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']") : substr2(input, ".*(named|titled|name|title) (\\S*)\\s?");

            // If ID provided
            if (id != null && !id.trim().isEmpty()) {

                // And name as well
                if (name != null && !name.trim().isEmpty()) {
                    addCreateEducationComponent("course", id, name, "AddCourse");
                } else {
                    addCreateEducationComponent("course", id, null, "AddCourse");
                }
            } // If name only
            else if (name != null && !name.trim().isEmpty()) {
                addCreateEducationComponent("course", null, name, "AddCourse");
            } else {
                addCreateEducationComponent("course", null, null, "AddCourse");
            }
        } else if (input.matches(".* (programme).*")) {
            // Detect ID and name
            String id = substr2(input, ".*(id|ID) (\\S*)\\s?");
            String name = substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']") != null && !substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']").trim().isEmpty() ? substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']") : substr2(input, ".*(named|titled|name|title) (\\S*)\\s?");

            // If ID provided
            if (id != null && !id.trim().isEmpty()) {

                // And name as well
                if (name != null && !name.trim().isEmpty()) {
                    addCreateEducationComponent("programme", id, name, "AddProgramme");
                } else {
                    addCreateEducationComponent("programme", id, null, "AddProgramme");
                }
            } // If name only
            else if (name != null && !name.trim().isEmpty()) {
                addCreateEducationComponent("programme", null, name, "AddProgramme");
            } else {
                addCreateEducationComponent("programme", null, null, "AddProgramme");
            }
        } else if (input.matches(".* (institution).*")) {
            // Detect ID and name
            String id = substr2(input, ".*(id|ID) (\\S*)\\s?");
            String name = substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']") != null && !substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']").trim().isEmpty() ? substr2(input, ".*(named|titled|name|title) [\"\']([^\']*)[\"\']") : substr2(input, ".*(named|titled|name|title) (\\S*)\\s?");

            // If ID provided
            if (id != null && !id.trim().isEmpty()) {

                // And name as well
                if (name != null && !name.trim().isEmpty()) {
                    addCreateEducationComponent("institution", id, name, "AddInstitution");
                } else {
                    addCreateEducationComponent("institution", id, null, "AddInstitution");
                }
            } // If name only
            else if (name != null && !name.trim().isEmpty()) {
                addCreateEducationComponent("institution", null, name, "AddInstitution");
            } else {
                addCreateEducationComponent("institution", null, null, "AddInstitution");
            }
        } else {
            System.out.println("Unknown command");
        }
    }

    // Asking questions
    public void questions(String input) {
        // If how many...
        if (input.matches("(how many).*")) {

            // How many classes?
            if (input.matches(".*(classes).*")) {
                if (input.matches(".*today.*")) {
                    List<Models.Session> output = getTodayClasses();
                    replyStats("<span>" + output.size() + "</span> classes", today);
                } else {
                   List<Models.Class> output = showClasses();
                replyStats("<span>" + output.size() + "</span> classes", "That you are in");
                }
            } else if (input.matches(".*(courses).*")) {
                List<Models.Course> output = showCourses();
                replyStats("<span>" + output.size() + "</span> courses", "That you are participating");
            } else if (input.matches(".*(programmes).*")) {
                List<Models.Programme> output = showProgrammes();
                replyStats("<span>" + output.size() + "</span> programmes", "That you are involved in");
            } else if (input.matches(".*(institutions).*")) {
                List<Models.Institution> output = showInstitutions();
                replyStats("<span>" + output.size() + "</span> institutions", "That you are a student of");
            } else {
                System.out.println("That is indeed a good question");
            }
        } // What time is it
        else if (input.matches("what (time|is the time|time is it)")) {
            DateTimeFormatter timeFmt = DateTimeFormat.forPattern("'It is currently' h:mm a'");
            DateTimeFormatter dateFmt = DateTimeFormat.forPattern("MMM d', ' YYYY ");
            String time = new DateTime().toString(timeFmt);
            String date = new DateTime().toString(dateFmt);
            System.out.println("It is currently " + new Date());
            replyStats(time, date);
            return;

        } else {
            System.out.println("That is indeed a good question");
        }
    }

    // Retrieving data
    public void retrieve(String input) {

        // If retrieve all
        if (input.matches(retrieveCommand + " (classes|courses|programmes|institutions|announcements|announcement).*")) {

            // if classes
            if (input.matches(".*(announcements|announcement).*")) {

                // If a "from" is provided
                if (input.matches(".*(from|in).*")) {
                    String from = substr(input, "from (.*)") == "" ? substr(input, "in (.*)") : substr(input, "from (.*)");
                    showAnnouncements(from);
                    return;
                } else {

                    showAnnouncements();
                    return;
                }

            }

            // if classes
            if (input.matches(".*(classes).*")) {
                System.out.println(input);

                // If classes today
                if (input.matches(".*(today).*")) {
                    getTodayClasses();
                    return;
                }

                // If a "from" is provided
                if (input.matches(".*(from|in).*")) {

                    // Check for both FROM xx and IN xx
                    String from = substr(input, "from (.*)") == "" ? substr(input, "in (.*)") : substr(input, "from (.*)");

                    showClasses(from);
                } else {
                    showClasses();
                }

            } // if courses
            else if (input.matches(".*(courses).*")) {
                // If a "from" is provided
                if (input.matches(".*(from|in).*")) {

                    // Check for both FROM xx and IN xx
                    String from = substr(input, "from (.*)") == "" ? substr(input, "in (.*)") : substr(input, "from (.*)");

                    showCourses(from);
                } else {
                    showCourses();
                }

            } // if programmes
            else if (input.matches(".*(programmes).*")) {
                // If a "from" is provided
                if (input.matches(".*(from|in).*")) {

                    // Check for both FROM xx and IN xx
                    String from = substr(input, "from (.*)") == "" ? substr(input, "in (.*)") : substr(input, "from (.*)");
                    showProgrammes(from);
                } else {
                    showProgrammes();
                }

            } // if institutions
            else if (input.matches(".*(institutions).*")) {
                showInstitutions();
            } else {
                replyChat("Sorry, I don't understand");
                return;
            }
        } // If retrieve singular
        else if (input.matches("(show me|give me|display|show|get).*")) {
            // Get singular target
            String target = substr2(input, "(class|course|programme|institution) (\\S*)");
            
            // If empty, swap position (eg. class LL222 > LL222 class)
            if(target == "")
                target = substr(input, "(\\S*) (class|course|programme|institution)");

            // target cannot be null
            if (target == "") {
                replyChat("Sorry, I don't understand");
                return;
            }
            System.out.println(target);
            // if class
            if (input.matches(".*(class).*")) {
                getSingleClass(target);
            } // if course
            else if (input.matches(".*(course).*")) {
                getSingleCourse(target);
            } // if programme
            else if (input.matches(".*(programme).*")) {
                getSingleProgramme(target);
            } // if institution
            else if (input.matches(".*(institution).*")) {
                getSingleInstitution(target);
            } else {
                replyChat("Sorry, please specify what you want to find");
            }
        } else {
            replyChat("Sorry, I don't understand.");
        }
    }

    // ----- UTIL METHODS BELOW -----
    public String substr(String input, String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(input);
        if (m.find()) {
            return m.group(1);
        } else {
            return "";
        }
    }

    public String substr2(String input, String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(input);
        if (m.find()) {
            return m.group(2);
        } else {
            return "";
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
