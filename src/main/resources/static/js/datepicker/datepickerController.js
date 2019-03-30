datePickerController.createDatePicker({
    formElements: {"datepicker": "%d.%m.%Y"},
    statusFormat: "%l, %j %F %Y",
    staticPos: true,
    hideInput: true,
    callbackFunctions: {
        "datereturned": [following]
    }
});

function following() {
    var datepickerInput = document.getElementById('datepicker');
    window.location.href = datepickerInput.value + '/';
}
