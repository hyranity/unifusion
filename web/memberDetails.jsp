<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Member List</title>
        <link rel="stylesheet" href="CSS/memberDetails.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>
    
    <body>
      <div id='container'>

        <div id='navbar'>
          <a href='#' id='back'>&lt; <span>Back</span></a>
          <a href='#' id='scaffold'>Scaffold</a>
          <a href='#' class='link'>Dashboard</a>
          <a href='#' class='link'>Account</a>
        </div>

        <div id='top'>
          <div id='topOverlay'></div>
          <div id='info'>
            <img id='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div id='text'>
              <a id='subheading'>C001 - Class Name (Class)</a>
              <a id='heading'>Member Details</a>
            </div>
          </div>
        </div>

        <div id='content' action=''>

          <div id='header'>
            <img id='memberIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div id='memberDetails'>
              <a id='memberId'>M001</a>
              <a id='memberName'>John Doe</a>
            </div>
          </div>

          <div id='details'>

            <div class='detail' onclick='location.href="#"'>
              <a class='label'>Submissions</a>
              <a class='value'>5</a>
            </div>

            <div class='detail'>
              <a class='label'>CGPA</a>
              <a class='value'>4.0000</a>
            </div>

            <div class='detail'>
              <a class='label'>Grade</a>
              <a class='value'>A</a>
            </div>

          </div>

          <form id='form' action=''>
            <a id='form-desc'>Make changes to this member's<br>CGPA/Grade using the form below:</a>
            <a class='label' style='margin-left: -180px;'>CGPA</a>
            <input class='textbox' type='text' name='cgpa' placeholder='eg. 4.0000'>
            <a class='label' style='margin-left: -180px;'>Grade</a>
            <input class='textbox' type='text' name='grade' placeholder='eg. A'>
            <input id='save-button' class='button' type='submit' value='Save!'>
          </form>

        </div>

      </div>

    </body>


</html>
