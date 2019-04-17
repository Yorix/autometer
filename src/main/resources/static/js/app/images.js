function sendFile(maxFileSize) {
    var form = document.getElementById("sendFile");
    var fileInput = document.getElementById('file_input');

    if (fileInput.files.length > 0) {
        var fileSize = fileInput.files[0].size;
        if (fileSize > maxFileSize) {
            alert("Размер файла - " + (fileInput.files[0].size / 1048576).toFixed(2)
                + " MБ. Выберите файл меньше " + (maxFileSize / 1048576) + " МБ.");
            return;
        }
    }
    form.submit();
}

function setDefault(carId, filename) {

    var formData = new FormData();
    formData.append("imgFilename", filename);

    var xhr = new XMLHttpRequest();
    xhr.open("PUT", "/cars/" + carId + "/");
    xhr.send(formData);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4)
            window.location = "../";
    };
}

function deleteImg(carId, filename) {

    var formData = new FormData();
    formData.append("filename", filename);
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/cars/" + carId + "/img/");
    xhr.send(formData);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4)
            window.location.reload();
    }
}
