var inputDate = document.getElementById("input-date");
var inputBuy = document.getElementById("input-buy");
var inputSale = document.getElementById("input-sale");
var selectNegative = document.getElementById("select-negative");
var inputNoteValue = document.getElementById("input-note-value");

inputDate.valueAsDate = new Date();
inputNoteValue ? inputNoteValue.value = "" : null;

inputBuy ? inputBuy.value = "" : null;
inputSale ? inputSale.value = "" : null;

function clickChange(id) {
    var inputDate = document.getElementById("input-date" + id);
    var inputBuy = document.getElementById("input-buy" + id);
    var inputSale = document.getElementById("input-sale" + id);
    var inputDesc = document.getElementById("input-desc" + id);
    var inputValue = document.getElementById("input-value" + id);

    var BtnChange = document.getElementById("btn-change" + id);
    var btnSubmit = document.getElementById("btn-submit" + id);

    inputDate.removeAttribute("readOnly");
    inputDesc.removeAttribute("readOnly");
    inputBuy ? inputBuy.removeAttribute("readOnly") : null;
    inputSale ? inputSale.removeAttribute("readOnly") : null;
    inputValue ? inputValue.removeAttribute("readOnly") : null;

    BtnChange.style.display = "none";
    btnSubmit.style.display = "block";
}

function clickSubmit() {
    if (selectNegative != null) {
        if (inputNoteValue.value === '')
            inputNoteValue.value = 0;
        if (selectNegative.selectedIndex === 0 && inputNoteValue.value > 0 || selectNegative.selectedIndex === 1 && inputNoteValue.value < 0)
            inputNoteValue.value = inputNoteValue.value * -1;
    }

    if (inputBuy != null) {
        if (inputBuy.value === '')
            inputBuy.value = 0;

        if (inputSale.value === '')
            inputSale.value = 0;

        if (inputBuy.value > 0)
            inputBuy.value = inputBuy.value * -1;
    }
}
