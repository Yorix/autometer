function clickChange(id) {
    var dateDiv = document.getElementById("div-date" + id);
    var descDiv = document.getElementById("div-desc" + id);
    var valueDiv = document.getElementById("div-value" + id);
    var changeBtn = document.getElementById("btn-change" + id);
    var submitBtn = document.getElementById("btn-submit" + id);

    dateDiv.setAttribute("contentEditable", "true");
    descDiv.setAttribute("contentEditable", "true");
    valueDiv.setAttribute("contentEditable", "true");
    changeBtn.style.display = "none";
    submitBtn.style.display = "block";
}

function clickSubmit(id) {
    var dateDiv = document.getElementById("div-date" + id);
    var descDiv = document.getElementById("div-desc" + id);
    var valueDiv = document.getElementById("div-value" + id);
    var changeBtn = document.getElementById("btn-change" + id);
    var submitBtn = document.getElementById("btn-submit" + id);
    var dateInput = document.getElementById("input-date" + id);
    var descInput = document.getElementById("input-desc" + id);
    var valueInput = document.getElementById("input-value" + id);

    dateDiv.setAttribute("contentEditable", "false");
    descDiv.setAttribute("contentEditable", "false");
    valueDiv.setAttribute("contentEditable", "false");
    changeBtn.style.display = "block";
    submitBtn.style.display = "none";

    dateInput.value = dateDiv.innerText;
    descInput.value = descDiv.innerText;
    valueInput.value = valueDiv.innerText
        .replace(",", ".")
        .replace(/\s+/g, "");
}
