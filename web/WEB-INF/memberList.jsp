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
            
            
<!--        <div class='member' id='tutor' onclick="location.href='#';">
            <a class='info'>TUTOR</a>
            <div class='left'>
              <select class='dropdown' onchange='editRole("001")'>
                <option value='institutionAdmin'>Insitution Admin</option>
                <option value='programmeLeader'>Programme Leader</option>
                <option value='courseLeader'>Course Leader</option>
                <option value='classTeacher' selected>Class Teacher</option>
              </select>
              <a class='dropdownLabel'>v</a>
              <a class='button save-button' id='save-button_001' href='#'>></a>
              <a class='button remove-button' href='RemoveMember?memberId=001&type=class&classId=C001'>x</a>
            </div>
            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='middle'>
              <div class='top'>
                <a class='id'>001</a>
                <a class='name'>Naganohara Mio</a>
              </div>
              <div class='bottom'>
              </div>
            </div>
          </div>-->

<!--          <div class='member' onclick="location.href='#';">
            <a class='info'>TUTOR</a>
            <div class='left'>
              <select class='dropdown' onchange='editRole("003")'>
                <option value='institutionAdmin'>Insitution Admin</option>
                <option value='programmeLeader'>Programme Leader</option>
                <option value='courseLeader'>Course Leader</option>
                <option value='classTeacher' selected>Class Teacher</option>
              </select>
              <a class='dropdownLabel'>v</a>
              <a class='button save-button' id='save-button_003' href='#'>></a>
              <a class='button remove-button' href='RemoveMember?memberId=003&type=class&classId=C001'>x</a>
            </div>
            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='middle'>
              <div class='top'>
                <a class='id'>003</a>
                <a class='name'>Minakami Mai</a>
              </div>
              <div class='bottom'>
              </div>
            </div>
          </div>

          <div class='member' onclick="location.href='#';">
            <a class='info'>MEMBER</a>
            <div class='left'>
              <a class='button remove-button' href='RemoveMember?memberId=003&type=class&classId=C001'>x</a>
            </div>
            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='middle'>
              <div class='top'>
                <a class='id'>007</a>
                <a class='name'>Aioi Yuuko</a>
              </div>
              <div class='bottom'>
                <a class='cgpa'><span>CGPA</span> 4.0000</a>
                <a class='grade'><span>GRADE</span> A</a>
              </div>
            </div>
          </div>-->
            
            
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
    
      function editRole(id, institution) {
        document.getElementById("save-button_" + id).href = "PerformEditRole?id=" + id + "&role=" + event.target.value+ "&institution=" + institution;
        //alert(document.getElementById("save-button_" + id).href);
      }

    </script>
</html>
