//Commented code is to be removed later

$(document).ready(function () {
    $("#station-input-search").autocomplete({
        minLength: 1,
        source: function (request, response) {
            $.ajax({
                beforeSend: function (request) {
                    request.setRequestHeader("Accept", "application/json");
                },
                url: "/getStationsForTrain?stationName=" + request.term,
                dataType: "json",
                success: function (data) {
                    response($.map($.parseJSON(JSON.stringify(data)), function (item) {
                        return {label: item.name, value: item.name};//value: item.id};
                    }));
                },
                error: function (error) {
                    console.log(error.prototype.message);
                    alert('search error');
                }
            });
        }
    });
});

function confirmDelete(delObj, delUrl) {
    if (confirm("Are you sure ?")) {
        delObj.action = delUrl;
        return true;
    }
    return false;
}

$(document).ready(function () {
    let psngrIndex = $('#journeyPassengers tbody').children().length - 1;
    if (psngrIndex >= 10) {
        $('#addPassengerButton').attr("disabled", true);
    }
});
$(document).ready(function () {
    $("#addPassengerButton").click(function () {
        let psngrIndex = $('#journeyPassengers tbody').children().length - 1;
        $("#addPassengerRow").before(
            "<tr>" +
            "<td><input name='passengers[" + psngrIndex + "].firstName'/></td>" +

            "<td><input name='passengers[" + psngrIndex + "].lastName'/></td>" +

            "<td><input name='passengers[" + psngrIndex + "].birthday' type='date' /></td>" +
            "</tr>");
        if (psngrIndex >= 10) {
            $('#addPassengerButton').attr("disabled", true);
        }
    });
});

$(document).ready(function () {
    $("#addRouteButton").click(function () {
        let routeIndex = $('#pathRoutes tbody').children().length - 1;
        $("#addRouteRow").before(
            "<tr>" +
            "<input name='primitivePath[" + routeIndex + "].orderIndex' type='hidden' value='" + routeIndex + "'>" +
            "<td><input name='primitivePath[" + routeIndex + "].station.name' class='hehe' id='station-input-search-" + routeIndex + "'/></td>" +

            "<td><input name='primitivePath[" + routeIndex + "].arrTime' type='time' /></td>" +

            "<td><input name='primitivePath[" + routeIndex + "].depTime' type='time' /></td>" +
            "</tr>");

        console.log(routeIndex);
        $(document).on("change", "#station-input-search-" + routeIndex + "" , createTrainAutoCompl("#station-input-search-" + routeIndex + ""));
    });
    let routeIndex = $('#pathRoutes tbody').children().length - 2;
    console.log(routeIndex);
    $(document).on("change", "#station-input-search-" + routeIndex + "" , createTrainAutoCompl("#station-input-search-" + routeIndex + ""));
});

$(document).ready(function () {
    $("#station-input-search1").autocomplete({
        minLength: 1,
        source: function (request, response) {
            $.ajax({
                beforeSend: function (request) {
                    request.setRequestHeader("Accept", "application/json");
                },
                url: "/getStationsForTrain?stationName=" + request.term,
                dataType: "json",
                success: function (data) {
                    response($.map($.parseJSON(JSON.stringify(data)), function (item) {
                        return {label: item.name, value: item.name};
                    }));
                },
                error: function (error) {
                    console.log(error.prototype.message);
                    alert('search error');
                }
            });
        }
    });
});
$(document).ready(function () {
    $("#station-input-search2").autocomplete({
        minLength: 1,
        source: function (request, response) {
            $.ajax({
                beforeSend: function (request) {
                    request.setRequestHeader("Accept", "application/json");
                },
                url: "/getStationsForTrain?stationName=" + request.term,
                dataType: "json",
                success: function (data) {
                    response($.map($.parseJSON(JSON.stringify(data)), function (item) {
                        return {label: item.name, value: item.name};
                    }));
                },
                error: function (error) {
                    console.log(error.prototype.message);
                    alert('search error');
                }
            });
        }
    });
});

function createTrainAutoCompl(obj) {
    $(obj).autocomplete({
        minLength: 1,
        source: function (request, response) {
            $.ajax({
                beforeSend: function (request) {
                    request.setRequestHeader("Accept", "application/json");
                },
                url: "/getStationsForTrain?stationName=" + $(obj).val(),
                dataType: "json",
                success: function (data) {
                    response($.map($.parseJSON(JSON.stringify(data)), function (item) {
                        return {label: item.name, value: item.name};
                    }));
                },
                error: function (error) {
                    console.log(error.prototype.message);
                    alert('search error');
                }
            });
        }
    });
}

