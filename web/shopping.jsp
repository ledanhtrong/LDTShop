<%-- 
    Document   : shopping
    Created on : Jul 5, 2021, 5:34:39 PM
    Audivor     : wifil
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Online Shop</title>
        <!--Css-->
        <link rel="stylesheet" type="text/css" href="./assets/css/shopping.css"/>
        <link rel="stylesheet" type="text/css" href="./assets/css/header.css"/>
        <!-- Font awesome-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    </head>
    <body>
        <!--Header-->
        <c:import url="header.jsp"/>

        <section class="content-area">

            <!--Search area-->
            <article class="search-area">
                <c:set var="DEFAULT_TYPE" value="All"/>
                <form action="shopping">
                    <!--Type select-->
                    <section class="select-type">
                        <label for="select">Type:</label>
                        <select name="txtTypeId" id="select">
                            <option value="${DEFAULT_TYPE}">All</option>
                            <c:if test="${not empty requestScope.TYPES}">
                                <c:forEach var="TYPE" items="${requestScope.TYPES}">
                                    <c:if test="${param.txtTypeId eq TYPE.id}">
                                        <option value="${TYPE.id}" selected>${TYPE.name}</option>
                                    </c:if>
                                    <c:if test="${param.txtTypeId ne TYPE.id}">
                                        <option value="${TYPE.id}">${TYPE.name}</option>
                                    </c:if>    
                                </c:forEach>
                            </c:if>
                        </select>
                    </section>

                    <!--Range select-->
                    <section class="input-range">
                        <p>Range (price):</p>
                        <input type="text" name="txtMin" value="${requestScope.MIN}" placeholder="Min">
                        <input type="text" name="txtMax" value="${requestScope.MAX}" placeholder="Max">
                    </section>

                    <div class="button">
                        <input type="submit" name="btAction" value="Apply">
                    </div>
                </form>
            </article>

            <article class="shopping-cart">
                <c:url var="viewCartURL" value="shopping">
                    <c:param name="btAction" value="View Cart"/>
                </c:url>
                <a href="${viewCartURL}">
                    <i class="fa fa-shopping-cart" aria-hidden="true"></i>
                    <p class="products-number">(${sessionScope.NUMBER_PRODUCTS_IN_CART + 0})</p>
                </a>
            </article>
                
            <!--Display product-->
            <article class="content">
                <section class="product-box">
                    <c:set var="PRODUCTS" value="${requestScope.SEARCH_RESULT}"/>
                    <c:if test="${not empty PRODUCTS}">
                        <c:forEach var="product" items="${PRODUCTS}">
                            <div class="product">
                                <img src="${product.image}" alt="${product.description}"
                                     height="200px" width="300px"/>
                                <div class="product-content">
                                    <h2>${product.name} - ${product.description}</h2>
                                    <p class="price">${product.price}đ / ${product.unit}</p>
                                    <form action="shopping">
                                        <input type="hidden" name="txtTypeId" value="${param.txtTypeId}"/>
                                        <input type="hidden" name="txtMin" value="${param.txtMin}"/>
                                        <input type="hidden" name="txtMax" value="${param.txtMax}"/>
                                        <input type="hidden" name="txtId" value="${product.id}"/>
                                        <input class="button" type="submit" name="btAction" value="Add To Cart"/>
                                        <p class="quantity">${product.quantity} items available</p>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </c:if>
                </section>
            </article>
        </section>
    </body>
</html>
