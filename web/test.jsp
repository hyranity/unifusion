<%-- 
    Document   : test
    Created on : May 6, 2020, 7:50:39 PM
    Author     : mast3
--%>

<%@page import="Models.Student"%>
<%@page import="Util.DB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><%=((Student) session.getAttribute("stud")).getName() %></h1>
    </body>
</html>
