// front-end input validator + form submission & error message handler

function validateInput(textbox, type, errorLabel) {
    switch(type) {
        case "name":
            validateName(textbox, errorLabel);
            break;
  
        case "email":
            validateEmail(textbox, errorLabel);
            break;
  
        default:
            alert(type + " isn't recognised as a unique input type by the Validator. Resorting to default input validator.");
    }
}

function isValid(textbox, type) {
    switch(type) {
        case "name":
            return isValidName(textbox);
            break;
  
        case "email":
            return isValidEmail(textbox);
            break;
  
        default:
            alert(type + " isn't recognised as a unique input type by the Validator. Resorting to default input validator.");
    }
}

function resetErrorLabel(errorLabel) {
    errorLabel.html("");
}

function displayErrorLabel(errorLabel) {
    errorLabel.css({
       "opacity": "1",
       "font-size": "15px"
    });
}

function hideErrorLabel(errorLabel) {
    errorLabel.css({
       "opacity": "0",
       "font-size": "0px"
    });
}

function fillErrorLabel(errorsFound, errorLabel) {
    for (var i = 0; i < errorsFound.length; i++) {
        errorLabel.append("- " + errorsFound[i] + "<br>");
    }
}

function validateName(textbox, errorLabel) {
    var name = textbox.val().trim();
    var errorsFound = new Array();

    if (name.length <= 0) {
        errorsFound.push("Name is required.");
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        //highlightTextbox(textbox);
    } else {
        //unhighlightTextbox(textbox);
    }
}

function isValidName(textbox) {
    var name = textbox.val().trim();

    if (name.length <= 0) {
        return false;
    }
    
    return true;
}

function validateEmail(textbox, errorLabel) {
    var email = textbox.val().trim();
    var errorsFound = new Array();

    if (email.length <= 0) {
        errorsFound.push("Email is required.");
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        //highlightTextbox(textbox);
    } else {
        //unhighlightTextbox(textbox);
    }
}

function isValidEmail(textbox) {
    var email = textbox.val().trim();

    if (email.length <= 0) {
        return false;
    }
    
    return true;
}