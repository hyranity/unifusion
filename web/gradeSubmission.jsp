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
        <title>UniFusion :: Grade Submission</title>
        <link rel="stylesheet" href="CSS/gradeSubmission.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>

    <body>
        <form id='container'>

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
                <a id='heading'>Grade Submission</a>
              </div>
            </div>
          </div>

          <div id='content' action=''>

            <div id='header'>
              <a id='assignmentId'>AS001</a>
              <a id='assignmentTitle'>Assignment Name</a>
            </div>

            <div id='details'>

              <div id='poster'>
                <img id='posterIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
                <div id='posterDetails'>
                  <a id='posterRole'>MEMBER</a>
                  <a href='#' id='posterName'>Aioi Yuuko</a>
                </div>
              </div>

              <div class='detail'>
                <a class='label'>SUBMITTED</a>
                <a class='value'>01 Jan 2020</a>
                <a class='subvalue'>12.30pm</a>
              </div>

            </div>

            <a class='label' style='margin-left: -80px;'>Total Marks</a>
            <input class='number' type='number' name='marks' placeholder='eg. 100'>

            <div id='buttons'>
              <input id='confirm-button' class='button' type='submit' value='Confirm!'>
            </div>

          </div>

        </form>

    </body>


</html>

