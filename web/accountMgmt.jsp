<%-- 
    Document   : accountManager
    Created on : Jul 8, 2021, 6:22:36 PM
    Author     : wifil
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<c:set var="ROLE" value="${sessionScope.CURRENT_USER_ROLE}"/>
<c:set var="USER_ROLE" value="${0}"/>
<c:set var="ADMIN_ROLE" value="${1}"/>
<c:set var="SUPER_ADMIN_ROLE" value="${2}"/>

<article class="content">
    <section class="account-mgmt">
        <h1>Account Management</h1>

        <form action="dashboard">
            <div class="control">
                <input type="text" name="txtSearchValue" value="${param.txtSearchValue}"
                       placeholder="Search.."/>
            </div>
            <div class="button">
                <input type="submit" name="btAction" value="Search">
                <c:if test="${ROLE > ADMIN_ROLE}">
                    <input type="submit" name="btAction" value="All Accounts"/>
                </c:if>
            </div>
        </form>

        <c:set var="ACCOUNTS" value="${requestScope.SEARCH_RESULT}"/>
        <c:if test="${not empty ACCOUNTS}">
            <div class="table">
                <div class="tr thead">
                    <div class="th">No.</div>
                    <div class="th">Username</div>
                    <div class="th">Full name</div>
                    <div class="th">Phone</div>
                    <div class="th">Address</div>
                    <div class="th">Role</div>
                    <div class="th">Manage</div>
                </div>
                <c:forEach var="account" items="${ACCOUNTS}" varStatus="counter">
                    <div class="tr">
                        <div class="td">${counter.count}</div>
                        <div class="td">${account.username}</div>
                        <div class="td">${account.fullname}</div>
                        <div class="td">${account.phone}</div>
                        <div class="td">${account.address}</div>
                        <div class="td">
                            <c:if test="${ROLE >= ADMIN_ROLE}">
                                <c:if test="${account.role >= ADMIN_ROLE}">Administrator</c:if>
                                <c:if test="${account.role < ADMIN_ROLE}">User</c:if>
                            </c:if>
                        </div>
                        <div class="td">
                            <c:if test="${ROLE > account.role}">
                                <c:url var="urlReWriting" value="dashboard">
                                    <c:param name="btAction" value="Process update"/>
                                    <c:param name="txtUser" value="${account.username}"/>
                                    <c:param name="txtSearchValue" value="${param.txtSearchValue}"/>
                                </c:url>
                                <a class="icon" href="${urlReWriting}">
                                    <i class="fa fa-cog" aria-hidden="true"></i>
                                </a>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <c:if test="${empty ACCOUNTS && not empty param.txtSearchValue}">
            <p class="message">Your search does not match any account.</p>
        </c:if>
    </section>

    <section class="process-update">
        <c:set var="UPDATED_ACCOUNT" value="${requestScope.PROCESSING_UPDATED_ACCOUNT}"/>
        <c:if test="${not empty UPDATED_ACCOUNT}">
            <c:set var="ERRORS" value="${requestScope.ERRORS}"/>

            <article class="updated-account">
                <div class="row">
                    <lable class="col-1" >Username </lable>
                    <p class="col-2">${UPDATED_ACCOUNT.username}</p>
                    <div class="col-3"></div>
                </div><br/>

                <form action="update">
                    <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}"/>
                    <input type="hidden" name="txtUser" value="${UPDATED_ACCOUNT.username}"/>
                    <div class="row">
                        <label class="col-1">Password </label>
                        <input class="col-2" type="password" name="txtPass" value=""
                               placeholder="******" id="password" onkeyup="enableButton(event)"/>
                        <input class="col-3" type="submit" name="btAction" value="Update" disabled/>
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
                    <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}"/>
                    <input type="hidden" name="txtUser" value="${UPDATED_ACCOUNT.username}"/>
                    <div class="row">
                        <label class="col-1">Full name </label>
                        <input class="col-2" type="text" name="txtFullname" value="${param.txtFullname}" 
                               onkeyup="enableButton(event)"
                               placeholder="${UPDATED_ACCOUNT.fullname}"/>
                        <input class="col-3" type="submit" name="btAction" value="Update" disabled/>
                    </div><br/>
                    <c:if test="${not empty ERRORS.fullnameLengthError}">
                        <p class="error-message">${ERRORS.fullnameLengthError}</p>
                    </c:if>
                </form>

                <form action="update">
                    <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}"/>
                    <input type="hidden" name="txtUser" value="${UPDATED_ACCOUNT.username}"/>
                    <div class="row">
                        <label class="col-1">Phone </label>
                        <input class="col-2" type="text" name="txtPhone" value="${param.txtPhone}" 
                               onkeyup="enableButton(event)" 
                               placeholder="${UPDATED_ACCOUNT.phone}"/>
                        <input class="col-3" type="submit" name="btAction" value="Update" disabled/>
                    </div><br/>
                    <c:if test="${not empty ERRORS.phoneFormatErr}">
                        <p class="error-message">${ERRORS.phoneFormatErr}</p>
                    </c:if>
                </form>

                <form action="update">
                    <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}"/>
                    <input type="hidden" name="txtUser" value="${UPDATED_ACCOUNT.username}"/>
                    <div class="row">
                        <label class="col-1">Address </label>
                        <input class="col-2" type="text" name="txtAddress" value="${param.txtAddress}" 
                               onkeyup="enableButton(event)"
                               placeholder="${UPDATED_ACCOUNT.address}"/>
                        <input class="col-3" type="submit" name="btAction" value="Update" disabled/>
                    </div><br/>
                    <c:if test="${not empty ERRORS.addressLengthErr}">
                        <p class="error-message">${ERRORS.addressLengthErr}</p>
                    </c:if>
                </form>

                <c:if test="${ROLE > ADMIN_ROLE}">
                    <form action="update">
                        <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}"/>
                        <input type="hidden" name="txtUser" value="${UPDATED_ACCOUNT.username}"/>
                        <div class="row">
                            <label class="col-1">Role</label>
                            <c:if test="${ROLE == SUPER_ADMIN_ROLE}">
                                <select class="col-2" name="txtRole">
                                    <c:if test="${UPDATED_ACCOUNT.role == USER_ROLE}">
                                        <option value="${USER_ROLE}" selected>User</option>
                                        <option value="${ADMIN_ROLE}">Administrator</option>
                                    </c:if>
                                    <c:if test="${UPDATED_ACCOUNT.role == ADMIN_ROLE}">
                                        <option value="${USER_ROLE}">User</option>
                                        <option value="${ADMIN_ROLE}" selected>Administrator</option>
                                    </c:if>
                                </select>
                            </c:if>
                            <input class="col-3" type="submit" name="btAction" value="Update"/>
                        </div>
                    </form>
                </c:if>

                <div class="bottom-button">
                    <c:if test="${ROLE > UPDATED_ACCOUNT.role}">
                        <c:url var="deleteURL" value="delete">
                            <c:param name="btAction" value="Delete"/>
                            <c:param name="txtUser" value="${UPDATED_ACCOUNT.username}"/>
                            <c:param name="txtSearchValue" value="${param.txtSearchValue}"/>
                        </c:url>
                        <a class="delete-button" href="${deleteURL}">Delete account</a>
                    </c:if>

                    <c:url var="cancelURL" value="dashboard">
                        <c:param name="btAction" value="Search"/>
                        <c:param name="txtSearchValue" value="${param.txtSearchValue}"/>
                    </c:url>
                    <a class="cancel-button" href="${cancelURL}">Close</a>
                </div>
            </article>
        </c:if>
    </section>
</article>
