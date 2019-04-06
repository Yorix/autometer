datePickerController.createDatePicker({
    formElements: {"datepicker": "%d.%m.%Y"},
    statusFormat: "%l, %j %F %Y",
    staticPos: false,
    hideInput: false,
    positioned: "pos",
    finalOpacity: 90
    // callbackFunctions: {
    //     "datereturned": [following]
    // }
});

// function following() {
//     var datepickerInput = document.getElementById('datepicker');
//     window.location.href = datepickerInput.value + '/';
// }
