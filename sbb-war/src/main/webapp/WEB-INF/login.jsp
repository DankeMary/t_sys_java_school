<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <title>Log In</title>
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
                <h3 style="text-align: center;">Log In</h3>
                <c:if test="${param.error != null}">
                    <div class="alert alert-danger">
                        <p>Invalid username and password.</p>
                    </div>
                </c:if>
                <c:if test="${param.logout != null}">
                    <div class="alert alert-success">
                        <p>You have been logged out successfully.</p>
                    </div>
                </c:if>

                <div class="form">
                    <form:form method="POST"
                               action="/login"
                               modelAttribute="userForm"
                               cssClass="register-form"
                               autocomplete="off">
                        <spring:bind path="username">
                            <input type="text"
                                   placeholder="username"
                                   value="${status.value}"
                                   name="${status.expression}"
                                   class="fm-with-valid"
                                   required
                                   autofocus>
                        </spring:bind>

                        <spring:bind path="password">
                            <input type="password"
                                   placeholder="password"
                                   value="${status.value}"
                                   name="${status.expression}"
                                   class="fm-with-valid"
                                   required>
                        </spring:bind>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div style="margin: auto;">
                            <button type="submit" id="submit" class="submit-btn btn btn-primary"
                                    style="margin-bottom: 20px">Log In
                            </button>
                        </div>

                        <a href="/signup">Sign Up</a>
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