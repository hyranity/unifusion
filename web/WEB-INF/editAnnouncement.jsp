<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Announcement Details</title>
        <link rel="stylesheet" href="CSS/editAnnouncement.css">
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
              <a id='subheading'>${subheading}</a>
              <a id='heading'>Edit Announcement</a>
            </div>
          </div>
        </div>

        <form id='form' action='PerformEditAnnouncement'>
            
             <!-- Hidden fields -->
            <input type="hidden" name="id" value="${id}"/>
            <input type="hidden" name="type" value="${type}"/>
            <input type="hidden" name="code" value="${announcement.getAnnouncementid()}"/>
             <!-- End of Hidden fields -->

          <div id='header'>
            <a id='announcementId'>Announcement</a>
            <a id='announcementTitle'>${announcement.getTitle()}</a>
          </div>
            
          <a id='error' style='margin-bottom: 20px;'><%out.print(Errors.requestSimple(session));%></a>

          <a class='label' style='margin-left: -485px;'>Title</a>
          <input class='textbox' type='text' name='title' placeholder='eg. New announcement!' value="${announcement.getTitle()}">

          <div id='input' >

            <div id='left'>
              <a class='label'>Message</a>
              <textarea class="textarea" name='message' placeholder='What do you want to tell everyone about?' >${announcement.getMessage()}</textarea>
            </div>

            <!-- Uneditable -->
<!--            <div id='right'>
              <a class='label'>Attachments</a>
              <input id='uploader' type='file' title="your text" multiple/>
            </div>-->

          </div>
            <br/>
            <a class='label'>Note: Uploaded files cannot be edited.</a>

          <input id='post-button' type='submit' value='Save!'>

        </form>

      </div>

    </body>
    
</html>
