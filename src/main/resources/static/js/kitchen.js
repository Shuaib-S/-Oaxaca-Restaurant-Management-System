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
                    <button class="delete-order-btn" onclick="deleteOrder(${order.id})">Delete Order</button>
                    <button class="btn pending" onclick="updateOrderStatus(${order.id}, 'pending')">Pending</button>
                    <button class="btn cooking" onclick="updateOrderStatus(${order.id}, 'cooking')">Cooking</button>
                    <button class="btn ready" onclick="updateOrderStatus(${order.id}, 'ready')">Ready</button>
                </div>                
            `;

            const indicator = document.createElement('span');
            switch(order.status) {
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
