<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Programme Details</title>
        <link rel="stylesheet" href="CSS/programmeDetails.css">
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
          <a id='heading'>Programme details</a>
          <a id='subheading'>Programme name</a>
        </div>
          
        <a id='error'>Error message</a>

        <!-- section 1: identity -->

        <div class='section'>
          <div class='header'>
            <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
          </div>

          <div class='textboxes'>

            <div id='left'>
            <a class='label' id='name'>Programme code</a>
              <input class='textbox' type='text' name='classCode' placeholder='eg. GG420'>
            </div>

            <div id='right'>
              <a class='label' id='email'>Email</a>
              <input class='textbox' type='text' name='email' placeholder='Email'>
            </div>

          </div>
        </div>

        <!-- section 2: institution -->

        <div class='section'>
          <div class='header'>
            <img src='https://i.postimg.cc/wBD3pkrF/unifusion-create-class-graphic-1-2.png'>
          </div>

          <div class='textboxes' id='institutionTextboxes'>
            <div id='left'>
              <a id='institutionTextboxesMsg'>The institution details of a programme cannot be edited.</a>
              <a class='label' id='name'>Is this programme part of an institution?</a>
              <input type='checkbox' class='checkbox' id='hasInstitution' name='hasInstitution' checked disabled>
              <label class='checkboxLabel' for='hasInstitution' id='hasInstitutionLabel'>
              <div class='slider'></div>
              </label>
              <a class='label' id='institutionCodeLabel'>Institution code</a>
              <input id='institutionCodeTextbox' class='textbox' type='text' name='institutionCode' placeholder='eg. LOL1337' disabled>
              <input type='hidden' id='institutionCodeEnabled' name='institutionCodeEnabled' value='false' disabled>
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
              <input class='textbox' type='text' name='classCode' placeholder='eg. GG420'>
            </div>

            <div id='right'>
              <a class='label' id='name'>Is this class public?</a>
              <input type='checkbox' class='checkbox' id='isPublic' name='isPublic'>
              <label class='checkboxLabel' for='isPublic' id='isPublicLabel'>
                <div class='slider'></div>
              </label>
            </div>

          </div>
        </div>

        <!-- section 4: design -->

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

      </div>
    </body>


    <script>
        function hasInstitutionClicked() {
            
            // note: editing institution settings in a programme has been disabled
            
            /*var hasInstitution = document.getElementById("hasInstitution");
            var institutionCodeTextbox = document.getElementById("institutionCodeTextbox");

            document.getElementById("institutionCodeEnabled").value = hasInstitution.checked;

            if (hasInstitution.checked) {
                institutionCodeTextbox.disabled = false;
            } else {
                institutionCodeTextbox.disabled = true;
            }*/
        }
    </script>
</html>
