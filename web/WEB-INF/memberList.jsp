<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Member List</title>
        <link rel="stylesheet" href="CSS/memberList.css">
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
            <img id='icon' src='${icon}'>
            <div id='text'>
              <a id='subheading'>${subheading}</a>
              <a id='heading'>Members</a>
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
          
            ${tutors}
            ${students}

            

            

        </div>

      </div>

    </body>

    <script>

    function search() {
            var query = document.getElementById("searchTextbox").value;
      var attribute = document.getElementById("searchAttribute").value;

      var members = document.getElementsByClassName('member');

      for (var i = 0; i < members.length; i++) {
            var id = members[i].children[2].textContent;
        var name = members[i].children[3].textContent;
        var data = "";

        if (attribute === "id") {
            data = id;
        } else if (attribute === "name") {
            data = name;
        } else {
            data = id;
        } 

        if (data.toLowerCase().includes(query.toLowerCase())) {
            members[i].style.display = "flex";
        } else {
            members[i].style.display = "none";
        }

      }
    }

    </script>
</html>
