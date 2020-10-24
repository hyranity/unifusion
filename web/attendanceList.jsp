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
        <link rel="stylesheet" href="CSS/attendanceList.css">
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
              <a id='heading'>Attendance List</a>
            </div>
          </div>
        </div>

        <div id='header'>
          <a id='sessionId'>S001</a>
          <a id='sessionDate'>1 January 2021</a>
          <a id='sessionTime'>2.00 pm - 4.30 pm</a>
        </div>

        <div id='stats'>
          <div class='stat'>
            <a class='label'>TOTAL</a>
            <a class='value'>10</a>
          </div>

          <div class='stat'>
            <a class='label'>ON TIME</a>
            <a class='value'>7</a>
          </div>

          <div class='stat'>
            <a class='label'>LATE</a>
            <a class='value'>1</a>
          </div>

          <div class='stat'>
            <a class='label'>ABSENT</a>
            <a class='value'>2</a>
          </div>
        </div>

        <div id='search'>
          <select class='dropdown' id='statusAttribute'>
            <option value='all'>All</option>
            <option value='on time'>On Time</option>
            <option value='late'>Late</option>
            <option value='absent'>Absent</option>
          </select>
          <select class='dropdown' id='searchAttribute'>
            <option value='id'>ID</option>
            <option value='name'>Name</option>
          </select>
          <a class='dropdownLabel'>Click to view options</a>
          <input class='textbox' type='text' id='searchTextbox' placeholder='Search...'>
          <input id='search-button' type='submit' value='>' onclick='search()'>
        </div>

        <div id='list'>

          <div class='member tutor' onclick="location.href='#';">
            <a class='info'>TUTOR</a>
            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='details'>
              <div class='top-details'>
                <a class='id'>001</a>
                <a class='name'>Naganohara Mio</a>
              </div>
              <div class='time-details'>
                <a class='time-label'>ON TIME</a>
                <a class='time-checked'>2.02pm</a>
              </div>
            </div>
          </div>

          <div class='member' onclick="location.href='#';">
            <a class='info'>MEMBER</a>
            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='details'>
              <div class='top-details'>
                <a class='id'>003</a>
                <a class='name'>Minakami Mai</a>
              </div>
              <div class='time-details'>
                <a class='time-label'>LATE</a>
                <a class='time-checked'>3.20pm</a>
              </div>
            </div>
          </div>

          <div class='member absent' onclick="location.href='#';">
            <a class='info'>MEMBER</a>
            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='details'>
              <div class='top-details'>
                <a class='id'>007</a>
                <a class='name'>Aioi Yuuko</a>
              </div>
              <div class='time-details'>
                <a class='time-label'>ABSENT</a>
                <a class='time-checked'>-</a>
              </div>
            </div>
          </div>

        </div>

      </div>

    </body>

    <script>

    function search() {
            var query = document.getElementById("searchTextbox").value;
      var attribute = document.getElementById("searchAttribute").value;
      var statusAttribute = document.getElementById("statusAttribute").value;

      var members = document.getElementsByClassName('member');

      for (var i = 0; i < members.length; i++) {
            var id = members[i].children[2].children[0].children[0].textContent;
        var name = members[i].children[2].children[0].children[1].textContent;
        var status = members[i].children[2].children[1].children[0].textContent;
        var data = "";

        if (statusAttribute === "all" || status.toLowerCase() === statusAttribute.toLowerCase()) {

          if (searchAttribute === "id") {
            data = id;
          } else if (searchAttribute === "name") {
            data = name;
          } else {
            data = id;
          } 

          if (data.toLowerCase().includes(query.toLowerCase())) {
            members[i].style.display = "flex";
          } else {
            members[i].style.display = "none";
          }

        } else {
            members[i].style.display = "none";
        }
      }

    }

    </script>

</html>

