<%@page import="Util.Errors"%>
<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Institution Details</title>
        <link rel="stylesheet" href="CSS/institutionDetails.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>
    
    <body>
      <div id='container'>

        <div id='navbar'>
            <a href='Dashboard' id='back'>&lt; <span>Back</span></a>
            <a href='Home' id='scaffold'>Scaffold</a>
            <a href='Dashboard' class='link'>Dashboard</a>
            <a href='AccountDetails' class='link'>Account</a>
        </div>

        <div id='top'>
          <a id='heading'>Institution details</a>
          <a id='subheading'>Institution name</a>
        </div>
          
        <form action="PerformEditInstitution">

        <!-- section 1: identity -->

        <div class='section'>
          <div class='header'>
            <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
          </div>

          <div class='textboxes'>

            <div id='left'>
            <a class='label' id='name'>Institution code</a>
              <input class='textbox' type='text' name='institutionCode' placeholder='eg. GG420'>
            </div>

            <div id='right'>
              <a class='label' id='email'>Institution name</a>
              <input class='textbox' type='text' name='institutionName' placeholder='eg. ABC College'>
            </div>

          </div>
        </div>

        <!-- section 3: details -->

        <div class='section'>
          <div class='header'>
            <img src='https://i.postimg.cc/wBD3pkrF/unifusion-create-class-graphic-1-2.png'>
          </div>

          <div class='textboxes'>

            <div id='left'>
              <a class='label' id='name'>Description</a>
              <input class='textbox' type='text' name='desc' placeholder='eg. This is a quality institution'>
              <a class='label' id='name'>Address</a>
              <input class='textbox' type='text' name='address' placeholder='eg. No 69, John Doe Street'>
            </div>

            <div id='right'>
              <a class='label' id='name'>Is this institution public?</a>
              <input type='checkbox' class='checkbox' id='isPublic' name='isPublic'>
              <label class='checkboxLabel' for='isPublic' id='isPublicLabel'>
                <div class='slider'></div>
              </label>
            </div>

          </div>
        </div>

        <!-- section 4: design (HEADER TBC) -->

        <div class='section'>
          <div class='header'>
            <img src='https://i.postimg.cc/wBD3pkrF/unifusion-create-class-graphic-1-2.png'>
          </div>

          <div class='textboxes'>

            <div id='left'>
              <a class='label' id='name'>Icon URL</a>
              <input class='textbox' type='text' name='classCode' placeholder='eg. example.com'>
              <a class='label' id='name'>Banner URL</a>
              <input class='textbox' type='text' name='classCode' placeholder='eg. example.com'>
            </div>

            <div id='right'>
              <a class='label'>Colour theme</a>
              <select class='dropdown' name='colourTheme'>
                <option value='lecture'>Default</option>
                <option value='tutorial'>Light</option>
                <option value='practical'>Dark</option>
              </select>
              <a class='dropdownLabel'>Click to view options</a>
            </div>

          </div>
        </div>

        <!-- bottom section: save button -->

        <div class='section'>
          <input id='save-button' type='submit' value='Save!'>
        </div>
        
        </form>

      </div>
    </body>
</html>
