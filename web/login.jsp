<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Login</title>
        <link rel="stylesheet" href="login.css">
    </head>
<body>
  <div id='container'>
    <a href='#' id='back'>&lt; Back</a>
    <div id='left'>
      <img src='https://i.imgur.com/2UR3HSF.png'>
      <a>Don't have an account?</a>
      <a href='register.jsp' id='link'>Register here!</a>
    </div>

    <div id='right'>
      <a id='title'>Login</a>
      <form action="LoginServlet">
        <input class='textbox' type='text' name='email' placeholder='Email'>
        <input class='textbox' type='password' name='password' placeholder='Password'>
        <input id='button' type='submit' value='>'>
      </form>
    </div>
    
  </div>
</body>
</html>
