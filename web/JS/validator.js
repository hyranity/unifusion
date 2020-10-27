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

function highlightTextbox(textbox) {
    textbox.css({
        "border": "2px solid #e66a6a"
    });
}

function unhighlightTextbox(textbox) {
    textbox.css({
        "border": "none"
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
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
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
    
    if (!emailValidatedWithRegex(email)) {
        errorsFound.push("Email format is not recognised.");
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
    }
}

function isValidEmail(textbox) {
    var email = textbox.val().trim();

    if (email.length <= 0) {
        return false;
    }
    
    return true;
}

// Regex expression used in this function is taken from a community wiki answer @ https://stackoverflow.com/a/46181/8919391
function emailValidatedWithRegex(email) {
    const regex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return regex.test(String(email).toLowerCase());
}

function validatePassword(textbox, confirmTextbox, errorLabel) {
    var password = textbox.val().trim();
    var confirmPassword = confirmTextbox.val().trim();
    var errorsFound = new Array();
    
    if (password.length < 8) {
        errorsFound.push("Password should be more than 8 characters long.");
    }
    
    if (password !== confirmPassword) {
        errorsFound.push("The passwords entered do not match.");
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
        highlightTextbox(confirmTextbox);
    } else {
        unhighlightTextbox(textbox);
        unhighlightTextbox(confirmTextbox);
    }
}

function isValidPassword(textbox, confirmTextbox) {
    var password = textbox.val().trim();
    var confirmPassword = confirmTextbox.val().trim();

    if (password.length < 8) {
        return false;
    }
    
    if (password !== confirmPassword) {
        return false;
    }
    
    return true;
}