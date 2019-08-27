function sendFile(maxFileSize) {
    var form = document.getElementById("form-send-file");
    var fileInput = document.getElementById('input-file');

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
