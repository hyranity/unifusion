// front-end input validator + form submission & error message handler

function validateInput(textbox, type, errorLabel) {
    switch(type) {
        case "name":
            validateName(textbox, errorLabel);
            break;
  
        case "email":
            validateEmail(textbox, errorLabel);
            break;
          
        case "date":
            validateDate(textbox,  errorLabel);
            break;
        
        case "address":
            validateAddress(textbox, errorLabel);
            break;
          
        case "time":
            validateTime(textbox, errorLabel);
            break;
            
        case "marks":
            validateMarks(textbox, errorLabel);
            break;
            
        case "capacity":
            validateCapacity(textbox, errorLabel);
            break;
        
        default:
            //alert(type + " isn't recognised as a unique input type by the Validator. Resorting to default input validator.");
            validateBasic(textbox, type, errorLabel);
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
            
        case "date":
            return isValidDate(textbox);
            break;
        
        case "address":
            return isValidAddress(textbox);
            break;
            
        case "time":
            return isValidTime(textbox);
            break;
          
        case "marks":
            return isValidMarks(textbox);
            break;
            
        case "capacity":
            return isValidCapacity(textbox);
            break;
            
        case "basic":
        default:
            //alert(type + " isn't recognised as a unique input type by the Validator. Resorting to default input validator.");
            return isValidBasic(textbox);
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

function validateBasic(textbox, inputName, errorLabel) {
    var input = textbox.val().trim();
    var errorsFound = new Array();
    if (inputName.startsWith("basic-")) {
        inputName = inputName.slice(6);
    }
    inputName = inputName.charAt(0).toUpperCase() + inputName.slice(1);

    if (!input) {
        errorsFound.push(inputName + " is required.");
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
    }
}

function isValidBasic(textbox) {
    var input = textbox.val().trim();

    if (!input) {
        return false;
    }
    
    return true;
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

function validateAddress(textbox, errorLabel) {
    var address = textbox.val().trim();
    var errorsFound = new Array();

    if (address.length <= 0) {
        errorsFound.push("Address is required.");
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
    }
}

function isValidAddress(textbox) {
    var address = textbox.val().trim();

    if (address.length <= 0) {
        return false;
    }
    
    return true;
}

function validateDate(textbox, errorLabel) {
    var date = textbox.val();
    var errorsFound = new Array();

    if (!date) {
        errorsFound.push("Date is required.");
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
    }
}

function isValidDate(textbox) {
    var date = textbox.val();

    if (!date) {
        return false;
    }
    
    return true;
}

function validateTime(textbox, errorLabel) {
    var time = textbox.val();
    var errorsFound = new Array();
    
    if (!time) {
        errorsFound.push("Time is required.");
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
    }
}

function isValidTime(textbox) {
    var time = textbox.val();

    if (!time) {
        return false;
    }
    
    return true;
}

function validateMarks(textbox, errorLabel) {
    var marks = parseInt(textbox.val());
    var errorsFound = new Array();
    
    if (!marks) {
        errorsFound.push("Marks is required.");
    } else {
        if (marks < 0) {
            errorsFound.push("Marks should be a positive value.");
        }
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
    }
}

function isValidMarks(textbox) {
    var marks = parseInt(textbox.val());
    
    if (!marks) {
        return false;
    } else {
        if (marks < 0) {
            return false;
        }
    }
    
    return true;
}

function validateCapacity(textbox, errorLabel) {
    var capacity = parseInt(textbox.val());
    var errorsFound = new Array();
    
    if (!capacity) {
        errorsFound.push("Capacity is required.");
    } else {
        if (capacity < 0) {
            errorsFound.push("Capacity should be a positive value.");
        }
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
    }
}

function isValidCapacity(textbox) {
    var capacity = parseInt(textbox.val());
    
    if (!capacity) {
        return false;
    } else {
        if (capacity < 0) {
            return false;
        }
    }
    
    return true;
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

function validateCourse(textbox, checkbox, errorLabel) {
    var courseCode = textbox.val();
    var errorsFound = new Array();
    
    if (checkbox.checked) {
        if (!courseCode) {
            errorsFound.push("Course code is required.");
        }
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
    }
}

function isValidCourse(textbox, checkbox) {
    var courseCode = textbox.val();
    
    if (checkbox.checked) {
        if (!courseCode) {
            return false;
        }
    }
    
    return true;
}

function validateProgramme(textbox, checkbox, errorLabel) {
    var programmeCode = textbox.val();
    var errorsFound = new Array();
    
    if (checkbox.checked) {
        if (!programmeCode) {
            errorsFound.push("Programme code is required.");
        }
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
    }
}

function isValidProgramme(textbox, checkbox) {
    var programmeCode = textbox.val();
    
    if (checkbox.checked) {
        if (!programmeCode) {
            return false;
        }
    }
    
    return true;
}

function validateInstitution(textbox, checkbox, errorLabel) {
    var institutionCode = textbox.val();
    var errorsFound = new Array();
    
    if (checkbox.checked) {
        if (!institutionCode) {
            errorsFound.push("Institution code is required.");
        }
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
    }
}

function isValidInstitution(textbox, checkbox) {
    var institutionCode = textbox.val();
    
    if (checkbox.checked) {
        if (!institutionCode) {
            return false;
        }
    }
    
    return true;
}

function validateTempVenue(textbox, checkbox, errorLabel) {
    var tempVenue = textbox.val();
    var errorsFound = new Array();
    
    if (checkbox.checked) {
        if (!tempVenue) {
            errorsFound.push("Temporary venue is required.");
        }
    }
    
    if (errorsFound.length > 0) {
        fillErrorLabel(errorsFound, errorLabel);
        highlightTextbox(textbox);
    } else {
        unhighlightTextbox(textbox);
    }
}

function isValidTempVenue(textbox, checkbox) {
    var tempVenue = textbox.val();
    
    if (checkbox.checked) {
        if (!tempVenue) {
            return false;
        }
    }
    
    return true;
}