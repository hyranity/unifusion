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
        <div id='bot' onclick='location.href = "Chatbot"'>
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
                        <a class='text'>This is an e-learning platform like no other. You can create or join classes with a few clicks. Every functionality is integrated with one another, providing an easy cohesion of modules to ensure a seamless education experience.</a>
                    </div>
                </div>

                <div class='item'>
                    <div class='details' id='right'>
                        <a class='text'>Scaffold employs a flexible educational structure of class, course, programme and institution, simplified for you to use. You can start small from a simple class, or scale up to a complicated institution that spans multiple programmes. No more headaches!</a>
                    </div>
                    <div class='image' >
                        <img src='https://i.imgur.com/vAkuzbA.png' style="width: 300px;">
                    </div>
                </div>

                <div class='item'>
                    <div class='image'>
                        <img src='https://i.imgur.com/pPWwSCC.png' style="width: 350px;">
                    </div>
                    <div class='details'>
                        <a class='text'>Whether you are planning to hold an online class or a real-world one, or even somewhere between, Scaffold will still be able to meet your requirements. For instance, class sessions can be scheduled in a real-world location or an online meeting. With such flexibility, you can adapt to even the most unpredictable times.</a>
                    </div>
                </div>

            <div class='item'>
                <div class='details' id='right'>
                    <a class='text'>Eugeo is a simple chatbot that makes your Scaffold life easier. You can ask him to show you any education level that you're participating, fetch a specific class, ask him to help you to get started in creating classes, and many more! By default, he shows you today's classes to get you up to speed with what's going on.</a>
                </div>
                <div class='image' >
                    <img src='https://i.imgur.com/d1YVL1Z.png' style="width: 350px;">
                </div>
            </div>
                
                <div class='item'>
                    <div class='image'>
                        <img src='https://i.imgur.com/rk9nKxs.png' style="width: 270px;">
                    </div>
                    <div class='details'>
                        <a class='text'>There's just so many things you can do in Scaffold! Whether it's making announcements, marking assignments, or scheduling class sessions, our many sophisticated features that integrate with the 4 education levels make this e-learning platform a "one-size-fits-all" system.</a>
                    </div>
                </div>

        </div>
    </body>
</html>
