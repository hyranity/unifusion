<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Create a Venue</title>
        <link rel="stylesheet" href="CSS/venues.css">
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
              <a id='heading'>Venues</a>
            </div>
          </div>
        </div>

        <div id='search'>
          <select class='dropdown' id='statusAttribute'>
            <option value='all'>All</option>
            <option value='active'>Active</option>
            <option value='inactive'>Inactive</option>
          </select>
          <select class='dropdown' id='searchAttribute'>
            <option value='id'>ID</option>
            <option value='name'>Name</option>
            <option value='location'>Location</option>
          </select>
          <a class='dropdownLabel'>Click to view options</a>
          <input class='textbox' type='text' id='searchTextbox' placeholder='Search...'>
          <input id='search-button' class='button' type='submit' value='>' onclick='search()'>
          <a href='#' id='create-button' class='button'>Add a Venue</a>
        </div>

        <div id='list'>

          <div class='venue' onclick="location.href='#';">
            <a class='status'>ACTIVE</a>
            <a class='id'>001</a>
            <a class='name'>Ur Moms House</a>
            <a class='location'>Malaysia</a>
          </div>

          <div class='venue' onclick="location.href='#';">
            <a class='status'>ACTIVE</a>
            <a class='id'>003</a>
            <a class='name'>My Gf Room</a>
            <a class='location'>Malaysia</a>
          </div>

          <div class='venue' id='inactive' onclick="location.href='#';">
            <a class='status'>INACTIVE</a>
            <a class='id'>007</a>
            <a class='name'>Uncle James Living Room</a>
            <a class='location'>Afghanistan</a>
          </div>

        </div>

      </div>

    </body>

    <script>

    function search() {
      var query = document.getElementById("searchTextbox").value;
      var searchAttribute = document.getElementById("searchAttribute").value;
      var statusAttribute = document.getElementById("statusAttribute").value;

      var venues = document.getElementsByClassName('venue');

      for (var i = 0; i < venues.length; i++) {
            var status = venues[i].children[0].textContent;
            var id = venues[i].children[1].textContent;
        var name = venues[i].children[2].textContent;
        var location = venues[i].children[3].textContent;
        var data = "";

        if (statusAttribute === "all" || status.toLowerCase() === statusAttribute.toLowerCase()) {

          if (searchAttribute === "id") {
            data = id;
          } else if (searchAttribute === "name") {
            data = name;
          } else if (searchAttribute === "location") {
            data = location;
          } else {
            data = id;
          } 

          if (data.toLowerCase().includes(query.toLowerCase())) {
            venues[i].style.display = "flex";
          } else {
            venues[i].style.display = "none";
          }

        } else {
            venues[i].style.display = "none";
        }

      }
    }

    </script>
</html>
