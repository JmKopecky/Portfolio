function submitContactForm() {
    let nameInput = $("#contact-name");
    if (nameInput.val() === "") {
        nameInput.focus();
        return;
    }
    let emailInput = $("#contact-email");
    if (emailInput.val() === "") {
        emailInput.focus();
        return;
    }
    let messageInput = $("#contact-message");
    if (messageInput.val() === "") {
        messageInput.focus();
        return;
    }
    fetch("/admin/signon", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: nameInput.val(),
            email: emailInput.val(),
            message: messageInput.val()
        }),
    }).then((r) => {
        if (r.ok) {
            $("#contact-button").text("Thank You!");
            setTimeout(() => {
                $("#contact-button").text("Send");
            }, 5000);
        }
    });
}