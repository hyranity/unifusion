/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
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
    
}
