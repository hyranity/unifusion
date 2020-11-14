$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#institutionCode-input"), "basic-institution code", $("#error"));
    validateInput($("#institutionName-input"), "basic-institution name", $("#error"));
    validateInput($("#description-input"), "basic-description", $("#error"));
    validateInput($("#address-input"), "basic-address", $("#error"));

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#institutionCode-input"), "basic") &
        isValid($("#institutionName-input"), "basic") &
        isValid($("#description-input"), "basic") &
        isValid($("#address-input"), "basic"))
    {
        return false;
    }
    return true;
}