function createAccount(db, username, password, callback) {
    db.run(
        "INSERT INTO users (username, password) VALUES (?, ?)",
        [username, password],
        callback
    );
}

function findMatchingUser(db, username, password, callback) {
    db.get(
        "SELECT username FROM users WHERE username = ? AND password = ?",
        [username, password],
        callback
    );
}

module.exports = {
    createAccount,
    findMatchingUser
};