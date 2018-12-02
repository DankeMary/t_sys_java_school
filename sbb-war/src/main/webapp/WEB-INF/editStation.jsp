<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Edit Station</title>
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
                <h3>Edit The Station's Details</h3>
                <c:choose>
                    <c:when test="${empty stationForm}">
                        <h5>No station with such data found</h5>
                    </c:when>
                    <c:otherwise>
                        <form:form method="POST"
                                   action="/worker/stations/${stationForm.id}"
                                   modelAttribute="stationForm"
                                   autocomplete="off">
                            <div class="formFragment">
                                <form:label path="name"
                                            cssClass="fm-with-valid">Name</form:label>
                                <spring:bind path="name">
                                    <input value="${status.value}"
                                           name="${status.expression}"
                                           class="fm-with-valid form-control"
                                           style="width: 200px; display: inline-block; height: 30px;"
                                    required>
                                    <div class="form-group col-md-6" style="color: red; display: inline-block;">
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
                            </div>
                            <div style="margin: auto;">
                                <input type="submit" value="Submit" class="submit-btn"/>
                            </div>
                        </form:form>
                    </c:otherwise>
                </c:choose>
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
