<%-- 
    Document   : nav
    Created on : Jul 8, 2021, 6:28:17 PM
    Author     : wifil
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="USERNAME" value="${sessionScope.CURRENT_USER_USERNAME}"/>
<c:set var="FULLNAME" value="${sessionScope.CURRENT_USER_FULLNAME}"/>

<header>
    <!--Header left-->
    <nav class="nav-left">
        <!--Logo-->
        <img class="logo" src="./assets/img/logo.png" alt="logo"/>
        
        <a class="link home" href="front">Home</a>
        <a class="link shop" href="shopping">Shop</a>
        <c:url var="viewCartURL" value="shopping">
            <c:param name="btAction" value="View Cart"/>
        </c:url>
        <a class="link cart" href="${viewCartURL}">
            <i class="fa fa-shopping-cart" aria-hidden="true"></i>
            <p class="products-number">(${sessionScope.NUMBER_PRODUCTS_IN_CART + 0}) cart</p>
        </a>
    </nav>


    <!--Header right-->
    <c:if test="${empty USERNAME}">
        <nav class="nav-right not-login">
            <a class="link" href="loginHTML">Login</a>
            <a class="link" href="signupHTML">Sign up</a>

        </nav>
    </c:if>
    <c:if test="${not empty USERNAME}">
        <nav class="nav-right logged-in">
            <div class="welcome-user">
                Hello, ${FULLNAME}
                <i class="fa  fa-chevron-down user-icon" aria-hidden="true"></i>
            </div>
            <nav class="subnav">
                <a class="link" href="dashboard">Dashboard</a>
                <a class="link" href="logout">Logout</a>
            </nav>
        </nav>
    </c:if>

</header>