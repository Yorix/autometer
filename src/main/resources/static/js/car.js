function clickChangeCar() {
    const btnChange = document.getElementById("btn-change");
    const btnSubmit = document.getElementById("btn-submit");
    const inputsRO = document.getElementsByClassName("read-only-input");

    btnChange.style.display = "none";
    btnSubmit.style.display = "inline";
    Array.from(inputsRO).forEach(element => element.removeAttribute("readonly"));
}
