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


function goto(url) {
    window.location.href = url;
}

function cuttingDecimalPlaces(e) {
    if (e.value.indexOf(".") !== -1)
        e.value = e.value.substring(0, e.value.indexOf(".") + 3);
}


var moneyElements = document.getElementsByClassName("money");
for (var i = 0; i < moneyElements.length; i++) {
    toMoneyFormat(moneyElements[i]);
}

function toMoneyFormat(e) {
    var text = e.innerText;
    if (Number(text) < 0) {
        e.classList.add("negMoney");
    } else if (Number(text) > 0) {
        e.classList.add("posMoney");
    } else {
        e.classList.add("zerMoney");
    }
    e.innerText = (Math.round((Number(text)) * 100) / 100).toLocaleString("ru");
}

function exitProgram() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/exit/");
    xhr.send();
    xhr.onreadystatechange = function () {
        window.close();
    };
}


/* Edit budget */
var btnAddToBudget = document.getElementById("btn-add-to-budget");
var inputBudget = document.getElementById("input-budget");
var btnSubmitBudget = document.getElementById("btn-submit-budget");

function editBudget() {
    btnAddToBudget.style.display = "none";
    inputBudget.style.display = "block";
    btnSubmitBudget.style.display = "block";
}

function submitBudget(budget) {
    btnAddToBudget.style.display = "block";
    inputBudget.style.display = "none";
    btnSubmitBudget.style.display = "none";

    var formData = new FormData();
    formData.append("value", Number(inputBudget.value) + budget);

    var xhr = new XMLHttpRequest();
    xhr.open("PUT", "/params/budget/");
    xhr.send(formData);
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200)
            window.location.reload();
    }
}


/* Add new note */
var noteDateInput = document.getElementById("input-note-date");
var noteDescriptionInput = document.getElementById("input-note-desc");
var negativeSelect = document.getElementById("select-negative");
var noteValueInput = document.getElementById("input-note-value");

noteDateInput.valueAsDate = new Date();
noteValueInput.value = "";

function noteSubmit(carId) {
    if (noteValueInput.value.length > 0) {
        if (negativeSelect.selectedIndex === 0)
            noteValueInput.value = noteValueInput.value * -1;
        var date = new Date(noteDateInput.value);
        var options = {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        };
        var dateStr = date.toLocaleString("ru", options);
        dateStr = dateStr.replace(" г.", "");
        var formData = new FormData();
        formData.append("description", noteDescriptionInput.value);
        formData.append("value", noteValueInput.value);
        formData.append("date", dateStr);
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/cars/" + carId + "/notes/");
        xhr.send(formData);

        xhr.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200)
                window.location.reload();
        }
    }
}