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

            <a href='#' id='back'>&lt; Back</a>

            <div id='top'>
                <div id='topOverlay'></div>
                <div id='info'>
                    <img id='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
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
                                <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
                                <a class='text' id='className'>${classroom.getClasstitle()}</a>
                            </div>

                            <div class='box' id='desc'>
                                <a class='label'>Description</a>
                                <a class='text'>${classroom.getDescription()}</a>
                            </div>

                        </div>

                        <a class='more'>Click to view more ></a>

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
                                <%

                                           // Get list of tutors and students
                                           ArrayList<Users> tutorList = (ArrayList) request.getAttribute("tutorList");
                                           ArrayList<Users> studentList = (ArrayList) request.getAttribute("studentList");

                                           // Merge them
                                           tutorList.addAll(studentList);

                                           // Get current user
                                           Users user = Util.Server.getUser(request, response);
                                           
                                           for (Users participant : tutorList) {
                                               // If this participant == logged in user
                                               String you = user != null ? participant.getUserid() == user.getUserid() ? "id='you'" : "" : "";
                                               
                                               out.print("<div class='box' " + you + ">"
                                                       + "<img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>"
                                                       + "<a class='name'>Leader</a>"
                                                       + "</div>");
                                         
                                %>

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

                </div>

            </div>

            <!-- END: right column -->

        </div>

    </body>

</html>
