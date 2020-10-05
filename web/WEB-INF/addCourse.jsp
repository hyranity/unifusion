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
                <a href='MyClasses' id='back'>&lt; <span>Back</span></a>
                <a href='Home' id='scaffold'>Scaffold</a>
                <a href='MyClasses' class='link'>Dashboard</a>
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

            <form id='form' action="PerformAddCourse">

                <div class='section'>
                    <div class='image' id='left'>
                        <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
                    </div>
                    <div class='text' id='right'>
                        <a class='label'>Course code</a>
                        <input class='textbox' type='text' name='courseCode' placeholder='eg. GG420' required>
                        <a class='label'>Course name</a>
                        <input class='textbox' type='text' name='courseName' placeholder='eg. Computer Science' required>
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
                        <input id='programmeCodeTextbox' class='textbox' type='text' name='programmeCode' placeholder='eg. LOL1337' disabled>
                        <input type='hidden' id='programmeCodeEnabled' name='programmeCodeEnabled' value='false'>
                    </div>
                    <div class='image' id='right'>
                        <img src='https://i.postimg.cc/wBD3pkrF/unifusion-create-class-graphic-1-2.png'>
                    </div>
                </div>

                <div class='section'>
                    <div class='image' id='left'>
                        <img src='https://i.postimg.cc/wBD3pkrF/unifusion-create-class-graphic-1-2.png'>
                    </div>
                    <div class='text' id='right'>
                        <a class='label' id='name'>Is this course part of a semester?</a>
                        <input type='checkbox' class='checkbox' id='hasSemester' name='hasSemester' onclick='hasSemesterClicked()'>
                        <label class='checkboxLabel' for='hasSemester' id='hasSemesterLabel'>
                            <div class='slider'></div>
                        </label>
                        <a class='label' id='semesterCodeLabel'>Semester code</a>
                        <input id='semesterCodeTextbox' class='textbox' type='text' name='semesterCode' placeholder='eg. LOL1337' disabled>
                        <input type='hidden' id='semesterCodeEnabled' name='semesterCodeEnabled' value='false'>
                    </div>
                </div>

                <div class='section'>
                    <div class='text' id='left'>
                        <a class='label'>Description</a>
                        <input class='textbox' type='text' name='description' placeholder='eg. This is a CS class.' required>
                    </div>
                    <div class='image' id='right'>
                        <img src='https://i.postimg.cc/wBD3pkrF/unifusion-create-class-graphic-1-2.png'>
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

            if (hasProgramme.checked) {
                programmeCodeTextbox.disabled = false;
            } else {
                programmeCodeTextbox.disabled = true;
            }
        }

        function hasSemesterClicked() {
            var hasSemester = document.getElementById("hasSemester");
            var semesterCodeTextbox = document.getElementById("semesterCodeTextbox");

            document.getElementById("semesterCodeEnabled").value = hasSemester.checked;

            if (hasSemester.checked) {
                semesterCodeTextbox.disabled = false;
            } else {
                semesterCodeTextbox.disabled = true;
            }
        }
    </script>
</html>
