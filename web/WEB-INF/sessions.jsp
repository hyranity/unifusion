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
        <title>UniFusion :: Sessions</title>
        <link rel="stylesheet" href="CSS/sessions.css">
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
              <a id='subheading'>${subheading}</a>
              <a id='heading'>Sessions</a>
            </div>
          </div>
        </div>

        <div id='search'>
          <select class='dropdown' id='searchAttribute'>
            <option value='all'>All</option>
            <option value='upcoming'>Upcoming</option>
            <option value='past'>Past</option>
          </select>
          <a class='dropdownLabel'>Click to view options</a>
          <input id='search-button' class='button' type='submit' value='>' onclick='search()'>
          <a href='AddSession?id=${id}' id='create-button' class='button'>Create a Session</a>
        </div>

        <div id='list'>
            
            ${sessionUI}

          <!-- sample session 1 -->

<!--          <div class='session' id='upcoming'>
            <a class='time'>In 1h 13m</a>
            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='message'>3.00 pm, 13 Jan 2021</a>
              <a class='tutor'>John Doe</a>
            </div>
          </div>

           sample session 2 

          <div class='session' id='past'>
            <a class='time'>2d ago</a>
            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='message'>10.30 am, 11 Jan 2021</a>
              <a class='tutor'>John Doe</a>
            </div>
          </div>

           sample session 3 

          <div class='session' id='past'>
            <a class='time'>3d ago</a>
            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='message'>12.30 pm, 10 Jan 2021</a>
              <a class='tutor'>John Doe</a>
            </div>
          </div>-->

        </div>

      </div>

    </body>

    <script>

    function search() {
      var attribute = document.getElementById("searchAttribute").value;

      var sessions = document.getElementsByClassName('session');

      for (var i = 0; i < sessions.length; i++) {

        var type = sessions[i].id;

        if (attribute === "all") {
            sessions[i].style.display = "flex";
        } else if (type.toLowerCase().includes(attribute.toLowerCase())) {
            sessions[i].style.display = "flex";
        } else {
            sessions[i].style.display = "none";
        }

      }
    }

    </script>

</html>

