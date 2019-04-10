function clickChange(id) {
    var dateDiv = document.getElementById('date_div' + id);
    var descDiv = document.getElementById('desc_div' + id);
    var valueDiv = document.getElementById('value_div' + id);
    var changeBtn = document.getElementById('change_btn' + id);
    var submitBtn = document.getElementById('submit_btn' + id);

    dateDiv.setAttribute('contentEditable', 'true');
    descDiv.setAttribute('contentEditable', 'true');
    valueDiv.setAttribute('contentEditable', 'true');
    changeBtn.style.display = 'none';
    submitBtn.style.display = 'block';
}

function clickSubmit(id, carId) {
    var dateDiv = document.getElementById('date_div' + id);
    var descDiv = document.getElementById('desc_div' + id);
    var valueDiv = document.getElementById('value_div' + id);
    var changeBtn = document.getElementById('change_btn' + id);
    var submitBtn = document.getElementById('submit_btn' + id);


    dateDiv.setAttribute('contentEditable', 'false');
    descDiv.setAttribute('contentEditable', 'false');
    valueDiv.setAttribute('contentEditable', 'false');
    changeBtn.style.display = 'block';
    submitBtn.style.display = 'none';


    var formData = new FormData();
    formData.append('id', id);
    formData.append('description', descDiv.innerText);
    formData.append('value', valueDiv.innerText);
    formData.append('date', dateDiv.innerText);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/cars/" + carId + "/notes/");
    xhr.send(formData);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200)
            window.location.reload();
    };
}
