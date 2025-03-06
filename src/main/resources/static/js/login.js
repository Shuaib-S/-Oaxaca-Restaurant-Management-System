fetch('api/login/validate')
    .then(response => response.json())
    .then(data => {
        if (data.authenticated) {
            window.location.href= 'login-confirmation.html';
        }
    })

document.getElementById("loginForm").addEventListener("submit", async function(event) {
    event.preventDefault(); 

    const loginData = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    };

        fetch("/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(loginData)
        })
        .then(response => response.json())
            .then(data => {
                if (data.success) {
                    window.location.href = data.redirect;
                } else {
                    document.getElementById("responseMessage").innerText = "Login failed. Please try again.";
                }
            })
        .catch (error => {
            console.error("Error:", error);
            document.getElementById("responseMessage").innerText = "An error occurred. Please try again.";
        });
});
