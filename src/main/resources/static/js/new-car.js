var form = document.getElementById("form-new-car");
var inputMake = document.getElementById("input-make");
var inputModel = document.getElementById("input-model");
var btnSubmit = document.getElementById("btn-submit");

btnSubmit.addEventListener("click", function () {
    if (inputMake.value.length > 0 && inputModel.value.length > 0)
        form.submit();
    else
        alert("Введите название марки и модели.");
});
