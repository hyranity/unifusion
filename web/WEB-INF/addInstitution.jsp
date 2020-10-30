<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Create an Insititution</title>
        <link rel="stylesheet" href="CSS/addInstitution.css">
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
          <div class='image'>
            <img src='https://image.flaticon.com/icons/svg/2991/2991548.svg'>
          </div>
          <div class='text'>
            <a id='title'>Create an institution</a>
            <a id='desc'>You're now creating an institution, let's begin!</a>
            <div id='buttons'>
              <a id='proceed-button' href='#form'>Proceed!</a>
              <a id='learn-button' href='#'>Learn more</a>
            </div>
          </div>
        </div>
          
        <a id='error' style='margin-top: 40px;'><%out.print(Errors.requestSimple(session));%></a>

        <form id='form' action="PerformAddInstitution">

          <div class='section'>
            <div class='image' id='left'>
              <img src='https://i.postimg.cc/PrYP2szz/unifusion-create-institution-graphic-1.png'>
            </div>
            <div class='text' id='right'>
              <a class='label'>Institution code</a>
              <input class='textbox' type='text' name='institutionCode' placeholder='eg. GG420' required value="${id}">
              <a class='label'>Institution name</a>
              <input class='textbox' type='text' name='institutionName' placeholder='eg. ABC College' required value="${title}">
            </div>
          </div>
            
          <div class='section'>
            <div class='text' id='left'>
              <a class='label'>Description</a>
              <input class='textbox' type='text' name='description' placeholder='eg. This is quality institution.' required>
              <a class='label'>Address</a>
              <input class='textbox' type='text' name='address' placeholder='eg. No 69, John Doe Street' required>
              <a class='label' id='name'>Is this institution public?</a>
              <input type='checkbox' class='checkbox' id='isPublic' name='isPublic'>
              <label class='checkboxLabel' for='isPublic' id='isPublicLabel'>
                <div class='slider'></div>
              </label>
            </div>
            <div class='image' id='right'>
              <img src='https://i.postimg.cc/L8VhFtWJ/unifusion-create-institution-graphic-2.png'>
            </div>
          </div>

          <input id='create-button' type='submit' value='Create institution!'>

       </form>

      </div>
    </body>
    
    <script>
        function hasInstitutionClicked() {
            var hasInstitution = document.getElementById("hasInstitution");
            var institutionCodeTextbox = document.getElementById("institutionCodeTextbox");

            document.getElementById("institutionCodeEnabled").value = hasInstitution.checked;

            if (hasInstitution.checked) {
                institutionCodeTextbox.disabled = false;
            } else {
                institutionCodeTextbox.disabled = true;
            }
        }
    </script>
</html>
