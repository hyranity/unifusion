<%@page import="Util.Quick"%>
<%@page import="Models.Users"%>
<%@page import="Models.Users"%>
<%@page import="Util.Server"%>
<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Account Details</title>
        <link rel="stylesheet" href="CSS/accountDetails.css">
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
        <a href='AccountDetails' class='link' id='currentLink'>Account</a>
        <a href='Chatbot' class='link'>Chatbot</a>
        <a href='Logout' class='link'>Logout</a>
    </div>

    <div id='top'>
      <a id='heading'>Account details</a>
    </div>
      
      <a id='error' style='margin-bottom: 15px;'><%out.print(Errors.requestSimple(session));%></a>

    <form id='bottom' action="PerformAccountUpdate">

      <div id='left'>
      <a class='label' id='name'>Name</a>
        <input class='textbox' id='name-textbox' type='text' name='name' placeholder='Name' value="${name}">
        <a class='label' id='age'>Date of Birth</a>
        <input class='date' id='date-textbox' type='date' name='dateOfBirth' value="${dateOfBirth}">
        <a class='label' id='address'>Address</a>
        <input class='textbox' id='address-textbox' type='text' name='address' placeholder='Address' value=${address}>
        <input id='button' type='submit' value='Save'>
        
      </div>

      <div id='right'>
        <a class='label' id='email'>Email</a>
        <input class='textbox' id='email-textbox' type='text' name='email' placeholder='Email' value=${email}>
        <a class='label' id='imageUrl'>Image URL</a>
        <input class='textbox' id='imageUrl-textbox' type='text' name='imageUrl' placeholder='Image URL' value=${imageUrl}>
        <!--<a class='label' id='email'>Password</a>
        <input class='textbox' id='password-textbox' type='password' name='password' placeholder='Password'>
        <a class='label' id='email'>Confirm password</a>
        <input class='textbox' id='confirmPassword-textbox' type='password' name='confirmPassword' placeholder='Confirm password'>-->
      </div>

    </form>

  </div>
</body>

<script src="JS/validator.js"></script>
<script src="JS/accountDetails.js"></script>

</html>
