const btnAddToBudget = document.getElementById("btn-add-to-budget");
const inputBudget = document.getElementById("input-budget");
const btnSubmitBudget = document.getElementById("btn-submit-budget");
const moneyElements = document.getElementsByClassName("money");


for (let i = 0; i < moneyElements.length; i++) {
    toMoneyFormat(moneyElements[i]);
}

function toMoneyFormat(e) {
    let text;
    if (e.nodeName === "INPUT")
        text = e.value;
    else
        text = e.innerText;

    if (Number(text) < 0)
        e.classList.add("negMoney");
    else if (Number(text) > 0)
        e.classList.add("posMoney");
    else
        e.classList.add("zerMoney");

    text = (Math.round((Number(text)) * 100) / 100).toLocaleString("ru");
    if (e.nodeName === "INPUT")
        e.value = text.replace(",", ".").replace(/\s/, "");
    else
        e.innerText = text;
}


function editBudget() {
    btnAddToBudget.style.display = "none";
    inputBudget.style.display = "block";
    btnSubmitBudget.style.display = "inline";
}

function submitBudget() {
    if (inputBudget.value === '')
        inputBudget.value = 0;
}

function cuttingDecimalPlaces(e) {
    if (e.value.indexOf(".") !== -1)
        e.value = e.value.substring(0, e.value.indexOf(".") + 3);
}
