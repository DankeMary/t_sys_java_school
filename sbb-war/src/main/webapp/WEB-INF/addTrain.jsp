<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Add New Train</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="/resources/style.css">
    <link rel="stylesheet" type="text/css" href="/resources/jquery-ui.min.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
        </div>
        <div class="col-8" style="border: 3px outset steelblue">
            <jsp:include page="menu.jsp"/>
            <div class="col-12" style="overflow:auto">
                <h3>Enter The Train Details</h3>
                <form:form method="post"
                           modelAttribute="trainForm"
                           onsubmit="return validateTrain(this, '${pageContext.request.contextPath}/trains')">
                    <div class="formFragment">
                        <form:label path="number">Number</form:label>
                        <spring:bind path="number">
                            <input id="train-number" value="${status.value}" name="${status.expression}">
                            <span id="js-number-error" style="color: red">
                                <c:if test="${status.error}">
                                    <c:forEach items="${status.errorMessages}" var="error">
                                        <c:out value="${error}"/>
                                    </c:forEach>
                                </c:if>
                            </span>
                            </input>
                        </spring:bind>
                    </div>

                    <div class="formFragment">
                        <form:label path="capacity">Capacity</form:label>
                        <spring:bind path="capacity">
                            <input id="train-capacity" value="${status.value}" name="${status.expression}">
                            <span id="js-capacity-error" style="color: red">
                                <c:if test="${status.error}">
                                    <c:forEach items="${status.errorMessages}" var="error">
                                        <c:out value="${error}"/>
                                    </c:forEach>
                                </c:if>
                            </span>
                            </input>
                        </spring:bind>
                    </div>

                    <h5>Add Train Path</h5>
                    <div class="formFragment">
                        <form:label path="">Station</form:label>
                        <input type="text" id="station-input-search2" value="${status.value}"
                               name="inp1"/>
                    </div>

                    <div class="formFragment">
                        <form:label path="">Arrival</form:label>
                        <input type="time" id="timeArr" value="${status.value}"
                               name="inp2" style="margin-right:20px"/>

                        <form:label path="">Departure</form:label>
                        <input type="time" id="timeDep" value="${status.value}"
                               name="inp3"/>
                    </div>
                    <a href="#" style="margin: 10px 24% 10px;" onclick="return add()">Add</a>

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
<script src="/resources/validation.js"></script>
<script src="<c:url value="/resources/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/utils.js" />"></script>
<!--<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>-->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>
