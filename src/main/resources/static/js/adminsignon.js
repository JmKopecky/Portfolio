function sign() {
    const signonUsernameField = document.getElementById("signon-username-input");
    const signonPasswordField = document.getElementById("signon-password-input");
    let signonUsername = signonUsernameField.value;
    let signonPassword = signonPasswordField.value;

    if (signonUsername === "") {
        signonUsernameField.focus();
    }
    if (signonPassword === "") {
        signonPasswordField.focus();
    }

    fetch("signon", {
        method: "POST",
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        "body": JSON.stringify({
            "username": signonUsername,
            "password": signonPassword
        }),
    }).then(r => {
        r.text().then((data) => {
            console.log(data);
            const feedback = document.getElementById("auth-message");
            if (data === "authenticated") {
                feedback.textContent = "Authenticated...";
                feedback.classList.add("message-hidden");
                swup.navigate("/admin/panel");
            } else {
                feedback.textContent = "Unauthenticated";
                feedback.classList.remove("message-hidden");
            }
            return false;
        })
    });
    return false;
}