var dateOfIssueInput = document.getElementById("input-date-of-issue");
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
    dutyDiv.innerText = additionalSumInput.value * usdInput.value / 10;


    toMoneyFormat(dutyDiv);
    toMoneyFormat(exciseDiv);
    toMoneyFormat(vatDiv);
    toMoneyFormat(totalDiv);
}

