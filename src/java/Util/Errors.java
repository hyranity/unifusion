package Util;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Johann Lee Jia Xuan
 *
 */
public class Errors {

    private ArrayList<String> errorList;
    private HttpServletRequest request;
    
    // Regular constructor
    public Errors() {
        request = null;
        errorList = new ArrayList();
    }

    // To easily get previous errors
    public Errors(HttpServletRequest request) {
        this.request = request;
        errorList = new ArrayList();
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
    
    // Get errorList from another servlet
    public  void requestErrors(HttpServletRequest request){
        this.errorList =  (ArrayList<String>) request.getAttribute("errorList");
    }
    
    // Put errorList into another servlet
    public void respondErrors(HttpServletRequest request){
         request.setAttribute("errorList", errorList);
    }
}
