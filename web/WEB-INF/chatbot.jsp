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

            <input class='textbox' type='text' name='query' placeholder='Ask me anything!'>

            <div id='results'>

                <!-- create a class with id abc123 and name testing -->
                ${result}
                <!--          <div class='action'>
                            <div class='top'>
                              <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/3324/3324859.svg'>
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
            </div>
        </form>
    </body>

</html>
