function clickChangeCar() {
    const btnChange = document.getElementById("btn-change");
    const btnSubmit = document.getElementById("btn-submit");
    const inputsRO = document.getElementsByClassName("read-only-input");

    btnChange.style.display = "none";
    btnSubmit.style.display = "inline-block";
    Array.from(inputsRO).forEach(function (item) {
        item.classList.remove("read-only-input");
        item.removeAttribute("readonly");
        item.removeAttribute("disabled");
    });
}
