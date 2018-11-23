<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Train Journeys</title>
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
                <h5>Train Journeys</h5>
                <div class="formFragment">
                <form:form method="POST"
                           action="/trains/${trainId}/journeys"
                           modelAttribute="journeyForm">
                        <spring:bind path="departureDay">
                            <label style="width: 110px;">Departure Day</label>
                            <input type="date" value="${status.value}"
                                   name="${status.expression}">
                            <span style="color: red">
                            <c:if test="${status.error}">
                                <c:forEach items="${status.errorMessages}" var="error">
                                    <c:out value="${error}"/>
                                </c:forEach>
                            </c:if></span>
                            </input>
                        </spring:bind>

                    <div style="display: inline-block;">
                        <input type="submit" value="Add New" id="submit" class="submit-btn"/>
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
                            <a href="/trains/${trainId}/journeys?page=${currentPage - 1}">&laquo;</a>
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
                                                        <a href="/trains/${trainId}/journeys?page=${i}">${i}</a>
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
                                                        <a href="/trains/${trainId}/journeys?page=${i}">${i}</a>
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
                                                <a href="/trains/${trainId}/journeys?page=${i}">${i}</a>
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
                                        <a href="/trains/${trainId}/journeys?page=${i}">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${currentPage lt navPagesQty}">
                            <a href="/trains/${trainId}/journeys?page=${currentPage + 1}">&raquo;</a>
                        </c:when>
                        <c:otherwise>
                            <a class="invisible-link">&raquo;</a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <table class="table table-striped table-hover" style="width: 100%; min-width: 500px">
                    <thead class="thead-light">
                    <tr>
                        <th>Date</th>
                        <th></th>
                    </tr>
                    </thead>
                    <c:forEach items="${journeys}" var="journey">
                        <tr>
                            <td>${journey.departureDay}</td>
                            <td>
                                <a href="/trains/${trainId}/journeys/${journey.journeyId}/passengers">Passengers</a> |
                                <a href="/trains/${trainId}/journeys/${journey.journeyId}/cancel"
                                   onclick="return confirmDelete(this, '${pageContext.request.contextPath}/trains/${trainId}/journeys/${journey.journeyId}/cancel')">Cancel</a>
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