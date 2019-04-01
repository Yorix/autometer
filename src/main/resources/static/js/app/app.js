var backBtn = document.getElementById('back_btn');
var newCarBtn = document.getElementById('new_car_btn');
var allNotesBtn = document.getElementById('all_notes_btn');

if (backBtn !== null) {
    /* Go backBtn by backspace pressing */
    document.addEventListener('keydown', function (ev) {
        if (ev.key.charCodeAt(8)) {
            var inputs = document.getElementsByTagName('input');
            for (var i = 0; i < inputs.length; i++)
                if (inputs[i] === document.activeElement)
                    return;
            window.location.href = '../';
        }
    });

    backBtn.addEventListener('click', function () {
        window.location.href = '../';
    });
}

if (newCarBtn !== null)
    newCarBtn.addEventListener('click', function () {
        window.location.href = '/cars/newCar/';
    });

if (allNotesBtn !== null)
    allNotesBtn.addEventListener('click', function () {
        window.location.href = 'allNotes/';
    });
