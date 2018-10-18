<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <title>Passenger Profile</title>
        <link rel="stylesheet" type="text/css" href="/resources/style.css">
    </head>
    <body>
        <!--<jsp:include page="menu.jsp"/>-->
        <div>
            <table>
                <thead>
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Birthday</th>
                </tr>
                </thead>
                <tr>
                    <td>${passenger.firstName}</td>
                    <td>${passenger.lastName}</td>
                    <td>${passenger.birthday}</td>
                </tr>
            </table>

            <form:form name="delete" method="post" modelAttribute="passenger"
                       onsubmit="return confirmDelete(this, '${pageContext.request.contextPath}/passengers/${passenger.id}/delete')">

                <input type="submit" value="DELETE" />
            </form:form>

            <script>
                function confirmDelete(delForm, delUrl) {
                if (confirm("Are you sure ?")) {
                delForm.action = delUrl;
                return true;
                }
                return false;
                }
            </script>
        </div>
    </body>
</html>