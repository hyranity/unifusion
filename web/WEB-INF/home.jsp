<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Home</title>
        <link rel="stylesheet" href="CSS/home.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>
    <body>
        <div id='bot' onclick='location.href="Chatbot"'>
            <img id='eugeo' src='https://www.flaticon.com/svg/static/icons/svg/3398/3398640.svg'>
            <a id='desc'>Need help?<br><span>Eugeo's here!</span></a>
        </div>
        
        <div id='container'>
            
        <div id='navbar'>
            <a href='Home' id='scaffold'>Scaffold</a>
            <a href='Dashboard' class='link'>Dashboard</a>
            <a href='AccountDetails' class='link'>Account</a>
            <a href='Chatbot' class='link'>Chatbot</a>
        </div>

          <div id='top'>
            <div id='overlay'>
              <div id='text'>
                <a id='title'>Scaffold</a>
                <a id='description'>Now anyone can teach and learn.</a>
                <div id='buttons'>
                  ${login}
                  <!--<a id='learn-button' href='#'>Learn more</a>-->
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
                <a class='text'>Scaffold makes it easy for teachers to handle classes. You can start small from a simple class, or scale up to a complicated institution that spans multiple programmes. No more headaches!</a>
                <a class='button' href='#'>Learn more</a>
              </div>
            </div>

            <div class='item'>
              <div class='details' id='right'>
                <a class='text'>Students can easily participate in classes, courses, programmes and institutions, all by using a simple code.</a>
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
