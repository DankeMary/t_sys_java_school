<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Train Journeys</title>
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
        <div class="col-8" style="border: 3px outset steelblue">
            <jsp:include page="menu.jsp"/>
            <div class="col-12" style="overflow:auto">
                <h5>Train Journeys</h5>
                <form:form method="POST"
                           action="/trains/${trainId}/journeys"
                           modelAttribute="journeyForm">
                    <div class="formFragment">
                        <form:label path="departureDay">Departure Day</form:label>
                        <spring:bind path="departureDay">
                            <input type="date" value="${status.value}"
                                   name="${status.expression}">
                            <span style="color: red">
                            <c:if test="${status.error}">
                                <c:forEach items="${status.errorMessages}" var="error">
                                    <c:out value="${error}"/>
                                </c:forEach>
                            </c:if></span>
                            </input>
                        </spring:bind>
                        <span style="color:red; display: block;"><c:out value="${depDayError}"/></span>
                        <span style="color:red; display: block;"><c:out value="${journeyExists}"/></span>
                        <span style="color:red; display: block;"><c:out value="${invalidTrip}"/></span>
                    </div>
                    <div style="display: inline-block;">
                        <input type="submit" value="Add New" id="submit" class="submit-btn"/>
                    </div>
                </form:form>
                <span style="color:red; display: block;"><c:out value="${ticketsSold}"/></span>
                <table class="table table-striped table-hover" style="width: 100%; min-width: 500px">
                    <thead class="thead-light">
                    <tr>
                        <th>Date</th>
                        <th></th>
                    </tr>
                    </thead>
                    <c:forEach items="${journeys}" var="journey">
                        <tr>
                            <td>${journey.departureDay}</td>
                            <td>
                                <a href="/trains/${trainId}/journeys/${journey.journeyId}/passengers">Passengers</a> |
                                <a href="/trains/${trainId}/journeys/${journey.journeyId}/cancel"
                                   onclick="return confirmDelete(this, '${pageContext.request.contextPath}/trains/${trainId}/journeys/${journey.journeyId}/cancel')">Cancel</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="col">
        </div>
    </div>
</div>
<script src="<c:url value="/resources/utils.js" />"></script>
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