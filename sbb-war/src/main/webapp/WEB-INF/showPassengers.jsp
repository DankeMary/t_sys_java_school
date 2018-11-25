<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Passengers List</title>
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
                <h5>Passengers of train #${trainNumber} on ${localDateFormat.format(departureDay)}</h5>
                <c:choose>
                    <c:when test="${empty tickets}">
                        <h4>No passengers yet</h4>
                    </c:when>
                    <c:when test="${not empty tickets}">
                        <div class="pagination">
                            <c:choose>
                                <c:when test="${currentPage != 1}">
                                    <a href="/worker/trains/${trainId}/journeys/${journeyId}/passengers?page=${currentPage - 1}">&laquo;</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="invisible-link">&laquo;</a>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${navPagesQty gt 7}">
                                    <c:choose>
                                        <c:when test="${currentPage - 3 gt 1}">
                                            <c:choose>
                                                <c:when test="${currentPage + 3 lt navPagesQty}">
                                                    <c:forEach begin="${currentPage - 3}" end="${currentPage + 3}" var="i">
                                                        <c:choose>
                                                            <c:when test="${currentPage eq i}">
                                                                <a href="#" class="active">${i}</a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a href="/worker/trains/${trainId}/journeys/${journeyId}/passengers?page=${i}">${i}</a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach begin="${navPagesQty - 7 + 1}" end="${navPagesQty}"
                                                               var="i">
                                                        <c:choose>
                                                            <c:when test="${currentPage eq i}">
                                                                <a href="#" class="active">${i}</a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a href="/worker/trains/${trainId}/journeys/${journeyId}/passengers?page=${i}">${i}</a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach begin="1" end="7" var="i">
                                                <c:choose>
                                                    <c:when test="${currentPage eq i}">
                                                        <a href="#" class="active">${i}</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="/worker/trains/${trainId}/journeys/${journeyId}/passengers?page=${i}">${i}</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach begin="1" end="${navPagesQty}" var="i">
                                        <c:choose>
                                            <c:when test="${currentPage eq i}">
                                                <a href="#" class="active">${i}</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/worker/trains/${trainId}/journeys/${journeyId}/passengers?page=${i}">${i}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${currentPage lt navPagesQty}">
                                    <a href="/worker/trains/${trainId}/journeys/${journeyId}/passengers?page=${currentPage + 1}">&raquo;</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="invisible-link">&raquo;</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <table class="table table-striped table-hover" style="width: 100%; min-width: 500px">
                            <thead class="thead-light">
                            <tr>
                                <th>Ticket</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>Birthday</th>
                                <th>From</th>
                                <th>To</th>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <th>Bought By</th>
                                </sec:authorize>
                            </tr>
                            </thead>
                            <c:forEach items="${tickets}" var="ticket">
                                <tr>
                                    <td>${ticket.id}</td>
                                    <td>${ticket.passenger.firstName}</td>
                                    <td>${ticket.passenger.lastName}</td>
                                    <td>${localDateFormat.format(ticket.passenger.birthday)}</td>
                                    <td>${ticket.from.route.station.name} <br>
                                        <span style="font-size: 10px;">
                                                ${localDateTimeFormat.format(ticket.from.date)}
                                        </span>
                                    </td>
                                    <td>${ticket.to.route.station.name} <br>
                                        <span style="font-size: 10px;">
                                                ${localDateTimeFormat.format(ticket.to.date)}
                                        </span>
                                    </td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <td><a href="/user/${ticket.boughtBy.username}">${ticket.boughtBy.username}</a></td>
                                    </sec:authorize>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                </c:choose>
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