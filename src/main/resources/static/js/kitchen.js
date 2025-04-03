let allStock = [];
const ingredientList = [
    {item : "Malcom", ingredients: "Reynolds"},
  ];

document.addEventListener('DOMContentLoaded', function () {
    fetchOrders();
    fetchStock();
});

async function fetchOrders() {
    try {
        const response = await fetch('/api/CurrentOrders/all');
        if (!response.ok) {
            throw new Error('Failed to fetch orders');
        }

        const orders = await response.json();
        const ordersContainer = document.getElementById('orders-container');
        ordersContainer.innerHTML = '';
        const confirmedOrders = orders.filter(order => order.confirmed === true);
        
        if (confirmedOrders.length === 0) {
            ordersContainer.innerHTML = '<p>No orders found.</p>';
            return;
       }

        confirmedOrders.forEach(order => {
            const orderElement = document.createElement('div');
            orderElement.className = 'order-card';

            orderElement.innerHTML = `
                <div class="order-card-header">
                    <strong>Order #${order.id}</strong> - Table ${order.tableNumber}
                </div>
                <div class="order-card-body">
                    <p><strong>Items:</strong> ${formatOrderItems(order.items)}</p>
                    <p><strong>Ordered:</strong> ${formatOrderTime(order.orderTime)} 
                    (${formatTimeSinceOrder(order.timeSinceOrder)})</p>
                </div>
                <div class="order-card-footer">
                    <button class="delete-order-btn" onclick="deleteOrder(${order.id})">Cancel Order</button>
                    <button class="confirm-order-btn" onclick="confirmOrder(${order.id})">Confirm Order</button>
                    <button class="btn pending" onclick="updateOrderStatus(${order.id}, 'pending')">Pending</button>
                    <button class="btn cooking" onclick="updateOrderStatus(${order.id}, 'cooking')">Cooking</button>
                    <button class="btn ready" onclick="updateOrderStatus(${order.id}, 'ready')">Ready</button>
                    <button class="btn delivered" onclick="updateOrderStatus(${order.id}, 'delivered')">Delivered</button>
                </div>                
            `;

            const indicator = document.createElement('span');
            switch (order.status) {
                case 'delivered':
                    orderElement.style.borderColor = 'blue';
                    orderElement.style.boxShadow = '0 3px 8px blue';
                    indicator.className = 'order-delivered-indicator';
                    indicator.title = 'Order delivered';
                    break;
                case 'ready':
                    orderElement.style.borderColor = 'green';
                    orderElement.style.boxShadow = '0 3px 8px green';
                    indicator.className = 'order-ready-indicator';
                    indicator.title = 'Ready for pickup';
                    break;
                case 'cooking':
                    orderElement.style.borderColor = 'orange';
                    orderElement.style.boxShadow = '0 3px 8px orange';
                    indicator.className = 'order-cooking-indicator';
                    indicator.title = 'Order is being cooked';
                    break;
                case 'pending':
                default:
                    orderElement.style.borderColor = 'red';
                    orderElement.style.boxShadow = '0 3px 8px red';
                    indicator.className = 'order-pending-indicator';
                    indicator.title = 'Order is pending';
            }

            orderElement.appendChild(indicator);
            ordersContainer.appendChild(orderElement);
        });

    } catch (error) {
        console.error('Error fetching orders:', error);
        document.getElementById('orders-container').innerHTML = '<p>Error loading orders.</p>';
    }
}

async function fetchStock() {
    try {
        const response = await fetch('/api/stock', {
            headers: {
                'Accept': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        allStock = data;
        console.log(allStock);
        const beefGringa = ["Tortilla", "Steak"];
        return data;
    } catch (error) {
        console.error('Error fetching items:', error);
        return [];
    }
}


async function confirmOrder(orderId) {
    let ingredientStock = true;
    try {
        const response = await fetch('/api/CurrentOrders/orderItems');
        if (!response.ok) {
            throw new Error('Failed to fetch orders');
        }

        const orders = await response.json();
        const order = orders.find(order => order.id == orderId);
        if (!order) throw new Error("Order not found");

        const orderedItems = Object.entries(order.items).flatMap(([itemName, quantity]) =>
            Array(quantity).fill(itemName)
        );

        await Promise.all(orderedItems.map(async (itemName) => {
            try {
                const res = await fetch(`/api/recipes/name/${itemName}`, {
                    method: 'GET',
                    headers: { "Accept": "application/json" }
                });

                if (!res.ok) {
                    throw new Error(`This item does not have ingredients assigned on the menu.`);
                }

                const data = await res.json();
                let ingredients = data.ingredients;

                for (let k = 0; k < allStock.length; k++) {
                    if (ingredients.includes(allStock[k].title)) {
                        if (allStock[k].quantity <= 0) {
                            ingredientStock = false;
                            return;
                        }
                        allStock[k].quantity -= 1;
                    }
                }
            } catch (error) {
                console.error("Error fetching recipe:", error);
            }
        }));

    } catch (error) {
        console.error('Error fetching items:', error);
    }

    if (ingredientStock) {
        alert("Sufficient amount of ingredients to create the order.");
    } else {
        alert("Insufficient amount of ingredients to create the order. Please cancel the order.");
    }
}


function formatOrderItems(items) {
    return Object.entries(items).map(([name, quantity]) => `${name} x${quantity}`).join(', ');
}

function formatOrderTime(timestamp) {
    const date = new Date(timestamp);
    return date.toLocaleString();

}

function formatTimeSinceOrder(durationString) {
    const match = durationString.match(/PT(\d+H)?(\d+M)?(\d+S)?/);
    const hours = match[1] ? match[1].replace('H', ' hours ') : '';
    const minutes = match[2] ? match[2].replace('M', ' minutes ') : '';
    const seconds = match[3] ? match[3].replace('S', ' seconds') : '';
    return `${hours}${minutes}${seconds}`.trim();
}

async function updateOrderStatus(orderId, newStatus) {
    const response = await fetch(`/api/orders/${orderId}/status`, {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ status: newStatus })
    });
    if (!response.ok) {
      throw new Error('Failed to update order status');
    }
    fetchOrders();
}

async function deleteOrder(orderId) {
    if (!confirm(`Are you sure you want to delete Order #${orderId}?`)) {
        return;
    }

    try {
        const response = await fetch('/api/DeleteOrder', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(orderId)
        });

        if (!response.ok) {
            throw new Error('Failed to delete order');
        }

        alert(`Order #${orderId} deleted successfully!`);
        fetchOrders();  // Refresh orders list after deletion
    } catch (error) {
        console.error('Error deleting order:', error);
        alert('Failed to delete order.');
    }
}

async function updateOrderStatus(orderId, newStatus) {
    const response = await fetch(`/api/orders/${orderId}/status`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ status: newStatus })
    });

    if (!response.ok) {
        throw new Error('Failed to update order status');
    }

    fetchOrders();
}
