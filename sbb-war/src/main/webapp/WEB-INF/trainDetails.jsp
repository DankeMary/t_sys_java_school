<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Train Path</title>
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
                <h5>Train Details</h5>
                <div style="display:inline-block; margin-right: 15px;"><b>Train : </b>${trainData.trainBean.number}
                </div>
                <div style="display:inline-block; margin-right: 15px;"><b>Capacity :</b> ${trainData.trainBean.capacity}
                </div>
                <div style="display:inline-block; margin-right: 15px;"><b>Price :</b> ${trainData.trainBean.price}</div>
                <div style="display:inline-block">
                    <a href="/trains/${trainData.trainBean.id}/update">Edit</a> |
                    <a href="/trains/${trainData.trainBean.id}/delete"
                       onclick="return confirmDelete(this, '${pageContext.request.contextPath}/trains/${trainData.trainBean.id}/delete')">Delete</a>
                </div>
                <p><b>Journeys : </b> <a href="/trains/${trainData.trainBean.id}/journeys">See More...</a></p>
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
                            <!--<td>${routePoint.arrival}</td>
                            <td>${routePoint.departure}</td>-->
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