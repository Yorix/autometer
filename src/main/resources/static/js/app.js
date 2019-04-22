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
    window.location.href = url;
}

function cuttingDecimalPlaces(e) {
    if (e.value.indexOf(".") !== -1)
        e.value = e.value.substring(0, e.value.indexOf(".") + 3);
}

var money = document.getElementsByClassName("money");
if (money !== null) {
    for (var i = 0; i < money.length; i++) {
        var text = money[i].innerText;

        if (Number(text) < 0) {
            money[i].classList.add("negMoney");
        } else if (Number(text) > 0) {
            money[i].classList.add("posMoney");
        } else {
            money[i].classList.add("zerMoney");
        }

        money[i].innerText = Number(text).toLocaleString("ru");
    }
}
