<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Register</title>
        <link rel="stylesheet" href="CSS/register.css">
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
      <form action="PerformRegister">
        <input class='textbox' type='text' name='name' placeholder='Full name'>
        <input class='textbox' type='text' name='email' placeholder='Email'>
        <input class='textbox' type='password' name='password' placeholder='Password'>
        <input class='textbox' type='password' name='password' placeholder='Confirm password'>
        <input id='button' type='submit' value='>'>
      </form>
    </div>
    
  </div>
</body>
</html>
