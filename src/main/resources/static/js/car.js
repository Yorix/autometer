var makeDiv = document.getElementById("div-make");
var modelDiv = document.getElementById("div-model");
var yearDiv = document.getElementById("div-year");
var makeInput = document.getElementById("input-make");
var modelInput = document.getElementById("input-model");
var yearInput = document.getElementById("input-year");
var editCarnameBtn = document.getElementById("btn-edit-carname");
var submitCarnameBtn = document.getElementById("btn-submit-carname");

submitCarnameBtn.style.display = "none";

editCarnameBtn.addEventListener("click", function () {
    makeDiv.setAttribute('contentEditable', 'true');
    modelDiv.setAttribute('contentEditable', 'true');
    yearDiv.setAttribute('contentEditable', 'true');
    submitCarnameBtn.style.display = 'block';
    editCarnameBtn.style.display = 'none';
});

submitCarnameBtn.addEventListener("click", function () {
    makeDiv.setAttribute('contentEditable', 'false');
    modelDiv.setAttribute('contentEditable', 'false');
    yearDiv.setAttribute('contentEditable', 'false');
    submitCarnameBtn.style.display = 'none';
    editCarnameBtn.style.display = 'block';
    makeInput.value = makeDiv.innerText;
    modelInput.value = modelDiv.innerText;
    yearInput.value = yearDiv.innerText;
});
