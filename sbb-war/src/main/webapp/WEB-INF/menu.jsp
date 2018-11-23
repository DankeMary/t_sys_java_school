<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/resources/style.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light" style="background-color: #e3f2fd;">
    <a class="navbar-brand" href="/">SBB</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
        <th width="100"></th>
    </sec:authorize>
    <sec:authorize access="hasRole('ADMIN')">
        <th width="100"></th>
    </sec:authorize>
    <%--<div class="authbar">
        <span>Dear , Welcome.</span> <span class="floatRight"><a href="<c:url value="/logout" />">Logout</a></span>
    </div>--%>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle navbarDropdown" href="#"  role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Passengers
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="/passengers">View All</a>
                    <a class="dropdown-item" href="/passengers/add">Add New</a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle navbarDropdown" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Stations
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="/worker/stations">View All</a>
                    <a class="dropdown-item" href="/worker/stations/add">Add New</a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle navbarDropdown" href="#"  role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Trains
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="/worker/trains">View All</a>
                    <a class="dropdown-item" href="/worker/trains/add">Add New</a>
                </div>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link" href="/schedule"  role="button" aria-haspopup="true" aria-expanded="false">
                    Schedule
                </a>
            </li>
            <li class="nav-item dropdown">
                <a class="nav-link" href="/trains/find" role="button" aria-haspopup="true" aria-expanded="false">
                    Buy Tickets
                </a>
            </li>
        </ul>
        <div class="pull-right">
            <ul class="navbar-nav">
                <><><><><here>
                //TODO
                <li><strong>${loggedinuser}</strong></li>
                <li><a class="nav-link nav-btn" role="button" href="/logout">Log Out</a></li>
            </ul>
        </div>
    </div>
</nav>
</body>
</html>
