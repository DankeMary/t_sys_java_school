<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Passenger Profile</title>
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
            <div class="col-12" style="overflow:auto;">
                <c:choose>
                    <c:when test="${empty userData}">
                        <h5>User not found</h5>
                    </c:when>
                    <c:otherwise>
                        <h5>User <b>${userData.user.username}</b> Profile</h5>

                        <h6>Tickets</h6>
                        <c:choose>
                            <c:when test="${empty userData.tickets}">
                                No tickets bought for future yet
                            </c:when>
                            <c:otherwise>
                                <div class="pagination">
                                    <c:choose>
                                        <c:when test="${currentPage != 1}">
                                            <a href="/user/${userData.user.username}?page=${currentPage - 1}">&laquo;</a>
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
                                                            <c:forEach begin="${currentPage - 3}"
                                                                       end="${currentPage + 3}" var="i">
                                                                <c:choose>
                                                                    <c:when test="${currentPage eq i}">
                                                                        <a href="#" class="active">${i}</a>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <a href="/user/${userData.user.username}?page=${i}">${i}</a>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:forEach begin="${navPagesQty - 7 + 1}"
                                                                       end="${navPagesQty}"
                                                                       var="i">
                                                                <c:choose>
                                                                    <c:when test="${currentPage eq i}">
                                                                        <a href="#" class="active">${i}</a>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <a href="/user/${userData.user.username}?page=${i}">${i}</a>
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
                                                                <a href="/user/${userData.user.username}?page=${i}">${i}</a>
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
                                                        <a href="/user/${userData.user.username}?page=${i}">${i}</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:choose>
                                        <c:when test="${currentPage lt navPagesQty}">
                                            <a href="/user/${userData.user.username}?page=${currentPage + 1}">&raquo;</a>
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
                                        <th>Train</th>
                                        <th>From</th>
                                        <th>To</th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <c:forEach items="${userData.tickets}" var="ticket">
                                        <tr>
                                            <td>${ticket.id}</td>
                                            <td>${ticket.passenger.firstName}</td>
                                            <td>${ticket.passenger.lastName}</td>
                                            <td>${localDateTimeFormat.format(ticket.passenger.birthday)}</td>
                                            <td>
                                                <a href="/user/trains/${ticket.from.route.trip.train.id}/journeys/${ticket.from.id}"
                                                   target="_blank">${ticket.from.route.trip.train.number}</a></td>
                                            <td>${ticket.from.route.station.name} <br>
                                                <span style="font-size: 11px; color: grey; display: block;">
                                                        ${localDateTimeFormat.format(ticket.from.date)}
                                                </span>
                                                <span style="font-size: 9px; color: green; display: block;">
                                                     ${ticket.from.route.departure}
                                                </span>
                                            </td>
                                            <td>${ticket.to.route.station.name} <br>
                                                <span style="font-size: 11px; color: grey; display: block;">
                                                        ${localDateTimeFormat.format(ticket.to.date)}
                                                </span>
                                                <span style="font-size: 9px; color: green; display: block;">
                                                        ${ticket.to.route.departure}
                                                </span>
                                            </td>
                                            <td><a href="/user/${loggedinuser}/tickets/${ticket.id}/delete"
                                                   onclick="return confirmDelete(this, '${pageContext.request.contextPath}/user/${loggedinuser}/tickets/${ticket.id}/delete')">Delete</a>
                                            </td>
                                        </tr>
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
<script src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/js/utils.js" />"></script>
<script src="<c:url value="/resources/js/popper.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>