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
        .then(async response => {
            if ((response.ok)) {
                const redirect = await response.text(); 
                if (redirect == "http://localhost:8080/login-confirmation.html") {
                    window.location.href = redirect; 
                } 
                else {
                    document.getElementById("responseMessage").innerText = "Login failed. Please try again.";
                }
            }
        })
        .catch (error => {
            console.error("Error:", error);
            document.getElementById("responseMessage").innerText = "An error occurred. Please try again.";
        });
});