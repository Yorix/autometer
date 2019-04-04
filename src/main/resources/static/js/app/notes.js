function clickChange(id) {
    var descTxt = document.getElementById('desc_txt' + id);
    var valueTxt = document.getElementById('value_txt' + id);
    var changeBtn = document.getElementById('change_btn' + id);
    var submitBtn = document.getElementById('submit_btn' + id);

    descTxt.setAttribute('contentEditable', 'true');
    valueTxt.setAttribute('contentEditable', 'true');
    changeBtn.style.display = 'none';
    submitBtn.style.display = 'block';
}

function clickSubmit(id, carId) {

    alert(id + ' ' + carId);

    var descTxt = document.getElementById('desc_txt' + id);
    var valueTxt = document.getElementById('value_txt' + id);
    var changeBtn = document.getElementById('change_btn' + id);
    var submitBtn = document.getElementById('submit_btn' + id);

    // создать объект для формы
    var formData = new FormData();
    // добавить к пересылке пару ключ - значение
    formData.append(descTxt.name, descTxt.textContent);
    formData.append(valueTxt.name, valueTxt.textContent);
    // отослать
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/cars/" + carId);
    xhr.send(formData);

    descTxt.setAttribute('contentEditable', 'false');
    valueTxt.setAttribute('contentEditable', 'false');
    changeBtn.style.display = 'block';
    submitBtn.style.display = 'none';
}