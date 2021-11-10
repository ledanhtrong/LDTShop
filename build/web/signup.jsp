<%-- 
    Document   : signup
    Created on : Jul 3, 2021, 5:58:49 PM
    Author     : wifil
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Sign Up</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Css -->
        <link rel="stylesheet" type="text/css" href="./assets/css/signup.css"/>
    </head>
    <body>
        <section class="content">
            <!--Logo-->
            <img class="logo" src="./assets/img/logo.png" alt="logo"/>

            <div class="signup-box">
                <h1>Sign up</h1>

                <form action="signup">
                    <div class="control">
                        <c:set var="ERRORS" value="${requestScope.ERRORS}"/>

                        Username <input type="text" name="txtUser" value="${param.txtUser}"/><br/>
                        <c:if test="${not empty ERRORS.usernameLengthError}">
                            <p class="error-message">${ERRORS.usernameLengthError}</p>
                        </c:if>
                        <c:if test="${not empty ERRORS.usernameFormatError}">
                            <p class="error-message">${ERRORS.usernameFormatError}</p>
                        </c:if>

                        Password <input type="password" name="txtPass" value=""/><br/>
                        <c:if test="${not empty ERRORS.passwordLengthError}">
                            <p class="error-message">${ERRORS.passwordLengthError}</p>
                        </c:if>
                        <c:if test="${not empty ERRORS.passwordFormatError}">
                            <p class="error-message">${ERRORS.passwordFormatError}</p>
                        </c:if>

                        Confirm <input type="password" name="txtConfirm" value=""/><br/>
                        <c:if test="${not empty ERRORS.confirmNotMatched}">
                            <p class="error-message">${ERRORS.confirmNotMatched}</p>
                        </c:if>

                        Full Name <input type="text" name="txtFullname" value="${param.txtFullname}"/><br/>
                        <c:if test="${not empty ERRORS.fullnameLengthError}">
                            <p class="error-message">${ERRORS.fullnameLengthError}</p>
                        </c:if>

                        Phone <input type="text" name="txtPhone" value="${param.txtPhone}"/><br/>
                        <c:if test="${not empty ERRORS.phoneFormatErr}">
                            <p class="error-message">${ERRORS.phoneFormatErr}</p>
                        </c:if>

                        Address <input type="text" name="txtAddress" value="${param.txtAddress}"/><br/>
                        <c:if test="${not empty ERRORS.addressLengthErr}">
                            <p class="error-message">${ERRORS.addressLengthErr}</p>
                        </c:if>

                    </div>

                    <div class="button">
                        <input type="submit" name="btAction" value="Create account"/><br/>
                    </div>

                    <c:if test="${not empty ERRORS.usernameIsExisted}">
                        <p class="error-message">${ERRORS.usernameIsExisted}</p>
                    </c:if>
                </form>

                <div class="links">
                    <a class="bottom-link" href="shopping.jsp">Shopping now!</a><br/>
                    <a class="bottom-link" href="login.html">Already has an account? Login!</a>
                </div>
            </div>
        </section>
    </body>
</html>

