function displayConfirm(passwordElm) {
    var confirmElm = document.getElementsByClassName("confirm")[0];
    if (passwordElm.value.length !== 0) {
        confirmElm.style.display = "flex";
    }

    if (passwordElm.value.length === 0) {
        confirmElm.style.display = "none";
        confirmElm.value = "";
    }

}

function enableButton(event) {
    var current = event.target;
    var parent = current.parentNode;
    var button = null;
    for(let i = 0; i < parent.childNodes.length ; i++) {
        if(parent.childNodes && parent.childNodes[i].className === "col-3") {
            button = parent.childNodes[i];
            break;
        }
    }
    if(current.value.length !== 0) {
        button.disabled = false;
    }

    if(current.value.length === 0) {
        button.disabled = true;
    }

    if(current.id === "password") {
        displayConfirm(current);
    }
    
}