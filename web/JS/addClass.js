$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#classCode-input"), "basic-class code", $("#error"));
    validateInput($("#className-input"), "basic-class name", $("#error"));
    validateCourse($("#courseCodeTextbox"), document.getElementById("hasCourse"), $("#error"));
    validateInput($("#description-input"), "basic-description", $("#error"));

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#classCode-input"), "basic") &
        isValid($("#className-input"), "basic") &
        isValidCourse($("#courseCodeTextbox"), document.getElementById("hasCourse")) &
        isValid($("#description-input"), "basic"))
    {
        return false;
    }
    return true;
}