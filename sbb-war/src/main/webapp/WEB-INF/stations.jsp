<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Stations List</title>
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
        <div class="col-sm-12 col-md-10 col-lg-9" style="padding:0;border: 3px outset steelblue">
            <jsp:include page="menu.jsp"/>
            <div class="col-12" style="overflow:auto">
                <c:choose>
                    <c:when test="${empty stations}">
                        No stations yet
                    </c:when>
                    <c:when test="${not empty stations}">
                        <h5>Stations</h5>
                        <span style="color:red; display: block;"><c:out value="${restrictDelete}"/></span>
                        <div class="pagination">
                            <c:choose>
                                <c:when test="${currentPage != 1}">
                                    <a href="/worker/stations?page=${currentPage - 1}">&laquo;</a>
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
                                                    <c:forEach begin="${currentPage - 3}" end="${currentPage + 3}"
                                                               var="i">
                                                        <c:choose>
                                                            <c:when test="${currentPage eq i}">
                                                                <a href="#" class="active">${i}</a>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a href="/worker/stations?page=${i}">${i}</a>
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
                                                                <a href="/worker/stations?page=${i}">${i}</a>
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
                                                        <a href="/worker/stations?page=${i}">${i}</a>
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
                                                <a href="/worker/stations?page=${i}">${i}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>

                            <c:choose>
                                <c:when test="${currentPage lt navPagesQty}">
                                    <a href="/worker/stations?page=${currentPage + 1}">&raquo;</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="invisible-link">&raquo;</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <table class="table table-striped table-hover" style="width: 100%; min-width: 500px">
                            <thead class="thead-light">
                            <tr>
                                <th>Station Name</th>
                                <th></th>
                            </tr>
                            </thead>
                            <c:forEach items="${stations}" var="station">
                                <tr>
                                    <td>${station.name}</td>
                                    <td><a href="/worker/stations/${station.id}/update">Edit</a> |
                                        <a href="/worker/stations/${station.id}/delete"
                                           onclick="return confirmDelete(this, '${pageContext.request.contextPath}/worker/station/${station.id}/delete')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
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