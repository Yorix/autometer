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
        if (window.parent == null)
            goto("../");
        else
            window.parent.location.href = window.parent.location.href + "../";
    }
});


function goto(url) {
    window.parent.location.href = url;
}

function cuttingDecimalPlaces(e) {
    if (e.value.indexOf(".") !== -1)
        e.value = e.value.substring(0, e.value.indexOf(".") + 3);
}


var updatedSpan = document.getElementById("span-updated");
var updateErrorSpan = document.getElementById("span-update-error");
var updateBtn = document.getElementById("btn-update");
if (updatedSpan !== null && updateErrorSpan !== null && updateBtn !== null) {
    updatedSpan.style.setProperty("display", "none");
    updateErrorSpan.style.setProperty("display", "none");
    updateBtn.style.setProperty("display", "none");

    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/updates/");
    xhr.send();
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            if (xhr.response.startsWith("Already up to date"))
                updatedSpan.style.setProperty("display", "block");
            else if (xhr.response.startsWith("Updating"))
                updateBtn.style.setProperty("display", "block");
            else
                updateErrorSpan.style.setProperty("display", "block");
        }
    };
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