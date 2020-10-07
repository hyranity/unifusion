<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Create a Class</title>
        <link rel="stylesheet" href="CSS/addClass.css">
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
            <a id='title'>Create a class</a>
            <a id='desc'>You're now creating a class, how exciting!</a>
            <div id='buttons'>
              <a id='proceed-button' href='#form'>Proceed!</a>
              <a id='learn-button' href='#'>Learn more</a>
            </div>
          </div>
        </div>
          
        <a id='error'>${errorMessage}</a>

        <form id='form' action="PerformAddClass">

          <div class='section'>
            <div class='image' id='left'>
              <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
            </div>
            <div class='text' id='right'>
              <a class='label'>Class code</a>
              <input class='textbox' type='text' name='classCode' placeholder='eg. GG420' required>
              <a class='label'>Class name</a>
              <input class='textbox' type='text' name='className' placeholder='eg. Computer Science' required>
            </div>
          </div>

          <div class='section'>
            <div class='text' id='left'>
              <a class='label' id='name'>Is this class part of a course?</a>
              <input type='checkbox' class='checkbox' id='hasCourse' name='hasCourse' onclick='hasCourseClicked()'>
              <label class='checkboxLabel' for='hasCourse' id='hasCourseLabel'>
                <div class='slider'></div>
              </label>
              <a class='label' id='courseCodeLabel'>Course code</a>
              <input id='courseCodeTextbox' class='textbox' type='text' name='courseCode' placeholder='eg. LOL1337' disabled>
              <input type='hidden' id='courseCodeEnabled' name='courseCodeEnabled' value='false'>
            </div>
            <div class='image' id='right'>
              <img src='https://i.postimg.cc/wBD3pkrF/unifusion-create-class-graphic-1-2.png'>
            </div>
          </div>
            
          <div class='section'>
            <div class='image' id='left'>
              <img src='https://i.postimg.cc/Rh6Rc5yL/unifusion-create-class-graphic-2.png'>
            </div>
            <div class='text' id='right'>
              <a class='label'>Description</a>
              <input class='textbox' type='text' name='description' placeholder='eg. This is a CS class.' required>
              
              <a class='label' id='name'>Is this class public?</a>
              <input type='checkbox' class='checkbox' id='isPublic' name='isPublic'>
              <label class='checkboxLabel' for='isPublic' id='isPublicLabel'>
                <div class='slider'></div>
              </label>
              
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

          <input id='create-button' type='submit' value='Create class!'>

       </form>

      </div>
    </body>
    
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
</html>
