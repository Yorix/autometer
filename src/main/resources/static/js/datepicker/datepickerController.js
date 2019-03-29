datePickerController.createDatePicker({
    formElements: {"datepicker": "%d.%m.%Y"},
    statusFormat: "%l, %j %F %Y",
    staticPos: true,
    hideInput: true,
    callbackFunctions: {
        "datereturned": [formsubmit]
    }
});

function formsubmit() {
    var form = document.getElementById('dateform');
    form.submit();
}
