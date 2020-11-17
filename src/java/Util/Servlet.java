package Util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Johann Lee Jia Xuan This is a class to make servlet methods easier to
 * code
 */
public class Servlet {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public Servlet(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public Object getSession(String name) {
        return request.getSession().getAttribute(name);
    }

    public void putInJsp(String name, Object value) {
        request.setAttribute(name, value);
    }
    
    public void toServlet(String servletName){
        try {
            response.sendRedirect(servletName);
        } catch (IOException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    // Must be inside WEB-INF to work
    public void servletToJsp(String jspName){
        try {
            System.out.println("jspName: " + jspName);
            request.getRequestDispatcher("WEB-INF/" + jspName).forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getQueryStr(String param){
       return request.getParameter(param);
    }
    
    // Block guests
    public boolean  blockGuests(){
        if(this.getSession("user")==null){
            this.toServlet("Home");
            return true;
        }
        return false;
    }
}
