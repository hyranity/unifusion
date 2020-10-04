<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>UniFusion :: My Classes</title>
        <link rel="stylesheet" href="CSS/myClasses.css">
    </head>
    <body>
        <div id='container'>

                <div id='navbar'>
                    <a href='Home' id='scaffold'>Scaffold</a>
                    <a href='MyClasses' class='link' id='currentLink'>Dashboard</a>
                    <a href='AccountDetails' class='link'>Account</a>
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
