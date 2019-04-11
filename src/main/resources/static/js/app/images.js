function sendFile() {
    var form = document.getElementById("sendFile");
    var fileInput = document.getElementById('file_input');
    var fileSize = fileInput.files[0].size;

    if (fileSize > 2097152) {
        alert("Файл слишком большой.");
        return;
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
