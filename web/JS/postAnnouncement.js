$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#title-input"), "basic-title", $("#error"));
    validateInput($("#message-input"), "basic-message", $("#error"));
    

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#title-input"), "basic") &
        isValid($("#message-input"), "basic"))
    {
        return false;
    }
    return true;
}