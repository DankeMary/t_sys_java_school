<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Find Tickets</title>
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
                <h5>Find Tickets</h5>
                <jsp:include page="findTicketsForm.jsp"/>
                <c:choose>
                    <c:when test="${empty ticketsAvailable}">
                        No trains found
                    </c:when>
                    <c:otherwise>
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
                                                <span style="font-size: 11px; color: green;">
                                                        ${item.fromTDBean.route.departure}
                                                </span>
                                                <br>
                                                <span style="font-size: 9px; color: grey;">
                                                    ${item.fromTDBean.date}
                                                    </span>
                                            </td>

                                            <td>${item.toTDBean.route.station.name} <br>
                                                <span style="font-size: 11px; color: green;">
                                                        ${item.toTDBean.route.departure}
                                                </span>
                                                <br>
                                                <span style="font-size: 9px; color: grey;">
                                                        ${item.toTDBean.date}
                                                </span>
                                            </td>
                                            <td>
                                                ${item.ticketsQty}
                                            </td>
                                            <td><a href="#">Buy</a></td>
                                        </tr>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </table>
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