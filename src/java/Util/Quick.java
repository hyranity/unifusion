/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controllers.Perform.PerformPostAnnouncement;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileExistsException;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 *
 * @author mast3
 */
public class Quick {

    public static void print(String string) {
        System.out.println(string);
    }

    public static int getAge(Date dateOfBirth) {
        return Years.yearsBetween(new DateTime(dateOfBirth), DateTime.now()).getYears();
    }

    public static Object getSession(HttpServletRequest request, String name) {
        HttpSession session = request.getSession();
        return session.getAttribute(name);
    }

    public static void putInJsp(HttpServletRequest request, String name, String value) {
        request.setAttribute(name, value);
    }

    public static <T> String generateID(EntityManager em, UserTransaction utx, java.lang.Class<T> classType, String idFieldName) {
        int repeater = 0;

        // Generate a random ID
        String id = UUID.randomUUID().toString();

        // Check if this ID exists in DB or not
        while (repeater < 5 && new DB(em, utx).getSingleResult(idFieldName, id, classType) != null) {
            // regenerate
            id = UUID.randomUUID().toString();
            repeater++;
        }

        return UUID.randomUUID().toString();
    }

    public static String getIcon(String iconURL) {
        return iconURL == null || iconURL.trim().isEmpty() ? "https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg" : iconURL;
    }

    public static String getRole(String role) {

        if (role.equalsIgnoreCase("classTeacher")) {
            return "Class Teacher";
        } else if (role.equalsIgnoreCase("courseLeader")) {
            return "Course Leader";
        } else if (role.equalsIgnoreCase("programmeLeader")) {
            return "Programme Leader";
        } else if (role.equalsIgnoreCase("institutionAdmin")) {
            return "Administrator";
        } else {
            return "Unknown role";
        }
    }

    public static void writeFile(FileItem item, String entireFileName) throws FileUploadException, FileExistsException, Exception {
        item.write(new File(entireFileName));
    }

    public static void displayFile(String filename, ServletContext context, HttpServletRequest request, HttpServletResponse response, Util.Servlet servlet, String errorRedirectURL) throws IOException {
        File file = new File(filename);
        String fileType = context.getMimeType(filename);

        // Check for valid types
        if (fileType != null) {
            // Don't do anything, since it's a valid file type
        } else if (fileType == null && filename.contains(".mp4")) {
            fileType = "video/mp4";
        } else {
            Errors.respondSimple(request.getSession(), "Unplayable file type");
            System.out.println("Unplayable file type");
            servlet.toServlet(errorRedirectURL);
            return;
        }

        response.setHeader("Content-Type", fileType);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        try {
            Files.copy(file.toPath(), response.getOutputStream());
        } catch (Exception ex) {
            Errors.respondSimple(request.getSession(), "No file found");
            System.out.println("No file found");
            servlet.toServlet(errorRedirectURL);
            return;
        }
    }

    public static void deleteFile(String filename) {
        File file = new File(filename);
        file.delete();
    }

    public static String minsBetween(Date date1, Date date2) {

        // Get time difference
        Period period = new Period(new DateTime(date1), new DateTime(date2));

        // Build and return string in minutes
        PeriodFormatterBuilder builder = new PeriodFormatterBuilder();
        return builder.appendMinutes().printZeroNever().toFormatter().print(period);
    }

    public static String timeSince(Date date) {
        Period period = new Period(new DateTime(date), DateTime.now());

        PeriodFormatterBuilder builder = new PeriodFormatterBuilder();

        if (period.getYears() > 0) {
            builder.appendYears().appendSuffix(" yrs ago");
        } else if (period.getMonths() > 0) {
            builder.appendMonths().appendSuffix(" mths ago");
        } else if (period.getWeeks() > 0) {
            builder.appendWeeks().appendSuffix(" wks ago");
        } else if (period.getDays() > 0) {
            builder.appendDays().appendSuffix(" days ago");
        } else if (period.getHours() > 0) {
            builder.appendHours().appendSuffix(" hrs ago");
        } else if (period.getMinutes() > 0) {
            builder.appendMinutes().appendSuffix(" mins ago");
        } else {
            return "Just now";
        }

        PeriodFormatter formatter = builder.printZeroNever().toFormatter();

        return formatter.print(period);
    }

    public static String timeTo(Date date) {
        Period period = new Period(DateTime.now(), new DateTime(date));

        PeriodFormatterBuilder builder = new PeriodFormatterBuilder();

        if (period.getYears() > 0) {
            builder.appendYears().appendSuffix(" yrs left");
        } else if (period.getMonths() > 0) {
            builder.appendMonths().appendSuffix(" mths left");
        } else if (period.getWeeks() > 0) {
            builder.appendWeeks().appendSuffix(" wks left");
        } else if (period.getDays() > 0) {
            builder.appendDays().appendSuffix(" days left");
        } else if (period.getHours() > 0) {
            builder.appendHours().appendSuffix(" hrs left");
        } else if (period.getMinutes() > 0) {
            builder.appendMinutes().appendSuffix(" min left");
        } else {
            return "Starting";
        }

        PeriodFormatter formatter = builder.printZeroNever().toFormatter();

        return formatter.print(period);
    }

