<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Create a Course</title>
        <link rel="stylesheet" href="CSS/dashboardMenu.css">
    </head>
    <body>
        <div id='container'>

          <div id='navbar'>
                <a href='MyClasses' id='back'>&lt; <span>Back</span></a> <!-- note: should redirect to eg. Class?id=C001 -->
                <a href='Home' id='scaffold'>Scaffold</a>
                <a href='MyClasses' class='link'>Dashboard</a>
                <a href='AccountDetails' class='link'>Account</a>
            </div>

          <div id='text'>

            <a class='title'>Dashboard Menu</a>

            <div class='section'>
              <a class='header'>Classes</a>
              <div class='links'>
                <a class='link' href='AddClass'>Create a class</a>
                <a class='link' href='#'>Join a class</a>
              </div>  
            </div>

            <div class='section'>
              <a class='header'>Courses</a>
              <div class='links'>
                <a class='link' href='AddCourse'>Create a course</a>
                <a class='link' href='#'>Join a course</a>
              </div>  
            </div>

            <div class='section'>
              <a class='header'>Programmes</a>
              <div class='links'>
                <a class='link' href='#'>Create a programme</a>
                <a class='link' href='#'>Join a programme</a>
              </div>  
            </div>

            <div class='section'>
              <a class='header'>Insitutions</a>
              <div class='links'>
                <a class='link' href='#'>Create a institution</a>
                <a class='link' href='#'>Join a institution</a>
              </div>  
            </div>

          </div>

        </div>
    </body>
</html>
