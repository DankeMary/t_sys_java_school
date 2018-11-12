<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Station Schedule</title>
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
                <h5>Station Schedule</h5>
                <div class="formFragment">
                    <form method="GET" action="/schedule">
                        <input type="text" placeholder="Station Name" id="station-input-search" value="${stationName}"
                               name="stationName">
                        <input type="submit" value="Show"/>
                    </form>
                </div>
                <c:choose>
                    <c:when test="${not empty noStationMessage}">
                        ${noStationMessage}
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${empty schedule}">
                                No trains yet
                            </c:when>
                            <c:otherwise>
                                <table class="table table-striped table-hover" style="width: 100%; min-width: 500px">
                                    <thead class="thead-light">
                                    <tr>
                                        <th>Train</th>
                                        <th>From</th>
                                        <th>To</th>
                                        <th>Date</th>
                                        <th>Arrival</th>
                                        <th>Departure</th>
                                    </tr>
                                    </thead>
                                    <c:forEach items="${schedule}" var="item">
                                        <c:choose>
                                            <c:when test="${not item.tripData.isCancelled}">
                                                <tr>
                                                    <td>${item.train.number}</td>
                                                    <td>${item.trip.from.name}</td>
                                                    <td>${item.trip.to.name}</td>
                                                    <td>${item.tripData.date}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not item.tripData.isLate}">
                                                                ${item.route.arrival}
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span style="color: red">is late</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${not item.tripData.isLate}">
                                                                ${item.route.departure}
                                                            </c:when>
                                                            <c:otherwise>
                                                                <span style="color: red">is late</span>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                </table>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="col">
        </div>
    </div>
</div>
<script src="<c:url value="/resources/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/utils.js" />"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
        integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
        integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
        crossorigin="anonymous"></script>
</body>
</html>