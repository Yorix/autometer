var noteForm = document.getElementById('note_form');
var noteValueInput = document.getElementById('note_value_input');
var negativeSelect = document.getElementById('negative_select');
var noteSubmit = document.getElementById('note_submit');

noteValueInput.value = '';

noteSubmit.addEventListener('click', function () {
    if (noteValueInput.value.length > 0)
        if (negativeSelect.selectedIndex === 0)
            noteValueInput.value = noteValueInput.value * -1;
    noteForm.submit();

});
