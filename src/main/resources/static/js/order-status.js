document.addEventListener("DOMContentLoaded", async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get("orderId");

    if (!orderId) {
        alert("Invalid order. Returning to menu.");
        window.location.href = "/menu.html";
        return;
    }

    const statusText = document.getElementById("status-text");
    const statusImage = document.getElementById("status-image");
    const orderIdDisplay = document.getElementById("order-id");
    const refreshButton = document.getElementById("refresh-status");
    const payOnlineBtn = document.getElementById("pay-online-btn");
    const payWaiterBtn = document.getElementById("pay-waiter-btn");

    orderIdDisplay.textContent = orderId;

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

            // Will just show this message instead of showing the other pages (Not tested, should work)
            if (order.paid) {
                statusText.textContent = "Thank you for your payment! We hope you enjoyed your meal.";
                statusImage.src = "images/payment_complete.gif"; // Needs to be added to the files (File currently missing)
                document.getElementById('payment-options').style.display = 'none';
                return;
            }

            switch (status) {
                case 'delivered':
                    statusText.textContent = "Your food has been delivered. Enjoy!";
                    statusImage.src = "images/food_delivered.gif";
                    
                    // Displays the payment option
                    document.getElementById('payment-options').style.display = 'block';
                    
                    payOnlineBtn.onclick = function() {
                        window.location.href = `/payment-form.html?orderId=${orderId}&tableNumber=${tableNumber}`;
                    };
                    break;
                case 'pending':
                    statusText.textContent = "We will get to your food shortly!";
                    statusImage.src = "images/food_loading.gif";
                    
                    // Hides the payment option (Copied for all cases for the status)
                    document.getElementById('payment-options').style.display = 'none';
                    break;
                case 'cooking':
                    statusText.textContent = "We are making your delicious food!!";
                    statusImage.src = "images/food_cooking.gif";
                    
                    document.getElementById('payment-options').style.display = 'none';
                    break;
                case 'ready':
                    statusText.textContent = "Your food is on the way!";
                    statusImage.src = "images/food_ready.gif";
                    
                    document.getElementById('payment-options').style.display = 'none';
                    break;
                default:
                    statusImage.src = "images/default-status.png";
                    
                    document.getElementById('payment-options').style.display = 'none';
            }
        } catch (error) {
            console.error("Error fetching order status:", error);
            statusText.textContent = "Error fetching status.";
        }
    }

    refreshButton.addEventListener("click", fetchOrderStatus);
    fetchOrderStatus();

    // Auto-refresh order status every 5 seconds
    setInterval(fetchOrderStatus, 5000);
});