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
        <div class="col-sm-12 col-md-10 col-lg-8" style="padding:0;border: 3px outset steelblue">
            <jsp:include page="menu.jsp"/>
            <div class="col-12" style="overflow:auto">
                <h5>Train Trip Details</h5>
                <div style="display:inline-block; margin-right: 15px;"><b>Train : </b>${train.number}
                </div>
                <div style="display:inline-block; margin-right: 15px;"><b>Trip Departure : </b>${localDateFormat.format(tripDepDay)}
                </div>

                <table class="table table-striped table-hover" style="width: 100%; min-width: 500px">
                    <thead class="thead-light">
                    <tr>
                        <th>Station</th>
                        <th>Arrival</th>
                        <th>Departure</th>
                    </tr>
                    </thead>
                    <c:forEach items="${journeyDetails}" var="item">
                        <tr>
                            <td>${item.route.station.name}</td>
                            <td>
                                <span style="font-size: 11px; color: green; display: block;">
                                        ${item.route.arrival}
                                </span>
                                <span style="font-size: 9px; color: grey; display: block;">
                                        ${localDateFormat.format(item.date)}
                                </span>
                            </td>
                            <td><span style="font-size: 11px; color: green; display: block;">
                                    ${item.route.departure}
                            </span>
                                <span style="font-size: 9px; color: grey; display: block;">
                                        ${localDateFormat.format(item.date)}
                                </span></td>
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