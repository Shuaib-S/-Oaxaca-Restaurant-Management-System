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

    orderIdDisplay.textContent = orderId;

    async function fetchOrderStatus() {
        try {
            const response = await fetch(`/api/orders/${orderId}`);
            if (!response.ok) throw new Error("Failed to fetch order status");

            const order = await response.json();
            const status = order.status.toLowerCase();

            // Changes page based on your order status
            switch (status) {
                case "pending":
                    statusText.textContent = "We will get to your food shortly!";
                    statusImage.src = "images/food_loading.gif";
                    break;
                case "cooking":
                    statusText.textContent = "We are making your delicious food!!";
                    statusImage.src = "images/food_cooking.gif";
                    break;
                case "ready":
                    statusText.textContent = "Your food is on the way!";
                    statusImage.src = "images/food_ready.gif";
                    break;
                   
                //case "delivered":
                 //   statusImage.src = "images/food_ready.gif";
                //    break;
                     
                default:
                    statusImage.src = "images/default-status.png";
            }
        } catch (error) {
            console.error("Error fetching order status:", error);
            statusText.textContent = "Error fetching status.";
        }
    }

    refreshButton.addEventListener("click", fetchOrderStatus);
    fetchOrderStatus();

    //  Auto-refresh order status every 5 seconds
    setInterval(fetchOrderStatus, 5000);
});
