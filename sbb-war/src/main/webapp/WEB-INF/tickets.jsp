<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Find Tickets</title>
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
        <div class="col-sm-12 col-md-10 col-lg-8" style="padding:0;border: 3px outset steelblue">
            <jsp:include page="menu.jsp"/>
            <div class="col-12" style="overflow:auto">
                <h5>Find Tickets</h5>
                <jsp:include page="findTicketsForm.jsp"/>
                <c:choose>
                    <c:when test="${not empty fromStation and empty ticketsAvailable}">
                        <h6>No trains found</h6>
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${not empty ticketsAvailable}">
                                <div class="pagination">
                                    <c:choose>
                                        <c:when test="${currentPage != 1}">
                                            <a href="/trains/find?fromDay=${fromDay}&fromTime=${fromTime}&toDay=${toDay}&toTime=${toTime}&fromStation=${fromStation}&toStation=${toStation}&page=${currentPage - 1}">&laquo;</a>
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
                                                                        <a href="/trains/find?fromDay=${fromDay}&fromTime=${fromTime}&toDay=${toDay}&toTime=${toTime}&fromStation=${fromStation}&toStation=${toStation}&page=${i}">${i}</a>
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
                                                                        <a href="/trains/find?fromDay=${fromDay}&fromTime=${fromTime}&toDay=${toDay}&toTime=${toTime}&fromStation=${fromStation}&toStation=${toStation}&page=${i}">${i}</a>
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
                                                                <a href="/trains/find?fromDay=${fromDay}&fromTime=${fromTime}&toDay=${toDay}&toTime=${toTime}&fromStation=${fromStation}&toStation=${toStation}&page=${i}">${i}</a>
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
                                                        <a href="/trains/find?fromDay=${fromDay}&fromTime=${fromTime}&toDay=${toDay}&toTime=${toTime}&fromStation=${fromStation}&toStation=${toStation}&page=${i}">${i}</a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:choose>
                                        <c:when test="${currentPage lt navPagesQty}">
                                            <a href="/trains/find?fromDay=${fromDay}&fromTime=${fromTime}&toDay=${toDay}&toTime=${toTime}&fromStation=${fromStation}&toStation=${toStation}&page=${currentPage + 1}">&raquo;</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="invisible-link">&raquo;</a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <table class="table table-striped table-hover" style="width: 100%; min-width: 500px">
                                    <thead class="thead-light">
                                    <tr>
                                        <th>Train</th>
                                        <th>From</th>
                                        <th>To</th>
                                        <th>Tickets</th>
                                        <th>Buy</th>
                                    </tr>
                                    </thead>
                                    <c:forEach items="${ticketsAvailable}" var="item">
                                        <c:choose>
                                            <c:when test="${not item.fromTDBean.isCancelled}">
                                                <tr>
                                                    <td>${item.fromTDBean.route.trip.train.number}</td>
                                                    <td>${item.fromTDBean.route.station.name} <br>
                                                        <span style="font-size: 11px; color: green; display: block;">
                                                                ${item.fromTDBean.route.departure}
                                                        </span>
                                                        <span style="font-size: 9px; color: grey; display: block;">
                                                                ${localDateTimeFormat.format(item.fromTDBean.date)}
                                                        </span>
                                                    </td>

                                                    <td>${item.toTDBean.route.station.name} <br>
                                                        <span style="font-size: 11px; color: green; display: block;">
                                                                ${item.toTDBean.route.departure}
                                                        </span>
                                                        <span style="font-size: 9px; color: grey; display: block;">
                                                                ${localDateTimeFormat.format(item.toTDBean.date)}
                                                        </span>
                                                    </td>
                                                    <td>
                                                            ${item.ticketsQty}
                                                    </td>
                                                    <td>
                                                        <a href="/user/buyTicket?fromJourney=${item.fromTDBean.id}&toJourney=${item.toTDBean.id}">Buy</a>
                                                    </td>
                                                </tr>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                </table>
                            </c:when>
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
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/js/utils.js" />"></script>
<script src="<c:url value="/resources/js/popper.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>