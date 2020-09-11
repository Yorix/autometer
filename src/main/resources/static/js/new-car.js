const formAddCar = document.getElementById("form-add-car");
const btnAddSubmit = document.getElementById("btn-add-submit");
const inputMake = document.getElementById("input-make");
const inputModel = document.getElementById("input-model");
const inputYear = document.getElementById("input-year");
const header = document.getElementById("header");

inputYear.value = new Date().getFullYear();

inputMake.addEventListener("keyup", input);
inputModel.addEventListener("keyup", input);
inputYear.addEventListener("keyup", input);
inputYear.addEventListener("click", input);


btnAddSubmit.addEventListener("click", function () {
    if (inputMake.value.length > 0 && inputModel.value.length > 0 && inputYear.value > 0)
        formAddCar.submit();
    else
        alert("Введите название марки, модели и год выпуска.");
});

function input() {
    header.textContent = inputMake.value + ' ' + inputModel.value + ' ' + inputYear.value;
}
