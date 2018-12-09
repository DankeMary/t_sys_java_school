<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Train Journeys</title>
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
                <h5>Train Journeys</h5>
                <div class="formFragment">
                    <form:form method="POST"
                               action="/worker/trains/${trainId}/journeys"
                               modelAttribute="journeyForm"
                               autocomplete="off">
                        <spring:bind path="departureDay">
                            <label style="width: 110px;">Departure Day</label>
                            <input type="date" value="${status.value}"
                                   name="${status.expression}"
                                   class=" form-control" style="width: 150px; display: inline-block;"
                            required>
                            <span style="color: red">
                            <c:if test="${status.error}">
                                <c:forEach items="${status.errorMessages}" var="error">
                                    <c:out value="${error}"/>
                                </c:forEach>
                            </c:if></span>
                            </input>
                        </spring:bind>

                        <div style="display: inline-block;">
                            <input type="submit" value="Add New" id="submit"
                                   style="background-color: #dde2e3; width: 90px;
                               border-radius: 5px; height: 38px;"/>
                        </div>
                        <span style="color:red; display: block;"><c:out value="${depDayError}"/></span>
                        <span style="color:red; display: block;"><c:out value="${journeyExists}"/></span>
                        <span style="color:red; display: block;"><c:out value="${invalidTrip}"/></span>
                    </form:form>
                </div>
                <span style="color:red; display: block;"><c:out value="${ticketsSold}"/></span>
                <div class="pagination">
                    <c:choose>
                        <c:when test="${currentPage != 1}">
                            <a href="/worker/trains/${trainId}/journeys?page=${currentPage - 1}">&laquo;</a>
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
                                                        <a href="/worker/trains/${trainId}/journeys?page=${i}">${i}</a>
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
                                                        <a href="/worker/trains/${trainId}/journeys?page=${i}">${i}</a>
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
                                                <a href="/worker/trains/${trainId}/journeys?page=${i}">${i}</a>
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
                                        <a href="/worker/trains/${trainId}/journeys?page=${i}">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${currentPage lt navPagesQty}">
                            <a href="/worker/trains/${trainId}/journeys?page=${currentPage + 1}">&raquo;</a>
                        </c:when>
                        <c:otherwise>
                            <a class="invisible-link">&raquo;</a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:choose>
                    <c:when test="${empty journeys}">
                        <h6>No journeys yet</h6>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-striped table-hover" style="width: 100%; min-width: 500px">
                            <thead class="thead-light">
                            <tr>
                                <th>Date</th>
                                <th></th>
                            </tr>
                            </thead>
                            <c:forEach items="${journeys}" var="journey">
                                <tr>
                                    <td>${localDateTimeFormat.format(journey.departureDay)}</td>
                                    <td>
                                        <a href="/worker/trains/${trainId}/journeys/${journey.journeyId}/passengers">Passengers</a>
                                        |
                                        <a href="/user/trains/${trainId}/journeys/${journey.journeyId}">Details</a> |
                                        <a href="/worker/trains/${trainId}/journeys/${journey.journeyId}/cancel"
                                           onclick="return confirmDelete(this, '${pageContext.request.contextPath}/worker/trains/${trainId}/journeys/${journey.journeyId}/cancel')">Cancel</a>
                                    </td>
                                </tr>
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
<script src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/js/utils.js" />"></script>
<script src="<c:url value="/resources/js/popper.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>