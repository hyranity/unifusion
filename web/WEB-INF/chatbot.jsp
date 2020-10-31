<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Chatbot</title>
        <link rel="stylesheet" href="CSS/chatbot.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>

    <body>
        <form id='container'>

            <div id='navbar'>
                <a href='#' id='back'>&lt; <span>Back</span></a>
                <a href='#' id='scaffold'>Scaffold</a>
                <a href='#' class='link'>Dashboard</a>
                <a href='#' class='link'>Account</a>
            </div>

            <input class='textbox' type='text' name='query' placeholder='Ask me anything!' value="${query}">

            <div id='results'>

                ${result}
                
                <!-- create a class with id abc123 and name testing -->
                <!--<div class='result action'>
                  <div class='top'>
                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/867/867855.svg'>
                    <div class='text'>
                      <a class='type'>ACTION</a>
                      <a class='desc'>Create a <span>Class</span></a>
                    </div>
                  </div>
                  <div class='bottom'>
                    <a class='desc'>With the following details:</a>
                    <div class='item'>
                      <a class='label'>ID</a>
                      <a class='value'>ABC123</a>
                    </div>
                    <div class='item'>
                      <a class='label'>Name</a>
                      <a class='value'>Testing</a>
                    </div>
                  </div>
                </div>-->
                      
                <!-- show me classes under taruckl -->      
<!--                <div class='result display'>
                  <div class='top'>
                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>
                    <div class='text'>
                      <a class='type'>CLASS</a>
                      <a class='name'>Class Name</a>
                      <a class='subname'>TARUCKL</a>
                    </div>
                  </div>
                </div>-->

                <!-- chat -->
<!--                <div class='result chat'>
                  <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/1041/1041916.svg'>
                  <a class='text'>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</a>
                </div>-->
                
            </div>
        </form>
    </body>

</html>
