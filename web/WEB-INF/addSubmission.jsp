<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Submit Assignment</title>
        <link rel="stylesheet" href="CSS/addSubmission.css">
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
            <img id='icon' src='${icon}'>
            <div id='text'>
              <a id='subheading'>${subheading}</a>
              <a id='heading'>Submit Assignment</a>
            </div>
          </div>
        </div>

        <form id='form' action='PerformAddSubmission' enctype ="multipart/form-data" method="post">
            
            <!-- To store assignment id and class code -->
            <input type="hidden" value="${id}" name="id"/>
            <input type="hidden" value="${code}" name="code"/>

          <div id='header'>
            <a id='assignmentId'>${assignment.getComponentid()}</a>
            <a id='assignmentTitle'>${assignment.getTitle()}</a>
          </div>
            
          <a id='error' style='margin-bottom: 30px;'><%out.print(Errors.requestSimple(session));%></a>

          <div class='row'>

            <div class='left'>
              <a class='label'>Comment</a>
              <textarea class="textarea" name='comment' placeholder='Any comments to make about your submission?'></textarea>
            </div>

            <div class='right'>
              <a class='label'>Attachments</a>
              <input id='uploader' type='file' multiple name="files" />
            </div>

          </div>

          <input id='submit-button' type='submit' value='Submit!'>

        </form>

      </div>

    </body>

</html>
