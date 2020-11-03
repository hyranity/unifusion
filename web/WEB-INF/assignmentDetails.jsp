<%@page import="Util.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Assignment Details</title>
        <link rel="stylesheet" href="CSS/assignmentDetails.css">
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
                    <img id='icon' src='${icon}'>
                    <div id='text'>
                        <a id='subheading'>${subheading}</a>
                        <a id='heading'>Assignment Details</a>
                    </div>
                </div>
            </div>

            <div id='content' action=''>

                <div id='header'>
                    <a id='assignmentId'>${assignment.getComponentid()}</a>
                    <a id='assignmentTitle'>${assignment.getTitle()}</a>
                </div>

                <a id='error' style='margin-bottom: 20px;'><%out.print(Errors.requestSimple(session));%></a>

                <div id='details'>

                    <div class='detail'>
                        <a class='label'>Deadline</a>
                        <a class='value'>${deadline}</a>
                    </div>

                    <div class='detail'>
                        <a class='label'>Total marks</a>
                        <a class='value'>${assignment.getTotalmarks()}</a>
                    </div>

                </div>

                <a id='message'>${assignment.getDetails()}</a>

                <div id='attachments'>

                    ${fileStr}

                    <!--                    <div class='attachment'>
                                            <img class='icon' src='https://icons.iconarchive.com/icons/pelfusion/flat-file-type/512/doc-icon.png'>
                                            <a href='#' class='name'>sampleFile.doc</a>
                                        </div>
                                        <div class='attachment'>
                                            <img class='icon' src='https://how-to.aimms.com/_images/exe-file-icon-68130.png'>
                                            <a href='#' class='name'>sampleProgram.exe</a>
                                        </div>-->
                </div>

                <div id='buttons'>
                    ${viewSubmissionsBt}
                    ${editBt}
                    ${viewMySubmission}
                    ${submitBt}
                    ${deleteBt}
                </div>

            </div>

        </div>

    </body>

</html>
