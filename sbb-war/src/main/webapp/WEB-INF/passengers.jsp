
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Passengers List</title>
        <link rel="stylesheet" type="text/css" href="/resources/style.css">
    </head>
    <body>
        <jsp:include page="menu.jsp"/>
        <div style="border: 3px outset steelblue; height: 70px; width: 40%; overflow:auto">
            <table style="width: 100%">
                <thead>
                    <tr>
                        <th style="min-width:20%">First Name</th>
                        <th style="min-width:20%">Last Name</th>
                        <th style="min-width:20%">Birthday</th>
                        <th style="min-width:20%"></th>
                        <th style="min-width:20%"></th>
                    </tr>
                </thead>
            <c:forEach items="${passengers}" var="passenger">
                <tr>
                    <td>${passenger.firstName}</td>
                    <td>${passenger.lastName}</td>
                    <td>${passenger.birthday}</td>
                    <td><a href="/passengers/${passenger.id}">Profile</a></td>
                    <td><a href="/passengers/${passenger.id}/update">Edit</a></td>
                </tr>
            </c:forEach>
            </table>
        </div>
    </body>
</html>