<%-- 
    Document   : error
    Created on : Jul 3, 2021, 10:43:01 AM
    Author     : wifil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1>Error!</h1>
        <p>${requestScope.ERROR_MESSAGE}</p>
        <a href="front">Back to home page</a>
    </body>
</html>
