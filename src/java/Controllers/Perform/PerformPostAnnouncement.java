/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.Perform;

import Models.Announcement;
import Models.Course;
import Models.Institution;
import Models.Participant;
import Models.Programme;
import Util.Errors;
import Util.Quick;
import Util.Server;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;

/**
 *
 * @author mast3
 */
@WebServlet(name = "PerformPostAnnouncement", urlPatterns = {"/PerformPostAnnouncement"})
public class PerformPostAnnouncement extends HttpServlet {

    @PersistenceContext
    EntityManager em;

    @Resource
    private UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            
            // Util objects
            Util.Servlet servlet = new Util.Servlet(request, response);
            Util.DB db = new Util.DB(em, utx);
            Models.Users user = Server.getUser(request, response);
            
            // File path for uploading
            String filePath = "C:/Scaffold/data";
            
            String title = "";
            String message = "";
            String id = "";
            String type = "";
            
            // Get form fields
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for(FileItem item : items){
                if(item.isFormField()){
                    switch(item.getFieldName()){
                        case "title":
                            title = item.getString();
                            break;
                        case "message":
                            message = item.getString();
                            break;
                        case "type":
                            type = item.getString();
                            break;
                        case "id":
                            id = item.getString();
                            break;
                    }
                }
            }
                
            
            System.out.println(title);
            
            // Validation goes here
            if (title == null || message == null || title.trim().isEmpty() || message.trim().isEmpty()) {
                // Has null data
                System.out.println("Null fields!");
                Errors.respondSimple(request.getSession(), "Ensure all fields have been filled in.");
                servlet.toServlet("PostAnnouncement");
                return;
            }
            
            
            
            // New announcement object
            Announcement announcement = new Announcement();
            announcement.setAnnouncementid(Quick.generateID(em, utx, Announcement.class, "announcementid"));
            announcement.setTitle(title);
            announcement.setMessage(message);
            announcement.setDateannounced(DateTime.now().toDate());
            
            // Check to see if this person has authority to post in this level
            // If no error, assign that level into the announcement
            try {
                if ("class".equalsIgnoreCase(type)) {
                    // Get the class
                    Models.Class classroom = (Models.Class) em.createNativeQuery("select c.* from class c, classparticipant cpa, participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role = 'teacher'", Models.Class.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                    Models.Participant participant = (Models.Participant) em.createNativeQuery("select p.* from class c, classparticipant cpa, participant p where c.classid = ? and cpa.classid = c.classid and cpa.participantid = p.participantid and p.userid = ? and cpa.role = 'teacher'", Models.Participant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                    
                    // No error, assign announcement
                    announcement.setClassid(classroom);
                    announcement.setPosterid(participant);
                    
                } else if ("course".equalsIgnoreCase(type)) {
                    // Get the course
                    Models.Course course = (Models.Course) em.createNativeQuery("select c.* from course c, courseparticipant cpa, participant p where c.coursecode = ? and cpa.coursecode = c.coursecode and cpa.participantid = p.participantid and p.userid = ? and cpa.role = 'teacher'", Course.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                    Models.Participant participant = (Models.Participant) em.createNativeQuery("select p.* from course c, courseparticipant cpa, participant p where c.coursecode = ? and cpa.coursecode = c.coursecode and cpa.participantid = p.participantid and p.userid = ? and cpa.role = 'teacher'", Participant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                    
                    // No error, assign announcement
                    announcement.setCoursecode(course);
                    announcement.setPosterid(participant);
                } else if ("programme".equalsIgnoreCase(type)) {
                    // Get the course
                    Models.Programme programme = (Models.Programme) em.createNativeQuery("select pg.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ? and ppa.programmecode = pg.programmecode and ppa.participantid = p.participantid and p.userid = ? and ppa.role = 'teacher'", Programme.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                    Models.Participant participant = (Models.Participant) em.createNativeQuery("select p.* from programme pg, programmeparticipant ppa, participant p where pg.programmecode = ? and ppa.programmecode = pg.programmecode and ppa.participantid = p.participantid and p.userid = ? and ppa.role = 'teacher'", Participant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                    
                    // No error, assign announcement
                    announcement.setProgrammecode(programme);
                    announcement.setPosterid(participant);
                } else if ("institution".equalsIgnoreCase(type)) {
                    // Get the course
                    Models.Institution institution = (Models.Institution) em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ? and ipa.role = 'teacher'", Institution.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                    Models.Participant participant = (Models.Participant) em.createNativeQuery("select i.* from institution i, institutionparticipant ipa, participant p where i.institutioncode = ? and ipa.institutioncode = i.institutioncode and ipa.participantid = p.participantid and p.userid = ? and ipa.role = 'teacher'", Participant.class).setParameter(1, id).setParameter(2, user.getUserid()).getSingleResult();
                    
                    // No error, assign announcement
                    announcement.setInstitutioncode(institution);
                    announcement.setPosterid(participant);
                } else {
                    // Incorrect type
                    System.out.println("Type is incorrect");
                    servlet.toServlet("Dashboard");
                    return;
                }
            } catch (NoResultException e) {
                System.out.println("No data found");
                return;
            }
            
            // Upload file
            ArrayList<String> fileList = uploadFile(filePath, request);
            
            // Set to announcement
            String fileUrl = "";
            int counter = 0;
            for (String file : fileList) {
                counter++;
                
                fileUrl += file; // Append each file to the summarized url
                
                // If not yet end, add separator
                if (counter < fileList.size()) {
                    fileUrl += "|";
                }
            }
            
            // announcement.setFileurl(fileUrl);
            // Set in db
            db.insert(announcement);
            
            System.out.println("Announcement successfully posted");
            servlet.toServlet("Announcement?type="+type +"&id=" + id);
        } catch (FileUploadException ex) {
            Logger.getLogger(PerformPostAnnouncement.class.getName()).log(Level.SEVERE, null, ex);
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

    private ArrayList<String> uploadFile(String filePath, HttpServletRequest request) {
        ArrayList<String> uploadedFiles = new ArrayList();

        // If the form submission is multipart content
        if (ServletFileUpload.isMultipartContent(request)) {

            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multiparts) {
                    System.out.println("is item form field? " + item.getFieldName());
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();
                        item.write(new File(filePath + File.separator + name));
                        System.out.println("Writing in " + filePath + File.separator + name);
                        uploadedFiles.add(filePath + File.separator + name);
                    }
                }

            } catch (FileUploadException ex) {
                Logger.getLogger(PerformPostAnnouncement.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(PerformPostAnnouncement.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("File uploaded successfully");

        }

        return uploadedFiles;
    }

}
