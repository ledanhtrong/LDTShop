<%-- 
    Document   : viewCart
    Created on : Jul 6, 2021, 4:03:04 PM
    Author     : wifil
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>View Cart</title>
        <!--Css-->
        <link rel="stylesheet" type="text/css" href="./assets/css/viewCart.css"/>
        <link rel="stylesheet" type="text/css" href="./assets/css/header.css"/>
        <!-- Font awesome-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body>
        <!--Header-->
        <c:import url="header.jsp"/>

        <section class="content-area">
            
            <c:set var="PRODUCTS" value="${requestScope.CART_PRODUCT_LIST}"/>
            <article class="view-cart-area">
                <div class="head">
                    <h1>Your Cart (${sessionScope.NUMBER_PRODUCTS_IN_CART + 0})</h1>
                    <c:url var="processCheckoutURL" value="shopping">
                        <c:param name="btAction" value="Process checkout"/>
                    </c:url>
                    <c:if test="${not empty PRODUCTS}">
                        <a href="${processCheckoutURL}">Process to checkout</a>
                    </c:if>
                </div>

                <c:if test="${empty PRODUCTS}">
                    <p class="empty">Your cart is empty.</p>
                </c:if>

                <c:if test="${not empty PRODUCTS}">
                    <div class="row thead">
                        <div class="td">No.</div>
                        <div class="td">Id</div>
                        <div class="td">Name</div>
                        <div class="td">Price / Unit</div>
                        <div class="td">Quantity</div>
                        <div class="td">Total</div>
                        <div class="td">Action</div>
                    </div>
                    <c:set var="TOTAL_PRICE" value="${0}"/>
                    <c:forEach var="product" items="${PRODUCTS}" varStatus="counter">
                        <div class="row">
                            <div class="td">${counter.count}</div>
                            <div class="td">${product.id}</div>
                            <div class="td">${product.name}</div>
                            <div class="td">${product.price}đ / ${product.unit}</div>
                            <div class="td">${product.quantity}</div>
                            <div class="td">${product.price * product.quantity}đ</div>
                            <form class="td" action="shopping" method="POST">
                                <input type="hidden" name="txtId" value="${product.id}"/>
                                <input class="remove-button" type="submit" name="btAction" value="Remove"/>
                            </form>
                        </div>
                        <c:set var="TOTAL_PRICE" value="${TOTAL_PRICE + product.price*product.quantity}"/>
                    </c:forEach>
                    <div class="last-row">
                        <div class="td">
                            Subtotal (${sessionScope.NUMBER_PRODUCTS_IN_CART + 0} items): ${TOTAL_PRICE}đ
                        </div>
                    </div>
                </c:if>
            </article>
        </section>
    </body>
</html>
