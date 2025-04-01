// Place holder while testing
document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get('orderId');
    const tableNumber = urlParams.get('tableNumber');
    
    if (orderId) {
        document.getElementById('order-id').textContent = orderId;
    }
    
    if (tableNumber) {
        document.getElementById('table-number').textContent = tableNumber;
    }
    
    document.getElementById('cancel-payment').addEventListener('click', function() {
        window.location.href = `/order-status.html?orderId=${orderId}`;
    });
    
    document.getElementById('payment-form').addEventListener('submit', function(event) {
        event.preventDefault();

    });
    
    const cardNumberInput = document.getElementById('card-number');
    cardNumberInput.addEventListener('input', function() {

    });
    
    const expiryInput = document.getElementById('expiry-date');
    expiryInput.addEventListener('input', function() {

    });
});