var makeDiv = document.getElementById("div-make");
var modelDiv = document.getElementById("div-model");
var makeInput = document.getElementById("input-make");
var modelInput = document.getElementById("input-model");
var editCarnameBtn = document.getElementById("btn-edit-carname");
var submitCarnameBtn = document.getElementById("btn-submit-carname");

submitCarnameBtn.style.display = "none";

editCarnameBtn.addEventListener("click", function () {
    makeDiv.setAttribute('contentEditable', 'true');
    modelDiv.setAttribute('contentEditable', 'true');
    submitCarnameBtn.style.display = 'block';
    editCarnameBtn.style.display = 'none';
});

submitCarnameBtn.addEventListener("click", function () {
    makeDiv.setAttribute('contentEditable', 'false');
    modelDiv.setAttribute('contentEditable', 'false');
    submitCarnameBtn.style.display = 'none';
    editCarnameBtn.style.display = 'block';
    makeInput.value = makeDiv.innerText;
    modelInput.value = modelDiv.innerText;
});
