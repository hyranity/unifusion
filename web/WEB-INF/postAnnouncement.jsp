<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Post Announcement</title>
        <link rel="stylesheet" href="CSS/postAnnouncement.css">
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
            <img id='icon' src='${icon}'>
            <div id='text'>
              <a id='subheading'>${subheading}</a>
              <a id='heading'>Post Announcement</a>
            </div>
          </div>
        </div>

        <form id='form' action='PerformPostAnnouncement' enctype ="multipart/form-data" method='post'>
            
            <!-- Hidden fields -->
            <input type="hidden" name="id" value="${id}"/>
            <input type="hidden" name="type" value="${type}"/>
             <!-- End of Hidden fields -->

          <a class='label' style='margin-left: -485px;'>Title</a>
          <input class='textbox' type='text' name='title' placeholder='eg. New announcement!'>

          <div id='input'>

            <div id='left'>
              <a class='label'>Message</a>
              <textarea class="textarea" name='message' placeholder='What do you want to tell everyone about?'></textarea>
            </div>

            <div id='right'>
              <a class='label'>Attachments</a>
              <input id='uploader' type='file' title="your text" name="file" size='50'/>

            </div>

          </div>

          <input id='post-button' type='submit' value='Post!'>

        </form>

      </div>

    </body>
    
</html>
