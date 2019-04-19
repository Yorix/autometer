/* Go backBtn by backspace pressing */
document.addEventListener('keydown', function (ev) {
    if (ev.key.charCodeAt(8)) {
        for (var i = 0; i < inputs.length; i++)
            if (inputs[i] === document.activeElement)
                return;

        var divs = document.getElementsByTagName('div');
        for (i = 0; i < divs.length; i++)
            if (divs[i].getAttribute('contentEditable') === 'true')
                return;
        goto('../');
    }
});


function goto(url) {
    window.location.href = url;
}

function cuttingDecimalPlaces(e) {
    if (e.value.indexOf(".") !== -1)
        e.value = e.value.substring(0, e.value.indexOf(".") + 3);
}

/* Set CSS variables */

var root = document.querySelector(':root');
var rootStyles = getComputedStyle(root);

var balance = document.getElementById('balance_info').dataset.balance;
var posColor = rootStyles.getPropertyValue('--pos-color');
var negColor = rootStyles.getPropertyValue('--neg-color');
var zerColor = rootStyles.getPropertyValue('--zer-color');
if (balance < 0)
    root.style.setProperty('--var-color', negColor);
else if (balance > 0)
    root.style.setProperty('--var-color', posColor);
else root.style.setProperty('--var-color', zerColor);
