function clickChangeCar() {
    const btnChange = document.getElementById("btn-change");
    const btnSubmit = document.getElementById("btn-submit");
    const inputsRO = document.getElementsByClassName("read-only-input");
    const selectUser = document.getElementById("select-user");

    btnChange.style.display = "none";
    btnSubmit.style.display = "inline";
    Array.from(inputsRO).forEach(element => element.removeAttribute("readonly"));
    selectUser.removeAttribute("disabled");
}
