<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <title>Sign Up</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="/resources/style.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
        </div>
        <div class="col-sm-12 col-md-10 col-lg-8" style="padding:0;border: 3px outset steelblue">
            <jsp:include page="menu.jsp"/>
            <div class="col-12" style="overflow:auto">
                <h3 style="text-align: center;">Enter The User Details</h3>
                <c:if test="${not empty uniqueUsername}">
                    <div class="alert alert-danger">
                        <p>${uniqueUsername}</p>
                    </div>
                </c:if>
                <div class="form">
                    <form:form method="POST"
                               action="/signup"
                               modelAttribute="userForm">
                        <spring:bind path="username">
                            <input type="text"
                                   placeholder="username"
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
                                    style="margin-bottom: 20px">Sign Up
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
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>
