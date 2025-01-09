document.addEventListener("DOMContentLoaded", () => {
    let contactButton = document.getElementById("contact-button");

    contactButton.addEventListener("keypress", function(event) {
        if (event.key === "Enter") {
            event.preventDefault();
            contactButton.click();
        }
    });

    contactButton.addEventListener("click", () => {
        let name = document.getElementById("contact-name");
        let email = document.getElementById("contact-email");
        let message = document.getElementById("contact-message");

        if (name.value === "") {
            name.focus();
            return;
        }
        if (email.value === "") {
            email.focus();
            return;
        }
        if (message.value === "") {
            message.focus();
            return;
        }

        console.log("name=" + name + "; email=" + email + "; message=" + message);
    });
})


