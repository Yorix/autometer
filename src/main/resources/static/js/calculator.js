var yearOfIssueInput = document.getElementById("input-year-of-issue");
var volumeInput = document.getElementById("input-volume");
var additionalSumInput = document.getElementById("input-additional-sum");
var usdInput = document.getElementById("input-usd");
var eurInput = document.getElementById("input-eur");

var dutyDiv = document.getElementById("div-duty");
var exciseDiv = document.getElementById("div-excise");
var vatDiv = document.getElementById("div-vat");
var totalDiv = document.getElementById("div-total");

var allInputs = document.getElementsByTagName("input");
for (var i = 0; i < allInputs.length; i++) {
    allInputs[i].addEventListener("input", calculate);
}

function calculate() {
    if (yearOfIssueInput.value === "")
        yearOfIssueInput.value = 2019;
    if (yearOfIssueInput.value > new Date().getFullYear())
        yearOfIssueInput.value = 2019;
    var age = new Date().getFullYear() - yearOfIssueInput.value - 1;
    if (age < 1) age = 1;

    var duty = additionalSumInput.value * usdInput.value / 10;
    var factor;
    volumeInput.value > 3 ? factor = 75 : factor = 50;
    var excise = volumeInput.value * age * factor * eurInput.value;
    var vat = (Number(additionalSumInput.value) * Number(usdInput.value) + duty + excise) * .2;
    var total = duty + excise + vat;

    dutyDiv.innerText = duty.toString();
    exciseDiv.innerText = excise.toString();
    vatDiv.innerText = vat.toString();
    totalDiv.innerText = total.toString();

    toMoneyFormat(dutyDiv);
    toMoneyFormat(exciseDiv);
    toMoneyFormat(vatDiv);
    toMoneyFormat(totalDiv);
}

