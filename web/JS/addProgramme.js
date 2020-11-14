$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#programmeCode-input"), "basic-programme code", $("#error"));
    validateInput($("#programmeName-input"), "basic-programme name", $("#error"));
    validateInstitution($("#institutionCodeTextbox"), document.getElementById("hasInstitution"), $("#error"));
    validateInput($("#description-input"), "basic-description", $("#error"));

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#programmeCode-input"), "basic") &
        isValid($("#programmeName-input"), "basic") &
        isValidInstitution($("#institutionCodeTextbox"), document.getElementById("hasInstitution")) &
        isValid($("#description-input"), "basic"))
    {
        return false;
    }
    return true;
}