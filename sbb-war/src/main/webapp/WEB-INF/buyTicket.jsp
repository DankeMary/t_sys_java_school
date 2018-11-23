<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <title>Buy Ticket</title>
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
        <div class="col-sm-12 col-md-10 col-lg-8" style="padding:0;border: 3px outset steelblue">
            <jsp:include page="menu.jsp"/>
            <div class="col-12" style="overflow:auto">
                <h5>Buy Tickets <span style="color: red;">(Max tickets quantity is 10!)</span></h5>
                <p><b>Train:</b> #${trainNumber}</p>
                <p><b>From:</b> ${fromMetaInfo}</p>
                <p><b>To:</b> ${toMetaInfo}</p>
                <p><b>Price:</b> ${ticketPrice}</p>
                <c:choose>
                    <c:when test="${empty noTickets}">
                        <span style="color: red; display: block;">${psngrInfo}</span>
                        <h6>Enter The Passengers Details</h6>
                        <c:if test="${not empty possibleErrors}">
                            <spring:hasBindErrors name="ticketForm">
                                <span style="color: red; display: block;">Some of the given data is invalid or missing. Check:</span>
                                <ul style="color: red; padding-left: 15px;">
                                    <c:forEach items="${possibleErrors}" var="error">
                                        <li>${error}</li>
                                    </c:forEach>
                                </ul>
                            </spring:hasBindErrors>
                        </c:if>
                        <form:form method="POST"
                                   action="/user/buyTicket"
                                   modelAttribute="ticketForm">
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
                                                           name="${status.expression}"/>
                                                </spring:bind>
                                            </td>
                                            <td>
                                                <spring:bind path="ticketForm.passengers[${status.index}].lastName">
                                                    <input value="${status.value}" name="${status.expression}"/>
                                                </spring:bind>
                                            </td>
                                            <td>
                                                <spring:bind path="ticketForm.passengers[${status.index}].birthday">
                                                    <input type='date' value="${status.value}"
                                                           name="${status.expression}"/>
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
                            <div style="text-align: right">
                                <input type="button" id="addPassengerButton" value="Add Passenger"/>
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
