function clickChange(id) {
    var descDiv = document.getElementById('desc_div' + id);
    var valueDiv = document.getElementById('value_div' + id);
    var changeBtn = document.getElementById('change_btn' + id);
    var submitBtn = document.getElementById('submit_btn' + id);

    descDiv.setAttribute('contentEditable', 'true');
    valueDiv.setAttribute('contentEditable', 'true');
    changeBtn.style.display = 'none';
    submitBtn.style.display = 'block';
}

function clickSubmit(id, carId, date) {
    var descDiv = document.getElementById('desc_div' + id);
    var valueDiv = document.getElementById('value_div' + id);
    var changeBtn = document.getElementById('change_btn' + id);
    var submitBtn = document.getElementById('submit_btn' + id);

    var formData = new FormData();

    formData.append('id', id);
    formData.append('description', descDiv.textContent);
    formData.append('value', valueDiv.textContent);
    formData.append('date', date);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/cars/" + carId);
    xhr.send(formData);


    descDiv.setAttribute('contentEditable', 'false');
    valueDiv.setAttribute('contentEditable', 'false');
    changeBtn.style.display = 'block';
    submitBtn.style.display = 'none';
}

