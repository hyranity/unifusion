$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#email-textbox"), "basic-email", $("#error"));
    validateInput($("#password-textbox"), "basic-password", $("#error"));

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#email-textbox"), "basic") &
        isValid($("#password-textbox"), "basic"))
    {
        return false;
    }
    return true;
}