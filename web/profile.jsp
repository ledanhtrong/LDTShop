<%-- 
    Document   : userInfor
    Created on : Jul 8, 2021, 6:22:15 PM
    Author     : wifil
--%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="ERRORS" value="${requestScope.ERRORS}"/>
<c:set var="USERNAME" value="${sessionScope.CURRENT_USER_USERNAME}"/>


<article class="content">
    <section class="profile-box">
        <h1>Profile</h1>

        <div class="row">
            <lable class="col-1" >Username </lable>
            <p class="col-2">${USERNAME}</p>
            <div class="col-3"></div>
        </div><br/>

        <form action="update">
            <input type="hidden" name="txtUser" value="${USERNAME}"/>
            <div class="row">
                <label class="col-1">Password </label>
                <input class="col-2" type="password" name="txtPass" value=""
                       placeholder="******" id="password" onkeyup="enableButton(event)"/>
                <input class="col-3" type="submit" name="btAction" value="Save change" disabled/>
            </div><br/>
            <c:if test="${not empty ERRORS.passwordLengthError}">
                <p class="error-message">${ERRORS.passwordLengthError}</p>
            </c:if>
            <c:if test="${not empty ERRORS.passwordFormatError}">
                <p class="error-message">${ERRORS.passwordFormatError}</p>
            </c:if>
            <c:if test="${not empty ERRORS.confirmNotMatched}">
                <p class="error-message">${ERRORS.confirmNotMatched}</p>
            </c:if>
            <div class="confirm row">
                <label class="col-1">Confirm </label>
                <input class="col-2" type="password" name="txtConfirm" value=""/>
                <div class="col-3"></div>
            </div><br/>
        </form>

        <form action="update">
            <input type="hidden" name="txtUser" value="${USERNAME}"/>
            <div class="row">
                <label class="col-1">Full name </label>
                <input class="col-2" type="text" name="txtFullname" value="${param.txtFullname}" 
                       onkeyup="enableButton(event)"
                       placeholder="${sessionScope.CURRENT_USER_FULLNAME}"/>
                <input class="col-3" type="submit" name="btAction" value="Save change" disabled/>
            </div><br/>
            <c:if test="${not empty ERRORS.fullnameLengthError}">
                <p class="error-message">${ERRORS.fullnameLengthError}</p>
            </c:if>
        </form>

        <form action="update">
            <input type="hidden" name="txtUser" value="${USERNAME}"/>
            <div class="row">
                <label class="col-1">Phone </label>
                <input class="col-2" type="text" name="txtPhone" value="${param.txtPhone}" 
                       onkeyup="enableButton(event)" 
                       placeholder="${sessionScope.CURRENT_USER_PHONE}"/>
                <input class="col-3" type="submit" name="btAction" value="Save change" disabled/>
            </div><br/>
            <c:if test="${not empty ERRORS.phoneFormatErr}">
                <p class="error-message">${ERRORS.phoneFormatErr}</p>
            </c:if>
        </form>

        <form action="update">
            <input type="hidden" name="txtUser" value="${USERNAME}"/>
            <div class="row">
                <label class="col-1">Address </label>
                <input class="col-2" type="text" name="txtAddress" value="${param.txtAddress}" 
                       onkeyup="enableButton(event)"
                       placeholder="${sessionScope.CURRENT_USER_ADDRESS}"/>
                <input class="col-3" type="submit" name="btAction" value="Save change" disabled/>
            </div><br/>
            <c:if test="${not empty ERRORS.addressLengthErr}">
                <p class="error-message">${ERRORS.addressLengthErr}</p>
            </c:if>
        </form>

    </section>
</article>