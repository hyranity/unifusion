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
        <title>UniFusion :: Join Course</title>
        <link rel="stylesheet" href="CSS/joinCourse.css">
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
          <div class='image'>
            <img src='https://image.flaticon.com/icons/svg/3300/3300028.svg'>
          </div>
          <div class='text'>
            <a id='title'>Join a Course</a>
            <a id='instruction'>Enter in the code of an existing course below.</a>
            <form id='form' action="PerformJoinCourse">
               <div class='input' id='right'>
                <a class='label'>Course code</a>
                <input class='textbox' type='text' name='courseCode' placeholder='eg. 177013'>
              </div>
              <input type='submit' id='proceed-button' value='Join!'>
            </form>
          </div>
        </div>

      </div>
    </body>
</html>
