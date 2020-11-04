<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: My Classes</title>
        <link rel="stylesheet" href="CSS/myClasses.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>
    <body>
        <div id='bot' onclick='location.href="Chatbot"'>
            <img id='eugeo' src='https://www.flaticon.com/svg/static/icons/svg/3398/3398640.svg'>
            <a id='desc'>Need help?<br><span>Eugeo's here!</span></a>
        </div>
        
        <div id='container'>

            <div id='navbar'>
                <a href='Dashboard' id='back'>&lt; <span>Back</span></a>
                <a href='Home' id='scaffold'>Scaffold</a>
                <a href='Dashboard' class='link' id='currentLink'>Dashboard</a>
                <a href='AccountDetails' class='link'>Account</a>
                <a href='Chatbot' class='link'>Chatbot</a>
            </div>

            <div id='top'>
                <a id='heading'>Dashboard</a>
                <div id='actions'>
                    <a href='dashboardMenu.jsp'>Open menu</a> <!-- to change to href='DashboardMenu' -->
                </div>
            </div>

            <div id='classes'>

                <!-- row 1 -->

               

                    <!-- row 1, class 1 -->
                   ${output}

                    

          

        </div>
    </body>
</html>
