<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <title>Buy Ticket</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/style.css" />">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery-ui.min.css" />">
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col">
        </div>
        <div class="col-sm-12 col-md-10 col-lg-9" style="padding:0;border: 3px outset steelblue">
            <jsp:include page="menu.jsp"/>
            <div class="col-12" style="overflow:auto">
                <h5>Buy Tickets <span style="color: red;">(Max tickets quantity is 10!)</span></h5>
                <p><b>Train:</b> #${trainNumber}</p>
                <p><b>From:</b> <c:out value="${fromMetaInfo}"/></p>
                <p><b>To:</b> ${toMetaInfo}</p>
                <p><b>Price:</b> ${ticketPrice}</p>
                <p><b>Train Path:</b> <a href="/user/trains/${trainId}/journeys/${fromJourneyId}" target="_blank">See
                    More...</a></p>
                <c:choose>
                    <c:when test="${empty noTickets}">
                        <h6>Enter The Passengers Details <span style="font-size: 14px">(Completely blank items are not counted)</span></h6>
                        <span style="color: red; display: block;">${psngrInfo}</span>
                        <c:if test="${not empty possibleErrors}">
                            <span style="color: red; display: block;">Some of the given data is invalid or missing. Check:</span>
                            <ul style="color: red; padding-left: 15px;">
                                <c:forEach items="${possibleErrors}" var="error">
                                    <li>${error}</li>
                                </c:forEach>
                            </ul>
                        </c:if>
                        <form:form method="POST"
                                   action="/user/buyTicket"
                                   modelAttribute="ticketForm"
                                   autocomplete="off">
                            <div class="formFragment">
                                <table id="journeyPassengers" class="table" style="width: 100%; min-width: 500px">
                                    <thead class="thead-light">
                                    <tr>
                                        <th>First Name</th>
                                        <th>Last Name</th>
                                        <th>Birthday</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach begin="0" end="${ticketsQty - 1}" var="i" varStatus="status">
                                        <tr>
                                            <td>
                                                <spring:bind path="ticketForm.passengers[${status.index}].firstName">
                                                    <input value="${status.value}"
                                                           name="${status.expression}"
                                                           class=" form-control"/>
                                                </spring:bind>
                                            </td>
                                            <td>
                                                <spring:bind path="ticketForm.passengers[${status.index}].lastName">
                                                    <input value="${status.value}" name="${status.expression}"
                                                           class=" form-control"/>
                                                </spring:bind>
                                            </td>
                                            <td>
                                                <spring:bind path="ticketForm.passengers[${status.index}].birthday">
                                                    <input type='date' value="${status.value}"
                                                           name="${status.expression}"
                                                           class=" form-control"/>
                                                </spring:bind>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <tr id="addPassengerRow"></tr>
                                    </tbody>
                                </table>
                            </div>
                            <spring:bind path='fromJourneyId'>
                                <input type='hidden'
                                       value='${fromJourneyId}' name="${status.expression}"/>
                            </spring:bind>
                            <spring:bind path='toJourneyId'>
                                <input type='hidden'
                                       value='${toJourneyId}' name="${status.expression}"/>
                            </spring:bind>
                            <div style="text-align: right; margin-right: 10px;">
                                <input type="button" id="addPassengerButton" value="Add Passenger"
                                       style="background-color: #dde2e3; width: 130px;
                                       border-radius: 5px; height: 38px;"/>
                            </div>
                            <div style="margin: auto;">
                                <input type="submit" value="Submit" id="submit" class="submit-btn"/>
                            </div>
                        </form:form>
                    </c:when>
                    <c:otherwise>
                        <h5><span style="color: red; display: block;">${noTickets}</span></h5>
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
