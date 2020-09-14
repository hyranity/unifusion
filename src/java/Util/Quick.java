/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    
}
