const express = require("express");
const session = require("express-session");
const sqlite3 = require("sqlite3").verbose();
const path = require("path");
const crypto = require("crypto");

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
    const username = String(req.body.username || "").trim();
    const password = String(req.body.password || "");

    if (!username || !password) {
        return res.status(400).render("login", {
            error: "Please enter your username and password."
        });
    }

    db.get(
        "SELECT username FROM users WHERE username = ? AND password = ?",
        [username, password],
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
    const username = String(req.body.username || "").trim();
    const password = String(req.body.password || "");

    if (!username || !password.trim()) {
        return res.status(400).render("register", {
            error: "Please enter a username and password."
        });
    }

    db.run(
        "INSERT INTO users (username, password) VALUES (?, ?)",
        [username, password],
        (err) => {
            if (err) {
                const message =
                    err.code === "SQLITE_CONSTRAINT"
                        ? "That username already exists. Please choose another."
                        : "Unable to create your account. Please try again.";

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