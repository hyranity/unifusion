<%@page import="Models.Users"%>
<%@page import="Models.Institution"%>
<%@page import="Util.Quick"%>
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
            <img id='icon' src='<% out.print(Quick.getIcon(((Institution) request.getAttribute("institution")).getIconurl()));%>'>
            <div id='text'>
              <a id='subheading'>Institution</a>
              <a id='heading'>${institution.getName()}</a>
              ${authCode}
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

            <div class='panel' id='details'>

              <a class='heading'>Details</a>

              <div class='boxes'>

                <div class='box'>
                  <a class='label'>Name</a>
                  <img class='icon' src='<% out.print(Quick.getIcon(((Institution) request.getAttribute("institution")).getIconurl()));%>'>
                  <a class='text' id='institutionName'>${institution.getName()}</a>
                </div>

                <div class='box' id='desc'>
                  <a class='label'>Description</a>
                  <a class='text'>${institution.getDescription()}</a>
                </div>

              </div>

              ${editBt}
              <a class='link' href='Relations?type=institution&id=${institution.getInstitutioncode()}'>View relations ></a>

            </div>

            <!-- END: details -->

            <!-- START: members -->

            <div class='panel' id='members'>

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
                                    <img class='icon' src='<% out.print(Quick.getIcon(((Users) request.getAttribute("creator")).getImageurl()));%>'>
                                    <a class='name'>${creator.getName()}</a>
                                </div>

                                ${youBox}

                                </div>

                               ${moreStr}


                            </div>

                <a class='more'  href='MemberList?type=institution&id=${institution.getInstitutioncode()}'>View more ></a>

              </div>

            <!-- END: members -->
            
            <!-- START: venues -->

            <div class='panel'>

              <div class='top'>
                <a class='heading'>Venues</a>
                
                <div class='stats'>
                  <div class='stat'>
                    <a class='value' id='venueCount'>1</a>
                    <a class='desc'>total</a>
                  </div>
                </div>
              </div>

              <a href='Venues?id=${institution.getInstitutioncode()}' class='more'>View more ></a>

            </div>

            <!-- END: venues -->

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
                    <a class='value' id='announcementsDay'>${todayAnnounced}</a>
                    <a class='desc'>today</a>
                  </div>

                  <div class='stat'>
                    <a class='value' id='announcementsWeek'>${announcementCount}</a>
                    <a class='desc'>total</a>
                  </div>
                </div>
              </div>

              <div class='bottom'>

               ${announcementUI}

              </div>

              <a class='more' href="Announcement?type=institution&id=${institution.getInstitutioncode()}">View more ></a>

            </div>

            <!-- END: announcements -->

            <!-- START: courses -->

            <div class='panel'>

              <div class='top'>
                <a class='heading'>Programmes</a>
                
                <div class='stats'>
                  <div class='stat'>
                    <a class='value' id='programmeCount'>${institution.getProgrammeCollection().size()}</a>
                    <a class='desc'>total</a>
                  </div>
                </div>
              </div>

              <a class='more' href='EducationList?type=institution&id=${institution.getInstitutioncode()}'>View more ></a>

            </div>

            <!-- END: courses -->
            
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
