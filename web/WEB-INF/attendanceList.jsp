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
        <title>UniFusion :: Attendance List</title>
        <link rel="stylesheet" href="CSS/attendanceList.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>
    
    <body>
        <div id='bot' onclick='location.href="Chatbot"'>
            <img id='eugeo' src='https://www.flaticon.com/svg/static/icons/svg/3398/3398640.svg'>
            <a id='desc'>Need help?<br><span>Eugeo's here!</span></a>
        </div>
        
      <div id='container' action=''>

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
              <a id='heading'>Attendance List</a>
            </div>
          </div>
        </div>

        <div id='header'>
          <a id='sessionId'>${session.getSessionid()}</a>
          <a id='sessionDate'>${date}</a>
          <a id='sessionTime'>${range}</a>
        </div>

        <div id='stats'>
          <div class='stat'>
            <a class='label'>TOTAL</a>
            <a class='value'>${total}</a>
          </div>

          <div class='stat'>
            <a class='label'>PRESENT</a>
            <a class='value'>${present}</a>
          </div>

          <div class='stat'>
            <a class='label'>LATE</a>
            <a class='value'>${late}</a>
          </div>

          <div class='stat'>
            <a class='label'>ABSENT</a>
            <a class='value'>${absent}</a>
          </div>
        </div>

        <div id='search'>
          <select class='dropdown' id='statusAttribute'>
            <option value='all'>All</option>
            <option value='present'>Present</option>
            <option value='late'>Late</option>
            <option value='absent'>Absent</option>
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
            
            ${attendanceUI}

<!--          <div class='member tutor'>
            <a class='info'>TUTOR</a>
            <div class='buttons'>
              <select class='dropdown' onchange='editAttendance("001")'>
                <option value='present' selected>Present</option>
                <option value='late'>Late</option>
                <option value='absent'>Absent</option>
              </select>
              <a class='dropdownLabel'>v</a>
              <a class='save-button' id='save-button_001' href='EditAttendance?id=001&status=present'>></a>
            </div>
            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='details'>
              <div class='top-details'>
                <a class='id'>001</a>
                <a class='name'>Naganohara Mio</a>
              </div>
              <div class='time-details'>
                <a class='time-label'>PRESENT</a>
                <a class='time-checked'>2.02pm</a>
              </div>
            </div>
          </div>-->

<!--          <div class='member'>
            <a class='info'>MEMBER</a>
            <div class='buttons'>
              <select class='dropdown' onchange='editAttendance("002")'>
                <option value='present' selected>Present</option>
                <option value='late'>Late</option>
                <option value='absent'>Absent</option>
              </select>
              <a class='dropdownLabel'>v</a>
              <a class='save-button' id='save-button_002' href='EditAttendance?id=002&status=present'>></a>
            </div>
            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='details'>
              <div class='top-details'>
                <a class='id'>002</a>
                <a class='name'>Jeff Bezos</a>
              </div>
              <div class='time-details'>
                <a class='time-label'>PRESENT</a>
                <a class='time-checked'>2.03pm</a>
              </div>
            </div>
          </div>

          <div class='member'>
            <a class='info'>MEMBER</a>
            <div class='buttons'>
              <select class='dropdown' onchange='editAttendance("003")'>
                <option value='present'>Present</option>
                <option value='late' selected>Late</option>
                <option value='absent'>Absent</option>
              </select>
              <a class='dropdownLabel'>v</a>
              <a class='save-button' id='save-button_003' href='EditAttendance?id=003&status=late'>></a>
            </div>
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

          <div class='member absent'>
            <a class='info'>MEMBER</a>
            <div class='buttons'>
              <select class='dropdown' onchange='editAttendance("007")'>
                <option value='present'>Present</option>
                <option value='late'>Late</option>
                <option value='absent' selected>Absent</option>
              </select>
              <a class='dropdownLabel'>v</a>
              <a class='save-button' id='save-button_007' href='EditAttendance?id=007&status=absent'>></a>
            </div>
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
          </div>-->

        </div>

      </div>

    </body>

    <script>

    function searchList() {
      var query = document.getElementById("searchTextbox").value;
      var searchAttribute = document.getElementById("searchAttribute").value;
      var statusAttribute = document.getElementById("statusAttribute").value;

      var members = document.getElementsByClassName('member');

      for (var i = 0; i < members.length; i++) {

        var id = members[i].children[3].children[0].children[0].textContent;
        var name = members[i].children[3].children[0].children[1].textContent;
        var status = members[i].children[3].children[1].children[0].textContent;
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

    function editAttendance(id) {
      document.getElementById("save-button_" + id).href = "PerformEditAttendance?id=" + id + "&status=" + event.target.value;  

      //alert(document.getElementById("save-button_" + id).href);
    }

    </script>

</html>

