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
            <a href='Dashboard' id='back'>&lt; <span>Back</span></a>
            <a href='Home' id='scaffold'>Scaffold</a>
            <a href='Dashboard' class='link'>Dashboard</a>
            <a href='AccountDetails' class='link'>Account</a>
            <a href='Chatbot' class='link'>Chatbot</a>
        </div>

        <div id='top'>
          <div id='topOverlay'></div>
          <div id='info'>
            <img id='icon' src='${icon}'>
            <div id='text'>
              <a id='subheading'>${subheading}</a>
              <a id='heading'>Edit Assignment</a>
            </div>
          </div>
        </div>
          
        <a id='error' style='margin-top: -30px; margin-bottom: 30px;'><%out.print(Errors.requestSimple(session));%></a>

        <form id='form' action='PerformEditAssignment'>
            
            <!-- hidden fields -->
            <input type="hidden" name="id" value="${assignment.getComponentid()}"/>
            <input type="hidden" name="code" value="${assignment.getClassid().getClassid()}"/>

          <div id='header'>
            <a id='assignmentId'>${assignment.getComponentid()}</a>
            <a id='assignmentTitle'>${assignment.getTitle()}</a>
          </div>

          <a class='label' style='margin-left: -485px;'>Title</a>
          <input class='textbox' type='text' name='title' placeholder='eg. Semester Assignment' value="${assignment.getTitle()}">

          <div class='row'>

            <div class='left'>
              <a class='label'>Details</a>
              <textarea class="textarea" name='details' placeholder='What is this assignment about?'>${assignment.getDetails()}</textarea>
              <br/>
              <a class='label'>Note: You can't edit uploaded attachments.</a>
            </div>
   

            <!-- Not allowed to edit attachments -->
<!--            <div class='right'>
                <a class='label'>Attachments</a>
                <input id='uploader' type='file' multiple />
            </div>-->

          </div>

          <div class='row'>
            <div class='left'>
              <a class='label'>Deadline date</a>
              <input class='date' type='date' name='deadlineDate' value='${deadline}'>
            </div>
            <div class='right'>
              <a class='label'>Deadline time</a>
              <input class='time' type='time' name='deadlineTime' value="${timeDeadline}">
            </div>
          </div>


          <div class='row'>
            <div class='left'>
              <a class='label'>Total Marks</a>
              <input class='number' type='number' name='marks' placeholder='eg. 100' value='${assignment.getTotalmarks()}'>
            </div>
            <div class='right' style='margin-left: 50px; flex-direction: row; align-items: center;'>
              <a class='label' id='name'>Is this assignment only<br> used to show marks?</a>
              <input type='checkbox' class='checkbox' id='isActive' name='isForMarksOnly' ${assignment.getIstoshowmarksonly() ? "checked" : ""}>
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
