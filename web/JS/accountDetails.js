$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#name-textbox"), "name", $("#error"));
    validateInput($("#date-textbox"), "date", $("#error"));
    validateInput($("#address-textbox"), "address", $("#error"));
    validateInput($("#email-textbox"), "email", $("#error"));
    //validatePassword($("#password-textbox"), $("#confirmPassword-textbox"), $("#error"));

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#name-textbox"), "name") &
        isValid($("#date-textbox"), "date") &
        isValid($("#address-textbox"), "address") &
        isValid($("#email-textbox"), "email")
        /*isValidPassword($("#password-textbox"), $("#confirmPassword-textbox"))*/)
    {
        return false;
    }
    return true;
}