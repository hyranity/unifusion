<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Chatbot</title>
        <link rel="stylesheet" href="CSS/chatbot.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>

    <body>
        <div id='bot' onclick='location.href="Chatbot"'>
            <img id='eugeo' src='https://www.flaticon.com/svg/static/icons/svg/3398/3398640.svg'>
            <a id='desc'>Need help?<br><span>Eugeo's here!</span></a>
        </div>
        
        <form id='container'>

            <div id='navbar'>
                <a href='Dashboard' id='back'>&lt; <span>Back</span></a>
                <a href='Home' id='scaffold'>Scaffold</a>
                <a href='Dashboard' class='link'>Dashboard</a>
                <a href='AccountDetails' class='link'>Account</a>
                <a href='Chatbot' class='link' id='currentLink'>Chatbot</a>
            </div>
            
            <div id='top'>
              <img id='botImg' src='https://www.flaticon.com/svg/static/icons/svg/3398/3398640.svg'>
              <div id='text'>
                <a id='name'>EUGEO</a>
                <a id='desc'>A somewhat intelligent being..</a>
              </div>
            </div>

            <input class='textbox' type='text' name='query' placeholder='Ask me anything!' value="${query}">
            <a id='textboxLabel'>Click ENTER to search!</a>

            <div id='results'>

                ${result}
                
                <!-- stat -->
<!--                <div class='result stat'>
                  <div class='top'>
                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/423/423786.svg'>
                    <div class='text'>
                      <a class='stat'>2 Classes</a>
                      <a class='desc'>In TARUCKL</a>
                    </div>
                  </div>
                </div>-->
                
                <!-- create a class with id abc123 and name testing -->
                <!--<div class='result action'>
                  <div class='top'>
                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/867/867855.svg'>
                    <div class='text'>
                      <a class='type'>ACTION</a>
                      <a class='desc'>Create a <span>Class</span></a>
                    </div>
                  </div>
                  <div class='bottom'>
                    <a class='desc'>With the following details:</a>
                    <div class='item'>
                      <a class='label'>ID</a>
                      <a class='value'>ABC123</a>
                    </div>
                    <div class='item'>
                      <a class='label'>Name</a>
                      <a class='value'>Testing</a>
                    </div>
                  </div>
                </div>-->
                      
                <!-- show me classes under taruckl -->      
<!--                <div class='result display'>
                  <div class='top'>
                    <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/717/717874.svg'>
                    <div class='text'>
                      <a class='type'>CLASS</a>
                      <a class='name'>Class Name</a>
                      <a class='subname'>TARUCKL</a>
                    </div>
                  </div>
                </div>-->

                <!-- chat -->
<!--                <div class='result chat'>
                  <img class='icon' src='https://www.flaticon.com/svg/static/icons/svg/1041/1041916.svg'>
                  <a class='text'>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</a>
                </div>-->
                
            </div>
        </form>
    </body>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script>
      $("#top").hover(function(){

        $(this).css({
          "opacity": "1"
        });

        $("#top #bot").css({
          "height": "350px"
        });

        $("#top #name").css({
          "font-size": "90px"
        });

        var descs = [
          "A somewhat intelligent being...",
          "Critics say he isn't truly intelligent...",
          "He'll answer anything! Really!",
          "Operates 24/7. What a hard worker!",
          "Planning to take over the world..",
          "That smiles never fades...",
          "Eugeo loves job. He loves it. He really does-",
          "'Sword Art Online? What's that?' Eugeo says.",
          "Aspires to be among the ranks of Siri and Deep Blue.",
          "Eugeo is currently pursuing a PhD in Chatbology. Hard worker!",
          "Don't ask about his neck, he's a little touchy about that..."
        ];
        var randy = Math.floor(Math.random()*descs.length);

        $("#top #desc").text(descs[randy]);

        $("#top #desc").css({
          "font-size": "20px",
          "margin-top": "-25px"
        });

      },function() {

        $(this).css({
          "opacity": "0.9"
        });

        $("#top #bot").css({
          "height": "400px"
        });

        $("#top #name").css({
          "font-size": "0px"
        });

        var descs = [
          "A somewhat intelligent being...",
          "Critics say he isn't truly intelligent...",
          "He'll answer anything! Really!",
          "Operates 24/7. What a hard worker!",
          "Planning to take over the world..",
          "That smiles never fades...",
          "Eugeo loves job. He loves it. He really does-",
          "'Sword Art Online? What's that?' Eugeo says.",
          "Aspires to be among the ranks of Siri and Deep Blue.",
          "Eugeo is currently pursuing a PhD in Chatbology. Hard worker!",
          "Don't ask about his neck, he's a little touchy about that..."
        ];
        var randy = Math.floor(Math.random()*descs.length);

        $("#top #desc").css({
          "font-size": "0px",
          "margin-top": "0px"
        });

      });
    </script>

</html>
