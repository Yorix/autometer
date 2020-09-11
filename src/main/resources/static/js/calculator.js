const yearOfIssueInput = document.getElementById("input-year-of-issue");
const volumeInput = document.getElementById("input-volume");
const additionalSumInput = document.getElementById("input-additional-sum");
const usdInput = document.getElementById("input-usd");
const eurInput = document.getElementById("input-eur");

const dutyDiv = document.getElementById("div-duty");
const exciseDiv = document.getElementById("div-excise");
const vatDiv = document.getElementById("div-vat");
const totalDiv = document.getElementById("div-total");

const allInputs = document.getElementsByTagName("input");
for (let i = 0; i < allInputs.length; i++) {
    allInputs[i].addEventListener("input", calculate);
}

function calculate() {
    if (yearOfIssueInput.value === "")
        yearOfIssueInput.value = 2019;
    if (yearOfIssueInput.value > new Date().getFullYear())
        yearOfIssueInput.value = 2019;
    let age = new Date().getFullYear() - yearOfIssueInput.value - 1;
    if (age < 1) age = 1;

    const duty = additionalSumInput.value * usdInput.value / 10;
    let factor;
    volumeInput.value > 3 ? factor = 75 : factor = 50;
    const excise = volumeInput.value * age * factor * eurInput.value;
    const vat = (Number(additionalSumInput.value) * Number(usdInput.value) + duty + excise) * .2;
    const total = duty + excise + vat;

    dutyDiv.innerText = duty.toString();
    exciseDiv.innerText = excise.toString();
    vatDiv.innerText = vat.toString();
    totalDiv.innerText = total.toString();

    toMoneyFormat(dutyDiv);
    toMoneyFormat(exciseDiv);
    toMoneyFormat(vatDiv);
    toMoneyFormat(totalDiv);
}

