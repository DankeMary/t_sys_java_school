<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <title>Sign Up</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
        </div>
        <div class="col-sm-12 col-md-10 col-lg-9" style="padding:0;border: 3px outset steelblue">
            <jsp:include page="menu.jsp"/>
            <div class="col-12" style="overflow:auto">
                <h3 style="text-align: center;">Enter The User Details</h3>
                <c:if test="${not empty uniqueUsername}">
                    <div class="alert alert-danger"
                         style="max-width: 360px; margin: 0 auto; text-align: center;">
                        <p style="margin: 0;">${uniqueUsername}</p>
                    </div>
                </c:if>
                <div class="form">
                    <form:form method="POST"
                               action="/signup"
                               modelAttribute="userForm"
                               autocomplete="off">
                        <spring:bind path="username">
                            <input type="text"
                                   placeholder="username"
                                   value="${status.value}"
                                   name="${status.expression}"
                                   class="fm-with-valid"
                                   required
                                   autofocus>
                            <div style="color: red; display: inline-block;">
                                <div class="error-list">
                                    <c:if test="${status.error}">
                                        <c:forEach items="${status.errorMessages}" var="error">
                                            <span style="display: block;">${error}</span>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                            </input>
                        </spring:bind>


                        <spring:bind path="password">
                            <input type="password"
                                   placeholder="password"
                                   value="${status.value}"
                                   name="${status.expression}"
                                   class="fm-with-valid"
                                   required>
                            <div style="color: red; display: inline-block;">
                                <div class="error-list">
                                    <c:if test="${status.error}">
                                        <c:forEach items="${status.errorMessages}" var="error">
                                            <span style="display: block;">${error}</span>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                            </input>
                        </spring:bind>

                        <div style="margin: auto;">
                            <button type="submit" value="" id="submit" class="submit-btn btn btn-success"
                                    style="margin-bottom: 20px; width: 80px;">Sign Up
                            </button>
                        </div>

                        <a href="/login">Log In</a>
                    </form:form>
                </div>
            </div>
        </div>
        <div class="col">
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/js/popper.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>
