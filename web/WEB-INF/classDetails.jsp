<%@page import="Util.Errors"%>
<%@page import="Util.Quick"%>
<%@page import="Models.Users"%>
<%@page import="Models.Users"%>
<%@page import="Util.Server"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Class Details</title>
        <link rel="stylesheet" href="CSS/classDetails.css">
    </head>
    <body>
        <div id='container'>

            <div id='navbar'>
                <a href='Dashboard' id='back'>&lt; <span>Back</span></a> <!-- note: should redirect to eg. Class?id=C001 -->
                <a href='Home' id='scaffold'>Scaffold</a>
                <a href='Dashboard' class='link'>Dashboard</a>
                <a href='AccountDetails' class='link'>Account</a>
            </div>

            <div id='top'>
                <a id='heading'>Class details</a>
                <a id='subheading'>Group name</a>
            </div>
            
            <a id='error'><%out.print(Errors.requestSimple(session));%></a>

            <form action="PerformEditClass">

                <!-- section 1: identity -->

                <div class='section'>
                    <div class='header'>
                        <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
                    </div>

                    <div class='textboxes'>

                        <div id='left'>
                            <a class='label' id='name'>Class code</a>
                            <input class='textbox' type='text' name='classCode' placeholder='eg. GG420' value="${class.getClassid()}" readonly>
                        </div>

                        <div id='right'>
                            <a class='label' id='email'>Title</a>
                            <input class='textbox' type='text' name='classTitle' placeholder='Title' value="${class.getClasstitle()}">
                        </div>

                    </div>
                </div>

                <!-- section 2: course -->

                <div class='section'>
                    <div class='header'>
                        <img src='https://i.postimg.cc/wBD3pkrF/unifusion-create-class-graphic-1-2.png'>
                    </div>

                        <div class='textboxes' id='courseTextboxes'>
                            
                          <div id='left'>
                            <a id='courseTextboxesMsg'>The course settings of a class cannot be edited.</a>
                            <a class='label' id='name'>Is this class part of a course?</a>
                            <input type='checkbox' class='checkbox' id='hasCourse' name='hasCourse' disabled>
                            <label class='checkboxLabel' for='hasCourse' id='hasCourseLabel'>
                                <div class='slider'></div>
                            </label>
                            <a class='label' id='courseCodeLabel'>Course code</a>
                            <input id='courseCodeTextbox' class='textbox' type='text' name='courseCode' placeholder='eg. LOL1337' value="${class.getCoursecode()}" disabled>
                            <input type='hidden' id='courseCodeEnabled' name='courseCodeEnabled' value='false' disabled>
                        </div>

                    </div>
                </div>

                <!-- section 3: details -->

                <div class='section'>
                    <div class='header'>
                        <img src='https://i.postimg.cc/Rh6Rc5yL/unifusion-create-class-graphic-2.png'>
                    </div>

                    <div class='textboxes'>

                        <div id='left'>
                            <a class='label' id='name'>Description</a>
                            <input class='textbox' type='text' name='description' placeholder='eg. GG420' value="${class.getDescription()}">
                        </div>

                        <div id='right'>
                            <a class='label'>Class type</a>
                            <select class='dropdown' name='classType'>

                                <%
                                    // Display class type

                                    String lecture = "";
                                    String practical = "";
                                    String tutorial = "";
                                    String other = "";

                                    Models.Class classroom = (Models.Class) request.getAttribute("class");

                                    if (classroom.getClasstype().equalsIgnoreCase("lecture")) {
                                        lecture = "selected";
                                    } else if (classroom.getClasstype().equalsIgnoreCase("practical")) {
                                        practical = "selected";
                                    } else if (classroom.getClasstype().equalsIgnoreCase("tutorial")) {
                                        tutorial = "selected";
                                    } else {
                                        other = "selected";
                                    }

                                    out.print("<option value='lecture' " + lecture + ">Lecture</option>");
                                    out.print("<option value='practical' " + practical + ">Practical</option>");
                                    out.print("<option value='tutorial' " + tutorial + ">Tutorial</option>");
                                    out.print("<option value='other' " + other + ">Other</option>");
                                %>


                            </select>
                            <a class='dropdownLabel'>Click to view options</a>
                        </div>

                    </div>
                </div>

                <!-- section 4: design (HEADER TBC) -->

                <div class='section'>
                    <div class='header'>
                        <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
                    </div>

                    <div class='textboxes'>

                        <div id='left'>
                            <a class='label' id='name'>Icon URL</a>
                            <input class='textbox' type='text' name='iconURL' placeholder='eg. example.com' value="${class.getIconurl()}">
                            <a class='label' id='name'>Banner URL</a>
                            <input class='textbox' type='text' name='bannerURL' placeholder='eg. example.com' value="${class.getBannerurl()}">
                        </div>

                        <div id='right'>
                            <a class='label'>Colour theme</a>
                            <select class='dropdown' name='colourTheme'>
                                <%
                                    // Display color theme
                                    String dark = "";
                                    String light = "";
                                    String def = "";

                                    if (classroom.getColourtheme() != null && classroom.getColourtheme().equalsIgnoreCase("dark")) {
                                        dark = "selected";
                                    } else if (classroom.getColourtheme() != null && classroom.getColourtheme().equalsIgnoreCase("light")) {
                                        light = "selected";
                                    } else {
                                        def = "selected";
                                    }

                                    out.print("<option value='practical' " + dark + ">Dark</option>");
                                    out.print("<option value='tutorial' " + light + ">Light</option>");
                                    out.print("<option value='lecture' " + def + ">Default</option>");
                                %>
                            </select>
                            <a class='dropdownLabel'>Click to view options</a>

                            <a class='label' id='name'>Is this class public?</a>
                            <%
                                if (classroom.getIspublic()) {
                                    out.print("<input type='checkbox' class='checkbox' id='isPublic' name='isPublic' checked>");
                                } else {
                                    out.print("<input type='checkbox' class='checkbox' id='isPublic' name='isPublic'>");
                                }
                            %>

                            <label class='checkboxLabel' for='isPublic' id='isPublicLabel'>
                                <div class='slider'></div>
                            </label>
                        </div>

                    </div>
                </div>

                <!-- bottom section: save button -->

                <div class='section'>
                    <input id='save-button' type='submit' value='Save!'>
                </div>
                
            </form>
        </div>

        <script>
            function hasCourseClicked() {
                
                // note: editing course settings in a class has been disabled
                
                /*var hasCourse = document.getElementById("hasCourse");
                var courseCodeTextbox = document.getElementById("courseCodeTextbox");

                document.getElementById("courseCodeEnabled").value = hasCourse.checked;

                if (hasCourse.checked) {
                    courseCodeTextbox.disabled = false;
                } else {
                    courseCodeTextbox.disabled = true;
                }*/
            }
        </script>

    </body>
</html>

