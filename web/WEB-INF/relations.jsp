<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Relations</title>
        <link rel="stylesheet" href="CSS/relations.css">
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
            <img id='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div id='text'>
              <a id='subheading'>C001 - Course Name (Course)</a>
              <a id='heading'>Relations</a>
            </div>
          </div>
        </div>

        <div id='relations'>
            
          ${output}

          <!--<div class='panel'>
            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='id'>C001</a>
              <a class='title'>Institution Name</a>
              <a class='desc'>Insitution</a>
            </div>
          </div>

          <a class='divider'>^</a>

          <div class='panel'>
            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='id'>P001</a>
              <a class='title'>Programme Name</a>
              <a class='desc'>Programme</a>
            </div>
          </div>

          <a class='divider'>^</a>

          <div class='panel' id='current'>
            <img class='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
            <div class='text'>
              <a class='id'>CO001</a>
              <a class='title'>Course Name</a>
              <a class='desc'>Course</a>
            </div>
          </div>

          <a class='divider'>v</a>

          <div class='panel'>
            <div class='text'>
              <a class='title'>5 Classes...</a>
            </div>
          </div>-->

        </div>

      </div>

    </body>

</html>
