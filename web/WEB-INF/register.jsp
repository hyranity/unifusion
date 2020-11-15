<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Register</title>
        <link rel="stylesheet" href="CSS/register.css">
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
      <img src='https://i.imgur.com/FzvvT7c.png'>
      <a>Already have an account?</a>
      <a href='Login' id='link'>Login here!</a>
    </div>

    <div id='right'>
      <a id='title'>Register</a>
      <a id='error'><%out.print(Errors.requestSimple(session));%></a>
      <form id='form' action="PerformRegister">
        <input class='textbox' id='name-textbox' type='text' name='name' placeholder='Full name'>
        <input class='textbox' id='email-textbox' type='text' name='email' placeholder='Email'>
        <input class='textbox' id='password-textbox' type='password' name='password' placeholder='Password'>
        <input class='textbox' id='confirmPassword-textbox' type='password' name='password' placeholder='Confirm password'>
        <input id='button' type='submit' value='>' onclick='test()'>
      </form>
    </div>
    
  </div>
</body>

<script src="JS/validator.js"></script>
<script src="JS/register.js"></script>

</html>
