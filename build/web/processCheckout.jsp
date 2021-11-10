<%-- 
    Document   : checkOut.jsp
    Created on : Jul 7, 2021, 11:46:20 AM
    Author     : wifil
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Process Shipping</title>
        
        <!--Css-->
        <link rel="stylesheet" type="text/css" href="./assets/css/processCheckout.css"/>
        <link rel="stylesheet" type="text/css" href="./assets/css/header.css"/>
        <!-- Font awesome-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body>
        <!--Header-->
        <c:import url="header.jsp"/>

        <article class="content-area">
            <c:set var="FULLNAME" value="${sessionScope.CURRENT_USER_FULLNAME}"/>
            <c:set var="PHONE" value="${sessionScope.CURRENT_USER_PHONE}"/>
            <c:set var="ADDRESS" value="${sessionScope.CURRENT_USER_ADDRESS}"/>
            
            <section class="process-checkout">
                <h1>Select your shipping address</h1>
                <form action="shopping">
                    <div class="control">
                        Address:
                        <input type="text" name="txtAddress" value="${param.txtAddress}" placeholder="${ADDRESS}"/>
                        <c:if test="${not empty requestScope.ERRORS.addressLengthErr}">
                            <p class="error-message">${requestScope.ERRORS.addressLengthErr}<p>
                        </c:if>
                        
                        Receiver's full name:
                        <input type="text" name="txtReceiver" value="${param.txtReceiver}" placeholder="${FULLNAME}"/>
                        <c:if test="${not empty requestScope.ERRORS.fullnameLengthErr}">
                            <p class="error-message">${requestScope.ERRORS.fullnameLengthErr}<p>
                        </c:if>
                                
                        Phone:
                        <input type="text" name="txtPhone" value="${param.txtPhone}" placeholder="${PHONE}"/>
                        <c:if test="${not empty requestScope.ERRORS.phoneFormatErr}">
                            <p class="error-message">${requestScope.ERRORS.phoneFormatErr}<p>
                        </c:if>
                    </div>
                    <div class="button">
                        <input type="submit" name="btAction" value="Delivery to this address">
                    </div>
                </form>
            </section>
                    
        </article>
    </body>
</html>
