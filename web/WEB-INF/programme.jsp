<%@page import="Models.Users"%>
<%@page import="Util.Quick"%>
<%@page import="Models.Programme"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Programme</title>
        <link rel="stylesheet" href="CSS/programme.css">
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
            <img id='icon' src='<% out.print(Quick.getIcon(((Programme) request.getAttribute("programme")).getIconurl()));%>'>
            <div id='text'>
              <a id='subheading'>Programme</a>
              <a id='heading'>${programme.getTitle()}</a>
            </div>
          </div>
        </div>
            
            <div id='search'>
              <input class='textbox' type='text' id='searchTextbox' placeholder='Search...'>
              <input id='search-button' type='submit' value='>' onclick='search()'>
            </div>

        <div id='content'>

          <!-- START: left column -->

          <div class='column' id='leftColumn'>

            <!-- START: details -->

            <div class='panel' id='details' onclick='location.href="#"'>

              <a class='heading'>Details</a>

              <div class='boxes'>

                <div class='box'>
                  <a class='label'>Name</a>
                  <img class='icon' src='<% out.print(Quick.getIcon(((Users) request.getAttribute("creator")).getImageurl()));%>'>
                  <a class='text' id='programmeName'>${programme.getTitle()}</a>
                </div>

                <div class='box' id='desc'>
                  <a class='label'>Description</a>
                  <a class='text'>${programme.getDescription()}</a>
                </div>

              </div>

              ${editBt}

            </div>

            <!-- END: details -->

            <!-- START: members -->

            <div class='panel' id='members' onclick='location.href="#"'>

              <a class='heading'>Members</a>

              <div class='stats'>
                <div class='stat'>
                  <a class='value' id='membersTutors'>${tutorList.size()}</a>
                  <a class='desc'>tutors</a>
                </div>

                <div class='stat'>
                  <a class='value' id='membersStudents'>${studentList.size()}</a>
                  <a class='desc'>students</a>
                </div>
              </div>

                <div class='content'>
                            <div class='boxes'>

                                <div class='box'>
                                    <img class='icon' src='${creator.getImageurl()}'>
                                    <a class='name'>${creator.getName()}</a>
                                </div>

                                ${youBox}

                                </div>

                               ${moreStr}


                            </div>

                <a class='more'>Click to view more ></a>

              </div>

            <!-- END: members -->

          </div>

          <!-- END: left column -->

          <!-- START: right column -->

          <div class='column' id='rightColumn'>

            <!-- START: announcements -->

            <div class='panel' id='announcements' onclick='location.href="#"'>

              <div class='top'>
                <a class='heading'>Announcements</a>

                <div class='stats'>
                  <div class='stat'>
                    <a class='value' id='announcementsDay'>3</a>
                    <a class='desc'>today</a>
                  </div>

                  <div class='stat'>
                    <a class='value' id='announcementsWeek'>12</a>
                    <a class='desc'>this week</a>
                  </div>
                </div>
              </div>

              <div class='bottom'>

                <!-- sample announcement 1 -->

                <div class='announcement'>
                  <a class='time'>15m ago</a>
                  <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
                  <div class='text'>
                    <a class='message'><span>Name</span> posted item</a>
                    <a class='item'>Item name</a>
                  </div>
                </div>

                <!-- sample announcement 2 -->

                <div class='announcement'>
                  <a class='time'>3h ago</a>
                  <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
                  <div class='text'>
                    <a class='message'><span>Name</span> commented</a>
                    <a class='item'>Comment excerpt</a>
                  </div>
                </div>

                <!-- sample announcement 3 -->

                <div class='announcement'>
                  <a class='time'>6h ago</a>
                  <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
                  <div class='text'>
                    <a class='message'><span>Name</span> posted item</a>
                    <a class='item'>Item name</a>
                  </div>
                </div>

              </div>

              <a class='more'>Click to view more ></a>

            </div>

            <!-- END: announcements -->

            <!-- START: courses -->

            <div class='panel' id='courses' onclick='location.href="#"'>

              <div class='top'>
                <a class='heading'>Courses</a>
              </div>

              <a class='more'>Click to view more ></a>

            </div>

            <!-- END: programmes -->

          </div>

        </div>

        <!-- END: right column -->

      </div>

    </body>
    
    <script>
  
    function search() {
      var query = document.getElementById("searchTextbox").value;
      var panels = document.getElementsByClassName('panel');

      for (var i = 0; i < panels.length; i++) {
          if (panels[i].id.includes(query)) {
          panels[i].style.display = "flex";
          panels[i].style.opacity = "1";
        } else {
          panels[i].style.opacity = "0";
          panels[i].style.display = "none";
        }
      }
    }

    </script>

</html>
