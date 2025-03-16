
function logoutOrLoginButton() {
    fetch('/api/login/validate', {
        method: 'GET',
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Unauthorised');
        })
        .then (data => {
            if (data.authenticated) {
                document.getElementById('logoutButton').style.display = 'block';
                document.getElementById('loginButton').style.display = 'none';
            }
        })
        .catch(error => {
            document.getElementById('logoutButton').style.display = 'none';
            document.getElementById('loginButton').style.display = 'block';
        })
}

function logout() {
    fetch('/api/login/logout', {
        method: 'POST',
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                logoutOrLoginButton();
                window.location.href = '/';
            }
        })
        .catch(error => {
            console.error('Logout failed', error);
        });
}

document.addEventListener('DOMContentLoaded', () => {
    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', logout);
    }

    logoutOrLoginButton();
});
