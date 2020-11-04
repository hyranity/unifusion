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
        <div id='bot' onclick='location.href="Chatbot"'>
            <img id='eugeo' src='https://www.flaticon.com/svg/static/icons/svg/3398/3398640.svg'>
            <a id='desc'>Need help?<br><span>Eugeo's here!</span></a>
        </div>
        
      <div id='container'>

        <div id='navbar'>
            <a href='Dashboard' id='back'>&lt; <span>Back</span></a>
            <a href='Home' id='scaffold'>Scaffold</a>
            <a href='Dashboard' class='link'>Dashboard</a>
            <a href='AccountDetails' class='link'>Account</a>
            <a href='Chatbot' class='link'>Chatbot</a>
        </div>

        <div id='top'>
          <a id='heading'>Institution details</a>
          <a id='subheading'>Institution name</a>
        </div>
          
          <a id='error' style='margin-top: 40px;'><%out.print(Errors.requestSimple(session));%></a>
          
        <form action="PerformEditInstitution">

        <!-- section 1: identity -->

        <div class='section'>
          <div class='header'>
            <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
          </div>

          <div class='textboxes'>

            <div id='left'>
            <a class='label' id='name'>Institution code</a>
              <input class='textbox' type='text' name='institutionCode' placeholder='eg. GG420' readonly value="${institution.getInstitutioncode()}">
            </div>

            <div id='right'>
              <a class='label' id='email'>Institution name</a>
              <input class='textbox' type='text' name='institutionName' placeholder='eg. ABC College' value="${institution.getName()}">
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
              <input class='textbox' type='text' name='description' placeholder='eg. This is a quality institution' value="${institution.getDescription()}">
              <a class='label' id='name'>Address</a>
              <input class='textbox' type='text' name='address' placeholder='eg. No 69, John Doe Street'  value="${institution.getAddress()}">
            </div>

            <div id='right'>
              <a class='label' id='name'>Is this institution public?</a>
              <input type='checkbox' class='checkbox' id='isPublic' name='isPublic' ${institution.getIspublic() ? "checked" : ""}>
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
              <input class='textbox' type='text' name='iconURL' placeholder='eg. example.com' value="${institution.getIconurl()}">
              <a class='label' id='name'>Banner URL</a>
              <input class='textbox' type='text' name='bannerURL' placeholder='eg. example.com' value="${institution.getBannerurl()}">
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
