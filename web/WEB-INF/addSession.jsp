<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Create a Session</title>
        <link rel="stylesheet" href="CSS/addSession.css">
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
            <img id='icon' src='${icon}'>
            <div id='text'>
              <a id='subheading'>${subheading}</a>
              <a id='heading'>Create Session</a>
            </div>
          </div>
        </div>
              
              <a id='error'><%out.print(Errors.requestSimple(session));%></a>

        <form id='form' action='PerformAddSession'>
            
            <input type="hidden" value="${id}" name="id"/>

          <a class='label' style='margin-left: -470px;'>Venue</a>
          <input class='textbox' type='text' name='venue' placeholder='eg. Block C 1-5, meet.google.com/abcdef'>

          <a class='label' style='margin-left: -470px;'>Date</a>
          <input class='date' type='date' name='date'>

          <div id='times'>
            <div class='timeBox'>
              <a class='label'>Start time</a>
              <input class='time' type='time' name='startTime'>
            </div>
            <div class='timeBox'>
              <a class='label'>End time</a>
              <input class='time' type='time' name='endTime'>
            </div>
          </div>

          <input id='post-button' type='submit' value='Create!'>

        </form>

      </div>

    </body>
    
</html>
