var form = document.getElementById("form-new-car");
var btnSubmit = document.getElementById("btn-submit");
var inputMake = document.getElementById('input-make');
var inputModel = document.getElementById('input-model');
var inputYear = document.getElementById('input-year');
var header = document.getElementById('header');

inputMake.addEventListener('keyup', input);
inputModel.addEventListener('keyup', input);
inputYear.addEventListener('keyup', input);
inputYear.addEventListener('click', input);


btnSubmit.addEventListener("click", function () {
    if (inputMake.value.length > 0 && inputModel.value.length > 0 && inputYear.value > 0)
        form.submit();
    else
        alert("Введите название марки, модели и год выпуска.");
});

function input() {
    header.textContent = inputMake.value + ' ' + inputModel.value + ' ' + inputYear.value;
}