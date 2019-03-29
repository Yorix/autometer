/* Go back by backspace pressing */
document.addEventListener('keydown', function (ev) {
    if (ev.key.charCodeAt(8) && document.getElementById('back_btn') !== null) {
        history.back();
    }
});

/* Go back by back-button pressing */
var back = document.getElementById('back_btn');
back.addEventListener('click', function () {
    history.back();
});
