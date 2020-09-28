const inputDate = document.getElementById("input-date");
const inputBuy = document.getElementById("input-buy");
const inputSale = document.getElementById("input-sale");
const selectNegative = document.getElementById("select-negative");
const inputNoteValue = document.getElementById("input-note-value");

inputDate.valueAsDate = new Date();
inputNoteValue ? inputNoteValue.value = "" : null;

inputBuy ? inputBuy.value = "" : null;
inputSale ? inputSale.value = "" : null;

function clickChange(id) {
    const inputsRO = document.getElementsByClassName("read-only-input");
    const inputsROId = [];
    Array.from(inputsRO).forEach(function (item) {
        if (item.id.split("-").pop() === id.toString())
            inputsROId.push(item);
    });

    const BtnChange = document.getElementById("btn-change-".concat(id));
    const btnSubmit = document.getElementById("btn-submit-".concat(id));

    inputsROId.forEach(function (item) {
        item.classList.remove("read-only-input");
        item.removeAttribute("readonly")
    });

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
