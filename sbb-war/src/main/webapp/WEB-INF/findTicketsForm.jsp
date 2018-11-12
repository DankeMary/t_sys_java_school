<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form method="get"
      action="/trains/find">
    <span style="color: red">
        <c:choose>
            <c:when test="${not empty dataError}">
                ${dataError}
            </c:when>
        </c:choose>
    </span>
    <div class="formFragment">
        <label name="fromDay">Min Day</label>
        <input type="date" name="fromDay" value="${fromDay}"/>
    </div>
    <div class="formFragment">
        <label path="toDay">Max Day</label>
        <input type="date" name="toDay" value="${toDay}"/>
    </div>

    <div class="formFragment">
        <label path="fromTime">Min Time</label>
        <input type='time' name="fromTime" value="${fromTime}"/>
    </div>

    <div class="formFragment">
        <label path="toTime">Max Time</label>
        <input type='time' name="toTime" value="${toTime}"/>
    </div>
    <div class="formFragment">
        <input type="text" placeholder="Station Name" id="station-input-search1" value="${fromStation}"
               name="fromStation" onchange='findTicketsAutoCompl(this);'>
    </div>
    <div class="formFragment">
        <input type="text" placeholder="Station Name" id="station-input-search2" value="${toStation}"
               name="toStation" onchange='findTicketsAutoCompl(this);'>
    </div>
    <div style="margin: auto;">
        <input type="submit" value="Submit" id="submit" class="submit-btn"/>
    </div>
</form>
