<%-- 
    Document   : login
    Created on : Jun 28, 2021, 4:30:43 PM
    Author     : wifil
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Css -->
        <link rel="stylesheet" type="text/css" href="./assets/css/login.css"/>
    </head>
    <body>

        <section class="content">
            <!--Logo-->
            <img class="logo" src="./assets/img/logo.png" alt="logo"/>

            <div class="login-box">
                <h1>Login</h1>

                <form action="login" method="POST">
                    <div class="control">
                        Username <input type="text" name="txtUser" value=""/><br/>
                        Password <input type="password" name="txtPass" value=""/><br/>
                    </div>
                    <c:if test="${not empty requestScope.LOGIN_FAILED}">
                        <p class="error-message">${requestScope.LOGIN_FAILED}</p>
                    </c:if>
                    <div class="button">
                        <input type="submit" name="btAction" value="Login">
                    </div>
                    <c:if test="${not empty requestScope.ERROR_MESSAGE}">
                        <p class="error-message">${requestScope.ERROR_MESSAGE}</p>
                    </c:if>
                </form>

                <div class="links">
                    <a class="bottom-link" href="shopping">Shopping now!</a><br/>
                    <a class="bottom-link" href="signupHTML">Don't have an account? Sign up!</a>
                </div>
            </div>
        </section>
    </body>
</html>
