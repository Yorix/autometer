var noteForm = document.getElementById('note_form');
var noteValueInput = document.getElementById('note_value_input');
var negativeSelect = document.getElementById('negative_select');

var carId = document.getElementById('car_id').dataset.id;
var makeDiv = document.getElementById('make_div');
var modelDiv = document.getElementById('model_div');
var editCarnameBtn = document.getElementById('edit_carname_btn');
var submitCarnameBtn = document.getElementById('submit_carname_btn');

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
    formData.append('id', carId);
    formData.append('make', makeDiv.innerText);
    formData.append('model', modelDiv.innerText);
    var xmlHttpRequest = new XMLHttpRequest();
    xmlHttpRequest.open('POST', '/cars/');
    xmlHttpRequest.send(formData);

    xmlHttpRequest.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200)
            window.location.reload();
    }
});

noteValueInput.value = '';


function noteSubmit() {
    if (noteValueInput.value.length > 0)
        if (negativeSelect.selectedIndex === 0)
            noteValueInput.value = noteValueInput.value * -1;
    noteForm.submit();
}
