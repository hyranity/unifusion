<%@page import="Models.Institution"%>
<%@page import="Util.Quick"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Programmes</title>
        <link rel="stylesheet" href="CSS/programmes.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>
    
    <body>
        <div id='bot' onclick='location.href="Chatbot"'>
            <img id='eugeo' src='https://www.flaticon.com/svg/static/icons/svg/3398/3398640.svg'>
            <a id='desc'>Need help?<br><span>Eugeo's here!</span></a>
        </div>
        
      <div id='container'>

        <div id='navbar'>
            <a href='Dashboard' id='back'>&lt; <span>Back</span></a>
            <a href='Home' id='scaffold'>Scaffold</a>
            <a href='Dashboard' class='link'>Dashboard</a>
            <a href='AccountDetails' class='link'>Account</a>
            <a href='Chatbot' class='link'>Chatbot</a>
        </div>

        <div id='top'>
          <div id='topOverlay'></div>
          <div id='info'>
            <img id='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div id='text'>
              <a id='subheading'>${id} - ${name} (Institution)</a>
              <a id='heading'>Programmes</a>
            </div>
          </div>
        </div>

        <div id='search'>
          <select class='dropdown' id='searchAttribute'>
            <option value='id'>ID</option>
            <option value='name'>Name</option>
          </select>
          <a class='dropdownLabel'>Click to view options</a>
          <input class='textbox' type='text' id='searchTextbox' placeholder='Search...'>
          <input id='search-button' type='submit' value='>' onclick='search()'>
        </div>

        <div id='list'>
            
          ${output}

          <!--

          <div class='programme'>
            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='id'>P001</a>
              <a class='title'>Programme One</a>
              <a class='desc'>Lorem ipsum</a>
            </div>
          </div>

          <div class='programme'>
            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='id'>P002</a>
              <a class='title'>Programme Two</a>
              <a class='desc'>Lorem ipsum</a>
            </div>
          </div>

          <div class='programme'>
            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='id'>P003</a>
              <a class='title'>Programme Three</a>
              <a class='desc'>Lorem ipsum</a>
            </div>
          </div>-->

        </div>

      </div>

    </body>

    <script>

    function search() {
      var query = document.getElementById("searchTextbox").value;
      var attribute = document.getElementById("searchAttribute").value;

      var programmes = document.getElementsByClassName('programme');

      for (var i = 0; i < programmes.length; i++) {

        var id = programmes[i].children[1].children[0].textContent;
        var name = programmes[i].children[1].children[1].textContent;
        var data = "";
        
        if (attribute === "id") {
            data = id;
        } else if (attribute === "name") {
            data = name;
        } else {
            data = id;
        }

        if (data.toLowerCase().includes(query.toLowerCase())) {
            programmes[i].style.display = "flex";
        } else {
            programmes[i].style.display = "none";
        }

      }
    }

    </script>

</html>
