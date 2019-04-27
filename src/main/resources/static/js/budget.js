var btnEditBudget = document.getElementById("btn-edit-budget");
var inputBudget = document.getElementById("input-budget");
var btnSubmitBudget = document.getElementById("btn-submit-budget");

inputBudget.style.display = "none";
btnSubmitBudget.style.display = "none";

function editBudget() {
    btnEditBudget.style.display = "none";
    inputBudget.style.display = "block";
    btnSubmitBudget.style.display = "block";
}

function submitBudget() {
    btnEditBudget.style.display = "block";
    inputBudget.style.display = "none";
    btnSubmitBudget.style.display = "none";

    var formData = new FormData();
    formData.append("value", inputBudget.value);
    var xhr = new XMLHttpRequest();
    xhr.open("PUT", "/params/budget/");
    xhr.send(formData);
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200)
            window.parent.location.reload();
    }
}