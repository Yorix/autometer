var backBtn = document.getElementById('back_btn');
var newCarBtn = document.getElementById('new_car_btn');
var allNotesBtn = document.getElementById('all_notes_btn');

// var budget =

// var div = document.createElement('div');
// div.className = "alert alert-success";
// div.innerHTML = "<strong>Бютжет:</strong> ";


if (backBtn !== null) {
    /* Go backBtn by backspace pressing */
    document.addEventListener('keydown', function (ev) {
        if (ev.key.charCodeAt(8)) {
            var inputs = document.getElementsByTagName('input');
            for (var i = 0; i < inputs.length; i++)
                if (inputs[i] === document.activeElement)
                    return;

            var divs = document.getElementsByTagName('div');
            for (var i = 0; i < divs.length; i++)
                if (divs[i].getAttribute('contentEditable') === 'true')
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


function cuttingDecimalPlaces(e) {
    if (e.value.indexOf(".") !== -1) {
        e.value = e.value.substring(0, e.value.indexOf(".") + 3);
    }
}


// Set CSS variables

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
