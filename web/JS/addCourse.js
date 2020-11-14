$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#courseCode-input"), "basic-course code", $("#error"));
    validateInput($("#courseName-input"), "basic-course name", $("#error"));
    validateProgramme($("#programmeCodeTextbox"), document.getElementById("hasProgramme"), $("#error"));
    validateInput($("#description-input"), "basic-description", $("#error"));

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#courseCode-input"), "basic") &
        isValid($("#courseName-input"), "basic") &
        isValidProgramme($("#programmeCodeTextbox"), document.getElementById("hasProgramme")) &
        isValid($("#description-input"), "basic"))
    {
        return false;
    }
    return true;
}