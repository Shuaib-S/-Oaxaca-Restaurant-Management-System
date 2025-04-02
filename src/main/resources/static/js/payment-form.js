document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get('orderId');
    const tableNumber = urlParams.get('tableNumber');
    
    if (orderId) {
        document.getElementById('order-id').textContent = orderId;
        fetchOrderDetails(orderId);
    } else {
        window.location.href = '/menu.html';
    }
    
    if (tableNumber) {
        document.getElementById('table-number').textContent = tableNumber;
    }
    
    document.getElementById('cancel-payment').addEventListener('click', function() {
        window.location.href = `/order-status.html?orderId=${orderId}`;
    });
    
    document.getElementById('payment-form').addEventListener('submit', function(event) {
        event.preventDefault();
        
        const cardHolder = document.getElementById('card-holder').value;
        const cardNumber = document.getElementById('card-number').value.replace(/\s/g, '');
        const expiryDate = document.getElementById('expiry-date').value;
        const cvv = document.getElementById('cvv').value;
        const email = document.getElementById('email').value;
        
        // Validation can be improved, to check characters etc. (Should be good enough for now though)
        if (!cardHolder || !cardNumber || !expiryDate || !cvv) {
            alert('Please fill in all required fields');
            return;
        }
        
        processPayment(orderId);
    });
    
    // Formats the card number incl. white spaces
    const cardNumberInput = document.getElementById('card-number');
    cardNumberInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        value = value.replace(/(.{4})/g, '$1 ').trim();
        e.target.value = value;
    });
    
    // Formats expiry date as MM/YY
    const expiryInput = document.getElementById('expiry-date');
    expiryInput.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length > 2) {
            value = value.substring(0, 2) + '/' + value.substring(2, 4);
        }
        e.target.value = value;
    });

    // Simple check to only allow numbers in the CVV form
    const cvvInput = document.getElementById('cvv');
    cvvInput.addEventListener('input', function(e) {
        e.target.value = e.target.value.replace(/\D/g, '');
    });
});

// Fetch order details to display in the payment form
async function fetchOrderDetails(orderId) {
    try {
        const response = await fetch(`/api/orders/${orderId}`);
        if (!response.ok) throw new Error('Failed to fetch order details');
        
        const order = await response.json();
        
        document.getElementById('table-number').textContent = order.tableNumber;
        
        const orderItemsContainer = document.getElementById('order-items');
        orderItemsContainer.innerHTML = '<h3>Order Items:</h3>';
        
        const itemsList = document.createElement('ul');
        itemsList.classList.add('items-list');

        // NOTE: Needs to properly fetch order information to use correct prices & items
        
        let totalAmount = 0;
        
        if (order.items) {
            Object.entries(order.items).forEach(([name, quantity]) => {
                const item = document.createElement('li');
                const itemPrice = 10; // NOTE: Using a place holder price for testing purposes. Needs to be updated to fetch actual item prices.
                item.textContent = `${name} x${quantity} - £${(itemPrice * quantity).toFixed(2)}`;
                itemsList.appendChild(item);
                totalAmount += itemPrice * quantity;
            });
        }
        
        orderItemsContainer.appendChild(itemsList);
        
        document.getElementById('order-amount').textContent = `£${totalAmount.toFixed(2)}`;    
    } catch (error) {
        console.error('Error fetching order details:', error);
        document.getElementById('order-items').innerHTML = '<p>Unable to fetch order details</p>';
    }
}

// Process payment
function processPayment(orderId) {
    const submitButton = document.getElementById('submit-payment');
    const originalText = submitButton.textContent;
    submitButton.textContent = 'Processing...';
    submitButton.disabled = true;
    
    // Send payment to backend (Needs to be added to test cases, should work though)
    fetch(`/api/orders/${orderId}/pay`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (!response.ok) throw new Error('Payment failed');
        return response.json();
    })
    .then(data => {
        alert('Payment successful! Thank you for dining with us.');
        
        window.location.href = `/order-status.html?orderId=${orderId}&paid=true`;
    })
    .catch(error => {
        console.error('Error processing payment:', error);
        alert('Payment processing failed. Please try again.');
        
        submitButton.textContent = originalText;
        submitButton.disabled = false;
    });
}