var inputDate = document.getElementById("input-date");
var inputBuy = document.getElementById("input-buy");
var inputSale = document.getElementById("input-sale");

inputDate.valueAsDate = new Date();
inputBuy.value = "";
inputSale.value = "";

function clickChange(id) {
    var inputDate = document.getElementById("input-date" + id);
    var inputDesc = document.getElementById("input-desc" + id);
    var inputBuy = document.getElementById("input-buy" + id);
    var inputSale = document.getElementById("input-sale" + id);

    var btnChange = document.getElementById("btn-change" + id);
    var btnSubmit = document.getElementById("btn-submit" + id);

    inputDate.removeAttribute("readOnly");
    inputDesc.removeAttribute("readOnly");
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

    if (inputBuy.value > 0)
        inputBuy.value = inputBuy.value * -1;
}
//
// function clickOrderChange(id) {
//     var btnOrderChange = document.getElementById("btn-order-change" + id);
//     var btnOrderSubmit = document.getElementById("btn-order-submit" + id);
//     var inputOrderDesc = document.getElementById("input-order-desc" + id);
//     var inputOrderBuy = document.getElementById("input-order-buy" + id);
//     var inputOrderSale = document.getElementById("input-order-sale" + id);
//     var inputOrderBalance = document.getElementById("input-order-balance" + id);
//
//     btnOrderChange.style.display = "none";
//     btnOrderSubmit.style.display = "inline";
//
//     inputOrderDesc.removeAttribute("readOnly");
//     inputOrderBuy.removeAttribute("hidden");
//     inputOrderSale.removeAttribute("hidden");
//     inputOrderBalance.setAttribute("hidden", "hidden");
// }
