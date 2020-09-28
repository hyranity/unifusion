<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Home</title>
        <link rel="stylesheet" href="CSS/home.css">
    </head>
    <body>
        <div id='container'>
            
        <div id='navbar'>
            <a href='Home' id='scaffold'>Scaffold</a>
            <a href='MyClasses' class='link' id='currentLink'>Dashboard</a>
            <a href='AccountDetails' class='link'>Account</a>
            
        </div>

          <div id='top'>
            <div id='overlay'>
              <div id='text'>
                <a id='title'>UniFusion</a>
                <a id='description'>The ultimate e-learning platform, remastered.</a>
                <div id='buttons'>
                  ${login}
                  <a id='learn-button' href='#'>Learn more</a>
                </div>
              </div>
            </div>
          </div>

          <div id='more'>
            <div class='item'>
              <div class='image'>
                <img src='https://i.imgur.com/eS3FqbD.png'>
              </div>
              <div class='details'>
                <a class='text'>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Cras ornare arcu dui vivamus arcu.</a>
                <a class='button' href='#'>Learn more</a>
              </div>
            </div>

            <div class='item'>
              <div class='details' id='right'>
                <a class='text'>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Cras ornare arcu dui vivamus arcu.</a>
                <a class='button' href='#'>Learn more</a>
              </div>
              <div class='image'>
                <img src='https://i.imgur.com/eS3FqbD.png'>
              </div>
            </div>
          </div>

        </div>
      </body>
</html>
