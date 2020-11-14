<%@page import="Util.Server"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Models.Classparticipant"%>
<%@page import="Models.Classparticipant"%>
<%@page import="Models.Users"%>
<%@page import="Util.Quick"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Class</title>
        <link rel="stylesheet" href="CSS/class.css">
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
                    <img id='icon' src='<% out.print(Quick.getIcon(((Models.Class) request.getAttribute("classroom")).getIconurl()));%>'>
                    <div id='text'>
                        <a id='subheading'>Class</a>
                        <a id='heading'>${classroom.getClasstitle()}</a>
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

                    <div class='panel' id='details' onclick='location.href = "#"'>

                        <a class='heading'>Details</a>

                        <div class='boxes'>

                            <div class='box'>
                                <a class='label'>Name</a>
                                <img class='icon' src='<% out.print(Quick.getIcon(((Models.Class) request.getAttribute("classroom")).getIconurl()));%>'>
                                <a class='text' id='className'>${classroom.getClasstitle()}</a>
                            </div>

                            <div class='box' id='desc'>
                                <a class='label'>Description</a>
                                <a class='text'>${classroom.getDescription()}</a>
                            </div>

                        </div>

                        ${editBt}
                        <a class='link' href='Relations?type=class&id=${classroom.getClassid()}'>View relations ></a>

                    </div>

                    <!-- END: details -->

                    <!-- START: members -->

                    <div class='panel' id='members' onclick='location.href = "#"'>

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

                            <a class='more' href='MemberList?type=class&id=${classroom.getClassid()}'>View more ></a>

                        </div>

                        <!-- END: members -->
                        
                        <!-- START: sessions -->

                        <div class='panel' id='sessions' onclick='location.href="#"'>

                          <div class='top'>
                            <a class='heading'>Sessions</a>

                            <div class='stats'>
                              <div class='stat'>
                                <a class='value' id='classworkWeek'>${todaySessions}</a>
                                <a class='desc'>today</a>
                              </div>

                              <div class='stat'>
                                <a class='value' id='classworkTotal'>${totalSessions}</a>
                                <a class='desc'>total</a>
                              </div>
                            </div>

                            <a class='more' href="Sessions?id=${classroom.getClassid()}">View more ></a>

                          </div>

                        </div>

                        <!-- END: sessions -->

                    </div>

                    <!-- END: left column -->

                    <!-- START: right column -->

                    <div class='column' id='rightColumn'>
                        <!-- START: announcements -->

                        <div class='panel' id='announcements' onclick='location.href = "#"'>

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

                            <a class='more' href="Announcement?type=class&id=${classroom.getClassid()}">View more ></a>

                        </div>

                        <!-- END: announcements -->
                        
                        <!-- START: classwork -->

                        <div class='panel' id='classwork' >

                          <div class='top'>
                            <a class='heading'>Classwork</a>

                            <div class='stats'>
                              <div class='stat'>
                                <a class='value' id='classworkWeek'>${todayClasswork}</a>
                                <a class='desc'>due today</a>
                              </div>

                              <div class='stat'>
                                <a class='value' id='classworkTotal'>${classroom.getGradedcomponentCollection().size()}</a>
                                <a class='desc'>total</a>
                              </div>
                            </div>
                          </div>

                          <div class='bottom'>
                              
                              ${classworkUI}

<!--                             sample assignment 1 

                            <div class='assignment'>
                              <a class='due'>1d</a>
                              <a class='message'>Tutorial 6</a>
                              <a class='slug'></a>
                            </div>

                             sample assignment 2 

                            <div class='assignment'>
                              <a class='due'>5d</a>
                              <a class='message'>Tutorial 7</a>
                              <a class='slug'></a>
                            </div>

                             sample assignment 3 

                            <div class='assignment'>
                              <a class='due'>16d</a>
                              <a class='message'>Assignment</a>
                              <a class='slug'>50% coursework</a>
                            </div>-->

                          </div>

                          <a class='more' onclick='location.href="Assignments?id=${classroom.getClassid()}"'>View more ></a>

                        </div>

                        <!-- END: classwork -->

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
