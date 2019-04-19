var noteDateInput = document.getElementById("input-note-date");
var noteDescriptionInput = document.getElementById("input-note-desc");
var negativeSelect = document.getElementById("select-negative");
var noteValueInput = document.getElementById("input-note-value");

noteValueInput.value = "";

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
