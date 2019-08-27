var btnAddToBudget = document.getElementById("btn-add-to-budget");
var inputBudget = document.getElementById("input-budget");
var btnSubmitBudget = document.getElementById("btn-submit-budget");

var moneyElements = document.getElementsByClassName("money");


/* Go backBtn by backspace pressing */
document.addEventListener("keydown", function (ev) {
    if (ev.key.charCodeAt(8)) {
        var inputs = document.getElementsByTagName("input");
        var divs = document.getElementsByTagName("div");
        for (var i = 0; i < inputs.length; i++)
            if (inputs[i] === document.activeElement)
                return;
        for (i = 0; i < divs.length; i++)
            if (divs[i].getAttribute("contentEditable") === "true")
                return;
        if (window.location.href.indexOf("calculator") + 1) {
            window.history.back();
            return;
        }
        goto("../");
    }
});


for (var i = 0; i < moneyElements.length; i++) {
    toMoneyFormat(moneyElements[i]);
}

function toMoneyFormat(e) {
    var text;
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
    btnSubmitBudget.style.display = "block";
}

function submitBudget() {
    if (inputBudget.value === '')
        inputBudget.value = 0;
}


function goto(url) {
    window.location.href = url;
}

function cuttingDecimalPlaces(e) {
    if (e.value.indexOf(".") !== -1)
        e.value = e.value.substring(0, e.value.indexOf(".") + 3);
}
