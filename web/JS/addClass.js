$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#title-input"), "basic-title", $("#error"));

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#title-input"), "basic") &
        isValid($("#details-input"), "basic"))
    {
        return false;
    }
    return true;
}