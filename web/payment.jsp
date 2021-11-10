<%-- 
    Document   : payment
    Created on : Jul 10, 2021, 7:39:58 PM
    Author     : wifil
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<c:set var="IS_SUCCESS" value="${requestScope.IS_SUCCESSFUL_PAYMENT}"/>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <c:if test="${IS_SUCCESS}">
            <meta http-equiv="refresh" content="5;shopping">
        </c:if>
        <title>Payment</title>

        <!-- Css -->
        <link rel="stylesheet" type="text/css" href="./assets/css/header.css"/>
        <link rel="stylesheet" type="text/css" href="./assets/css/payment.css"/>
        <!-- Font awesome-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body>
        <!--Header-->
        <c:import url="header.jsp"/>

        <article class="content-area">
            <c:set var="ORDER" value="${sessionScope.CURRENT_USER_ORDER}"/>
            <c:if test="${not empty ORDER}">
                <section class="order-area">
                    <h1>Please confirm your Order</h1>
                    <div class="deliver-detail">
                        <h2>Delivery Information:</h2>
                        <p class="information">Buyer: ${ORDER.buyer}</p>
                        <p class="information">Receiver: ${ORDER.receiver}</p>
                        <p class="information">Delivery to: ${ORDER.address}</p>
                        <p class="information">Contact phone: ${ORDER.phone}</p>
                        <div class="buttons">
                            <a class="confirm" href="processPayment">Confirm and Buy</a>    
                            <a class="cancel" href="shopping">Cancel</a>    
                        </div>
                    </div>
                    <div class="details-table">
                        <h2>Buying products:</h2>
                        <div class="row thead">
                            <div class="td">No.</div>
                            <div class="td">Name</div>
                            <div class="td">Quantity</div>
                            <div class="td">Price</div>
                            <div class="td">Total</div>
                        </div>
                        <c:set var="SUBTOTAL" value="${0}"/>
                        <c:forEach var="detail" items="${ORDER.details}" varStatus="counter">
                            <div class="row">
                                <div class="td">${counter.count}</div>
                                <div class="td">${detail.name}</div>
                                <div class="td">${detail.quantity}</div>
                                <div class="td">${detail.price} / ${detail.unit}</div>
                                <div class="td">${detail.quantity * detail.price}</div>
                            </div>
                            <c:set var="SUBTOTAL" value="${SUBTOTAL + detail.quantity * detail.price}"/>
                        </c:forEach>
                        <div class="row">
                            <div class="td">Subtotal: ${SUBTOTAL}</div>
                        </div>
                    </div>
                </section>
            </c:if>


            <c:if test="${not empty IS_SUCCESS}">
                <section class="pop-up-background">
                    <section class="pop-up">
                        <c:if test="${IS_SUCCESS}">
                            <p class="information">
                                We are processing your order for delivery.
                                It will take for at most a week. Thank you for buying.
                            </p>
                        </c:if>
                        <c:if test="${not IS_SUCCESS}">
                            <p class="information">
                                Something went wrong. Your order is not valid.
                                Please contact us for more information.
                            </p>
                        </c:if>
                        <p class="notice">The page is redirect after 5 seconds.<p>
                    </section>

                </section>
            </c:if>


        </article>

    </body>
</body>
</html>
