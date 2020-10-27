<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Assignment Submissions</title>
        <link rel="stylesheet" href="CSS/submissions.css">
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
              <a id='heading'>Assignment Submissions</a>
            </div>
          </div>
        </div>

        <div id='search'>
          <select class='dropdown' id='statusAttribute' style='width: 150px;'>
            <option value='all'>All</option>
            <option value='marked'>Marked</option>
            <option value='pending'>Pending</option>
            <option value='late'>Late</option>
          </select>
          <select class='dropdown' id='searchAttribute'>
            <option value='id'>ID</option>
            <option value='name'>Name</option>
          </select>
          <a class='dropdownLabel'>Click to view options</a>
          <input class='textbox' type='text' id='searchTextbox' placeholder='Search...'>
          <a id='search-button' onclick='searchList()'>></a>
        </div>

        <div id='list'>
            
            ${submissions}

<!--           sample submission 1 

          <div class='submission marked'>
            <a class='time'>12:30 pm</a>
            <a class='memberId'>M001</a>
            <a class='name'>Naganohara Mio</a>
            <a class='date'>11 January 2021</a>
          </div>

           sample submission 2 

          <div class='submission pending'>
            <a class='time'>12:34 pm</a>
            <a class='memberId'>M003</a>
            <a class='name'>Minakami Mai</a>
            <a class='date'>11 January 2021</a>
          </div>

           sample submission 3 

          <div class='submission pending late'>
            <a class='time'>2:33 pm</a>
            <a class='memberId'>M007</a>
            <a class='name'>Aioi Yuuko</a>
            <a class='date'>12 January 2021</a>
          </div>-->

        </div>

      </div>

    </body>

    <script>

    function searchList() {
            var query = document.getElementById("searchTextbox").value;
      var searchAttribute = document.getElementById("searchAttribute").value;
      var statusAttribute = document.getElementById("statusAttribute").value;

      var submissions = document.getElementsByClassName('submission');

      for (var i = 0; i < submissions.length; i++) {

        var type = submissions[i].className;

        var id = submissions[i].children[1].textContent;
        var name = submissions[i].children[2].textContent;
        var data = "";

        if (statusAttribute === "all" || type.toLowerCase().includes(statusAttribute.toLowerCase())) {

          if (searchAttribute === "id") {
            data = id;
          } else if (searchAttribute === "name") {
            data = name;
          } else {
            data = id;
          } 

          if (data.toLowerCase().includes(query.toLowerCase())) {
            submissions[i].style.display = "flex";
          } else {
            submissions[i].style.display = "none";
          }

        } else {
            submissions[i].style.display = "none";
        }
      }

    }

    </script>

</html>
