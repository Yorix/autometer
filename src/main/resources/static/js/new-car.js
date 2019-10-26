var formParseCar = document.getElementById("form-parse-car");
var formAddCar = document.getElementById("form-add-car");
var btnParseSubmit = document.getElementById("btn-parse-submit");
var btnAddSubmit = document.getElementById("btn-add-submit");
var inputUrl = document.getElementById("input-url");
var inputMake = document.getElementById("input-make");
var inputModel = document.getElementById("input-model");
var inputYear = document.getElementById("input-year");
var header = document.getElementById("header");

inputYear.value = new Date().getFullYear();

inputMake.addEventListener("keyup", input);
inputModel.addEventListener("keyup", input);
inputYear.addEventListener("keyup", input);
inputYear.addEventListener("click", input);


btnParseSubmit.addEventListener("click", function () {
    formParseCar.submit();
});

btnAddSubmit.addEventListener("click", function () {
    if (inputMake.value.length > 0 && inputModel.value.length > 0 && inputYear.value > 0)
        formAddCar.submit();
    else
        alert("Введите название марки, модели и год выпуска.");
});

function input() {
    header.textContent = inputMake.value + ' ' + inputModel.value + ' ' + inputYear.value;
}