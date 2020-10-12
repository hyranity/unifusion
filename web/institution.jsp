<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Institution</title>
        <link rel="stylesheet" href="CSS/institution.css">
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
              <a id='subheading'>Institution</a>
              <a id='heading'>Institution name</a>
            </div>
          </div>
        </div>

        <div id='content'>

          <!-- START: left column -->

          <div class='column' id='leftColumn'>

            <!-- START: details -->

            <div id='details' onclick='location.href="#"'>

              <a class='heading'>Details</a>

              <div class='boxes'>

                <div class='box'>
                  <a class='label'>Name</a>
                  <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
                  <a class='text' id='institutionName'>Institution Name</a>
                </div>

                <div class='box' id='desc'>
                  <a class='label'>Description</a>
                  <a class='text'>Lorem ipsum dolor sit amet, consectetur adipiscing elit...</a>
                </div>

              </div>

              <a class='more'>Click to view more ></a>

            </div>

            <!-- END: details -->

            <!-- START: members -->

            <div id='members' onclick='location.href="#"'>

              <a class='heading'>Members</a>

              <div class='stats'>
                <div class='stat'>
                  <a class='value' id='membersTutors'>3</a>
                  <a class='desc'>tutors</a>
                </div>

                <div class='stat'>
                  <a class='value' id='membersStudents'>24</a>
                  <a class='desc'>students</a>
                </div>
              </div>

              <div class='content'>
                <div class='boxes'>
                  <div class='box'>
                    <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
                    <a class='name'>Leader</a>
                  </div>
                  <div class='box' id='you'>
                    <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
                    <a class='name'>You</a>
                  </div>
                </div>
                <a id='noOfMembers'>and 25 more...</a>
              </div>

              <a class='more'>Click to view more ></a>

            </div>

            <!-- END: members -->

          </div>

          <!-- END: left column -->

          <!-- START: right column -->

          <div class='column' id='rightColumn'>

            <!-- START: announcements -->

            <div id='announcements' onclick='location.href="#"'>

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

            <div id='programmes' onclick='location.href="#"'>

              <div class='top'>
                <a class='heading'>Programmes</a>
              </div>

              <a class='more'>Click to view more ></a>

            </div>

            <!-- END: courses -->

          </div>

        </div>

        <!-- END: right column -->

      </div>

    </body>

</html>
