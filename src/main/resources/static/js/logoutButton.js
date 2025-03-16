
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
            if (data.authentication) {
                document.getElementById('logoutButton').style.display = 'block';
                document.getElementById('loginButton').style.display = 'none';
            }
        })
        .catch(error => {
            document.getElementById('logoutButton').style.display = 'none';
            document.getElementById('loginButton').style.display = 'block';
        })
}

document.addEventListener('DOMContentLoaded', logoutOrLoginButton);
