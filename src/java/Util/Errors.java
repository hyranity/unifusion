package Util;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Johann Lee Jia Xuan
 *
 */
public class Errors {

    private ArrayList<String> errorList;
    private HttpServletRequest request;
    
    // Regular constructor
    public Errors(HttpSession session) {
        session.setAttribute("errorMessage", null);
        request = null;
        errorList = new ArrayList();
    }

    // To easily get previous errors
    public Errors(HttpServletRequest request, HttpSession session) {
        session.setAttribute("errorMessage", null);
        this.request = request;
        errorList = new ArrayList();
    }

    public Errors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    // To add an error
    public void add(String errorMsg) {
        errorList.add(errorMsg);
    }

    // Check if error exists
    public boolean hasError() {
        return errorList.size() > 0;
    }

    // Get listOfErrors
    /*
    Example:
    - Wrong ID
    - Wrong name
     */
    public String getList() {
        String errorStr = "";

        for (int i = 0; i < errorList.size(); i++) {
            errorStr += "- " + errorList.get(i);

            // Not at the end? Add newline
            if (i + 1 != errorList.size()) {
                errorStr += "\n";
            }
        }

        return errorStr;

    }
    
    // Get errorList from session, then delete it
    public static  ArrayList<String> requestErrors(HttpSession session){
        ArrayList<String> errors = (ArrayList<String>) session.getAttribute("errorList");
        session.setAttribute("errorList", null);
        return errors;
    }
    
    // Put errorList into session
    public void respondErrors(HttpSession session){
         session.setAttribute("errorList", errorList);
    }
    
    // Put a simple error in session
    public static void respondSimple(HttpSession session, String errorMessage){
        session.setAttribute("errorMessage", errorMessage);
    }
    
     // Get simple error from session, then delete it
    public static String requestSimple(HttpSession session){
        String message =  (String) session.getAttribute("errorMessage") == null? ""  : (String) session.getAttribute("errorMessage");
        session.setAttribute("errorMessage", null);
        return message;
    }
    
}
