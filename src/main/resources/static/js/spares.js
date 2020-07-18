var spareDateInput = document.getElementById("input-spare-date");
var spareBuyInput = document.getElementById("input-spare-buy");
var spareSaleInput = document.getElementById("input-spare-sale");

spareDateInput.valueAsDate = new Date();
spareBuyInput.value = "";
spareSaleInput.value = "";

function clickChange(id) {
    var dateInput = document.getElementById("input-date" + id);
    var descInput = document.getElementById("input-name" + id);
    var valueInput = document.getElementById("input-price" + id);

    var changeBtn = document.getElementById("btn-change" + id);
    var submitBtn = document.getElementById("btn-submit" + id);

    dateInput.removeAttribute("readOnly");
    descInput.removeAttribute("readOnly");
    valueInput.removeAttribute("readOnly");

    changeBtn.style.display = "none";
    submitBtn.style.display = "block";
}

function spareSubmit() {
    if (spareBuyInput.value === '')
        spareBuyInput.value = 0;

    if (spareSaleInput.value === '')
        spareSaleInput.value = 0;

    spareBuyInput.value = spareBuyInput.value * -1;
}