    public static String daysTo(Date date) {
        Period period = new Period(DateTime.now(), new DateTime(date));

        PeriodFormatterBuilder builder = new PeriodFormatterBuilder();

        if (period.getYears() > 0) {
            builder.appendYears().appendSuffix(" yrs left");
        } else if (period.getMonths() > 0) {
            builder.appendMonths().appendSuffix(" mths left");
        } else if (period.getWeeks() > 0) {
            builder.appendWeeks().appendSuffix(" wks left");
        } else if (period.getDays() > 0) {
            builder.appendDays().appendSuffix(" days left");

        } else {
            return "today";
        }

        PeriodFormatter formatter = builder.printZeroNever().toFormatter();

        return formatter.print(period);
    }

    public static String formatDate(Date date) {
        String dateStr = new DateTime(date).toString(DateTimeFormat.mediumDate());
        StringBuilder sb = new StringBuilder(dateStr);
        return sb.deleteCharAt(dateStr.indexOf(",")).toString();
    }

    // To generate a random string
    public static String generateStr(int length) {
        String charpool = "abcdefhijklmnqrsuvwxyz1234567890";
        String output = "";

        for (int i = 0; i < length; i++) {
            int randomIndex = new Random().nextInt(charpool.length());
            output += charpool.charAt(randomIndex);
        }

        return output.toUpperCase();
    }

    public static ArrayList<String> performUpload(String filePath, List<FileItem> multiparts, HttpServletRequest request, Util.Servlet servlet) {
        ArrayList<String> uploadedFiles = new ArrayList();

        // If the form submission is multipart content
        if (ServletFileUpload.isMultipartContent(request)) {

            try {
                System.out.println(multiparts.size());
                for (FileItem item : multiparts) {
                    System.out.println("is item form field? " + item.getFieldName());
                    if (!item.isFormField()) {
                        String name = new File(item.getName()).getName();

                        // File cant contain '|' character, using it for separating multiple urls in db
                        if (name.contains("|")) {
                            System.out.println("Invalid characters");
                            Errors.respondSimple(request.getSession(), "Your uploaded file(s) have invalid characters.");
                            return null;
                        }
                        try {
                            Quick.writeFile(item, filePath + File.separator + name);
                        } catch (FileExistsException ex) {
                            // Ignore this error and reuse the existing file in the directly
                            System.out.println("Duplicate file name for " + name);
                            Errors.respondSimple(request.getSession(), "Your uploaded file, " + name + " has a duplicate file name.");
                            return null;
                        }
                        System.out.println("Writing in " + filePath + File.separator + name);
                        uploadedFiles.add(filePath + File.separator + name);
                    }
                }

                System.out.println("File uploaded successfully");
            } catch (FileUploadException ex) {
                System.out.println(ex.getMessage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        }

        return uploadedFiles;
    }

    public static String combineStrArr(ArrayList<String> arrStr) {
        String outputStr = "";
        int counter = 0;
        for (String file : arrStr) {
            counter++;

            outputStr += file;

            // If not yet end, add separator
            if (counter < arrStr.size()) {
                outputStr += "|";
            }
        }

        return outputStr;
    }

    public static String uploadFile(String filePath, List<FileItem> multiparts, HttpServletRequest request, Util.Servlet servlet) {
        ArrayList<String> fileList = performUpload(filePath, multiparts, request, servlet);

        // Return "error" if an error occured
        if (fileList == null) {
            return "error";
        }

        // Return null if no files uploaded
        if (fileList.size() == 0l) {
            System.out.println("No files uploaded");
            return null;
        }

        return combineStrArr(fileList);
    }

    public static String getGrade(double marks) {
        if (marks < 50) {
            return "F";
        } else if (marks >= 50 && marks < 60) {
            return "C";
        } else {
            return "A";
        }
    }

    public static Date getDate(String date) {
        // Detect date (D/M/Y)
        DateTimeParser[] parsers = {
            DateTimeFormat.forPattern("dd/MM/yyyy").getParser(),
            DateTimeFormat.forPattern("dd MMM yyyy").getParser(),
            DateTimeFormat.forPattern("dd MMMM yyyy").getParser(),
            DateTimeFormat.forPattern("MMM dd yyyy").getParser(),
            DateTimeFormat.forPattern("MMMM dd yyyy").getParser(),
            DateTimeFormat.forPattern("dd-MM-yy").getParser(),
            DateTimeFormat.forPattern("dd/MM/yy").getParser(),
            DateTimeFormat.forPattern("dd MMM yy").getParser(),
            DateTimeFormat.forPattern("dd MMMM yy").getParser(),
            DateTimeFormat.forPattern("MMM dd yy").getParser(),
            DateTimeFormat.forPattern("MMMM dd yy").getParser(),
            DateTimeFormat.forPattern("dd-MM-yy").getParser()
        };

        DateTimeFormatter formatter = new DateTimeFormatterBuilder().append(null, parsers).toFormatter();
        
        return formatter.parseDateTime( date ).toDate();
    }

}
