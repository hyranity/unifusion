<%@page import="Util.Quick"%>
<%@page import="Models.Users"%>
<%@page import="Models.Users"%>
<%@page import="Util.Server"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Account Details</title>
        <link rel="stylesheet" href="CSS/accountDetails.css">
    </head>
    <body>
  <div id='container'>
  
    <div id='navbar'>
        <a href='Home' id='scaffold'>Scaffold</a>
        <a href='MyClasses' class='link'>Dashboard</a>
        <a href='AccountDetails' class='link' id='currentLink'>Account</a>
    </div>

    <div id='top'>
      <a id='heading'>Account details</a>
    </div>

    <form id='bottom' action="PerformAccountUpdate">

      <div id='left'>
      <a class='label' id='name'>Name</a>
        <input class='textbox' type='text' name='name' placeholder='Name' value=${name}>
        <a class='label' id='age'>Date of Birth</a>
        <input class='textbox' type='text' name='dateOfBirth' placeholder='Date of Birth' value=${dateOfBirth}>
        <a class='label' id='address'>Address</a>
        <input class='textbox' type='text' name='address' placeholder='Address' value=${address}>
        <input id='button' type='submit' value='Save'>
      </div>

      <div id='right'>
        <a class='label' id='email'>Email</a>
        <input class='textbox' type='text' name='email' placeholder='Email' value=${email}>
        <a class='label' id='email'>Password</a>
        <input class='textbox' type='password' name='password' placeholder='Password'>
        <a class='label' id='email'>Confirm password</a>
        <input class='textbox' type='password' name='confirmPassword' placeholder='Confirm password'>
      </div>

    </form>

  </div>
</body>

</html>
