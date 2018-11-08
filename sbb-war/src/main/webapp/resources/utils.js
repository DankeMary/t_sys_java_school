//Commented code is to be removed later

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
    $("#addRouteButton").click(function () {
        let routeIndex = $('#pathRoutes tbody').children().length - 1;
        $("#addRouteRow").before(
            "<tr>" +
            "<input name='primitivePath[" + routeIndex + "].orderIndex' type='hidden' value='" + routeIndex + "'>" +
            "<td><input name='primitivePath[" + routeIndex + "].station' onchange='createTrainAutoCompl(this);' id='station-input-search-" + routeIndex + "'/></td>" +

            "<td><input name='primitivePath[" + routeIndex + "].arrival' type='time' /></td>" +

            "<td><input name='primitivePath[" + routeIndex + "].departure' type='time' /></td>" +
            "</tr>");
    });
});

function createTrainAutoCompl(obj) {
    $("#" + $(obj).attr("id")).autocomplete({
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
}

/*

function add() {
    let valid = true;

    let stationName = $("#station-input-search2").val();
    if ($.trim(stationName) === "") {
        $('#station-input-search2').css('border-color', 'red');
        valid = false;
    }
    else {
        $('#station-input-search2').css('border-color', '');
    }

    let timeArr = $("#timeArr").val();
    if (timeArr === "") {
        $('#timeArr').css('border-color', 'red');
        valid = false;
    }
    else {
        $('#timeArr').css('border-color', '');
    }

    let timeDep = $("#timeDep").val();
    if (timeDep === "") {
        $('#timeDep').css('border-color', 'red');
        valid = false;
    }
    else {
        $('#timeDep').css('border-color', '');
    }
    if (valid) {
        $.ajax({
            type: "POST",
            url: "/addStationToList",
            data: "stationName=" + stationName + "&timeArr=" + timeArr + "&timeDep=" + timeDep,
            success: function (data) {
                console.log("success");
                $("#station-input-search2").val("");
                $("#timeArr").val("");
                $("#timeDep").val("");
            },
            error: function (error) {
                alert('search error');
            }
        });
    }
    return false;
}
*/
/*$(document).ready(function () {
    $("#train-input-search").autocomplete({
        minLength: 1,
        source: function (request, response) {
            $.ajax({
                beforeSend: function (request) {
                    request.setRequestHeader("Accept", "application/json");
                },
                url: "/getTrains?trainNumber=" + request.term,
                dataType: "json",
                success: function (data) {
                    response($.map($.parseJSON(JSON.stringify(data)), function (item) {
                        return {label: item.number, value: item.id};
                    }));
                },
                error: function (error) {
                    console.log(error);
                    alert('search error');
                }
            });
        },
        select: function (event, ui) {
            event.preventDefault();
            $("#train-input-search").val(ui.item.label);
        },
    });
});

$(document).ready(function () {
    $(".station-input-search").autocomplete({
        minLength: 1,
        source: function (request, response) {
            $.ajax({
                beforeSend: function (request) {
                    request.setRequestHeader("Accept", "application/json");
                },
                url: "/getStations?stationName=" + request.term,
                dataType: "json",
                success: function (data) {
                    response($.map($.parseJSON(JSON.stringify(data)), function (item) {
                        return {label: item.name, value: item.id};
                    }));
                },
                error: function (error) {
                    console.log(error);
                    alert('search error');
                }
            });
        }
    });
});*/

