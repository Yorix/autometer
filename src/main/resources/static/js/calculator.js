const yearOfIssueInput = document.getElementById("input-year-of-issue");
const volumeInput = document.getElementById("input-volume");
const additionalSumInput = document.getElementById("input-additional-sum");
const usdInput = document.getElementById("input-usd");
const eurInput = document.getElementById("input-eur");
const currentYear = new Date().getFullYear();

const dutyDiv = document.getElementById("div-duty");
const exciseDiv = document.getElementById("div-excise");
const vatDiv = document.getElementById("div-vat");
const totalDiv = document.getElementById("div-total");
const totalUsdDiv = document.getElementById("div-total-usd");

const allInputs = document.getElementsByTagName("input");
for (let i = 0; i < allInputs.length; i++) {
    allInputs[i].addEventListener("input", calculate);
}

yearOfIssueInput.value = currentYear;

function calculate() {
    if (yearOfIssueInput.value === "" || yearOfIssueInput.value > currentYear)
        yearOfIssueInput.value = currentYear;
    let age = new Date().getFullYear() - yearOfIssueInput.value - 1;
    if (age < 1) age = 1;

    const duty = additionalSumInput.value * usdInput.value / 10;
    let factor;
    volumeInput.value > 3 ? factor = 75 : factor = 50;
    const excise = volumeInput.value * age * factor * eurInput.value;
    const vat = (Number(additionalSumInput.value) * Number(usdInput.value) + duty + excise) * .2;
    const total = duty + excise + vat;
    const totalUsd = total / usdInput.value;

    dutyDiv.innerText = duty.toString();
    exciseDiv.innerText = excise.toString();
    vatDiv.innerText = vat.toString();
    totalDiv.innerText = total.toString();
    totalUsdDiv.innerText = totalUsd.toString();

    toMoneyFormat(dutyDiv);
    toMoneyFormat(exciseDiv);
    toMoneyFormat(vatDiv);
    toMoneyFormat(totalDiv);
    toMoneyFormat(totalUsdDiv);
}
