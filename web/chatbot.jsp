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
      <div id='container'>

        <div id='navbar'>
          <a href='#' id='back'>&lt; <span>Back</span></a>
          <a href='#' id='scaffold'>Scaffold</a>
          <a href='#' class='link'>Dashboard</a>
          <a href='#' class='link'>Account</a>
        </div>

        <input class='textbox' type='text' name='query' placeholder='Ask me anything!'>

        <div id='results'>
          <!-- Space to print out your results -->
        </div>

      </div>
    </body>

</html>
