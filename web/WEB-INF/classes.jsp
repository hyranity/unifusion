<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Classes</title>
        <link rel="stylesheet" href="CSS/classes.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>
    
    <body>
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
              <a id='subheading'>C001 - Course Name (Course)</a>
              <a id='heading'>Classes</a>
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

          <!--<div class='class'>
            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='id'>C001</a>
              <a class='title'>Class One</a>
              <a class='desc'>Lorem ipsum</a>
            </div>
          </div>

          <div class='class'>
            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='id'>C002</a>
              <a class='title'>Class Two</a>
              <a class='desc'>Lorem ipsum</a>
            </div>
          </div>


          <div class='class'>
            <img class='tutorIcon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='id'>C003</a>
              <a class='title'>Class Three</a>
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

      var classes = document.getElementsByClassName('class');

      for (var i = 0; i < classes.length; i++) {

        var id = classes[i].children[1].children[0].textContent;
        var name = classes[i].children[1].children[1].textContent;
        var data = "";
        
        if (attribute === "id") {
            data = id;
        } else if (attribute === "name") {
            data = name;
        } else {
            data = id;
        }

        if (data.toLowerCase().includes(query.toLowerCase())) {
            classes[i].style.display = "flex";
        } else {
            classes[i].style.display = "none";
        }

      }
    }

    </script>

</html>
