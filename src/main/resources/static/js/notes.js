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

function clickDelete(id) {
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", id + "/");
    xhr.send();
    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200)
            window.location.reload();
    }
}

function clickSubmit(id) {
    var dateDiv = document.getElementById("div-date" + id);
    var descDiv = document.getElementById("div-desc" + id);
    var valueDiv = document.getElementById("div-value" + id);
    var changeBtn = document.getElementById("btn-change" + id);
    var submitBtn = document.getElementById("btn-submit" + id);


    dateDiv.setAttribute("contentEditable", "false");
    descDiv.setAttribute("contentEditable", "false");
    valueDiv.setAttribute("contentEditable", "false");
    changeBtn.style.display = "block";
    submitBtn.style.display = "none";


    var value = valueDiv.innerText
        .replace(",", ".")
        .replace(/\s+/g, "");
    var formData = new FormData();
    formData.append("id", id);
    formData.append("description", descDiv.innerText);
    formData.append("value", value);
    formData.append("date", dateDiv.innerText);
    var xhr = new XMLHttpRequest();
    xhr.open("POST", window.location.href);
    xhr.send(formData);

    xhr.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200)
            window.location.reload();
    };
}
