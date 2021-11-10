<%-- 
    Document   : dashboard
    Created on : Jul 4, 2021, 10:13:47 AM
    Author     : wifil
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>

        <!--Css-->
        <link rel="stylesheet" type="text/css" href="./assets/css/dashboard.css"/>
        <link rel="stylesheet" type="text/css" href="./assets/css/header.css"/>
        <!-- Font awesome-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <!--Javascript-->
        <script type="text/javascript" src="./assets/js/dashboard.js?"></script>
    </head>
    <body>
        <c:set var="ROLE" value="${sessionScope.CURRENT_USER_ROLE}"/>
        <c:set var="ADMIN_ROLE" value="${1}"/>

        <c:set var="KEY" value="${requestScope.DASHBOARD_KEY}" />
        <c:set var="PROFILE_KEY" value="1"/>
        <c:set var="ACCOUNT_MGMT_KEY" value="2"/>
        <c:set var="PRODUCT_MGMT_KEY" value="3"/>

        <c:if var="IS_PROFILE" test="${empty KEY || KEY eq PROFILE_KEY}"/>
        <c:if var="IS_ACCOUNT_MGMT" test="${KEY eq ACCOUNT_MGMT_KEY}"/>
        <c:if var="IS_PRODUCT_MGMT" test="${KEY eq PRODUCT_MGMT_KEY}"/>

        <!--Header-->
        <c:import url="header.jsp"/>

        <section class="content-area">
            <nav>
                <form action="dashboard">
                    <input type="submit" name="btAction" value="Profile"
                           <c:if test="${IS_PROFILE}">class="active"</c:if>/>
                    <c:if test="${ROLE >= ADMIN_ROLE}">
                        <input type="submit" name="btAction" value="Account Management"
                               <c:if test="${IS_ACCOUNT_MGMT}">class="active"</c:if>/>
                               <input type="submit" name="btAction" value="Product Information"
                               <c:if test="${IS_PRODUCT_MGMT}">class="active"</c:if>/>
                    </c:if>
                </form>
            </nav>

            <c:if test="${IS_PROFILE}">
                <c:import url="profile.jsp"/>
            </c:if>
            <c:if test="${IS_ACCOUNT_MGMT}">
                <c:import url="accountMgmt.jsp"/>
            </c:if>
            <c:if test="${IS_PRODUCT_MGMT}">
                <c:import url="productMgmt.jsp"/>
            </c:if>

        </section>
    </body>
</html>
