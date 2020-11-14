$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#institutionCode-input"), "basic-institution code", $("#error"));
    validateAuthorisationCode($("#authorisationCodeTextbox"), document.getElementById("isStaff"), $("#error"));
    

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#institutionCode-input"), "basic") &
        isValidAuthorisationCode($("#authorisationCodeTextbox"), document.getElementById("isStaff")))
    {
        return false;
    }
    return true;
}