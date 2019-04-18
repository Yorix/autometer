var carId = document.getElementById('car-id').dataset.id;
var makeDiv = document.getElementById("div-make");
var modelDiv = document.getElementById("div-model");
var editCarnameBtn = document.getElementById("btn-edit-carname");
var submitCarnameBtn = document.getElementById("btn-submit-carname");

editCarnameBtn.addEventListener('click', function () {
    makeDiv.setAttribute('contentEditable', 'true');
    modelDiv.setAttribute('contentEditable', 'true');
    submitCarnameBtn.style.display = 'block';
    editCarnameBtn.style.display = 'none';
});

submitCarnameBtn.addEventListener('click', function () {
    makeDiv.setAttribute('contentEditable', 'false');
    modelDiv.setAttribute('contentEditable', 'false');
    submitCarnameBtn.style.display = 'none';
    editCarnameBtn.style.display = 'block';

    var formData = new FormData();
    formData.append('make', makeDiv.innerText);
    formData.append('model', modelDiv.innerText);
    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('PUT', '/cars/' + carId + '/');
    xmlHttpRequest.send(formData);

    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200)
            window.location.reload();
    }
});
