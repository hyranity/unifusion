$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error"));

    validateInput($("#name-input"), "name", $("#error"));
    validateInput($("#location-input"), "basic-location", $("#error"));
    validateInput($("#capacity-input"), "capacity", $("#error"));
    
    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#name-input"), "name") &
        isValid($("#location-input"), "basic-location") &
        isValid($("#capacity-input"), "capacity"))
    {
        return false;
    }
    return true;
}