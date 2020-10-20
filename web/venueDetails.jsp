<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Create a Session</title>
        <link rel="stylesheet" href="CSS/venueDetails.css">
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
              <a id='subheading'>I001 - Institution Name (Institution)</a>
              <a id='heading'>Venue Details</a>
            </div>
          </div>
        </div>

        <div id='content' action=''>

          <div id='header'>
            <a id='announcementId'>V001</a>
            <a id='announcementTitle'>Venue Name</a>
          </div>

          <div id='row'>
            <div class='detail' id='capacity'>
              <a class='label'>CAPACITY</a>
              <a class='value'>150</a>
            </div>

            <div class='detail' id='status'>
              <a class='label'>STATUS</a>
              <a class='value'>Public</a>
            </div>
          </div>

          <div class='detail' id='location'>
            <a class='label'>LOCATION</a>
            <a class='value'>123 Idiot Street</a>
          </div>

          <div id='buttons'>
            <input id='edit-button' class='button' type='submit' value='Edit'>
            <!--<input id='remove-button' class='button' type='submit' value='Delete'>-->
          </div>

        </div>

      </div>

    </body>
    
</html>
