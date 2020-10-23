<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Session Details</title>
        <link rel="stylesheet" href="CSS/sessionDetails.css">
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
              <a id='heading'>Session Details</a>
            </div>
          </div>
        </div>

        <div id='content'>

          <div id='header'>
            <a id='announcementId'>S001</a>
            <a id='announcementTitle'>1 January 2021</a>
          </div>

          <div id='row'>

            <div id='left'>

              <div id='tutor'>
                <img id='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
                <div id='tutorDetails'>
                  <a id='tutorRole'>TUTOR</a>
                  <a href='#' id='tutorName'>John Doe</a>
                </div>
              </div>

              <div class='detail'>
                <a class='label'>TIME</a>
                <a class='value'>2.00 pm - 4.30 pm</a>
              </div>

              <div class='detail'>
                <a class='label'>VENUE</a>
                <a class='value'>Your Mom's House</a>
              </div>

            </div>

            <div id='right'>

              <div id='attendance'>
                <div class='detail' id='status'>
                  <a class='label'>ATTENDANCE</a>
                  <a class='value'>Present</a>
                </div>
                <div class='detail' id='checked'>
                  <a class='label'>CHECKED</a>
                  <a class='value'>2.07 pm, 1 Jan 2021</a>
                </div>
                <a id='button' href='#'>Mark your attendance</a>
              </div>

            </div>

          </div>


          <div id='buttons'>
            <!--<input id='edit-button' class='button' type='submit' value='Edit'>-->
            <a href='#' id='attendance-button' class='button'>View Attendance List</a>
            <a href='#' id='remove-button' class='button'>Delete</a>
          </div>

        </div>

      </div>

    </body>
    
</html>
