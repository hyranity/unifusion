<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Take Attendance</title>
        <link rel="stylesheet" href="CSS/takeAttendance.css">
    </head>
    <body>
        <div id='container'>

          <a href='#' id='back'>&lt; Back</a>

          <div id='top'>
            <div class='image'>
              <img src='https://image.flaticon.com/icons/svg/3300/3300028.svg'>
            </div>
            <div class='text'>
              <a id='title'>Take attendance</a>
              <div class='desc'>
                <a class='label' style='margin-right: 24px;'>CLASS</a>
                <a class='value'>Computer Science</a>
              </div>
              <div class='desc'>
                <a class='label' style='margin-right: 10px;'>SESSION</a>
                <a class='value'>Thursday 3-5pm</a>
              </div>

              <form id='form' action="PerformTakeAttendance">
                 <div class='input' id='right'>
                  <a class='label'>Class code</a>
                  <input class='textbox' type='text' name='code' placeholder='eg. 177013'>
                </div>
                <input type='submit' id='proceed-button' value='Proceed!'>
              </form>
              <a id='error'><%out.print(Errors.requestSimple(session));%></a>
            </div>
          </div>

        </div>
      </body>
</html>
