<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Edit an Assignment</title>
        <link rel="stylesheet" href="CSS/editAssignment.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>
    
    <body>
      <div id='container'>

        <div id='navbar'>
          <a href='#' id='back'>&lt; <span>Back</span></a>
          <a href='#' id='scaffold'>Scaffold</a>
          <a href='#' class='link'>Dashboard</a>
          <a href='#' class='link'>Account</a>
        </div>

        <div id='top'>
          <div id='topOverlay'></div>
          <div id='info'>
            <img id='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div id='text'>
              <a id='subheading'>C001 - Class Name (Class)</a>
              <a id='heading'>Edit Assignment</a>
            </div>
          </div>
        </div>
          
        <a id='error' style='margin-top: -30px; margin-bottom: 30px;'><%out.print(Errors.requestSimple(session));%></a>

        <form id='form' action=''>

          <div id='header'>
            <a id='assignmentId'>AS001</a>
            <a id='assignmentTitle'>Assignment Name</a>
          </div>

          <a class='label' style='margin-left: -485px;'>Title</a>
          <input class='textbox' type='text' name='title' placeholder='eg. Semester Assignment'>

          <div class='row'>

            <div class='left'>
              <a class='label'>Details</a>
              <textarea class="textarea" name='details' placeholder='What is this assignment about?'></textarea>
            </div>

            <div class='right'>
              <a class='label'>Attachments</a>
              <input id='uploader' type='file' multiple />
            </div>

          </div>

          <a class='label' style='margin-left: -450px;'>Deadline</a>
          <input class='date' type='date' name='deadline'>

          <div class='row'>
            <div class='left'>
              <a class='label'>Total Marks</a>
              <input class='number' type='number' name='marks' placeholder='eg. 100'>
            </div>
            <div class='right' style='margin-left: 50px; flex-direction: row; align-items: center;'>
              <a class='label' id='name'>Is this assignment only<br> used to show marks?</a>
              <input type='checkbox' class='checkbox' id='isActive' name='isActive'>
              <label class='checkboxLabel' for='isActive' id='isActiveLabel' style='margin-left: 15px;'>
                <div class='slider'></div>
              </label>
            </div>
          </div>

          <input id='save-button' type='submit' value='Save!'>

        </form>

      </div>

    </body>

</html>
