/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Models.Users;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mast3
 * This class allows all server-related code to be centralized 
 */
public class Server {
    
   // Returns the current user if logged in
    public static Users getUser(HttpServletRequest request, HttpServletResponse response){
        
        return (Users) request.getSession(false).getAttribute("user");
    }
    
    // To validate user in JSP
    public static void blockAnonymous(HttpSession session, HttpServletResponse response){
          if((Users) session.getAttribute("user") == null){
            // User is not logged in
            redirectAnonymous(response);
        }
    }
    
    // To validate user in Servlet
    public static boolean isLoggedIn(HttpSession session, HttpServletResponse response){
        if(session.getAttribute("user")== null){
            redirectAnonymous(response);
            return false;
        }
        else
            return true;
    }
    
    // Where to redirect anonymous users
    public static void redirectAnonymous(HttpServletResponse response){
        try {
            response.sendRedirect("login.jsp");
            return;
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
