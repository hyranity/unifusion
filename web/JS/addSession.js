$("form").submit(function(e) {
    validateForm(e);
});

function validateForm(e) {
    resetErrorLabel($("#error")); 

    validateInput($("#date-input"), "date", $("#error"));
    validateInput($("#startTime-input"), "time", $("#error"));
    validateInput($("#endTime-input"), "time", $("#error"));
    validateVenueId($("#venueId"), document.getElementById("hasTempVenue"), $("#error"));
    validateTempVenue($("#tempVenueTextbox"), document.getElementById("hasTempVenue"), $("#error"));

    if (errorsExist()) {
        e.preventDefault();
        displayErrorLabel($("#error"));
    }
}

function errorsExist() {
    if (isValid($("#date-input"), "date") &
        isValid($("#startTime-input"), "time") &
        isValid($("#endTime-input"), "time") &
        isValidVenueId($("#venueId"), document.getElementById("hasTempVenue"), $("#error")) &
        isValidTempVenue($("#tempVenueTextbox"), document.getElementById("hasTempVenue")))
    {
        return false;
    }
    return true;
}