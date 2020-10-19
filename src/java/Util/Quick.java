/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FileExistsException;
import org.joda.time.*;

/**
 *
 * @author mast3
 */
public class Quick {
    public static void print(String string){
        System.out.println(string);
    }
    
    public static int getAge(Date dateOfBirth){
        return Years.yearsBetween(new DateTime(dateOfBirth), DateTime.now()).getYears();
    }
    
    public static Object getSession(HttpServletRequest request, String name){
        HttpSession session = request.getSession();
        return session.getAttribute(name);
    }
    
    public static void putInJsp(HttpServletRequest request, String name, String value){
        request.setAttribute(name, value);
    }
    
    public static <T>  String generateID(EntityManager em, UserTransaction utx, java.lang.Class<T> classType, String idFieldName){
        int repeater = 0;
        
        // Generate a random ID
        String id = UUID.randomUUID().toString();
        
        // Check if this ID exists in DB or not
        while(repeater <5 && new DB(em, utx).getSingleResult(idFieldName, id, classType) != null){
            // regenerate
            id = UUID.randomUUID().toString();
            repeater++;
        }
        
        return UUID.randomUUID().toString();
    }
    
    public static String getIcon(String iconURL){
        return iconURL == null ||  iconURL.trim().isEmpty()? "https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg" : iconURL;
    }
    
    public static String getRole(String role){

        if(role.equalsIgnoreCase("classTeacher")){
            return "Class Teacher";
        } else if(role.equalsIgnoreCase("courseLeader")){
            return "Course Leader";
        } else if(role.equalsIgnoreCase("programmeLeader")){
            return "Programme Leader";
        } else if(role.equalsIgnoreCase("institutionAdmin")){
            return "Administrator";
        } else{
            return "Unknown role";
        }
    }
    
    public static void writeFile(FileItem item, String entireFileName) throws FileUploadException, FileExistsException, Exception{
        item.write(new File(entireFileName));
    }
    
    public static void displayFile(String filename,ServletContext context, HttpServletRequest request, HttpServletResponse response, Util.Servlet servlet, String errorRedirectURL) throws IOException{
        File file = new File(filename);
        String fileType = context.getMimeType(filename);
        
        // Check for valid types
        if(fileType != null){
            // Don't do anything, since it's a valid file type
        } else if(fileType == null && filename.contains(".mp4")){
            fileType = "video/mp4";
        } else{
             Errors.respondSimple(request.getSession(), "Unplayable file type");
             System.out.println("Unplayable file type");
             servlet.toServlet(errorRedirectURL);
             return;
        }
        
     
        
        response.setHeader("Content-Type", fileType);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        Files.copy(file.toPath(), response.getOutputStream());
    }
    
}
