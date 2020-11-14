$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#className-input"), "basic-class name", $("#error"));
    validateInput($("#description-input"), "basic-description", $("#error"));

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#className-input"), "basic") &
        isValid($("#description-input"), "basic"))
    {
        return false;
    }
    return true;
}