const btnAddToBudget = document.getElementById("btn-add-to-budget");
const inputBudget = document.getElementById("input-budget");
const btnSubmitBudget = document.getElementById("btn-submit-budget");
const moneyElements = document.getElementsByClassName("money");
const tabs = document.getElementsByClassName("tab");
const navbar = document.getElementsByClassName("nav-item");


for (let i = 0; i < navbar.length; i++) {
    const div1 = document.createElement("div");
    const div2 = document.createElement("div");
    div1.appendChild(div2);
    const inner = navbar[i].childNodes;
    while (inner.length) div2.appendChild(inner[0]);
    navbar[i].appendChild(div1);
}

for (let i = 0; i < tabs.length; i++) {
    const div1 = document.createElement("div");
    const div2 = document.createElement("div");
    const div3 = document.createElement("div");
    const div4 = document.createElement("div");
    div1.appendChild(div2);
    div2.appendChild(div3);
    div3.appendChild(div4);
    const inner = tabs[i].childNodes;
    while (inner.length) div4.appendChild(inner[0]);
    tabs[i].appendChild(div1);
}

for (let i = 0; i < moneyElements.length; i++) {
    toMoneyFormat(moneyElements[i]);
}

function toMoneyFormat(e) {
    let text;
    if (e.nodeName === "INPUT")
        text = e.value;
    else
        text = e.innerText;

    if (Number(text) < 0)
        e.classList.add("negMoney");
    else if (Number(text) > 0)
        e.classList.add("posMoney");
    else
        e.classList.add("zerMoney");

    text = (Math.round((Number(text)) * 100) / 100).toLocaleString("ru");
    if (e.nodeName === "INPUT")
        e.value = text.replace(",", ".").replace(/\s/, "");
    else
        e.innerText = text;
}


function editBudget() {
    btnAddToBudget.style.display = "none";
    inputBudget.style.display = "block";
    btnSubmitBudget.style.display = "inline";
}

function submitBudget() {
    if (inputBudget.value === '')
        inputBudget.value = 0;
}

function cuttingDecimalPlaces(e) {
    if (e.value.indexOf(".") !== -1)
        e.value = e.value.substring(0, e.value.indexOf(".") + 3);
}

function sendFile(maxFileSize, album) {
    if (album == null) album = "";
    const form = document.getElementById("form-send-file" + album);
    const fileInput = document.getElementById("input-file" + album);

    if (fileInput.files.length > 0) {
        const fileSize = fileInput.files[0].size;
        if (fileSize > maxFileSize) {
            alert("Размер файла - " + (fileInput.files[0].size / 1048576).toFixed(2)
                + " MБ. Выберите файл меньше " + (maxFileSize / 1048576) + " МБ.");
            return;
        }
    }
    form.submit();
}
