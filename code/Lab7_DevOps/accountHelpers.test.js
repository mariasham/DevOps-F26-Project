const { test, beforeEach, afterEach } = require("node:test");
const assert = require("node:assert/strict");
const sqlite3 = require("sqlite3").verbose();

const {
    createAccount,
    findMatchingUser
} = require("./accountHelpers");

let db;

beforeEach(async () => {
    db = new sqlite3.Database(":memory:");

    await new Promise((resolve, reject) => {
        db.run(
            `CREATE TABLE users (
                username TEXT PRIMARY KEY NOT NULL,
                password TEXT NOT NULL
            )`,
            (error) => error ? reject(error) : resolve()
        );
    });
});

afterEach(async () => {
    await new Promise((resolve, reject) => {
        db.close((error) => error ? reject(error) : resolve());
    });
});

function register(username, password) {
    return new Promise((resolve, reject) => {
        createAccount(db, username, password, (error) => {
            if (error) {
                reject(error);
            } else {
                resolve();
            }
        });
    });
}

function login(username, password) {
    return new Promise((resolve, reject) => {
        findMatchingUser(db, username, password, (error, user) => {
            if (error) {
                reject(error);
            } else {
                resolve(user);
            }
        });
    });
}

test("creates an account that can log in", async () => {
    await register("maria", "secret123");

    const user = await login("maria", "secret123");

    assert.deepEqual(user, { username: "maria" });
});

test("rejects a duplicate username", async () => {
    await register("maria", "secret123");

    await assert.rejects(
        register("maria", "differentPassword"),
        { code: "SQLITE_CONSTRAINT" }
    );

    assert.deepEqual(
        await login("maria", "secret123"),
        { username: "maria" }
    );
});

test("rejects an incorrect password", async () => {
    await register("maria", "secret123");

    assert.equal(await login("maria", "wrongPassword"), undefined);
});

test("rejects a username that does not exist", async () => {
    assert.equal(await login("unknown", "secret123"), undefined);
});

test("treats passwords as case sensitive", async () => {
    await register("maria", "Secret123");

    assert.equal(await login("maria", "secret123"), undefined);
});

test("does not treat SQL text as a login command", async () => {
    await register("maria", "secret123");

    assert.equal(
        await login("' OR 1=1 --", "anything"),
        undefined
    );
});