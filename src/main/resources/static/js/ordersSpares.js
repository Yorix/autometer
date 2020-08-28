var inputDate = document.getElementById("input-date");
var inputBuy = document.getElementById("input-buy");
var inputSale = document.getElementById("input-sale");

inputDate.valueAsDate = new Date();
inputBuy.value = "";
inputSale.value = "";

function clickChange(id) {
    var inputDate = document.getElementById("input-date" + id);
    var descInput = document.getElementById("input-desc" + id);
    var inputBuy = document.getElementById("input-buy" + id);
    var inputSale = document.getElementById("input-sale" + id);

    var btnChange = document.getElementById("btn-change" + id);
    var btnSubmit = document.getElementById("btn-submit" + id);

    inputDate.removeAttribute("readOnly");
    descInput.removeAttribute("readOnly");
    inputBuy.removeAttribute("readOnly");
    inputSale.removeAttribute("readOnly");

    btnChange.style.display = "none";
    btnSubmit.style.display = "block";
}

function clickSubmit() {
    if (inputBuy.value === '')
        inputBuy.value = 0;

    if (inputSale.value === '')
        inputSale.value = 0;

    inputBuy.value = inputBuy.value * -1;
}
