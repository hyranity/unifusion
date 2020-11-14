$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#title-input"), "basic-title", $("#error"));
    validateInput($("#details-input"), "basic-details", $("#error"));
    // no validation for attachments-input required
    validateInput($("#date-input"), "date", $("#error"));
    validateInput($("#time-input"), "time", $("#error"));
    validateInput($("#marks-input"), "marks", $("#error"));

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#title-input"), "basic") &
        isValid($("#details-input"), "basic") &
        isValid($("#date-input"), "date") &
        isValid($("#time-input"), "time") &
        isValid($("#marks-input"), "marks"))
    {
        return false;
    }
    return true;
}