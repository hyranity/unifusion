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
            <a href='Dashboard' id='back'>&lt; <span>Back</span></a>
            <a href='Home' id='scaffold'>Scaffold</a>
            <a href='Dashboard' class='link'>Dashboard</a>
            <a href='AccountDetails' class='link'>Account</a>
            <a href='Chatbot' class='link'>Chatbot</a>
        </div>

        <a id='header'>Dashboard Menu</a>

        <div id='rows'>

          <div class='row'>
            <div class='box'>
              <a class='text'>Class</a>
            </div>
            <div class='box option' onclick='location.href="AddClass"'>
              <a class='label'>01</a>
              <a class='text'>Create a Class</a>
            </div>
            <div class='box option' onclick='location.href="JoinClass"'>
              <a class='label'>02</a>
              <a class='text'>Join a Class</a>
            </div>
          </div>

          <div class='row'>
            <div class='box option' onclick='location.href="AddCourse"'>
              <a class='label'>01</a>
              <a class='text'>Create a Course</a>
            </div>
            <div class='box option' onclick='location.href="JoinCourse"'>
              <a class='label'>02</a>
              <a class='text'>Join a Course</a>
            </div>
            <div class='box'>
              <a class='text'>Course</a>
            </div>
          </div>

          <div class='row'>
            <div class='box'>
              <a class='text'>Programme</a>
            </div>
            <div class='box option' onclick='location.href="AddProgramme"'>
              <a class='label'>01</a>
              <a class='text'>Create a Programme</a>
            </div>
            <div class='box option' onclick='location.href="JoinProgramme"'>
              <a class='label'>02</a>
              <a class='text'>Join a Programme</a>
            </div>
          </div>

          <div class='row'>
            <div class='box option' onclick='location.href="AddInstitution"'>
              <a class='label'>01</a>
              <a class='text'>Create a Institution</a>
            </div>
            <div class='box option' onclick='location.href="JoinInstitution"'>
              <a class='label'>02</a>
              <a class='text'>Join a Institution</a>
            </div>
            <div class='box'>
              <a class='text'>Institution</a>
            </div>
          </div>

        </div>

      </div>
    </body>

</html>
