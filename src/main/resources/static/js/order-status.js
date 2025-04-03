document.addEventListener("DOMContentLoaded", async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get("orderId");
    const isPaid = urlParams.get("paid") === "true";

    if (!orderId) {
        alert("Invalid order. Returning to menu.");
        window.location.href = "/menu.html";
        return;
    }

    const statusText = document.getElementById("status-text");
    const statusImage = document.getElementById("status-image");
    const orderIdDisplay = document.getElementById("order-id");
    const refreshButton = document.getElementById("refresh-status");
    const paymentOptions = document.getElementById("payment-options");
    const paymentConfirmation = document.getElementById("payment-confirmation");
    const payOnlineBtn = document.getElementById("pay-online-btn");
    const payWaiterBtn = document.getElementById("pay-waiter-btn");

    orderIdDisplay.textContent = orderId;

    if (isPaid) {
        showPaymentConfirmation();
    }

    payOnlineBtn.addEventListener("click", function() {
        window.location.href = `/payment-form.html?orderId=${orderId}`;
    });

    payWaiterBtn.addEventListener("click", function() {
        alert("A waiter will be with you shortly to process your payment. Thank you for dining with us!");
    });

    async function fetchOrderStatus() {
        try {
            const response = await fetch(`/api/orders/${orderId}`);
            if (!response.ok) throw new Error("Failed to fetch order status");

            const order = await response.json();
            const status = order.status.toLowerCase();
            const tableNumber = order.tableNumber;

            if (order.paid) {
                showPaymentConfirmation();
                return;
            }

            paymentConfirmation.style.display = 'none';

            switch (status) {
                case 'delivered':
                    statusText.textContent = "Your food has been delivered. Enjoy!";
                    statusImage.src = "images/food_delivered.gif";
                    
                    // Displays the payment option
                    paymentOptions.style.display = 'block';
                    
                    payOnlineBtn.onclick = function() {
                        window.location.href = `/payment-form.html?orderId=${orderId}&tableNumber=${tableNumber}`;
                    };
                    break;
                case 'pending':
                    statusText.textContent = "We will get to your food shortly!";
                    statusImage.src = "images/food_loading.gif";
                    
                    // Hides the payment option (Copied for all cases for the status)
                    paymentOptions.style.display = 'none';
                    break;
                case 'cooking':
                    statusText.textContent = "We are making your delicious food!!";
                    statusImage.src = "images/food_cooking.gif";
                    
                    paymentOptions.style.display = 'none';
                    break;
                case 'ready':
                    statusText.textContent = "Your food is on the way!";
                    statusImage.src = "images/food_ready.gif";
                    
                    paymentOptions.style.display = 'none';
                    break;
                default:
                    statusImage.src = "images/default-status.png";
                    
                    paymentOptions.style.display = 'none';
            }
        } catch (error) {
            console.error("Error fetching order status:", error);
            statusText.textContent = "Error fetching status.";
        }
    }

    // Func to show payment confirmation UI (moved from inside try case)
    function showPaymentConfirmation() {
        statusText.textContent = "Thank you for your payment! We hope you enjoyed your meal.";
        statusImage.src = "images/payment_complete.gif"; // This gif needs to be added into the files
        paymentOptions.style.display = 'none';
        paymentConfirmation.style.display = 'block';
        
        refreshButton.disabled = true;
    }

    refreshButton.addEventListener("click", fetchOrderStatus);
    fetchOrderStatus();

    // Auto-refresh order status every 5 seconds (If the order is not paid)
    const refreshInterval = setInterval(function() {
        if (paymentConfirmation.style.display === 'block') {
            clearInterval(refreshInterval);
        } else {
            fetchOrderStatus();
        }
    }, 5000);
});