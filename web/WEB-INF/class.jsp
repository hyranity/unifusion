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
                    <img id='icon' src='<% out.print(Quick.getIcon(((Models.Class) request.getAttribute("classroom")).getIconurl()));%>'>
                    <div id='text'>
                        <a id='subheading'>Class</a>
                        <a id='heading'>${classroom.getClasstitle()}</a>
                    </div>
                </div>
            </div>

            <div id='content'>

                <!-- START: left column -->

                <div class='column' id='leftColumn'>

                    <!-- START: details -->

                    <div id='details' onclick='location.href = "#"'>

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

                    </div>

                    <!-- END: details -->

                    <!-- START: members -->

                    <div id='members' onclick='location.href = "#"'>

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

                            <a class='more'>Click to view more ></a>

                        </div>

                        <!-- END: members -->

                    </div>

                    <!-- END: left column -->

                    <!-- START: right column -->

                    <div class='column' id='rightColumn'>
                        <!-- START: announcements -->

                        <div id='announcements' onclick='location.href = "#"'>

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
                        
                        <!-- START: classwork -->

                        <div id='classwork' onclick='location.href="#"'>

                          <div class='top'>
                            <a class='heading'>Classwork</a>

                            <div class='stats'>
                              <div class='stat'>
                                <a class='value' id='classworkWeek'>2</a>
                                <a class='desc'>due this<br>week</a>
                              </div>

                              <div class='stat'>
                                <a class='value' id='classworkTotal'>5</a>
                                <a class='desc'>due in<br>total</a>
                              </div>
                            </div>
                          </div>

                          <div class='bottom'>

                            <!-- sample assignment 1 -->

                            <div class='assignment'>
                              <a class='due'>1d</a>
                              <a class='message'>Tutorial 6</a>
                              <a class='slug'></a>
                            </div>

                            <!-- sample assignment 2 -->

                            <div class='assignment'>
                              <a class='due'>5d</a>
                              <a class='message'>Tutorial 7</a>
                              <a class='slug'></a>
                            </div>

                            <!-- sample assignment 3 -->

                            <div class='assignment'>
                              <a class='due'>16d</a>
                              <a class='message'>Assignment</a>
                              <a class='slug'>50% coursework</a>
                            </div>

                          </div>

                          <a class='more'>Click to view more ></a>

                        </div>

                        <!-- END: classwork -->

                    </div>

                </div>

                <!-- END: right column -->

            </div>

    </body>

</html>
