
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>${passenger.firstName}</h2>

    <c:forEach items="${list}" var="item">
        ${item.firstName}<br>
        ${item.lastName}<br>
        ${item.birthday}<br>
    </c:forEach>
</body>
</html>
