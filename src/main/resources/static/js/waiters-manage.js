document.addEventListener('DOMContentLoaded', function () {
    fetchOrders();
    generateTablesOverview();
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

        if (orders.length === 0) {
            ordersContainer.innerHTML = '<p>No orders found.</p>';
            return;
        }

        orders.forEach(order => {
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
                    <button class="delete-order-btn" onclick="deleteOrder(${order.id})">Delete Order</button>
                </div>
            `;

            ordersContainer.appendChild(orderElement);
        });

    } catch (error) {
        console.error('Error fetching orders:', error);
        document.getElementById('orders-container').innerHTML = '<p>Error loading orders.</p>';
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

function generateTablesOverview() {
    const tablesContainer = document.getElementById('tables-container');
    tablesContainer.innerHTML = '';

    for (let i = 1; i <= 12; i++) {
        const tableDiv = document.createElement('div');
        tableDiv.className = 'table';
        tableDiv.textContent = `Table ${i}`;
        tablesContainer.appendChild(tableDiv);
    }
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
