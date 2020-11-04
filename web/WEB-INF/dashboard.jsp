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
                    <a href='DashboardMenu'>Open menu</a> <!-- to change to href='DashboardMenu' -->
                </div>
            </div>
            
            <div id='search'>
                <select class='dropdown' id='typeAttribute' style='width: 150px;'>
                    <option value='all'>All</option>
                    <option value='class'>Class</option>
                    <option value='course'>Course</option>
                    <option value='programme'>Programme</option>
                    <option value='institution'>Institution</option>
                </select>
                <select class='dropdown' id='searchAttribute'>
                    <option value='id'>ID</option>
                    <option value='name'>Name</option>
                </select>
                <a class='dropdownLabel'>Click to view options</a>
                <input class='textbox' type='text' id='searchTextbox' placeholder='Search...'>
                <a class='button' id='search-button' onclick='searchList()'>></a>
                <a class='button' id='refresh-button' href='Dashboard'>Refresh</a>
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
                
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

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

            function searchList() {
                $("#search-button").css({
                   "display": "none" 
                });
                
                $("#refresh-button").css({
                    "background-color": "rgba(250, 226, 184, 0.5)",
                    "box-shadow": "0px 0px 50px rgba(250, 226, 184, 0.25)"
                });
                
                $(".dropdownLabel").css({
                    "margin-left": "-390px"
                });
                
                var query = document.getElementById("searchTextbox").value.trim();
                var searchAttribute = document.getElementById("searchAttribute").value;
                var typeAttribute = document.getElementById("typeAttribute").value;
                
                if (query === "" && typeAttribute === "all") {
                    return;
                }
                
                var classes = document.getElementsByClassName('class');
                var courses = document.getElementsByClassName('course');
                var programmes = document.getElementsByClassName('programme');
                var institutions = document.getElementsByClassName('institution');
                
                var panels = [...classes, ...courses, ...programmes, ...institutions];
                
                $("#classes").html("");

                for (var i = 0; i < panels.length; i++) {
                    
                    var type = panels[i].className;
                    
                    var id = "";
                    var name = "";
                    var data = "";
                    
                    switch(type) {
                        case "class":
                            var id = panels[i].children[1].children[0].children[0].textContent;
                            var name = panels[i].children[1].children[2].textContent;
                            var data = "";
                            break;
                            
                        case "course":
                            var id = panels[i].children[0].children[1].children[0].children[0].textContent;
                            var name = panels[i].children[0].children[1].children[2].textContent;
                            var data = "";
                            break;
                        
                        case "programme":
                            var id = panels[i].children[0].children[1].children[0].children[0].textContent;
                            var name = panels[i].children[0].children[1].children[2].textContent;
                            var data = "";
                            break;
                            
                        case "institution":
                            var id = panels[i].children[0].children[1].children[0].children[0].textContent;
                            var name = panels[i].children[0].children[1].children[2].textContent;
                            var data = "";
                            break;
                    }
                    
                    if (typeAttribute === "all" || type.toLowerCase().includes(typeAttribute.toLowerCase())) {

                        if (searchAttribute === "id") {
                            data = id;
                        } else if (searchAttribute === "name") {
                            data = name;
                        } else {
                            data = id;
                        }

                        if (data.toLowerCase().includes(query.toLowerCase())) {
                            $("#classes").append(panels[i]);
                        } else {
                            //panels[i].style.display = "none";
                        }

                    } else {
                      //panels[i].style.display = "none";
                    }
                }

            }
            
        </script>

    </body>
</html>
