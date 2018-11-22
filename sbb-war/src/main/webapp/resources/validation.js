const JAVA_INTEGER_MAX = 2147483647;


function validateTrain(addForm, addUrl) {
    let trainNumber = document.getElementById("train-number");
    let trainCapcity = document.getElementById("train-capacity");
    let valid = true;

    $(".error-list").empty();
    document.getElementById("js-number-error").innerHTML = "";
    document.getElementById("js-capacity-error").innerHTML = "";

    let numbers = /^[0-9]+$/;

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
    if (valid) {
        console.log("valid");
        addForm.action = addUrl;
        return true;
    }
    return false;
}