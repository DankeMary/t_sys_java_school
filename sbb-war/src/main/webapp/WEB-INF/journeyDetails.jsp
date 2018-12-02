<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Train Path</title>
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
                <h5>Train Trip Details</h5>
                <div style="display:inline-block; margin-right: 15px;"><b>Train : </b>${train.number}
                </div>
                <div style="display:inline-block; margin-right: 15px;"><b>Trip Departure
                    : </b>${localDateFormat.format(tripDepDay)}
                </div>

                <table class="table table-striped table-hover" style="width: 100%; min-width: 500px">
                    <thead class="thead-light">
                    <tr>
                        <th>Station</th>
                        <th>Arrival</th>
                        <th>Departure</th>
                    </tr>
                    </thead>
                    <c:forEach items="${journeyDetails}" var="item" varStatus="index">
                        <tr>
                            <td>${item.route.station.name}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${index.first}">
                                        —
                                    </c:when>
                                    <c:otherwise>
                                        <span style="font-size: 11px; color: green; display: block;">
                                                ${item.route.arrival}
                                        </span>
                                        <span style="font-size: 9px; color: grey; display: block;">
                                                ${localDateFormat.format(item.date)}
                                        </span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${index.last}">
                                        —
                                    </c:when>
                                    <c:otherwise>
                                        <span style="font-size: 11px; color: green; display: block;">
                                                ${item.route.departure}
                                        </span>
                                        <span style="font-size: 9px; color: grey; display: block;">
                                                ${localDateFormat.format(item.date)}
                                        </span>
                                    </c:otherwise>
                                </c:choose>
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
<script src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/js/utils.js" />"></script>
<script src="<c:url value="/resources/js/popper.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>