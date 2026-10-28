const express = require("express");
const session = require("express-session");
const sqlite3 = require("sqlite3").verbose();
const path = require("path");
const crypto = require("crypto");
const {
    normalizeCredentials,
    validateLogin,
    validateRegistration,
    getRegistrationErrorMessage
} = require("./authHelpers");

const {
    createAccount,
    findMatchingUser
} = require("./accountHelpers");

const app = express();
const PORT = 3000;

const db = new sqlite3.Database(path.join(__dirname, "users.db"));

app.set("view engine", "ejs");
app.set("views", path.join(__dirname, "views"));

app.use(express.urlencoded({ extended: false }));
app.use(express.static(path.join(__dirname, "public")));

app.use(
    session({
        secret: crypto.randomBytes(32).toString("hex"),
        resave: false,
        saveUninitialized: false,
        cookie: {
            httpOnly: true,
            sameSite: "lax"
        }
    })
);

app.get("/", (req, res) => {
    res.redirect(req.session.username ? "/landing" : "/login");
});

app.get("/login", (req, res) => {
    res.render("login", { error: null });
});

app.post("/login", (req, res) => {
    const { username, password } = normalizeCredentials(req.body);
    const error = validateLogin(username, password);

    if (error) {
        return res.status(400).render("login", { error });
    }

    findMatchingUser(
        db,
        username,
        password,
        (err, user) => {
            if (err) {
                return res.status(500).render("login", {
                    error: "Unable to log in. Please try again."
                });
            }

            if (!user) {
                return res.status(401).render("login", {
                    error: "Incorrect username or password."
                });
            }

            req.session.username = user.username;
            res.redirect("/landing");
        }
    );
});

app.get("/register", (req, res) => {
    res.render("register", { error: null });
});

app.post("/register", (req, res) => {
    const { username, password } = normalizeCredentials(req.body);
    const error = validateRegistration(username, password);

    if (error) {
        return res.status(400).render("register", { error });
    }

    createAccount(
        db,
        username,
        password,
        (err) => {
            if (err) {
                const message = getRegistrationErrorMessage(err);

                return res.status(400).render("register", {
                    error: message
                });
            }

            req.session.username = username;
            res.redirect("/landing");
        }
    );
});

app.get("/landing", (req, res) => {
    if (!req.session.username) {
        return res.redirect("/login");
    }

    res.render("landing", {
        username: req.session.username
    });
});

app.post("/logout", (req, res) => {
    req.session.destroy((err) => {
        if (err) {
            return res.status(500).send("Unable to log out. Please try again.");
        }

        res.clearCookie("connect.sid");
        res.redirect("/login");
    });
});

db.run(
    `CREATE TABLE IF NOT EXISTS users (
        username TEXT PRIMARY KEY NOT NULL,
        password TEXT NOT NULL
    )`,
    (err) => {
        if (err) {
            console.error("Unable to create the users table:", err.message);
            process.exit(1);
        }

        app.listen(PORT, () => {
            console.log(`App running at http://localhost:${PORT}`);
        });
    }
);