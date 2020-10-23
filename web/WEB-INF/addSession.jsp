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
            <a class='label' style='margin-left: -470px;'>Date</a>
            <input class='date' type='date' name='date'>

            <div id='times'>
              <div class='timeBox'>
                <a class='label'>Start time</a>
                <input class='time' type='time' name='startTime'>
              </div>
              <div class='timeBox'>
                <a class='label'>End time</a>
                <input class='time' type='time' name='startTime'>
              </div>
            </div>

            <div id='venues'>

              <a class='label'>Select a Venue:</a>

              <div id='search'>
                <select class='dropdown' id='searchAttribute'>
                  <option value='id'>ID</option>
                  <option value='name'>Name</option>
                  <option value='location'>Location</option>
                </select>
                <a class='dropdownLabel'>Click to view options</a>
                <input class='textbox' type='text' id='searchTextbox' placeholder='Search...'>
                <div id='search-button' class='button' value='>' onclick='searchVenue()'>></div>
              </div>

              <div id='list'>

                <div class='venue' id='venue-001' onclick='selectVenue("001")'>
                  <a class='id'>001</a>
                  <a class='name'>Ur Moms House</a>
                  <a class='location'>Malaysia</a>
                </div>

                <div class='venue' id='venue-003' onclick='selectVenue("003")'>
                  <a class='id'>003</a>
                  <a class='name'>My Gf Room</a>
                  <a class='location'>Malaysia</a>
                </div>

                <div class='venue' id='venue-069' onclick='selectVenue("069")'>
                  <a class='id'>069</a>
                  <a class='name'>My Garage</a>
                  <a class='location'>Los Angeles</a>
                </div>

                <div class='venue' id='venue-420' onclick='selectVenue("420")'>
                  <a class='id'>420</a>
                  <a class='name'>England is my city</a>
                  <a class='location'>London town</a>
                </div>

                <input type='hidden' id='venueId' name='venueId' value=''> <!-- NOTE: STORE SELECTED VENUE ID HERE -->

              </div>

            </div>

            <a class='label' id='name'>Is this venue not included above?</a>
            <input type='checkbox' class='checkbox' id='hasTempVenue' name='hasTempVenue' onclick='hasProgrammeClicked()'>
            <label class='checkboxLabel' for='hasTempVenue' id='hasTempVenueLabel'>
              <div class='slider'></div>
            </label>

            <a class='label' style='margin-left: -200px;' id='tempVenueLabel'>Temporary venue (If none in list)</a>
            <input class='textbox' type='text' name='tempVenue' id='tempVenueTextbox' placeholder='eg. meet.google.com/abcdef' style='height: 45px; font-size: 17px; width: 400px;'>
            <input type='hidden' id='tempVenueEnabled' name='tempVenueEnabled' value='false'>
            
            <input id='post-button' type='submit' value='Create!'>

          </form>

        </div>

      </body>


      <script>
        function searchVenue() {
          var query = document.getElementById("searchTextbox").value;
          var searchAttribute = document.getElementById("searchAttribute").value;

          var venues = document.getElementsByClassName('venue');

          for (var i = 0; i < venues.length; i++) {
            var id = venues[i].children[0].textContent;
            var name = venues[i].children[1].textContent;
            var location = venues[i].children[2].textContent;
            var data = "";

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
          }
        }

        function selectVenue(venueId) {
          // set venueId input hidden value
          document.getElementById("venueId").value = venueId;

          // reset all venue boxes colours
          var venues = document.getElementsByClassName('venue');

          for (var i = 0; i < venues.length; i++) {
            venues[i].style.backgroundColor = "rgba(39, 41, 100, 0.5)";
            venues[i].children[0].style.color = "rgba(250, 226, 184, 0.75)";
            venues[i].children[1].style.color = "rgba(223, 224, 242, 0.75)";
          }

          // set venue box that was clicked to different colour
          var selectedVenue = document.getElementById("venue-" + venueId);
          selectedVenue.style.backgroundColor = "rgba(250, 226, 184, 0.75)";
          selectedVenue.children[0].style.color = "rgba(39, 41, 100, 1)";
          selectedVenue.children[1].style.color = "rgba(39, 41, 100, 0.75)";
        }
        
        function hasTempVenueClicked() {
            var hasTempVenue = document.getElementById("hasTempVenue");
            var tempVenueTextbox = document.getElementById("tempVenueTextbox");

            document.getElementById("tempVenueEnabled").value = hasTempVenue.checked;

            if (hasTempVenue.checked) {
                tempVenueTextbox.disabled = false;
            } else {
                tempVenueTextbox.disabled = true;
            }
        }

      </script>

</html>
