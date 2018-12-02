<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form method="get"
      action="/trains/find">
    <span style="color: red; display: block;">
        <c:choose>
            <c:when test="${not empty dataError}">
                ${dataError}
            </c:when>
        </c:choose>
    </span>
    <div class="formFragment">
        <label name="fromDay">Min Day</label>
        <input type="date" name="fromDay" value="${fromDay}" class=" form-control" style="width: 150px; display:inline-block;" required/>

        <label path="fromTime">Min Time</label>
        <input type='time' name="fromTime" value="${fromTime}" class=" form-control" style="width: 100px; display:inline-block;" required/>
    </div>

    <div class="formFragment">
        <label path="toDay">Max Day</label>
        <input type="date" name="toDay" value="${toDay}" class=" form-control" style="width: 150px; display:inline-block;" required/>

        <label path="toTime">Max Time</label>
        <input type='time' name="toTime" value="${toTime}" class=" form-control" style="width: 100px; display:inline-block;" required/>
    </div>
    <div class="formFragment">
        <label path="fromStation">Stations</label>
        <input type="text" placeholder="From" id="station-input-search1" value="${fromStation}"
               name="fromStation" onchange='findTicketsAutoCompl(this);' class=" form-control" style="width: 200px; display:inline-block;" required>

        <input type="text" placeholder="To" id="station-input-search2" value="${toStation}"
               name="toStation" onchange='findTicketsAutoCompl(this);' class=" form-control" style="width: 200px; display:inline-block;" required>
    </div>
    <div style="margin: auto;">
        <input type="submit" value="Submit" id="submit" class="submit-btn"/>
    </div>
</form>

<script src="<c:url value="/resources/js/jquery-3.3.1.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery-ui.js" />"></script>
<script src="<c:url value="/resources/js/utils.js" />"></script>
