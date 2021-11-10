<%-- 
    Document   : index
    Created on : Jul 2, 2021, 5:55:21 PM
    Author     : wifil
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Home</title>

        <!-- Css -->
        <link rel="stylesheet" type="text/css" href="./assets/css/home.css"/>
        <link rel="stylesheet" type="text/css" href="./assets/css/header.css"/>
        <!-- Font awesome-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body>
        <!--Header-->
        <c:import url="header.jsp"/>

        <article class="content-area">
            <section class="content">
                <div class="welcome-message">
                    <h1>Welcome to online shop</h1>
                    <a class="shopping-link" href="shopping">Shopping now</a>
                </div>
            </section>
        </article>
    </body>
</html>
