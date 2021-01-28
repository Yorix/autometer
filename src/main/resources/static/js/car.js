function clickChangeCar() {
    const btnChange = document.getElementById("btn-change");
    const btnSubmit = document.getElementById("btn-submit");
    const inputsRO = document.getElementsByClassName("read-only-input");

    btnChange.style.display = "none";
    btnSubmit.style.display = "inline-block";
    Array.from(inputsRO).forEach(function (item) {
        item.classList.remove("read-only-input");
        item.removeAttribute("readonly");
        item.removeAttribute("disabled");
    });
}

function clickDeleteCar() {
    const send = confirm("Вы действительно хотите удалить машину?");
    if (send) {
        const form = document.getElementById("deleteLot");
        form.submit();
    }
}

function sendFile(maxFileSize, album) {
    const form = document.getElementById("form-send-file" + album);
    const fileInput = document.getElementById("input-file" + album);

    if (fileInput.files.length > 0) {
        const fileSize = fileInput.files[0].size;
        if (fileSize > maxFileSize) {
            alert("Размер файла - " + (fileInput.files[0].size / 1048576).toFixed(2)
                + " MБ. Выберите файл меньше " + (maxFileSize / 1048576) + " МБ.");
            return;
        }
    }
    form.submit();
}
