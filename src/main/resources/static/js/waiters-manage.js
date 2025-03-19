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
                    <button class="edit-order-btn" onclick="editOrder(${order.id})">Edit Order</button>
                    <button class="confirm-order-btn" onclick="deleteOrder(${order.id})">Confirm Order</button>
                    <button class="delete-order-btn" onclick="deleteOrder(${order.id})">Delete Order</button>
                </div>
            `; // change deleteOrder in edit-order-btn
            //below are the poor victims of angelo. GG indicators see you later
            const indicator = document.createElement('span');
            switch(order.status) {
                case 'ready':
                    indicator.className = 'order-ready-indicator';
                    indicator.title = 'Ready for pickup';
                    break;
                case 'cooking':
                    indicator.className = 'order-cooking-indicator';
                    indicator.title = 'Order is being cooked';
                    break;
                case 'pending':
                default:
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

async function editOrder(orderId) {
    try {
        const response = await fetch('/api/CurrentOrders/orderItems');
        if (!response.ok) {
            throw new Error('Failed to fetch orders');
        }
        let orderItems = null;
        let i = 0;
        let orderIdIndex = 0;
        const orders = await response.json();
        orders.forEach(order => {
            if (order.id == orderId) {
                orderIdIndex = i;
            }
            i++;
        });

        const editOrderModal = document.getElementById('edit-order-select');
        const select = document.createElement('select');

        let itemsToAdd = Object.keys(orders[orderIdIndex].items);
        console.log(itemsToAdd);
        itemsToAdd.forEach(item =>{
            const itemElement = document.createElement('option');
            itemElement.value = item;
            itemElement.textContent = item;
            select.appendChild(itemElement);
        });
        editOrderModal.innerHTML = `
        <p>Order ID: ${orderId}</p>
        <p>Items Currently In Cart:<p>
        <p>${formatOrderItems(orders[orderIdIndex].items)}</p>
        <button class="add-item-btn">Add To Order</button>
        <button class="delete-item-btn">Remove From Order</button>
        `;
        editOrderModal.appendChild(select);
        const closeButton = document.createElement('button');
        closeButton.textContent = 'Close';
        closeButton.onclick = function() {
            closeEditModal();
        };
        editOrderModal.appendChild(closeButton);
        document.getElementById('edit-order-modal').style.display = 'block';

    } catch (error) {
        console.error('Error fetching orders:', error);
        document.getElementById('orders-container').innerHTML = '<p>Error loading orders.</p>';
    }

}

function closeEditModal() {
    document.getElementById('edit-order-modal').style.display = 'none';
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

// Spectacular code that looks if to see if a table has a waiter
async function hasWaiter(tableNumber) {
    try {
        const response = await fetch('/api/tableAssignments/assignedTables');
        if (!response.ok) {
            throw new Error('Failed to fetch waiter assignments');
        }

        const assignments = await response.json();
        const assignment = assignments.find(assignment => assignment.tableNumber === tableNumber);

        // This should return the waiter's name or none 
        return assignment && assignment.waiterUsername ? assignment.waiterUsername : 'None';

    } catch (error) {
        console.error('Error checking waiter assignment:', error);
        return 'None';
    }
}


async function generateTablesOverview() {
    const tablesContainer = document.getElementById('tables-container');
    tablesContainer.innerHTML = '';

    try {
        const response = await fetch('/api/CurrentOrders/all');
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const orders = await response.json();
        console.log("Fetched orders:", orders); // Debugging

        // Create a Set of tables that have active orders
        const activeTables = new Set(orders.map(order => order.tableNumber));

        for (let i = 1; i <= 12; i++) {
            const tableDiv = document.createElement('div');
            tableDiv.className = 'table';
            tableDiv.dataset.tableId = i;

            // Check if the table is active and has a waiter asigned
            const isInUse = activeTables.has(i);
            const assignedWaiter = await hasWaiter(i);

            tableDiv.innerHTML = `
                <div class="table-header">
                    <span class="table-name">Table ${i}</span>
                    <span class="status-dot ${isInUse ? 'dot-red' : 'dot-green'}"></span>
                </div>
                <div class="table-details hidden">
                    <p>Status: <span class="${isInUse ? 'occupied' : 'available'}">
                        ${isInUse ? 'Occupied' : 'Available'}
                    </span></p>
                    <p>Assigned Waiter: <span class="waiter-name">${assignedWaiter}</span></p>
                        ${assignedWaiter === 'None' && isInUse === true ? `<button onclick="openWaiterAssignment(${i})">Assign Waiter</button>` : ''}
                </div>
            `;

            //  event listener for toggling visibility
            tableDiv.addEventListener('click', function () {
                this.querySelector('.table-details').classList.toggle('hidden');
            });

            tablesContainer.appendChild(tableDiv);
        }

    } catch (error) {
        console.error('Error fetching table status:', error);
        tablesContainer.innerHTML = `<p>Error loading tables. ${error.message}</p>`;
    }
}


async function openWaiterAssignment(tableNumber) {
    document.getElementById('selected-table').innerText = tableNumber;

    try {
        const response = await fetch('/api/login/all');
        if (!response.ok) throw new Error("Failed to fetch waiters");

        const waiters = await response.json();
        const waiterSelect = document.getElementById('waiter-select');
        waiterSelect.innerHTML = '';

        if (waiters.length === 0) {
            waiterSelect.innerHTML = '<option disabled>No waiters available</option>';
        } else {
            waiters.forEach(waiter => {
                const option = document.createElement('option');
                option.value = waiter;
                option.textContent = waiter;
                waiterSelect.appendChild(option);
            });
        }

        document.getElementById('assignWaiterModal').classList.remove('hidden');

    } catch (error) {
        console.error("Error fetching waiters:", error);
        alert("Failed to load waiters.");
    }
}

function closeModal() {
    document.getElementById('assignWaiterModal').classList.add('hidden');
}


async function assignWaiter() {
    const tableNumber = document.getElementById('selected-table').innerText;
    const waiterUsername = document.getElementById('waiter-select').value;

    try {
        const response = await fetch('/api/tableAssignments/assign', {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: new URLSearchParams({ tableNumber, waiterUsername })
        });

        if (!response.ok) throw new Error(await response.text());

        alert(`Waiter ${waiterUsername} assigned to Table ${tableNumber}`);
        closeModal();

    } catch (error) {
        console.error("Error assigning waiter:", error);
        alert("Failed to assign waiter.");
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
