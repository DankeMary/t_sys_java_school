<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
    <title>Create Station</title>
    <link rel="stylesheet" type="text/css" href="/resources/style.css">
</head>
<body>
<h3>Edit The Station Details</h3>
<form:form method="POST"
           action="/stations/${stationForm.id}"
           modelAttribute="stationForm">
    <table>
        <tr>
            <td><form:label path="name">Name</form:label></td>
            <td>
                <spring:bind path="name">
                    <input value="${status.value}" name="${status.expression}">
                        <c:if test="${status.error}">
                            <c:forEach items="${status.errorMessages}" var="error">
                                <c:out value="${error}"/>
                            </c:forEach>
                        </c:if>
                    </input>
                </spring:bind>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
