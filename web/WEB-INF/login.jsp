<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Login</title>
        <link rel="stylesheet" href="CSS/login.css">
        <link rel="stylesheet" href="CSS/all.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
<body>
  <div id='container'>
      
    <div id='navbar'>
        <a href='Home' id='scaffold'>Scaffold</a>
        <a href='Login' class='link'>Dashboard</a>
        <a href='Login' class='link'>Account</a>
    </div>
    
    <div id='left'>
      <img src='https://i.imgur.com/2UR3HSF.png'>
      <a>Don't have an account?</a>
      <a href='Register' id='link'>Register here!</a>
    </div>

    <div id='right'>
      <a id='title'>Login</a>
      <a id='error'><%out.print(Errors.requestSimple(session));%></a>
      <form action="PerformLogin">
        <input class='textbox' id='email-textbox' type='text' name='email' placeholder='Email'>
        <input class='textbox' id='password-textbox' type='password' name='password' placeholder='Password'>
        <input id='button' type='submit' value='>'>
      </form>
    </div>
    
  </div>
</body>

<script src="JS/validator.js"></script>
<script src="JS/login.js"></script>

</html>
