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

        <a href='#' id='back'>&lt; Back</a>

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

        <form id='form'>

          <div class='section'>
            <div class='image' id='left'>
              <img src='https://i.postimg.cc/kgFkP6cK/unifusion-create-class-graphic-1.png'>
            </div>
            <div class='text' id='right'>
              <a class='label'>Class code</a>
              <input class='textbox' type='text' name='classCode' placeholder='eg. GG420'>
              <a class='label'>Class name</a>
              <input class='textbox' type='text' name='className' placeholder='eg. Computer Science'>
            </div>
          </div>

          <div class='section'>
            <div class='text' id='left'>
              <a class='label' id='name'>Is this class part of a course?</a>
              <input type='checkbox' class='checkbox' id='hasCourse' name='hasCourse'>
              <label class='checkboxLabel' for='hasCourse' id='hasCourseLabel'>
                <div class='slider'></div>
              </label>
              <a class='label' id='courseCodeLabel'>Course code</a>
              <input id='courseCodeTextbox' class='textbox' type='text' name='className' placeholder='eg. LOL1337'>
            </div>
            <div class='image' id='right'>
              <img src='https://i.postimg.cc/wBD3pkrF/unifusion-create-class-graphic-1-2.png'>
            </div>
          </div>

          <input id='create-button' type='button' value='Create class!'>

       </form>

      </div>
    </body>
</html>
