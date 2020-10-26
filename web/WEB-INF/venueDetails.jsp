<%@page import="Models.Institution"%>
<%@page import="Util.Quick"%>
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
            <a href='Dashboard' id='back'>&lt; <span>Back</span></a>
            <a href='Home' id='scaffold'>Scaffold</a>
            <a href='Dashboard' class='link'>Dashboard</a>
            <a href='AccountDetails' class='link'>Account</a>
        </div>


        <div id='top'>
          <div id='topOverlay'></div>
          <div id='info'>
            <img id='icon' src='<% out.print(Quick.getIcon(((Institution) request.getAttribute("institution")).getIconurl()));%>'>
            <div id='text'>
              <a id='subheading'>${subheading}</a>
              <a id='heading'>Venue Details</a>
            </div>
          </div>
        </div>

        <div id='content' action=''>

          <div id='header'>
            <a id='announcementId'>${venue.getVenueid()}</a>
            <a id='announcementTitle'>${venue.getTitle()}</a>
          </div>

          <div id='row'>
            <div class='detail' id='capacity'>
              <a class='label'>CAPACITY</a>
              <a class='value'>${venue.getCapacity()}</a>
            </div>

            <div class='detail' id='status'>
              <a class='label'>STATUS</a>
              <a class='value'>${venue.getIsactive() ? "ACTIVE" : "INACTIVE"}</a>
            </div>
          </div>

          <div class='detail' id='location'>
            <a class='label'>LOCATION</a>
            <a class='value'>${venue.getLocation()}</a>
          </div>

          <div id='buttons' onclick="window.location.href='EditVenue?id=${id}&code=${code}'">
            <input id='edit-button' class='button' type='submit' value='Edit'>
            <!--<input id='remove-button' class='button' type='submit' value='Delete'>-->
          </div>

        </div>

      </div>

    </body>
    
</html>
