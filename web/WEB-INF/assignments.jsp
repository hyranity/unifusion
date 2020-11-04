<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Assignments</title>
        <link rel="stylesheet" href="CSS/assignments.css">
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
            <img id='icon' src='${icon}'>
            <div id='text'>
              <a id='subheading'>${subheading}</a>
              <a id='heading'>Assignments</a>
            </div>
          </div>
        </div>

        <div id='search'>
          <select class='dropdown' id='searchAttribute'>
            <option value='all'>All</option>
            <option value='submitted'>Submitted</option>
            <option value='current'>Current</option>
            <option value='late'>Late</option>
            <option value='missing'>Missing</option>
          </select>
          <a class='dropdownLabel'>Click to view options</a>
          <input id='search-button' class='button' type='submit' value='>' onclick='search()'>
          ${addBt}
        </div>

        <div id='list'>
            
            ${assignmentUI}

<!--           sample assignment 1 

          <div class='assignment submitted'>
            <a class='time'>Submitted</a>
            <a class='assignmentId'>S00001</a>
            <a class='title'>Semester Assignment</a>
            <a class='deadline'>11 January 2021</a>
          </div>

          
           sample assignment 2 

          <div class='assignment current'>
            <a class='time'>In 3d</a>
            <a class='assignmentId'>S00005</a>
            <a class='title'>Tutorial 5</a>
            <a class='deadline'>14 January 2021</a>
          </div>

           sample assignment 3 

          <div class='assignment submitted late'>
            <a class='time'>Late</a>
            <a class='assignmentId'>S00005</a>
            <a class='title'>Tutorial 4</a>
            <a class='deadline'>8 January 2021</a>
          </div>

           sample assignment 4

          <div class='assignment missing'>
            <a class='time'>Missing</a>
            <a class='assignmentId'>S00006</a>
            <a class='title'>Tutorial 3 Extra</a>
            <a class='deadline'>2 January 2021</a>
          </div>-->

        </div>

      </div>

    </body>

    <script>

    function search() {
      var attribute = document.getElementById("searchAttribute").value;

      var assignments = document.getElementsByClassName('assignment');

      for (var i = 0; i < assignments.length; i++) {

        var type = assignments[i].className;

        if (attribute === "all") {
            assignments[i].style.display = "flex";
        } else if (type.toLowerCase().includes(attribute.toLowerCase())) {
            assignments[i].style.display = "flex";
        } else {
            assignments[i].style.display = "none";
        }

      }
    }

    </script>
    
</html>
