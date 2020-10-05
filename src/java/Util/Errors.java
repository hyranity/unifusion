package Util;

import java.util.*;

/**
 *
 * @author Johann Lee Jia Xuan
 *
 */
public class Errors {

    private ArrayList<String> errorList;

    public Errors() {
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
}
