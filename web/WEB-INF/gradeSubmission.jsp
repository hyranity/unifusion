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
              <img id='icon' src='${icon}'>
              <div id='text'>
                <a id='subheading'>${subheading}</a>
                <a id='heading'>Grade Submission</a>
              </div>
            </div>
          </div>

          <div id='content' action='PerformGradeSubmission'>

            <div id='header'>
              <a id='assignmentId'>${assignment.getComponentid()}</a>
              <a id='assignmentTitle'>${assignment.getTitle()}</a>
            </div>

            <div id='details'>

              <div id='poster'>
                <img id='posterIcon' src='${studentIcon}'>
                <div id='posterDetails'>
                  <a id='posterRole'>MEMBER</a>
                  <a href='#' id='posterName'>${student.getName()}</a>
                </div>
              </div>

              <div class='detail'>
                <a class='label'>SUBMITTED</a>
                <a class='value'>${dateSubmitted}</a>
                <a class='subvalue'>${timeSubmitted}</a>
              </div>

            </div>

            <a class='label' style='margin-left: -80px;'>Total Marks</a>
            <input class='number' type='number' name='marks' placeholder='eg. 100'>
            
            <a class='label' style='margin-left: -400px;'>Tutor's remarks</a>
            <textarea class="textarea" name='remarks' placeholder='What is this assignment about?'></textarea>

            <div id='buttons'>
              <input id='confirm-button' class='button' type='submit' value='Confirm!'>
            </div>

          </div>

        </form>

    </body>


</html>

