var noteDateInput = document.getElementById("input-note-date");
var negativeSelect = document.getElementById("select-negative");
var noteValueInput = document.getElementById("input-note-value");

noteDateInput.valueAsDate = new Date();
noteValueInput.value = "";

function clickChange(id) {
    var dateInput = document.getElementById("input-date" + id);
    var descInput = document.getElementById("input-desc" + id);
    var valueInput = document.getElementById("input-value" + id);

    var changeBtn = document.getElementById("btn-change" + id);
    var submitBtn = document.getElementById("btn-submit" + id);

    dateInput.removeAttribute("readOnly");
    descInput.removeAttribute("readOnly");
    valueInput.removeAttribute("readOnly");

    changeBtn.style.display = "none";
    submitBtn.style.display = "block";
}

function noteSubmit() {
    if (noteValueInput.value === '')
        noteValueInput.value = 0;

    if (negativeSelect.selectedIndex === 0 && noteValueInput.value > 0 || negativeSelect.selectedIndex === 1 && noteValueInput.value < 0)
        noteValueInput.value = noteValueInput.value * -1;
}