<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <title>Create Passenger</title>
    <link rel="stylesheet" type="text/css" href="/resources/style.css">
</head>
<body>
    <h3>Enter The Passenger Details</h3>
    <form:form method="POST"
               action="/passengers"
               modelAttribute="passengerForm">
        <table>
            <tr>
                <td><form:label path="firstName">First Name</form:label></td>
                <td><form:input path="firstName"/></td>
                <form:errors path="firstName" cssClass="error" element="div"/>
            </tr>
            <tr>
                <td><form:label path="lastName">Last Name</form:label></td>
                <td><form:input path="lastName"/></td>
                <form:errors path="lastName" cssClass="error" element="div"/>
            </tr>
            <tr>
                <td><form:label path="birthday">Birthday</form:label></td>
                <td><form:input type="date" path="birthday" /></td>
                <form:errors path="birthday" cssClass="error" element="div"/>
            </tr>
            <tr>
                <td><input type="submit" value="Submit"/></td>
            </tr>
        </table>
    </form:form>
</body>
</html>
