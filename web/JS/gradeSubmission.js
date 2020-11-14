$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#marks-input"), "marks", $("#error"));
    validateInput($("#remarks-input"), "basic-remarks", $("#error"));
    
    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#marks-input"), "marks") &
        isValid($("#remarks-input"), "basic-remarks"))
    {
        return false;
    }
    return true;
}