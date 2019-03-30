/* Go back by backspace pressing */
document.addEventListener('keydown', function (ev) {
    if (ev.key.charCodeAt(8)
        && document.getElementById('back_btn') !== null) {
        var inputs = document.getElementsByTagName('input');
        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i] === document.activeElement) {
                return;
            }
        }
        history.back();
    }
});

/* Go back by back-button pressing */
var back = document.getElementById('back_btn');
back.addEventListener('click', function () {
    history.back();
});
