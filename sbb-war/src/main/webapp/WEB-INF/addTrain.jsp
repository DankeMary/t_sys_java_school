<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Add New Train</title>
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
                <h3>Enter The Train's Details</h3>
                <form:form method="post"
                           modelAttribute="trainForm"
                           onsubmit="return validateTrain(this, '${pageContext.request.contextPath}/worker/trains')"
                           autocomplete="off">
                    <div class="formFragment">
                        <form:label path="train.number"
                                    cssClass="fm-with-valid">Number</form:label>
                        <spring:bind path="train.number">
                            <input id="train-number"
                                   value="${status.value}"
                                   name="${status.expression}"
                                   class="fm-with-valid form-control"
                                   style="width: 150px; display: inline-block; height: 30px;"
                                   required>
                            <div class="form-group col-md-6" style="color: red; display: inline-block;">
                                <span id="js-number-error" style="display: block;"></span>
                                <div class="error-list">
                                    <span style="display: block;">${numberNonUnique}</span>
                                    <c:if test="${status.error}">
                                        <c:forEach items="${status.errorMessages}" var="error">
                                            <span style="display: block;">${error}</span>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                            </input>
                        </spring:bind>
                    </div>

                    <div class="formFragment">
                        <form:label path="train.capacity"
                                    cssClass="fm-with-valid">Capacity</form:label>
                        <spring:bind path="train.capacity">
                            <input id="train-capacity"
                                   value="${status.value}"
                                   name="${status.expression}"
                                   class="fm-with-valid form-control"
                                   style="width: 150px; display: inline-block; height: 30px;"
                                   required>
                            <div class="form-group col-md-6" style="color: red; display: inline-block;">
                                <span id="js-capacity-error" style="display: block;"></span>
                                <div class="error-list">
                                    <span style="display: block;">${capacityCannotUpdate}</span>
                                    <c:if test="${status.error}">
                                        <ul style="list-style-type:disc">
                                            <c:forEach items="${status.errorMessages}" var="error">
                                                <span style="display: block;">${error}</span>
                                            </c:forEach>
                                        </ul>
                                    </c:if>
                                </div>
                            </div>
                            </input>
                        </spring:bind>
                    </div>

                    <div class="formFragment">
                        <form:label path="train.price"
                                    cssClass="fm-with-valid">Price</form:label>
                        <spring:bind path="train.price">
                            <input type="number"
                                   id="train-price"
                                   min="1" step="0.01" max="7000"
                                   value="${status.value}"
                                   name="${status.expression}"
                                   class="fm-with-valid form-control"
                                   style="width: 150px; display: inline-block; height: 30px;"
                                   required>
                            <div class="form-group col-md-6" style="color: red; display: inline-block;">
                                <span id="js-price-error" style="display: block;"></span>
                                <div class="error-list">
                                    <c:if test="${status.error}">
                                        <c:forEach items="${status.errorMessages}" var="error">
                                            <span style="display: block;">${error}</span>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                            </input>
                        </spring:bind>
                    </div>

                    <h5>Add Train Path <span style="font-size: 14px">(Completely blank items are not counted)</span></h5>
                    <div class="error-list">
                        <span style="color:red; display: block;"><c:out value="${shortPath}"/></span>
                        <span style="color:red; display: block;"><c:out value="${wrongPath}"/></span>
                        <span style="color:red; display: block;"><c:out value="${dataMissing}"/></span>
                        <span style="color:red; display: block;"><c:out value="${invalidStations}"/></span>
                    </div>
                    <div class="formFragment">
                        <table id="pathRoutes" class="table" style="width: 100%; min-width: 500px">
                            <thead class="thead-light">
                            <tr>
                                <th>Station</th>
                                <th>Arrival</th>
                                <th>Departure</th>
                            </tr>
                            </thead>
                            <tbody>
                            <div id="parent">
                            <c:forEach begin="0" end="${routesQty - 1}" var="i" varStatus="status">
                                <tr>
                                    <td>
                                        <spring:bind path='trainForm.primitivePath[${status.index}].stationName'>
                                            <input id='station-input-search-${i}'
                                                   value="${status.value}"
                                                   name="${status.expression}"
                                            class="autocompl-dropdown form-control"/>
                                        </spring:bind>
                                    </td>
                                    <td>
                                        <spring:bind path='trainForm.primitivePath[${status.index}].arrTime'>
                                            <input type='time' value="${status.value}" name="${status.expression}" class="form-control"/>
                                        </spring:bind>
                                    </td>
                                    <td>
                                        <spring:bind path='trainForm.primitivePath[${status.index}].depTime'>
                                            <input type='time' value="${status.value}" name="${status.expression}" class="form-control"/>
                                        </spring:bind>
                                    </td>
                                </tr>
                            </c:forEach>
                            </div>
                            <tr id="addRouteRow"></tr>
                            </tbody>
                        </table>
                    </div>

                    <div style="text-align: right; margin-right: 10px;">
                        <input type="button"
                               id="addRouteButton"
                               value="Add Route"
                               style="background-color: #dde2e3; width: 100px;
                               border-radius: 5px; height: 38px;"/>
                    </div>

                    <div style="margin: auto;">
                        <input type="submit" value="Submit" class="submit-btn"/>
                    </div>
                </form:form>
            </div>
        </div>
        <div class="col">
        </div>
    </div>
</div>
<script src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/js/utils.js" />"></script>
<script src="<c:url value="/resources/js/validation.js" />"></script>
<script src="<c:url value="/resources/js/popper.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</body>
</html>
