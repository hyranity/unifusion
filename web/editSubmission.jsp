<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Submit Assignment</title>
        <link rel="stylesheet" href="CSS/editSubmission.css">
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
              <a id='heading'>Edit Submission</a>
            </div>
          </div>
        </div>

        <form id='form' action=''>

          <div id='header'>
            <a id='assignmentId'>AS001</a>
            <a id='assignmentTitle'>Assignment Name</a>
            <a id='submissionId'>Your submission ID:<br><span>SU001</span></a>
          </div>
            
          <a id='error' style='margin-bottom: 30px;'><%out.print(Errors.requestSimple(session));%></a>

          <div class='row'>

            <div class='left'>
              <a class='label'>Comment</a>
              <textarea class="textarea" name='comment' placeholder='Any comments to make about your submission?'></textarea>
            </div>

            <div class='right'>
              <a class='label'>Attachments</a>
              <input id='uploader' type='file' multiple />
            </div>

          </div>

          <input id='save-button' type='submit' value='Save!'>

        </form>

      </div>

    </body>

</html>
