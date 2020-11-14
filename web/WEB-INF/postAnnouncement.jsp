<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Post Announcement</title>
        <link rel="stylesheet" href="CSS/postAnnouncement.css">
        <link rel="stylesheet" href="CSS/all.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
              <a id='heading'>Post Announcement</a>
            </div>
          </div>
        </div>
              
              <a id='error' style='margin-bottom: 20px;'><%out.print(Errors.requestSimple(session));%></a>

        <form id='form' action='PerformPostAnnouncement' enctype ="multipart/form-data" method='post'>
            
            <!-- Hidden fields -->
            <input type="hidden" name="id" value="${id}"/>
            <input type="hidden" name="type" value="${type}"/>
             <!-- End of Hidden fields -->

          <a class='label' style='margin-left: -485px;'>Title</a>
          <input class='textbox' id='title-input' type='text' name='title' placeholder='eg. New announcement!'>

          <div id='input'>

            <div id='left'>
              <a class='label'>Message</a>
              <textarea class="textarea" id='message-input' name='message' placeholder='What do you want to tell everyone about?'></textarea>
            </div>

            <div id='right'>
              <a class='label'>Attachments</a>
              <input id='uploader' type='file' title="your text" name="file" size='50' multiple/>

            </div>

          </div>

          <input id='post-button' type='submit' value='Post!'>

        </form>

      </div>

    </body>
    
<script src="JS/validator.js"></script>
<script src="JS/postAnnouncement.js"></script>
    
</html>
