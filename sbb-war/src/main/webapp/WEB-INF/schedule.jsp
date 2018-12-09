<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Station Schedule</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery-ui.min.css" />">
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
                <h5>Station Schedule</h5>
                <div class="formFragment">
                    <form method="GET" action="/schedule">
                        <input type="text" placeholder="Station Name" id="station-input-search" value="${stationName}"
                               name="stationName"
                               class=" form-control" style="width: 200px; display: inline-block;">
                        <input type="submit" class="inp-btn" value="Show"/>
                        <span style="color:red; display: block;"><c:out value="${noStationMessage}"/></span>
                    </form>
                </div>
                <c:choose>
                    <c:when test="${empty noStationMessage}">
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
                                                    <td><a href="/user/trains/${item.train.id}/journeys/${item.tripData.id}"
                                                           target="_blank">${item.train.number}</a></td>
                                                    <td>${item.trip.from.name}</td>
                                                    <td>${item.trip.to.name}</td>
                                                    <td>${localDateTimeFormat.format(item.tripData.date)}</td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${item.route.arrival == item.route.departure and
                                                                            item.route.nextStation != null}">
                                                                —
                                                            </c:when>
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
                                                            <c:when test="${item.route.nextStation == null}">
                                                                —
                                                            </c:when>
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
                    </c:when>
                </c:choose>
            </div>
        </div>
        <div class="col">
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/js/utils.js" />"></script>
<script src="<c:url value="/resources/js/popper.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>