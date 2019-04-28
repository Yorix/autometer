var btnEditBudget = document.getElementById("btn-edit-budget");
var btnAddToBudget = document.getElementById("btn-add-to-budget");
var inputBudget = document.getElementById("input-budget");
var btnSubmitBudget = document.getElementById("btn-submit-budget");
var isIncrease;

inputBudget.style.display = "none";
btnSubmitBudget.style.display = "none";

function editBudget(val) {
    btnEditBudget.style.display = "none";
    btnAddToBudget.style.display = "none";

    inputBudget.style.display = "block";
    btnSubmitBudget.style.display = "block";
    inputBudget.value = val;
    isNaN(val) ? isIncrease = true : isIncrease = false;
}

function submitBudget(budget) {
    btnEditBudget.style.display = "block";
    btnAddToBudget.style.display = "block";
    inputBudget.style.display = "none";
    btnSubmitBudget.style.display = "none";

    var formData = new FormData();
    if (isIncrease)
        formData.append("value", Number(inputBudget.value) + budget);
    else
        formData.append("value", inputBudget.value);

    var xhr = new XMLHttpRequest();
    xhr.open("PUT", "/params/budget/");
    xhr.send(formData);
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200)
            window.parent.location.reload();
    }
}