var noteDateInput = document.getElementById("date");
var noteDescriptionInput = document.getElementById("description");
var negativeSelect = document.getElementById("negative-select");
var noteValueInput = document.getElementById("value");

noteValueInput.value = "";

function cuttingDecimalPlaces(e) {
    if (e.value.indexOf(".") !== -1)
        e.value = e.value.substring(0, e.value.indexOf(".") + 3);
}

function noteSubmit(carId) {
    if (noteValueInput.value.length > 0) {
        if (negativeSelect.selectedIndex === 0)
            noteValueInput.value = noteValueInput.value * -1;
        var date = new Date(noteDateInput.value);
        var options = {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
        };
        var dateStr = date.toLocaleString("ru", options);
        dateStr = dateStr.replace(" г.", "");
        var formData = new FormData();
        formData.append("description", noteDescriptionInput.value);
        formData.append("value", noteValueInput.value);
        formData.append("date", dateStr);
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/cars/" + carId + "/notes/");
        xhr.send(formData);

        xhr.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200)
                window.parent.location.reload();
        }
    }
}
