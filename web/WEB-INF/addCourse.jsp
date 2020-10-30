<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Create a Course</title>
        <link rel="stylesheet" href="CSS/addCourse.css">
    </head>
    <body>
        <div id='container'>

            <div id='navbar'>
                <a href='Dashboard' id='back'>&lt; <span>Back</span></a>
                <a href='Home' id='scaffold'>Scaffold</a>
                <a href='Dashboard' class='link'>Dashboard</a>
                <a href='AccountDetails' class='link'>Account</a>
            </div>

            <div id='top'>
                <div class='image'>
                    <img src='https://image.flaticon.com/icons/svg/2991/2991548.svg'>
                </div>
                <div class='text'>
                    <a id='title'>Create a course</a>
                    <a id='desc'>You're now creating a course, let's get it!</a>
                    <div id='buttons'>
                        <a id='proceed-button' href='#form'>Proceed!</a>
                        <a id='learn-button' href='#'>Learn more</a>
                    </div>
                </div>
            </div>
            
            <a id='error' style='margin-top: 40px;'><%out.print(Errors.requestSimple(session));%></a>

            <form id='form' action="PerformAddCourse">

                <div class='section'>
                    <div class='image' id='left'>
                        <img src='https://i.postimg.cc/hGwyDdzB/unifusion-create-course-graphic-1.png'>
                    </div>
                    <div class='text' id='right'>
                        <a class='label'>Course code</a>
                        <input class='textbox' type='text' name='courseCode' placeholder='eg. GG420' required value="${id}">
                        <a class='label'>Course name</a>
                        <input class='textbox' type='text' name='courseName' placeholder='eg. Computer Science' required value="${title}">
                    </div>
                </div>

                <div class='section'>
                    <div class='text' id='left'>
                        <a class='label' id='name'>Is this course part of a programme?</a>
                        <input type='checkbox' class='checkbox' id='hasProgramme' name='hasProgramme' onclick='hasProgrammeClicked()'>
                        <label class='checkboxLabel' for='hasProgramme' id='hasProgrammeLabel'>
                            <div class='slider'></div>
                        </label>
                        <a class='label' id='programmeCodeLabel'>Programme code</a>
                        <input id='programmeCodeTextbox' class='textbox' type='text' name='programmeCode' placeholder='eg. LOL1337' >
                        <input type='hidden' id='programmeCodeEnabled' name='programmeCodeEnabled' value='false'>
                        <!--<a class='label' id='semesterCodeLabel'>Semester code (optional)</a>
                        <input id='semesterCodeTextbox' class='textbox' type='text' name='semesterCode' placeholder='eg. LOL1337' disabled>
                        <input type='hidden' id='semesterCodeEnabled' name='semesterCodeEnabled' value='false'>-->
                    </div>
                    <div class='image' id='right'>
                        <img src='https://i.postimg.cc/8cgZWgmX/unifusion-create-course-graphic-2.png'>
                    </div>
                </div>
                
                <div class='section'>
                    <div class='image' id='left'>
                        <img src='https://i.postimg.cc/cHnDrLw8/unifusion-create-course-graphic-3.png'>
                    </div>
                    <div class='text' id='right'>
                        <a class='label'>Description</a>
                        <input class='textbox' type='text' name='description' placeholder='eg. This is a CS class.' required>
                        
                        <a class='label' id='name'>Is this course public?</a>
                        <input type='checkbox' class='checkbox' id='isPublic' name='isPublic'>
                        <label class='checkboxLabel' for='isPublic' id='isPublicLabel'>
                          <div class='slider'></div>
                        </label>
                    </div>
                </div>

                <input id='create-button' type='submit' value='Create course!'>

            </form>

        </div>
    </body>

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
</html>
