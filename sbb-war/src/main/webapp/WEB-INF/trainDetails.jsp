<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Train Details</title>
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
                <h5>Train Details</h5>
                <div style="display:inline-block; margin-right: 15px;"><b>Train : </b>${trainData.trainBean.number}
                </div>
                <div style="display:inline-block; margin-right: 15px;"><b>Capacity :</b> ${trainData.trainBean.capacity}
                </div>
                <div style="display:inline-block; margin-right: 15px;"><b>Price :</b> ${trainData.trainBean.price}</div>
                <div style="display:inline-block">
                    <a href="/worker/trains/${trainData.trainBean.id}/update">Edit</a> |
                    <a href="/worker/trains/${trainData.trainBean.id}/delete"
                       onclick="return confirmDelete(this, '${pageContext.request.contextPath}/worker/trains/${trainData.trainBean.id}/delete')">Delete</a>
                </div>
                <p><b>Journeys : </b> <a href="/worker/trains/${trainData.trainBean.id}/journeys">See More...</a></p>
                <h5>Train Path:</h5>
                <table class="table table-striped table-hover" style="width: 100%; min-width: 500px">
                    <thead class="thead-light">
                    <tr>
                        <th>Station</th>
                        <th>Arrival</th>
                        <th>Departure</th>
                    </tr>
                    </thead>
                    <c:forEach items="${trainData.trainRoute}" var="routePoint">
                        <tr>
                            <td>${routePoint.station.name}</td>
                            <td>${routePoint.arrival}</td>
                            <td>${routePoint.departure}</td>
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