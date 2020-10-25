<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Assignment Details</title>
        <link rel="stylesheet" href="CSS/assignmentDetails.css">
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
              <a id='heading'>Assignment Details</a>
            </div>
          </div>
        </div>

        <div id='content' action=''>

          <div id='header'>
            <a id='assignmentId'>A001</a>
            <a id='assignmentTitle'>Assignment Name</a>
          </div>

          <div id='details'>

            <div class='detail'>
              <a class='label'>Deadline</a>
              <a class='value'>01 Jan 2020</a>
            </div>

            <div class='detail'>
              <a class='label'>Total marks</a>
              <a class='value'>100</a>
            </div>

          </div>

          <a id='message'>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.<br><br>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</a>

          <div id='attachments'>
            <div class='attachment'>
              <img class='icon' src='https://icons.iconarchive.com/icons/pelfusion/flat-file-type/512/doc-icon.png'>
              <a href='#' class='name'>sampleFile.doc</a>
            </div>
            <div class='attachment'>
              <img class='icon' src='https://how-to.aimms.com/_images/exe-file-icon-68130.png'>
              <a href='#' class='name'>sampleProgram.exe</a>
            </div>
          </div>

          <div id='buttons'>
            <a href='#' id='viewSubmissions-button' class='button'>View Submissions</a>
            <a href='#' id='edit-button' class='button'>Edit</a>
            <a href='#' id='makeSubmission-button' class='button'>Make Submission</a>
            <a href='#' id='viewMySubmission-button' class='button'>View My Submission</a>
            <a href='#' id='remove-button' class='button'>Delete</a>
          </div>

        </div>

      </div>

    </body>

</html>
