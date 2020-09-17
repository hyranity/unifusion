<%@page import="Util.Quick"%>
<%@page import="Models.Users"%>
<%@page import="Models.Users"%>
<%@page import="Util.Server"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Clsss Details</title>
        <link rel="stylesheet" href="CSS/classDetails.css">
    </head>
<body>
  <div id='container'>
  
    <a href='#' id='back'>&lt; Back</a>

    <div id='top'>
      <a id='heading'>Class details</a>
      <a id='subheading'>Group name</a>
    </div>
    
    <!-- section 1: identity -->
    
    <div class='section'>
      <div class='header'>
        <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
      </div>

      <div class='textboxes'>

        <div id='left'>
        <a class='label' id='name'>Class code</a>
          <input class='textbox' type='text' name='classCode' placeholder='eg. GG420'>
        </div>

        <div id='right'>
          <a class='label' id='email'>Email</a>
          <input class='textbox' type='text' name='email' placeholder='Email'>
        </div>

      </div>
    </div>
    
    <!-- section 2: course -->
    
    <div class='section'>
      <div class='header'>
        <img src='https://i.postimg.cc/wBD3pkrF/unifusion-create-class-graphic-1-2.png'>
      </div>

      <div class='textboxes'>

        <div id='left'>
          <a class='label' id='name'>Is this class part of a course?</a>
          <input type='checkbox' class='checkbox' id='hasCourse' name='hasCourse'>
          <label class='checkboxLabel' for='hasCourse' id='hasCourseLabel'>
          <div class='slider'></div>
          </label>
          <a class='label' id='courseCodeLabel'>Course code</a>
          <input id='courseCodeTextbox' class='textbox' type='text' name='courseCode' placeholder='eg. LOL1337'>
        </div>

      </div>
    </div>
    
    <!-- section 3: details -->
    
    <div class='section'>
      <div class='header'>
        <img src='https://i.postimg.cc/Rh6Rc5yL/unifusion-create-class-graphic-2.png'>
      </div>

      <div class='textboxes'>

        <div id='left'>
        <a class='label' id='name'>Description</a>
          <input class='textbox' type='text' name='classCode' placeholder='eg. GG420'>
        </div>

        <div id='right'>
          <a class='label'>Class type</a>
          <select class='dropdown' name='classType'>
            <option value='lecture'>Lecture</option>
            <option value='tutorial'>Tutorial</option>
            <option value='practical'>Practical</option>
            <option value='other'>Other</option>
          </select>
          <a class='dropdownLabel'>Click to view options</a>
        </div>

      </div>
    </div>
    
    <!-- section 4: design (HEADER TBC) -->
    
    <div class='section'>
      <div class='header'>
        <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
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
          
          <a class='label' id='name'>Is this class public?</a>
          <input type='checkbox' class='checkbox' id='isPublic' name='isPublic'>
          <label class='checkboxLabel' for='isPublic' id='isPublicLabel'>
            <div class='slider'></div>
          </label>
        </div>

      </div>
    </div>
    
    <!-- bottom section: save button -->
    
    <div class='section'>
      <input id='save-button' type='submit' value='Save!'>
    </div>

  </div>
    
<script>
    function hasCourseClicked() {
        var hasCourse = document.getElementById("hasCourse");
        var courseCodeTextbox = document.getElementById("courseCodeTextbox");

        document.getElementById("courseCodeEnabled").value = hasCourse.checked;

        if (hasCourse.checked) {
            courseCodeTextbox.disabled = false;
        } else {
            courseCodeTextbox.disabled = true;
        }
    }
</script>

</body>
</html>

