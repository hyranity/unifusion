$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#courseName-input"), "basic-course name", $("#error"));
    validateInput($("#description-input"), "basic-description", $("#error"));

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#courseName-input"), "basic") &
        isValid($("#description-input"), "basic"))
    {
        return false;
    }
    return true;
}