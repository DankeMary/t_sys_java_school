<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Add New Station</title>
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
                <h3>Enter The Station's Details</h3>
                <form:form method="POST"
                           modelAttribute="stationForm"
                           onsubmit="return validateStation(this, '${pageContext.request.contextPath}/worker/stations')"
                           autocomplete="off">
                    <div class="formFragment">
                        <form:label path="name"
                                    cssClass="fm-with-valid">Name</form:label>
                        <spring:bind path="name">
                            <input id="station-name"
                                   value="${status.value}"
                                   name="${status.expression}"
                                   class="fm-with-valid form-control"
                                   style="width: 200px; display: inline-block; height: 30px;"
                                   maxlength="65"
                                   required>
                            <div class="form-group col-md-6" style="color: red; display: inline-block;">
                                <span id="js-name-error" style="display: block;"></span>
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
            </div>
        </div>
        <div class="col">
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/validation.js" />"></script>
<script src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/js/popper.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>
