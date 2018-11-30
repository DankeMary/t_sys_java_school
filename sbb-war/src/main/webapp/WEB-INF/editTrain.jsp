<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <title>Edit Train</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css" />">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
        </div>
        <div class="col-sm-12 col-md-10 col-lg-8" style="padding:0;border: 3px outset steelblue">
            <jsp:include page="menu.jsp"/>
            <div class="col-12" style="overflow:auto">
                <h3>Edit The Train's Details</h3>
                <c:choose>
                    <c:when test="${empty trainForm}">
                        <h5>No train with such data found</h5>
                    </c:when>
                    <c:otherwise>
                        <form:form method="POST"
                                   action="/worker/trains/${trainForm.id}"
                                   modelAttribute="trainForm"
                                   autocomplete="off">
                            <div class="formFragment">
                                <form:label path="number"
                                            cssClass="fm-with-valid">Number</form:label>
                                <spring:bind path="number">
                                    <input id="train-number"
                                           value="${status.value}"
                                           name="${status.expression}"
                                           class="fm-with-valid form-control"
                                           style="width: 150px; display: inline-block; height: 30px;"
                                           required>
                                    <div class="form-group col-md-6" style="color: red; display: inline-block;">
                                        <span id="js-number-error" style="display: block;"></span>
                                        <div class="error-list">
                                            <span style="display: block;">${numberNonUnique}</span>
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

                            <div class="formFragment">
                                <form:label path="capacity"
                                            cssClass="fm-with-valid">Capacity</form:label>
                                <spring:bind path="capacity">
                                    <input id="train-capacity"
                                           value="${status.value}"
                                           name="${status.expression}"
                                           class="fm-with-valid form-control"
                                           style="width: 150px; display: inline-block; height: 30px;"
                                           required>
                                    <div class="form-group col-md-6" style="color: red; display: inline-block;">
                                        <span id="js-capacity-error" style="display: block;"></span>
                                        <div class="error-list">
                                            <span style="display: block;">${capacityCannotUpdate}</span>
                                            <c:if test="${status.error}">
                                                <ul style="list-style-type:disc">
                                                    <c:forEach items="${status.errorMessages}" var="error">
                                                        <span style="display: block;">${error}</span>
                                                    </c:forEach>
                                                </ul>
                                            </c:if>
                                        </div>
                                    </div>
                                    </input>
                                </spring:bind>
                            </div>

                            <div class="formFragment">
                                <form:label path="price"
                                            cssClass="fm-with-valid">Price</form:label>
                                <spring:bind path="price">
                                    <input type="number"
                                           id="train-price"
                                           min="1" step="0.01" max="7000"
                                           value="${status.value}"
                                           name="${status.expression}"
                                           class="fm-with-valid form-control"
                                           style="width: 150px; display: inline-block; height: 30px;"
                                           required>
                                    <div class="form-group col-md-6" style="color: red; display: inline-block;">
                                        <span id="js-price-error" style="display: block;"></span>
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