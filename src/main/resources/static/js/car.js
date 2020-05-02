function clickChange() {
    const changeBtn = document.getElementById("btn-change");
    const submitBtn = document.getElementById("btn-submit");
    const roInputs = document.getElementsByClassName("read-only-input");

    changeBtn.style.display = "none";
    submitBtn.style.display = "block";
    Array.from(roInputs).forEach(element => element.removeAttribute("readonly"));
}
