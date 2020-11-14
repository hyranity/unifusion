<%@page import="Util.Errors"%>
<%@page import="Util.Quick"%>
<%@page import="Models.Users"%>
<%@page import="Models.Users"%>
<%@page import="Util.Server"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Grade Submission</title>
        <link rel="stylesheet" href="CSS/gradeSubmission.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>

    <body>
        <div id='bot' onclick='location.href="Chatbot"'>
            <img id='eugeo' src='https://www.flaticon.com/svg/static/icons/svg/3398/3398640.svg'>
            <a id='desc'>Need help?<br><span>Eugeo's here!</span></a>
        </div>
        
        <form id='container' action='PerformGradeSubmission'>

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
                    <img id='icon' src='${icon}'>
                    <div id='text'>
                        <a id='subheading'>${subheading}</a>
                        <a id='heading'>Grade Submission</a>
                    </div>
                </div>
            </div>
                        
                          


            <div id='content'>

                <!-- To store assignment id and class code -->
                <input type="hidden" value="${id}" name="id"/>
                <input type="hidden" value="${code}" name="code"/>
                <input type="hidden" value="${submissionId}" name="submission"/>

                <div id='header'>
                    <a id='assignmentId'>${assignment.getComponentid()}</a>
                    <a id='assignmentTitle'>${assignment.getTitle()}</a>
                </div>

                <a id='error' style='margin-bottom: 50px;'><%out.print(Errors.requestSimple(session));%></a>
                <div id='details'>

                    <div id='poster'>
                        <img id='posterIcon' src='${studentIcon}'>
                        <div id='posterDetails'>
                            <a id='posterRole'>MEMBER</a>
                            <a href='#' id='posterName'>${student.getName()}</a>
                        </div>
                    </div>

                    <div class='detail'>
                        <a class='label'>SUBMITTED</a>
                        <a class='value'>${dateSubmitted}</a>
                        <a class='subvalue'>${timeSubmitted}</a>
                    </div>

                </div>

                <a class='label' style='margin-left: -80px;'>Total Marks</a>
                <input class='number' id='marks-input' type='number' name='marks' placeholder='eg. 100' value="${submission.getMarks()}">
            
                <a class='label' style='margin-left: -400px;'>Tutor's remarks</a>
                <textarea class="textarea" id='remarks-input' name='remarks' placeholder='What is this assignment about?'>${submission.getRemarks()}</textarea>

                <div id='buttons'>
                    <input id='confirm-button' class='button' type='submit' value='${alreadyGraded ? "Regrade" : "Grade" }!'>
                </div>

            </div>

          </div>

        </form>

    </body>


</html>

