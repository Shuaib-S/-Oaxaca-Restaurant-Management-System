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
            if (response.ok) {
            const redirect = await response.text(); 
            window.location.href = redirect; 
            console.log("HELLO WORLD"); // I'll remove console logs later, I'm using these to test which statement the frontend uses when entering test data. Backend requires a security config before further progress can be made.
            } else {
            document.getElementById("responseMessage").innerText = "Login failed. Please try again.";
            console.log("HELLO");
            }
        })
        .catch (error => {
            console.error("Error:", error);
            document.getElementById("responseMessage").innerText = "An error occurred. Please try again.";
        });
});