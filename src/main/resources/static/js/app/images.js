function MaxFileSizeException(message) {
    this.message = message;
}

function sendFile(maxFileSize) {
    var form = document.getElementById("sendFile");
    var fileInput = document.getElementById('file_input');
    var fileSize = fileInput.files[0].size;
    alert(maxFileSize);
    // if (fileSize > maxFileSize) {
    //     alert('Размер файла больше ' + (maxFileSize) + ' МБ.');
    //     throw new MaxFileSizeException("Размер файла больше " + (maxFileSize) + " МБ.")
    // }
    form.submit();
}

//  / 1048576 + 1

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
