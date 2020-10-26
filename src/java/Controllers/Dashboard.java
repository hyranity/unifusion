/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Course;
import Models.Institution;
import Models.Programme;
import Models.Users;
import Util.DB;
import Util.Quick;
import Util.Server;
import Util.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
@WebServlet(name = "Dashboard", urlPatterns = {"/Dashboard"})
public class Dashboard extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Utility classes
        Servlet servlet = new Servlet(request, response);
        DB db = new DB(em, utx);
        Users user = Server.getUser(request, response);

        // Output string
        String output = "";

        // Get all institutions
        Query institutionQ = em.createNativeQuery("select distinct i.* from Institution i, Users u, Institutionparticipant ipa, Participant p where ipa.institutioncode = i.institutioncode and u.userid = p.userid and p.participantid = ipa.participantid  and u.userid = ?", Models.Institution.class).setParameter(1, user.getUserid());

        // Get all programmes WITHOUT institution
        Query programmeQ = em.createNativeQuery("select distinct pg.* from Programme pg, Users u, Programmeparticipant ppa, Participant p where ppa.programmecode = pg.programmecode and u.userid = p.userid and p.participantid = ppa.participantid  and u.userid = ? and pg.institutioncode is null", Models.Programme.class).setParameter(1, user.getUserid());

        // Get all courses WITHOUT programmes
        Query courseQ = em.createNativeQuery("select distinct c.* from Course c, Users u, Courseparticipant cpa, Participant p where cpa.coursecode = c.coursecode and u.userid = p.userid and p.participantid = cpa.participantid  and u.userid = ? and c.programmecode is null", Models.Course.class).setParameter(1, user.getUserid());

        // Get all INDIVIDUAL classes
        Query classQ = em.createNativeQuery("select distinct c.* from Class c, Users u, Classparticipant cpa, Participant p where cpa.classid = c.classid and u.userid = p.userid and p.participantid = cpa.participantid and u.userid = ?  and  c.coursecode is null", Models.Class.class).setParameter(1, user.getUserid());

        // Execute all queries
        List<Course> courseList = courseQ.getResultList();
        List<Models.Class> classList = classQ.getResultList();
        List<Models.Programme> programmeList = programmeQ.getResultList();
        List<Models.Institution> institutionList = institutionQ.getResultList();

        // Generate institution UI
        for (Institution institution : institutionList) {
            // Get the creator
            Users founder = db.getList(Models.Users.class, em.createNativeQuery("select u.* from Institution i, Institutionparticipant ipa, Participant p, Users u where i.institutioncode = ? and i.institutioncode = ipa.institutioncode and ipa.iscreator = true and p.participantid = ipa.participantid and u.userid = p.userid", Models.Users.class).setParameter(1, institution.getInstitutioncode())).get(0);

            output += "<div class='institution'>\n"
                    + "\n"
                    + "        <div class='institution-details' onclick=\"location.href='Institution?id=" + institution.getInstitutioncode() + "'\">\n"
                    + "          <img class='icon' src='" + Quick.getIcon(institution.getIconurl()) + "'>\n"
                    + "          <div class='details'>\n"
                    + "            <div class='top-details'>\n"
                    + "              <a class='id'>" + institution.getInstitutioncode() + "</a>\n"
                    + "              <a class='tutor'>" + founder.getName() + "</a>\n"
                    + "            </div>\n"
                    + "            <a class='type'>INSTITUTION</a>\n"
                    + "            <a class='name'>" + institution.getName() + "</a>\n"
                    + "            <a class='description'>" + institution.getDescription() + "</a>\n"
                    + "          </div>\n"
                    + "        </div>\n"
                    + "\n";

            // Get programmes in which user has joined under this institution
            Query progJoinQuery = em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ppa.programmecode and ppa.participantid = p.participantid and p.userid = ? and pg.institutioncode = ?", Models.Programme.class);
            progJoinQuery.setParameter(1, user.getUserid());
            progJoinQuery.setParameter(2, institution.getInstitutioncode());

            List<Models.Programme> institutionProgrammes = progJoinQuery.getResultList();

            if (institutionProgrammes.size() > 0) {
                output += "<input type='checkbox' name='seeProgrammes' class='seeProgrammes' id='seeProgrammes_" + institution.getInstitutioncode() + "' onclick='seeProgrammes(\"" + institution.getInstitutioncode() + "\")'>\n"
                        + "        <label class='seeProgrammesLabel' id='seeProgrammesLabel_" + institution.getInstitutioncode() + "' for='seeProgrammes_" + institution.getInstitutioncode() + "'>View programmes</label>\n"
                        + "\n"
                        + "        <div class='programmes' id='programmes_" + institution.getInstitutioncode() + "'>";
            }

            for (Programme programme : institutionProgrammes) {
                // Get the creator
                Users creator = db.getList(Models.Users.class, em.createNativeQuery("select u.* from Programme pg, Programmeparticipant ppa, Participant p, Users u where pg.programmecode = ? and pg.programmecode = ppa.programmecode and ppa.iscreator = true and p.participantid = ppa.participantid and u.userid = p.userid", Models.Users.class).setParameter(1, programme.getProgrammecode())).get(0);

                output += " <div class='programme'>\n"
                        + "\n"
                        + "                  <div class='programme-details'  onclick=\"location.href='Programme?id=" + programme.getProgrammecode() + "'\">\n"
                        + "                    <img class='icon' src='" + Quick.getIcon(programme.getIconurl()) + "'>\n"
                        + "                    <div class='details'>\n"
                        + "                      <div class='top-details'>\n"
                        + "                        <a class='id'>" + programme.getProgrammecode() + "</a>\n"
                        + "                        <a class='tutor'>" + creator.getName() + "</a>\n"
                        + "                      </div>\n"
                        + "                      <a class='type'>PROGRAMME</a>\n"
                        + "                      <a class='name'>" + programme.getTitle() + "</a>\n"
                        + "                      <a class='description'>" + programme.getDescription() + "</a>\n"
                        + "                    </div>\n"
                        + "                  </div>\n"
                        + "\n";

                // Get courses in which user has joined
                Query courseJoinQuery = em.createNativeQuery("select c.* from course c, courseparticipant cpa, participant p where c.coursecode = cpa.coursecode and cpa.participantid = p.participantid and p.userid = ? and c.programmecode = ?", Models.Course.class);
                courseJoinQuery.setParameter(1, user.getUserid());
                courseJoinQuery.setParameter(2, programme.getProgrammecode());

                List<Models.Course> programmeCourses = courseJoinQuery.getResultList();

                // Only show "View Courses" button when there are courses inside
                if (programmeCourses.size() > 0) {

                    output += " <input type='checkbox' name='seeCourses' class='seeCourses' id='seeCourses_" + programme.getProgrammecode() + "' onclick='seeCourses(\"" + programme.getProgrammecode() + "\")'>\n"
                            + "                  <label class='seeCoursesLabel' id='seeCoursesLabel_" + programme.getProgrammecode() + "' for='seeCourses_" + programme.getProgrammecode() + "'>View courses</label>"
                            + "                  <div class='courses' id='courses_" + programme.getProgrammecode() + "'>";
                }

                // Print each course
                for (Course course : programmeCourses) {
                    // Get the teacher
                    Users teacher = db.getList(Models.Users.class, em.createNativeQuery("select u.* from Course c, Courseparticipant cpa, Participant p, Users u where c.coursecode = ? and c.coursecode = cpa.coursecode and cpa.iscreator = true and p.participantid = cpa.participantid and u.userid = p.userid", Models.Users.class).setParameter(1, course.getCoursecode())).get(0);

                    // Print each course
                    output += " <div class='course'>\n"
                            + "      \n"
                            + "        <div class='course-details' onclick=\"location.href='Course?id=" + course.getCoursecode() + "'\">\n"
                            + "          <img class='icon' src='" + Quick.getIcon(course.getIconurl()) + "'>\n"
                            + "          <div class='details'>\n"
                            + "            <div class='top-details'>\n"
                            + "              <a class='id'>" + course.getCoursecode() + "</a>\n"
                            + "              <a class='tutor'>" + teacher.getName() + "</a>\n"
                            + "            </div>\n"
                            + "            <a class='type'>COURSE</a>\n"
                            + "            <a class='name'>" + course.getTitle() + "</a>\n"
                            + "            <a class='description'>" + course.getDescription() + "</a>\n"
                            + "          </div>\n"
                            + "        </div>\n"
                            + "        \n";

                    // Get classes in which user has joined
                    Query classJoinQuery = em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and c.coursecode = ?", Models.Class.class);
                    classJoinQuery.setParameter(1, user.getUserid());
                    classJoinQuery.setParameter(2, course.getCoursecode());

                    List<Models.Class> courseClasses = classJoinQuery.getResultList();

                    // Show the Classes list if only there are classes
                    if (courseClasses.size() > 0) {
                        output += "        <input type='checkbox' name='seeClasses' class='seeClasses' id='seeClasses_" + course.getCoursecode() + "' onclick='seeClasses(\"" + course.getCoursecode() + "\")'>\n"
                                + "        <label class='seeClassesLabel' id='seeClassesLabel_" + course.getCoursecode() + "' for='seeClasses_" + course.getCoursecode() + "'>View classes</label>\n"
                                + "        \n"
                                + "        <div class='classes' id='classes_" + course.getCoursecode() + "'>\n"
                                + "      \n"
                                + "\n";
                    }

                    // Print each class under each course
                    int counter = 0;
                    for (Models.Class classroom : courseClasses) {
                        System.out.println("Displaying class for " + classroom.getClasstitle());

                        if (counter == 0) {
                            // Start with new row
                            output += "<div class='row'>";
                        }

                        if (counter % 2 == 0 && counter > 0) {
                            // Break new row for every 2 classes
                            output += "</div>";
                            output += "<div class='row'>";
                        }

                        // Get the class teacher
                        Users classTeacher = db.getList(Models.Users.class, em.createNativeQuery("select u.* from Class c, Classparticipant cpa, Participant p, Users u where c.classid = ? and c.classid = cpa.classid and cpa.iscreator = true and p.participantid = cpa.participantid and u.userid = p.userid", Models.Users.class).setParameter(1, classroom.getClassid())).get(0);

                        output += "            <div class='class' onclick=\"location.href='Class?id=" + classroom.getClassid() + "';\">\n"
                                + "              <img class='icon' src='" + Quick.getIcon(classroom.getIconurl()) + "'>\n"
                                + "              <div class='details'>\n"
                                + "                <div class='top-details'>\n"
                                + "                  <a class='id'>" + classroom.getClassid() + "</a>\n"
                                + "                  <a class='tutor'>" + classTeacher.getName() + "</a>\n"
                                + "                </div>\n"
                                + "                <a class='type'>CLASS</a>\n"
                                + "                <a class='name'>" + classroom.getClasstitle() + "</a>\n"
                                + "                <a class='description'>" + (classroom.getDescription() == null ? "No description" : classroom.getDescription()) + "</a>\n"
                                + "              </div>\n"
                                + "            </div>\n"
                                + "\n";

                        counter++;
                    }

                    // To close the <row> and <classes> elements, ONLY if classes exist
                    if (courseClasses.size() > 0) {
                        output += "</div></div>";
                    }

                    output += "\n"
                            + "        \n"
                            + "        </div>\n"
                            + "        \n";
                }
                // To close <courses> tag, ONLY if courses exist
                if (programmeCourses.size() > 0) {
                    output += "</div>";
                }

                output += "</div>";
            }

            // To close <programmes> tag, ONLY if courses exist
            if (institutionProgrammes.size() > 0) {
                output += "</div>";
            }

            output += "</div>";

        }

        // Generate programme UI
        for (Programme programme : programmeList) {
            // Get the creator
            Users creator = db.getList(Models.Users.class, em.createNativeQuery("select u.* from Programme pg, Programmeparticipant ppa, Participant p, Users u where pg.programmecode = ? and pg.programmecode = ppa.programmecode and ppa.iscreator = true and p.participantid = ppa.participantid and u.userid = p.userid", Models.Users.class).setParameter(1, programme.getProgrammecode())).get(0);

            output += " <div class='programme'>\n"
                    + "\n"
                    + "                  <div class='programme-details'  onclick=\"location.href='Programme?id=" + programme.getProgrammecode() + "'\">\n"
                    + "                    <img class='icon' src='" + Quick.getIcon(programme.getIconurl()) + "'>\n"
                    + "                    <div class='details'>\n"
                    + "                      <div class='top-details'>\n"
                    + "                        <a class='id'>" + programme.getProgrammecode() + "</a>\n"
                    + "                        <a class='tutor'>" + creator.getName() + "</a>\n"
                    + "                      </div>\n"
                    + "                      <a class='type'>PROGRAMME</a>\n"
                    + "                      <a class='name'>" + programme.getTitle() + "</a>\n"
                    + "                      <a class='description'>" + programme.getDescription() + "</a>\n"
                    + "                    </div>\n"
                    + "                  </div>\n"
                    + "\n";

            // Get courses in which user has joined
            Query courseJoinQuery = em.createNativeQuery("select c.* from course c, courseparticipant cpa, participant p where c.coursecode = cpa.coursecode and cpa.participantid = p.participantid and p.userid = ? and c.programmecode = ?", Models.Course.class);
            courseJoinQuery.setParameter(1, user.getUserid());
            courseJoinQuery.setParameter(2, programme.getProgrammecode());

            List<Models.Course> programmeCourses = courseJoinQuery.getResultList();

            // Only show "View Courses" button when there are courses inside
            if (programmeCourses.size() > 0) {

                output += " <input type='checkbox' name='seeCourses' class='seeCourses' id='seeCourses_" + programme.getProgrammecode() + "' onclick='seeCourses(\"" + programme.getProgrammecode() + "\")'>\n"
                        + "                  <label class='seeCoursesLabel' id='seeCoursesLabel_" + programme.getProgrammecode() + "' for='seeCourses_" + programme.getProgrammecode() + "'>View courses</label>"
                        + "                  <div class='courses' id='courses_" + programme.getProgrammecode() + "'>";
            }

            // Print each course
            for (Course course : programmeCourses) {
                // Get the teacher
                Users teacher = db.getList(Models.Users.class, em.createNativeQuery("select u.* from Course c, Courseparticipant cpa, Participant p, Users u where c.coursecode = ? and c.coursecode = cpa.coursecode and cpa.iscreator = true and p.participantid = cpa.participantid and u.userid = p.userid", Models.Users.class).setParameter(1, course.getCoursecode())).get(0);

                // Print each course
                output += " <div class='course'>\n"
                        + "      \n"
                        + "        <div class='course-details' onclick=\"location.href='Course?id=" + course.getCoursecode() + "'\">\n"
                        + "          <img class='icon' src='" + Quick.getIcon(course.getIconurl()) + "'>\n"
                        + "          <div class='details'>\n"
                        + "            <div class='top-details'>\n"
                        + "              <a class='id'>" + course.getCoursecode() + "</a>\n"
                        + "              <a class='tutor'>" + teacher.getName() + "</a>\n"
                        + "            </div>\n"
                        + "            <a class='type'>COURSE</a>\n"
                        + "            <a class='name'>" + course.getTitle() + "</a>\n"
                        + "            <a class='description'>" + course.getDescription() + "</a>\n"
                        + "          </div>\n"
                        + "        </div>\n"
                        + "        \n";

                // Get classes in which user has joined
                Query classJoinQuery = em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and c.coursecode = ?", Models.Class.class);
                classJoinQuery.setParameter(1, user.getUserid());
                classJoinQuery.setParameter(2, course.getCoursecode());

                List<Models.Class> courseClasses = classJoinQuery.getResultList();

                // Show the Classes list if only there are classes
                if (courseClasses.size() > 0) {
                    output += "        <input type='checkbox' name='seeClasses' class='seeClasses' id='seeClasses_" + course.getCoursecode() + "' onclick='seeClasses(\"" + course.getCoursecode() + "\")'>\n"
                            + "        <label class='seeClassesLabel' id='seeClassesLabel_" + course.getCoursecode() + "' for='seeClasses_" + course.getCoursecode() + "'>View classes</label>\n"
                            + "        \n"
                            + "        <div class='classes' id='classes_" + course.getCoursecode() + "'>\n"
                            + "      \n"
                            + "\n";
                }

                // Print each class under each course
                int counter = 0;
                for (Models.Class classroom : courseClasses) {
                    System.out.println("Displaying class for " + classroom.getClasstitle());

                    if (counter == 0) {
                        // Start with new row
                        output += "<div class='row'>";
                    }

                    if (counter % 2 == 0 && counter > 0) {
                        // Break new row for every 2 classes
                        output += "</div>";
                        output += "<div class='row'>";
                    }

                    // Get the class teacher
                    Users classTeacher = db.getList(Models.Users.class, em.createNativeQuery("select u.* from Class c, Classparticipant cpa, Participant p, Users u where c.classid = ? and c.classid = cpa.classid and cpa.iscreator = true and p.participantid = cpa.participantid and u.userid = p.userid", Models.Users.class).setParameter(1, classroom.getClassid())).get(0);

                    output += "            <div class='class' onclick=\"location.href='Class?id=" + classroom.getClassid() + "';\">\n"
                            + "              <img class='icon' src='" + Quick.getIcon(classroom.getIconurl()) + "'>\n"
                            + "              <div class='details'>\n"
                            + "                <div class='top-details'>\n"
                            + "                  <a class='id'>" + classroom.getClassid() + "</a>\n"
                            + "                  <a class='tutor'>" + classTeacher.getName() + "</a>\n"
                            + "                </div>\n"
                            + "                <a class='type'>CLASS</a>\n"
                            + "                <a class='name'>" + classroom.getClasstitle() + "</a>\n"
                            + "                <a class='description'>" + (classroom.getDescription() == null ? "No description" : classroom.getDescription()) + "</a>\n"
                            + "              </div>\n"
                            + "            </div>\n"
                            + "\n";

                    counter++;
                }

                // To close the <row> and <classes> elements, ONLY if classes exist
                if (courseClasses.size() > 0) {
                    output += "</div></div>";
                }

                output += "\n"
                        + "        \n"
                        + "        </div>\n"
                        + "        \n";
            }
            // To close <courses> tag, ONLY if courses exist
            if (programmeCourses.size() > 0) {
                output += "</div>";
            }

            output += "</div>";
        }

        // Generate course UI
        for (Course course : courseList) {
            // Get the teacher
            Users teacher = db.getList(Models.Users.class, em.createNativeQuery("select u.* from Course c, Courseparticipant cpa, Participant p, Users u where c.coursecode = ? and c.coursecode = cpa.coursecode and cpa.iscreator = true and p.participantid = cpa.participantid and u.userid = p.userid", Models.Users.class).setParameter(1, course.getCoursecode())).get(0);

            // Print each course
            output += " <div class='course'>\n"
                    + "      \n"
                    + "        <div class='course-details' onclick=\"location.href='Course?id=" + course.getCoursecode() + "'\">\n"
                    + "          <img class='icon' src='" + Quick.getIcon(course.getIconurl()) + "'>\n"
                    + "          <div class='details'>\n"
                    + "            <div class='top-details'>\n"
                    + "              <a class='id'>" + course.getCoursecode() + "</a>\n"
                    + "              <a class='tutor'>" + teacher.getName() + "</a>\n"
                    + "            </div>\n"
                    + "            <a class='type'>COURSE</a>\n"
                    + "            <a class='name'>" + course.getTitle() + "</a>\n"
                    + "            <a class='description'>" + course.getDescription() + "</a>\n"
                    + "          </div>\n"
                    + "        </div>\n"
                    + "        \n";

            // Get classes in which user has joined
            Query classJoinQuery = em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = cpa.classid and cpa.participantid = p.participantid and p.userid = ? and c.coursecode = ?", Models.Class.class);
            classJoinQuery.setParameter(1, user.getUserid());
            classJoinQuery.setParameter(2, course.getCoursecode());

            List<Models.Class> courseClasses = classJoinQuery.getResultList();

            // Show the Classes list if only there are classes
            if (courseClasses.size() > 0) {
                output += "        <input type='checkbox' name='seeClasses' class='seeClasses' id='seeClasses_" + course.getCoursecode() + "' onclick='seeClasses(\"" + course.getCoursecode() + "\")'>\n"
                        + "        <label class='seeClassesLabel' id='seeClassesLabel_" + course.getCoursecode() + "' for='seeClasses_" + course.getCoursecode() + "'>View classes</label>\n"
                        + "        \n"
                        + "        <div class='classes' id='classes_" + course.getCoursecode() + "'>\n"
                        + "      \n"
                        + "\n";
            }

            // Print each class under each course
            int counter = 0;
            for (Models.Class classroom : courseClasses) {
                System.out.println("Displaying class for " + classroom.getClasstitle());

                if (counter == 0) {
                    // Start with new row
                    output += "<div class='row'>";
                }

                if (counter % 2 == 0 && counter > 0) {
                    // Break new row for every 2 classes
                    output += "</div>";
                    output += "<div class='row'>";
                }

                // Get the class teacher
                Users classTeacher = db.getList(Models.Users.class, em.createNativeQuery("select u.* from Class c, Classparticipant cpa, Participant p, Users u where c.classid = ? and c.classid = cpa.classid and cpa.iscreator = true and p.participantid = cpa.participantid and u.userid = p.userid", Models.Users.class).setParameter(1, classroom.getClassid())).get(0);

                output += "            <div class='class' onclick=\"location.href='Class?id=" + classroom.getClassid() + "';\">\n"
                        + "              <img class='icon' src='" + Quick.getIcon(classroom.getIconurl()) + "'>\n"
                        + "              <div class='details'>\n"
                        + "                <div class='top-details'>\n"
                        + "                  <a class='id'>" + classroom.getClassid() + "</a>\n"
                        + "                  <a class='tutor'>" + classTeacher.getName() + "</a>\n"
                        + "                </div>\n"
                        + "                <a class='type'>CLASS</a>\n"
                        + "                <a class='name'>" + classroom.getClasstitle() + "</a>\n"
                        + "                <a class='description'>" + (classroom.getDescription() == null ? "No description" : classroom.getDescription()) + "</a>\n"
                        + "              </div>\n"
                        + "            </div>\n"
                        + "\n";

                counter++;
            }

            // To close the <row> and <classes> elements, ONLY if classes exist
            if (courseClasses.size() > 0) {
                output += "</div></div>";
            }

            output += "\n"
                    + "        \n"
                    + "        </div>\n"
                    + "        \n";
        }

        // For every individual class
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
            Users teacher = db.getList(Models.Users.class, em.createNativeQuery("select u.* from Class c, Classparticipant cpa, Participant p, Users u where c.classid = ? and c.classid = cpa.classid and cpa.iscreator = true and p.participantid = cpa.participantid and u.userid = p.userid", Models.Users.class).setParameter(1, classList.get(i).getClassid())).get(0);

            output += "<div class='class' id='orange' onclick=\"location.href = 'Class?id=" + classList.get(i).getClassid() + "';\">\n"
                    + "                        <img class='icon' src='" + Quick.getIcon(classList.get(i).getIconurl()) + "'>\n"
                    + "                        <div class='details'>\n"
                    + "                            <div class='top-details'>\n"
                    + "                                <a class='id'>" + classList.get(i).getClassid() + "</a>\n"
                    + "                                <a class='tutor'>" + teacher.getName() + "</a>\n"
                    + "                            </div>\n"
                    + "                            <a class='type'>CLASS</a>\n"
                    + "                            <a class='name'>" + classList.get(i).getClasstitle() + "</a>\n"
                    + "                            <a class='description'>" + (classList.get(i).getDescription() == null ? "No description" : classList.get(i).getDescription()) + "</a>\n"
                    + "                        </div>\n"
                    + "                    </div>";

        }

        servlet.putInJsp("output", output);
        servlet.servletToJsp("dashboard.jsp");

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
