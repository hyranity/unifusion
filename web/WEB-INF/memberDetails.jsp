<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Member List</title>
        <link rel="stylesheet" href="CSS/memberDetails.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>
    
    <body>
      <div id='container'>

        <div id='navbar'>
          <a href='#' id='back'>&lt; <span>Back</span></a>
          <a href='#' id='scaffold'>Scaffold</a>
          <a href='#' class='link'>Dashboard</a>
          <a href='#' class='link'>Account</a>
        </div>

        <div id='top'>
          <div id='topOverlay'></div>
          <div id='info'>
            <img id='icon' src='${icon}'>
            <div id='text'>
              <a id='subheading'>${subheading}</a>
              <a id='heading'>Member Details</a>
            </div>
          </div>
        </div>

        <div id='content' action=''>

          <div id='header'>
            <img id='memberIcon' src='${userIcon}'>
            <div id='memberDetails'>
              <a id='memberId'>${member.getParticipantid().getParticipantid()}</a>
              <a id='memberName'>${member.getParticipantid().getUserid().getName()}</a>
            </div>
          </div
          
           <a id='error' style='margin-bottom: 50px;'><%out.print(Errors.requestSimple(session));%></a>

          <div id='details'>

            ${submissions}

            ${cgpa}

            ${grade}

          </div>

          <form id='form' action='PerformUpdateScores' display:none;>
            <a id='form-desc'>Make changes to this member's<br>CGPA/Grade using the form below:</a>
            <!-- Hidden fields -->
            <input type="hidden" name="id" value="${id}"/>
            <input type="hidden" name="type" value="${type}"/>
            <input type="hidden" name="memberId" value="${memberId}"/>
            
            ${cgpaForm}
            ${gradeForm}
            <input id='save-button' class='button' type='submit' value='Save!'>
          </form>

        </div>

      </div>

    </body>


</html>
