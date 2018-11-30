const JAVA_INTEGER_MAX = 2147483647;
const MAX_TICKET_PRICE = 7000;


function validateTrain(addForm, addUrl) {
    let trainNumber = document.getElementById("train-number");
    let trainCapcity = document.getElementById("train-capacity");
    let trainPrice = document.getElementById("train-price");
    let valid = true;

    $(".error-list").empty();
    document.getElementById("js-number-error").innerHTML = "";
    document.getElementById("js-capacity-error").innerHTML = "";
    document.getElementById("js-price-error").innerHTML = "";

    let numbers = /^[0-9]+$/;
    let money = /^[0-9]+(\.[0-9]{1,2})?$/;

    //Train Number
    if (trainNumber.value.length <= 0) {
        document.getElementById("js-number-error").innerHTML = "Train number is mandatory";
        valid = false;
    }
    else if (!trainNumber.value.match(numbers)) {
        document.getElementById("js-number-error").innerHTML = "Only digits are allowed";
        valid = false;
    }
    else if (trainNumber.value > JAVA_INTEGER_MAX) {
        document.getElementById("js-number-error").innerHTML = "Max train number - " + JAVA_INTEGER_MAX;
        valid = false;
    }

    //Train Capacity
    if (trainCapcity.value.length <= 0) {
        document.getElementById("js-capacity-error").innerHTML = "Train capacity is mandatory";
        valid = false;
    }
    else if (!trainCapcity.value.match(numbers)) {
        document.getElementById("js-capacity-error").innerHTML = "Only digits are allowed";
        valid = false;
    }
    else if (trainCapcity.value > JAVA_INTEGER_MAX) {
        document.getElementById("js-capacity-error").innerHTML = "Max train capacity - " + JAVA_INTEGER_MAX;
        valid = false;
    }

    //Ticket Price
    if (trainPrice.value.length <= 0) {
        document.getElementById("js-capacity-error").innerHTML = "Ticket price is mandatory";
        valid = false;
    }
    else if (!trainPrice.value.match(money)) {
        document.getElementById("js-capacity-error").innerHTML = "Wrong format! (Right Example: 13,55)";
        valid = false;
    }
    else if (trainPrice.value > MAX_TICKET_PRICE) {
        document.getElementById("js-capacity-error").innerHTML = "Max ticket price - " + MAX_TICKET_PRICE;
        valid = false;
    }

    if (valid) {
        addForm.action = addUrl;
        return true;
    }
    return false;
}

function validateStation(addForm, addUrl) {
    let stationName = document.getElementById("station-name");
    let valid = true;

    $(".error-list").empty();
    document.getElementById("js-name-error").innerHTML = "";

    let name = /^[a-zA-Z][a-zA-Z \-0-9]+$/;

    //Station Name
    if (stationName.value.length <= 0) {
        document.getElementById("js-name-error").innerHTML = "Station name is mandatory";
        valid = false;
    }
    else if (stationName.value.length < 2) {
        document.getElementById("js-name-error").innerHTML =
            "Station name has to have at least two symbols";
        valid = false;
    }
    else if (!stationName.value.match(name)) {
        document.getElementById("js-name-error").innerHTML =
            "Station name has to have at least one letter and can have only latin letters, digits, spaces and hyphens";
        valid = false;
    }

    if (valid) {
        addForm.action = addUrl;
        return true;
    }
    return false;
}