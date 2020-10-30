/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import java.util.regex.*;
import java.util.Date;

/**
 *
 * @author mast3
 */
@WebServlet(name = "Chatbot", urlPatterns = {"/Chatbot"})
public class Chatbot extends HttpServlet {
    
    @PersistenceContext
    EntityManager em;
    
    @Resource
    private UserTransaction utx;

    // Commands
    String retrieveCommand = "(show me|give me|display|show|get)( all)*.*";
    String questions = "(how many|what (time|is the time)).*";
    String create = "(create|make|new|develop)( a| an)? ";
    String createEducation = create + "(class|course|institution|programme).*";
    String createClassObjects = create + "(announcement|session|assignment).*";

    // Util objects
    Util.Servlet servlet;
    Util.DB db = new Util.DB(em, utx);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        servlet = new Util.Servlet(request, response);

        // Read input
        String input = servlet.getQueryStr("input");
        
        if (input == null) {
            // Redirect if no query
            servlet.servletToJsp("chatbot.jsp");
            return;
        }

        // Process query
        input(input);

        // Redirect
        servlet.servletToJsp("chatbot.jsp");
        
    }
    
    public void addCreateEducationComponent(String type, String itemFirstData, String itemSecondData) {
        
        String output = "  <div class='action'>\n"
                + "            <div class='top'>\n"
                + "              <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/3324/3324859.svg'>\n"
                + "              <div class='text'>\n"
                + "                <a class='type'>ACTION</a>\n"
                + "                <a class='desc'>Create a <span>" + type + "</span></a>\n"
                + "              </div>\n"
                + "            </div>\n"
                + "            <div class='bottom'>\n"
                + "              <a class='desc'>With the following details:</a>\n"
                + "              <div class='item'>\n"
                + "                <a class='label'>ID</a>\n"
                + "                <a class='value'>" + itemFirstData + "</a>\n"
                + "              </div>\n"
                + "              <div class='item'>\n"
                + "                <a class='label'>Name</a>\n"
                + "                <a class='value'>" + itemSecondData + "<</a>\n"
                + "              </div>\n"
                + "            </div>  \n"
                + "          </div>";
        
        servlet.putInJsp("result", output);
    }
    
    public void input(String input) {
        // Lowercase the first letter
        // Thanks to Rekin @
        // https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java
        input = input.substring(0, 1).toLowerCase() + input.substring(1);

        // Check intention
        // If retrieve
        if (input.matches(retrieveCommand)) {
            retrieve(input);
        } // If asking a question
        else if (input.matches(questions)) {
            questions(input);
        } else if (input.matches(createEducation)) {
            createEducation(input);
        } else if (input.matches(createClassObjects)) {
            createClassObjects(input);
        } else {
            System.out.println("Sorry, I don't understand.");
        }
    }

    // Create stuff in class (TEACHER ONLY!)
    public void createClassObjects(String input) {
        // Find target
        if (input.matches(".* (announcement|session|assignment) for.*")) {
            String target = substr(input, "for (.*)\\s");

            // Obtain the class here
            // If no target is found
            if (target == null || target == "") {
                System.out.println("Sorry, I don't understand");
                return;
            }
            
            if (input.matches(".*(announcement).*")) {
                System.out.println("Create an announcement for " + target);
            } else if (input.matches(".*(session).*")) {
                System.out.println("Create an assignment for " + target);
            } else if (input.matches(".*(assignment).*")) {
                System.out.println("Create an session for " + target);
            } else {
                System.out.println("Sorry, I don't understand");
            }
        }
        
    }

    // Create class/course/prog/institution
    public void createEducation(String input) {
        if (input.matches(".* (class).*")) {

            // Detect ID and name
            String id = substr2(input, ".*(id|ID) (\\S*)\\s?");
            String name = substr2(input, ".*(name|title) (\\S*)\\s?");

            // If ID provided
            if (id != null && !id.trim().isEmpty()) {

                // And name as well
                if (name != null && !name.trim().isEmpty()) {
                    System.out.println("Create a class with ID " + id + " titled " + name);
                } else {
                    System.out.println("Create a class with ID " + id);
                }
            } // If name only
            else if (name != null && !name.trim().isEmpty()) {
                System.out.println("Create a class titled " + name);
            } else {
                System.out.println("Click here to create class");
            }
        } else if (input.matches(".* (course).*")) {
            // Detect indeed
            String id = substr2(input, ".*(id|ID) (\\S*)\\s?");
            String name = substr2(input, ".*(name|title) (\\S*)\\s?");

            // If ID provided
            if (id != null && !id.trim().isEmpty()) {

                // And name as well
                if (name != null && !name.trim().isEmpty()) {
                    System.out.println("Create a course with ID " + id + " titled " + name);
                } else {
                    System.out.println("Create a course with ID " + id);
                }
            } // If name only
            else if (name != null && !name.trim().isEmpty()) {
                System.out.println("Create a course titled " + name);
            } else {
                System.out.println("Click here to create course");
            }
        } else if (input.matches(".* (programme).*")) {
            // Detect indeed
            String id = substr2(input, ".*(id|ID) (\\S*)\\s?");
            String name = substr2(input, ".*(name|title) (\\S*)\\s?");

            // If ID provided
            if (id != null && !id.trim().isEmpty()) {

                // And name as well
                if (name != null && !name.trim().isEmpty()) {
                    System.out.println("Create a programme with ID " + id + " titled " + name);
                } else {
                    System.out.println("Create a programme with ID " + id);
                }
            } // If name only
            else if (name != null && !name.trim().isEmpty()) {
                System.out.println("Create a programme titled " + name);
            } else {
                System.out.println("Click here to create programme");
            }
        } else if (input.matches(".* (institution).*")) {
            // Detect indeed
            String id = substr2(input, ".*(id|ID) (\\S*)\\s?");
            String name = substr2(input, ".*(name|title) (\\S*)\\s?");

            // If ID provided
            if (id != null && !id.trim().isEmpty()) {

                // And name as well
                if (name != null && !name.trim().isEmpty()) {
                    System.out.println("Create a institution with ID " + id + " titled " + name);
                } else {
                    System.out.println("Create a institution with ID " + id);
                }
            } // If name only
            else if (name != null && !name.trim().isEmpty()) {
                System.out.println("Create a institution titled " + name);
            } else {
                System.out.println("Click here to create institution");
            }
        } else {
            System.out.println("Unknown command");
        }
    }

    // Asking questions
    public void questions(String input) {
        // If how many...
        if (input.matches("(how many).*")) {

            // How many classes?
            if (input.matches(".*(classes).*")) {
                if (input.matches(".*today.*")) {
                    System.out.println("5 classes today.");
                } else {
                    System.out.println("You have 50 classes in total");
                }
            } else if (input.matches(".*(courses).*")) {
                System.out.println("You have 10 courses in total");
            } else if (input.matches(".*(programmes).*")) {
                System.out.println("You are active in 2 programmes");
            } else if (input.matches(".*(institutions).*")) {
                System.out.println("You are a student in 3 institutions.");
            } else {
                System.out.println("That is indeed a good question");
            }
        } // What time is it
        else if (input.matches("what (time|is the time|time is it)")) {
            System.out.println("It is currently " + new Date());
        } else {
            System.out.println("That is indeed a good question");
        }
    }

    // Retrieving data
    public void retrieve(String input) {

        // If retrieve all
        if (input.matches(retrieveCommand + " (classes|courses|programmes|institutions).*")) {

            // if classes
            if (input.matches(".*(classes).*")) {

                // If a "from" is provided
                if (input.matches(".*(from|in).*")) {

                    // Check for both FROM xx and IN xx
                    String from = substr(input, "from (.*)") == "" ? substr(input, "in (.*)") : substr(input, "from (.*)");
                    
                    System.out.println("Getting all classes from " + from);
                }
                
                System.out.println("Getting all classes");
                
            } // if courses
            else if (input.matches(".*(courses).*")) {
                // If a "from" is provided
                if (input.matches(".*(from|in).*")) {

                    // Check for both FROM xx and IN xx
                    String from = substr(input, "from (.*)") == "" ? substr(input, "in (.*)") : substr(input, "from (.*)");
                    
                    System.out.println("Getting all courses from " + from);
                }
                
                System.out.println("Getting all classes");
                
            } // if programmes
            else if (input.matches(".*(programmes).*")) {
                // If a "from" is provided
                if (input.matches(".*(from|in).*")) {

                    // Check for both FROM xx and IN xx
                    String from = substr(input, "from (.*)") == "" ? substr(input, "in (.*)") : substr(input, "from (.*)");
                    
                    System.out.println("Getting all programmes from " + from);
                }
                
                System.out.println("Getting all classes");
                
            } // if institutions
            else if (input.matches(".*(institutions).*")) {
                System.out.println("Showing all institutions");
            } else {
                System.out.println("error");
            }
        } // If retrieve singular
        else if (input.matches("(show me|give me|display|show|get).*")) {
            // Get singular target
            String target = substr2(input, "(class|course|programme|institution) (\\w*)");

            // target cannot be null
            if (target == "") {
                System.out.println("Unknown commannd");
                return;
            }

            // if class
            if (input.matches(".*(class).*")) {

                // If a "from" is provided
                if (input.matches(".*(from|in).*")) {

                    // Check for both FROM xx and IN xx
                    String from = substr(input, "from (.*)") == "" ? substr(input, "in (.*)") : substr(input, "from (.*)");
                    
                    System.out.println("Getting class " + target + " from " + from);
                } else {
                    // Get all
                    System.out.println("Getting class " + target);
                }
                
            } // if course
            else if (input.matches(".*(course).*")) {
                // If a "from" is provided
                if (input.matches(".*(from|in).*")) {

                    // Check for both FROM xx and IN xx
                    String from = substr(input, "from (.*)") == "" ? substr(input, "in (.*)") : substr(input, "from (.*)");
                    
                    System.out.println("Getting course " + target + " from " + from);
                } else {
                    // Get all
                    System.out.println("Getting course " + target);
                }
                
            } // if programme
            else if (input.matches(".*(programme).*")) {
                // If a "from" is provided
                if (input.matches(".*(from|in).*")) {

                    // Check for both FROM xx and IN xx
                    String from = substr(input, "from (.*)") == "" ? substr(input, "in (.*)") : substr(input, "from (.*)");
                    
                    System.out.println("Getting programme " + target + " from " + from);
                } else {
                    // Get programme
                    System.out.println("Getting programme " + target);
                }
            } // if institution
            else if (input.matches(".*(institution).*")) {
                System.out.println("Getting institution " + target);
            } else {
                // Try to find this target
                System.out.println("Getting " + target);
            }
        } else {
            System.out.println("error");
        }
    }
    
    public String substr(String input, String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(input);
        if (m.find()) {
            return m.group(1);
        } else {
            return "";
        }
    }
    
    public String substr2(String input, String pattern) {
        Matcher m = Pattern.compile(pattern).matcher(input);
        if (m.find()) {
            return m.group(2);
        } else {
            return "";
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

}
