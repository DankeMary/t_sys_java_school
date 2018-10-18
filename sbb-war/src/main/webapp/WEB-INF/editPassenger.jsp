
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <head>
        <title>Edit Passenger</title>
        <link rel="stylesheet" type="text/css" href="/resources/style.css">
    </head>
    <body>
        <h3>Edit The Passenger Details</h3>
        <form:form method="POST"
                   action="/passengers/${passengerForm.id}"
                   modelAttribute="passengerForm">
            <table>
                <tr>
                    <td><form:label path="firstName">First Name</form:label></td>
                    <td>
                        <spring:bind path="firstName">
                            <input value="${status.value}" name="${status.expression}">
                                <c:if test="${status.error}">
                                    Error codes:
                                    <c:forEach items="${status.errorMessages}" var="error">
                                        <c:out value="${error}"/>
                                    </c:forEach>
                                </c:if>
                            </input>
                        </spring:bind>
                    </td>
                    <form:errors path="firstName" cssClass="error" element="div"/>
                </tr>
                <tr>
                    <td><form:label path="lastName">Last Name</form:label></td>
                    <td>
                        <spring:bind path="lastName">
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
