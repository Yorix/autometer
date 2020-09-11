function clickChangeCar() {
    const btnChange = document.getElementById("btn-change");
    const btnSubmit = document.getElementById("btn-submit");
    const inputsRO = document.getElementsByClassName("read-only-input");

    btnChange.style.display = "none";
    btnSubmit.style.display = "inline";
    Array.from(inputsRO).forEach(element => {
        element.classList.remove("read-only-input");
        element.removeAttribute("readonly");
        element.removeAttribute("disabled");
    });
}

function sendFile(maxFileSize) {
    const form = document.getElementById("form-send-file");
    const fileInput = document.getElementById("input-file");

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
