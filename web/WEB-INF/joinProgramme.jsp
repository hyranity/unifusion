<%@page import="Util.Server"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Models.Classparticipant"%>
<%@page import="Models.Classparticipant"%>
<%@page import="Models.Users"%>
<%@page import="Util.Quick"%>
<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Join Programme</title>
        <link rel="stylesheet" href="CSS/joinProgramme.css">
        <link rel="stylesheet" href="CSS/all.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
          <div class='image'>
            <img src='https://image.flaticon.com/icons/svg/3300/3300028.svg'>
          </div>
          <div class='text'>
            <a id='title'>Join a Programme</a>
            <a id='instruction'>Enter in the code of an existing programme below.</a>
            <a id='error'><%out.print(Errors.requestSimple(session));%></a>
            <form id='form' action="PerformJoinProgramme">
               <div class='input' id='right'>
                <a class='label'>Programme code</a>
                <input class='textbox' id='programmeCode-input' type='text' name='programmeCode' placeholder='eg. 177013'>
              </div>
              <input type='submit' id='proceed-button' value='Join!'>
            </form>
          </div>
        </div>

      </div>
    </body>
    
<script src="JS/validator.js"></script>
<script src="JS/joinProgramme.js"></script>
    
</html>
