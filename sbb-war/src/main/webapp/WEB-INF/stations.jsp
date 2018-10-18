
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Stations</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css"/>"/>
</head>
<body>
<!--<jsp:include page="menu.jsp"/>-->
<a href="/passengers/add">Add Passenger  </a>
<a href="/stations/add">  Add Station</a>
<c:choose>
    <c:when test="${empty stations}">
        No stations here yet :(
    </c:when>
    <c:when test="${not empty stations}">
        <div style="border: 3px outset steelblue; height: 70px; width: 30%; overflow:auto">
            <table>
                <thead>
                <tr>
                    <th style="min-width:25%">Station Name</th>
                    <th style="min-width:25%"></th>
                    <th style="min-width:25%"></th>
                </tr>
                </thead>
                <c:forEach items="${stations}" var="station">
                    <tr>
                        <td>${station.name}</td>
                        <td><a href="/stations/${station.id}/update">Edit</a></td>
                        <td>
                            <form:form name="delete" method="post" modelAttribute="station"
                                   onsubmit="return confirmDelete(this, '${pageContext.request.contextPath}/stations/${station.id}/delete')">
                                <input type="submit" value="DELETE" />
                            </form:form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:when>
</c:choose>
<script>
    function confirmDelete(delForm, delUrl) {
        if (confirm("Are you sure ?")) {
            delForm.action = delUrl;
            return true;
        }
        return false;
    }
</script>
</body>
</html>