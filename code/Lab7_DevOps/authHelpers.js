function normalizeCredentials(body = {}) {
    return {
        username: String(body.username || "").trim(),
        password: String(body.password || "")
    };
}

function validateLogin(username, password) {
    if (!username || !password) {
        return "Please enter your username and password.";
    }

    return null;
}

function validateRegistration(username, password) {
    if (!username || !password.trim()) {
        return "Please enter a username and password.";
    }

    return null;
}

function getRegistrationErrorMessage(error) {
    if (error.code === "SQLITE_CONSTRAINT") {
        return "That username already exists. Please choose another.";
    }

    return "Unable to create your account. Please try again.";
}

module.exports = {
    normalizeCredentials,
    validateLogin,
    validateRegistration,
    getRegistrationErrorMessage
};