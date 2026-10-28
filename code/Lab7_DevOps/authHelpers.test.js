const test = require("node:test");
const assert = require("node:assert/strict");

const {
    normalizeCredentials,
    validateLogin,
    validateRegistration,
    getRegistrationErrorMessage
} = require("./authHelpers");

test("removes surrounding spaces from usernames", () => {
    const result = normalizeCredentials({
        username: "  maria  ",
        password: "secret123"
    });

    assert.equal(result.username, "maria");
});

test("preserves spaces in passwords", () => {
    const result = normalizeCredentials({
        username: "maria",
        password: " secret123 "
    });

    assert.equal(result.password, " secret123 ");
});

test("handles missing credentials", () => {
    assert.deepEqual(normalizeCredentials(), {
        username: "",
        password: ""
    });
});

test("accepts filled-in login fields", () => {
    assert.equal(validateLogin("maria", "secret123"), null);
});

test("rejects a missing login username", () => {
    assert.equal(
        validateLogin("", "secret123"),
        "Please enter your username and password."
    );
});

test("rejects a missing login password", () => {
    assert.equal(
        validateLogin("maria", ""),
        "Please enter your username and password."
    );
});

test("accepts filled-in registration fields", () => {
    assert.equal(validateRegistration("maria", "secret123"), null);
});

test("rejects a missing registration username", () => {
    assert.equal(
        validateRegistration("", "secret123"),
        "Please enter a username and password."
    );
});

test("rejects a missing registration password", () => {
    assert.equal(
        validateRegistration("maria", ""),
        "Please enter a username and password."
    );
});

test("rejects a registration password containing only spaces", () => {
    assert.equal(
        validateRegistration("maria", "   "),
        "Please enter a username and password."
    );
});

test("returns the duplicate username message", () => {
    assert.equal(
        getRegistrationErrorMessage({ code: "SQLITE_CONSTRAINT" }),
        "That username already exists. Please choose another."
    );
});

test("returns a general message for other database errors", () => {
    assert.equal(
        getRegistrationErrorMessage({ code: "SQLITE_ERROR" }),
        "Unable to create your account. Please try again."
    );
});