<%@page import="Util.Errors"%>
<%@page import="Models.Users"%>
<%@page import="Util.Quick"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0);
//prevents caching at the proxy server
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: Dashboard</title>
        <link rel="stylesheet" href="CSS/dashboard.css">
        <link rel="stylesheet" href="CSS/all.css">
    </head>
    <body>
        <div id='container'>

            <div id='navbar'>
                <a href='Home' id='scaffold'>Scaffold</a>
                <a href='Dashboard' class='link'>Dashboard</a>
                <a href='AccountDetails' class='link'>Account</a>
            </div>

            <div id='top'>
                <a id='heading'>Dashboard</a>
                <div id='actions'>
                    <a href='DashboardMenu'>Open menu</a> <!-- to change to href='DashboardMenu' -->
                </div>
            </div>

            <!--  To throw away all errors that may have redirected here-->
            <input type="hidden" value="<%out.print(Errors.requestSimple(session));%>"/>

            <div id='classes'>



                ${output}

                <!--
                
                programme
      
                <div class='programme'>

                  <div class='programme-details'>
                    <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                    <div class='details'>
                      <div class='top-details'>
                        <a class='id'>PO001</a>
                        <a class='tutor'>John Doe</a>
                      </div>
                      <a class='type'>PROGRAMME</a>
                      <a class='name'>RSD 3</a>
                      <a class='description'>Some programme</a>
                    </div>
                  </div>

                  <input type='checkbox' name='seeCourses' class='seeCourses' id='seeCourses_P0001' onclick='seeCourses("P0001")'>
                  <label class='seeCoursesLabel' id='seeCoursesLabel_PO001' for='seeCourses_P0001'>View courses</label>

                  <div class='courses' id='courses_P0001'>

                    course 1 in programme

                    <div class='course'>

                      <div class='course-details'>
                        <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                        <div class='details'>
                          <div class='top-details'>
                            <a class='id'>CO001</a>
                            <a class='tutor'>John Doe</a>
                          </div>
                          <a class='type'>COURSE</a>
                          <a class='name'>Computer Science</a>
                          <a class='description'>Bachelor-level course</a>
                        </div>
                      </div>

                      <input type='checkbox' name='seeClasses' class='seeClasses' id='seeClasses_CO001' onclick='seeClasses("CO001")'>
                      <label class='seeClassesLabel' id='seeClassesLabel_CO001' for='seeClasses_CO001'>View classes</label>

                      <div class='classes' id='classes_CO001'>

                        <div class='row'>

                          <div class='class' onclick="location.href='#';">
                            <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                            <div class='details'>
                              <div class='top-details'>
                                <a class='id'>C001</a>
                                <a class='tutor'>John Doe</a>
                              </div>
                              <a class='type'>CLASS</a>
                              <a class='name'>Math</a>
                              <a class='description'>Intermediate</a>
                            </div>
                          </div>

                          <div class='class' onclick="location.href='#';">
                            <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                            <div class='details'>
                              <div class='top-details'>
                                <a class='id'>C001</a>
                                <a class='tutor'>John Doe</a>
                              </div>
                              <a class='type'>CLASS</a>
                              <a class='name'>Math</a>
                              <a class='description'>Intermediate</a>
                            </div>
                          </div>

                        </div>

                      </div>

                    </div>

                    end of course 1 in programme

                    course 2 in programme

                    <div class='course'>

                      <div class='course-details'>
                        <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                        <div class='details'>
                          <div class='top-details'>
                            <a class='id'>CO002</a>
                            <a class='tutor'>John Doe</a>
                          </div>
                          <a class='type'>COURSE</a>
                          <a class='name'>Computer Science</a>
                          <a class='description'>Bachelor-level course</a>
                        </div>
                      </div>

                      <input type='checkbox' name='seeClasses' class='seeClasses' id='seeClasses_CO002' onclick='seeClasses("CO002")'>
                      <label class='seeClassesLabel' id='seeClassesLabel_CO002' for='seeClasses_CO002'>View classes</label>

                      <div class='classes' id='classes_CO002'>

                        <div class='row'>

                          <div class='class' onclick="location.href='#';">
                            <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                            <div class='details'>
                              <div class='top-details'>
                                <a class='id'>C001</a>
                                <a class='tutor'>John Doe</a>
                              </div>
                              <a class='type'>CLASS</a>
                              <a class='name'>Math</a>
                              <a class='description'>Intermediate</a>
                            </div>
                          </div>

                          <div class='class' onclick="location.href='#';">
                            <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                            <div class='details'>
                              <div class='top-details'>
                                <a class='id'>C001</a>
                                <a class='tutor'>John Doe</a>
                              </div>
                              <a class='type'>CLASS</a>
                              <a class='name'>Math</a>
                              <a class='description'>Intermediate</a>
                            </div>
                          </div>

                        </div>

                      </div>

                    </div>

                    end of course 2 in programme

                  </div>

                </div>

                end of programme

                course
                
               <div class='course'>

                    <div class='course-details'>
                        <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                        <div class='details'>
                            <div class='top-details'>
                                <a class='id'>CO00</a>
                                <a class='tutor'>John Doe</a>
                            </div>
                            <a class='type'>COURSE</a>
                            <a class='name'>Computer Science</a>
                            <a class='description'>Bachelor-level course</a>
                        </div>
                    </div>

                    <input type='checkbox' name='seeClasses' class='seeClasses' id='seeClasses_CO00' onclick='seeClasses("CO00")'>
                    <label class='seeClassesLabel' id='seeClassesLabel_CO00' for='seeClasses_CO00'>View classes</label>

                    <div class='classes' id='classes_CO00'>

                        <div class='row'>

                            <div class='class' onclick="location.href = '#';">
                                <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                                <div class='details'>
                                    <div class='top-details'>
                                        <a class='id'>C001</a>
                                        <a class='tutor'>John Doe</a>
                                    </div>
                                    <a class='type'>CLASS</a>
                                    <a class='name'>Math</a>
                                    <a class='description'>Intermediate</a>
                                </div>
                            </div>

                            <div class='class' onclick="location.href = '#';">
                                <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                                <div class='details'>
                                    <div class='top-details'>
                                        <a class='id'>C001</a>
                                        <a class='tutor'>John Doe</a>
                                    </div>
                                    <a class='type'>CLASS</a>
                                    <a class='name'>Math</a>
                                    <a class='description'>Intermediate</a>
                                </div>
                            </div>

                        </div>
                        
                        <div class='row'>

                            <div class='class' onclick="location.href = '#';">
                                <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                                <div class='details'>
                                    <div class='top-details'>
                                        <a class='id'>C001</a>
                                        <a class='tutor'>John Doe</a>
                                    </div>
                                    <a class='type'>CLASS</a>
                                    <a class='name'>Math</a>
                                    <a class='description'>Intermediate</a>
                                </div>
                            </div>
                        </div>

                    </div>

                </div>

                end of course

                row 1

    q          <div class='row'>

                     row 1, class 1 

                    <div class='class' id='orange' onclick="location.href = '#';">
                        <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                        <div class='details'>
                            <div class='top-details'>
                                <a class='id'>C001</a>
                                <a class='tutor'>John Doe</a>
                            </div>
                            <a class='type'>CLASS</a>
                            <a class='name'>Math</a>
                            <a class='description'>Intermediate arithmetic</a>
                        </div>
                    </div>

                     row 1, class 2 

                    <div class='class' onclick="location.href = '#';">
                        <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                        <div class='details'>
                            <div class='top-details'>
                                <a class='id'>C001</a>
                                <a class='tutor'>John Doe</a>
                            </div>
                            <a class='type'>CLASS</a>
                            <a class='name'>Math</a>
                            <a class='description'>Intermediate</a>
                        </div>
                    </div>

                </div>

                 row 2 

                <div class='row'>

                     row 2, class 1 

                    <div class='class' onclick="location.href = '#';">
                        <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                        <div class='details'>
                            <div class='top-details'>
                                <a class='id'>C001</a>
                                <a class='tutor'>John Doe</a>
                            </div>
                            <a class='type'>CLASS</a>
                            <a class='name'>Math</a>
                            <a class='description'>Intermediate</a>
                        </div>
                    </div>

                     row 2, class 2 

                    <div class='class' id='green' onclick="location.href = '#';">
                        <img class='icon' src='https://image.flaticon.com/icons/svg/3034/3034573.svg'>
                        <div class='details'>
                            <div class='top-details'>
                                <a class='id'>C001</a>
                                <a class='tutor'>John Doe</a>
                            </div>
                            <a class='type'>CLASS</a>
                            <a class='name'>Math</a>
                            <a class='description'>Intermediate arithmetic</a>
                        </div>
                    </div>

                </div>-->

            </div>

        </div>

        <script>
            function seeProgrammes(institutionId) {
                var checkbox = document.getElementById("seeProgrammes_" + institutionId);
                var programmes = document.getElementById("programmes_" + institutionId);
                if (checkbox.checked == true) {
                    programmes.style.display = "flex";
                    programmes.style.flexDirection = "column";
                } else {
                    programmes.style.display = "none";
                }
            }

            function seeCourses(programmeId) {
                var checkbox = document.getElementById("seeCourses_" + programmeId);
                var courses = document.getElementById("courses_" + programmeId);
                //alert(checkbox.checked);

                if (checkbox.checked == true) {
                    courses.style.display = "flex";
                    courses.style.flexDirection = "column";
                } else {
                    courses.style.display = "none";
                }
            }

            function seeClasses(courseId) {
                var checkbox = document.getElementById("seeClasses_" + courseId);
                var classes = document.getElementById("classes_" + courseId);
                //alert(checkbox.checked);

                if (checkbox.checked == true) {
                    classes.style.display = "flex";
                    classes.style.flexDirection = "column";
                } else {
                    classes.style.display = "none";
                }
            }
        </script>

    </body>
</html>
