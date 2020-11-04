<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Create an Assignment</title>
        <link rel="stylesheet" href="CSS/addAssignment.css">
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
          <div id='topOverlay'></div>
          <div id='info'>
            <img id='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div id='text'>
              <a id='subheading'>${subheading}</a>
              <a id='heading'>Create Assignment</a>
            </div>
          </div>
        </div>
          
        <a id='error' style='margin-bottom: 30px;'><%out.print(Errors.requestSimple(session));%></a>

        <form id='form' action='PerformAddAssignment' enctype ="multipart/form-data" method="post">
            
            <!-- To store ID -->
            <input type="hidden" value="${id}" name="id"/>

          <a class='label' style='margin-left: -485px;'>Title</a>
          <input class='textbox' type='text' name='title' placeholder='eg. Semester Assignment' value="${title}">

          <div class='row'>

            <div class='left'>
              <a class='label'>Details</a>
              <textarea class="textarea" name='details' placeholder='What is this assignment about?'>${desc}</textarea>
            </div>

            <div class='right'>
              <a class='label'>Attachments</a>
              <input id='uploader' type='file' name="files" size='50' multiple />
            </div>

          </div>

          <div class='row'>
            <div class='left'>
              <a class='label'>Deadline date</a>
              <input class='date' type='date' name='deadlineDate'>
            </div>
            <div class='right'>
              <a class='label'>Deadline time</a>
              <input class='time' type='time' name='deadlineTime'>
            </div>
          </div>

          <div class='row'>
            <div class='left'>
              <a class='label'>Total Marks</a>
              <input class='number' type='number' name='marks' placeholder='eg. 100'>
            </div>
            <div class='right' style='margin-left: 50px; flex-direction: row; align-items: center;'>
              <a class='label' id='name'>Is this assignment only<br> used to show marks?</a>
              <input type='checkbox' class='checkbox' id='isForMarksOnly' name='isForMarksOnly' >
              <label class='checkboxLabel' for='isForMarksOnly' id='isForMarksOnlyLabel' style='margin-left: 15px;'>
                <div class='slider'></div>
              </label>
            </div>
          </div>

          <input id='create-button' type='submit' value='Create!'>

        </form>

      </div>

    </body>

</html>
