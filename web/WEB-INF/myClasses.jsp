<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: My Classes</title>
        <link rel="stylesheet" href="CSS/myClasses.css">
    </head>
    <body>
        <div id='container'>

          <a href='#' id='back'>&lt; Back</a>

          <div id='top'>
            <a id='heading'>My classes</a>
            <div id='actions'>
              <a href='#'>Join a class</a>
              <a href='AddClass'>Create a class</a>
            </div>
          </div>

          <div id='classes'>

            <!-- row 1 -->

            <div class='row'>

              <!-- row 1, class 1 -->

              <div class='class' id='orange' onclick="location.href='#';">
                <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                <div class='details'>
                  <div class='top-details'>
                    <a class='id'>C001</a>
                    <a class='tutor'>John Doe</a>
                  </div>
                  <a class='name'>Math</a>
                  <a class='description'>Intermediate arithmetic</a>
                </div>
              </div>

              <!-- row 1, class 2 -->

              <div class='class' onclick="location.href='#';">
                <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                <div class='details'>
                  <div class='top-details'>
                    <a class='id'>C001</a>
                    <a class='tutor'>John Doe</a>
                  </div>
                  <a class='name'>Math</a>
                  <a class='description'>Intermediate</a>
                </div>
              </div>

            </div>

            <!-- row 2 -->

            <div class='row'>

              <!-- row 2, class 1 -->

              <div class='class' onclick="location.href='#';">
                <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                <div class='details'>
                  <div class='top-details'>
                    <a class='id'>C001</a>
                    <a class='tutor'>John Doe</a>
                  </div>
                  <a class='name'>Math</a>
                  <a class='description'>Intermediate</a>
                </div>
              </div>

              <!-- row 2, class 2 -->

              <div class='class' id='green' onclick="location.href='#';">
                <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                <div class='details'>
                  <div class='top-details'>
                    <a class='id'>C001</a>
                    <a class='tutor'>John Doe</a>
                  </div>
                  <a class='name'>Math</a>
                  <a class='description'>Intermediate arithmetic</a>
                </div>
              </div>

            </div>

          </div>

        </div>
    </body>
</html>
