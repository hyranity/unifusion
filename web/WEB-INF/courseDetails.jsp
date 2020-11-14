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
        <title>UniFusion :: Course Details</title>
        <link rel="stylesheet" href="CSS/courseDetails.css">
        <link rel="stylesheet" href="CSS/all.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <div id='bot' onclick='location.href="Chatbot"'>
            <img id='eugeo' src='https://www.flaticon.com/svg/static/icons/svg/3398/3398640.svg'>
            <a id='desc'>Need help?<br><span>Eugeo's here!</span></a>
        </div>
        
        <div id='container'>

        <div id='navbar'>
            <a href='Course?id=<%out.print(request.getParameter("course"));%>' id='back'>&lt; <span>Back</span></a>
            <a href='Home' id='scaffold'>Scaffold</a>
            <a href='Dashboard' class='link'>Dashboard</a>
            <a href='AccountDetails' class='link'>Account</a>
            <a href='Chatbot' class='link'>Chatbot</a>
        </div>
                
                

            <div id='top'>
                <a id='heading'>Course details</a>
                <a id='subheading'>Course name</a>
            </div>

            <a id='error' style='margin-top: 40px;'><%out.print(Errors.requestSimple(session));%></a>

            <form action='PerformEditCourse'>

                <!-- section 1: identity -->

                <div class='section'>
                    <div class='header'>
                        <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
                    </div>

                    <div class='textboxes'>

                        <div id='left'>
                            <a class='label' id='name'>Course code</a>
                            <input class='textbox' id='courseCode-input' style='background-color: rgba(223, 224, 242, 0.5); color: white;' type='text' name='courseCode' placeholder='eg. GG420' readonly value="${course.getCoursecode()}" disabled>
                        </div>

                        <div id='right'>
                            <a class='label' id='email'>Course title</a>
                            <input class='textbox' id='courseName-input' type='text' name='courseTitle' placeholder='Title' value="${course.getTitle()}">
                        </div>

                    </div>
                </div>

                <!-- section 2: programme -->

                <div class='section'>
                    <div class='header'>
                        <img src='https://i.postimg.cc/wBD3pkrF/unifusion-create-class-graphic-1-2.png'>
                    </div>

                    <div class='textboxes' id='programmeTextboxes'>

                        <div id='left'>
                            <a id='programmeTextboxesMsg'>The programme settings of a course cannot be edited.</a>
                            <a class='label' id='name'>Is this course part of a programme?</a>
                            <input type='checkbox' class='checkbox' id='hasProgramme' name='hasProgramme' disabled>
                            <label class='checkboxLabel' for='hasProgramme' id='hasProgrammeLabel'>
                                <div class='slider'></div>
                            </label>
                            <a class='label' id='programmeCodeLabel'>Programme code</a>
                            <input id='programmeCodeTextbox' class='textbox' type='text' name='programmeCode' placeholder='eg. LOL1337'>
                            <input type='hidden' id='programmeCodeEnabled' name='programmeCodeEnabled' value='false' disabled>
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
                            <input class='textbox' id='description-input' type='text' name='description' placeholder='eg. GG420' value="${course.getDescription()}">
                        </div>

                        <div id='right'>
                        </div>

                    </div>
                </div>

                <!-- section 4: design -->

                <div class='section'>
                    <div class='header'>
                        <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
                    </div>

                    <div class='textboxes'>

                        <div id='left'>
                            <a class='label' id='name'>Icon URL</a>
                            <input class='textbox' type='text' name='iconURL' placeholder='eg. example.com'>
                            <a class='label' id='name'>Banner URL</a>
                            <input class='textbox' type='text' name='bannerURL' placeholder='eg. example.com'>
                        </div>

                        <div id='right'>
                            <a class='label' id='name'>Is this class public?</a>
                            <%
                                Models.Course course = (Models.Course) request.getAttribute("course");
                                if (course.getIspublic()) {
                                    out.print("<input type='checkbox' class='checkbox' id='isPublic' name='isPublic' checked>");
                                } else {
                                    out.print("<input type='checkbox' class='checkbox' id='isPublic' name='isPublic'>");
                                }
                            %>
                            <label class='checkboxLabel' for='isPublic' id='isPublicLabel' ${course.getIspublic() ? "checked" : ""}>
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
            function hasProgrammeClicked() {
                var hasProgramme = document.getElementById("hasProgramme");
                var courseCodeTextbox = document.getElementById("programmeCodeTextbox");

                document.getElementById("programmeCodeEnabled").value = hasProgramme.checked;
                document.getElementById("semesterCodeEnabled").value = hasProgramme.checked;

                if (hasProgramme.checked) {
                    programmeCodeTextbox.disabled = false;
                    semesterCodeTextbox.disabled = false;
                } else {
                    programmeCodeTextbox.disabled = true;
                    semesterCodeTextbox.disabled = true;
                }
            }
        </script>

    </body>
    
<script src="JS/validator.js"></script>
<script src="JS/courseDetails.js"></script>
    
</html>

