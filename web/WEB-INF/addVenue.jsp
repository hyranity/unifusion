<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Create a Venue</title>
        <link rel="stylesheet" href="CSS/addVenue.css">
        <link rel="stylesheet" href="CSS/all.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
                <a href='Dashboard' class='link'>Dashboard</a>
                <a href='AccountDetails' class='link'>Account</a>
                <a href='Chatbot' class='link'>Chatbot</a>
            </div>

            <div id='top'>
                <div id='topOverlay'></div>
                <div id='info'>
                    <img id='icon' src='https://cdn.donmai.us/original/6f/90/__buratei_marii_joshiraku_drawn_by_taka_takahirokun__6f90a4d95e72eb6d5d0659af3a6efb9d.jpg'>
                    <div id='text'>
                        <a id='subheading'>${subheading}</a>
                        <a id='heading'>Create Venue</a>
                    </div>
                </div>
            </div>

            <a id='error' style='z-index: 1;'><%out.print(Errors.requestSimple(session));%></a>

            <form id='form' action='PerformAddVenue'>

                <!-- hidden fields -->
                <input type="hidden" name="id" value="${id}"/>

                <div id='input'>

                    <a class='label'>Name</a>
                    <input class='textbox' id='name-input' type='text' name='name' placeholder='eg. DK ABA'>

                    <a class='label'>Location</a>
                    <textarea class="textarea" id='location-input' name='location' placeholder='eg. Alien Insitute Main Campus, Area 51, Nevada'></textarea>

                    <div id='bottom'>
                      <div id='left'>
                        <a class='label'>Capacity</a>
                        <input class='number' id='capacity-input' type='number' name='capacity' placeholder='eg. 150'>
                      </div>
                      <div id='right'>
                        <a class='label' id='name'>Is this venue currently active?</a>
                        <input type='checkbox' class='checkbox' id='isActive' name='isActive'>
                        <label class='checkboxLabel' for='isActive' id='isActiveLabel'>
                          <div class='slider'></div>
                        </label>
                      </div>
                    </div>

                </div>

                <input id='create-button' type='submit' value='Create!'>

            </form>

        </div>

    </body>

<script src="JS/validator.js"></script>
<script src="JS/addVenue.js"></script>
    
</html>
