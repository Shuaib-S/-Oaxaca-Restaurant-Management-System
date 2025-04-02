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
                    ${order.paid ? '<p class="paid-status"><strong>PAID</strong></p>' : ''}
                </div>
                <div class="order-card-footer">
                    <button class="edit-order-btn" onclick="editOrder(${order.id})">Edit Order</button>
                    <button class="pending-order-btn" onclick="updateOrderStatus(${order.id}, 'pending')">Pending</button>
                    <button class="cooking-order-btn" onclick="updateOrderStatus(${order.id}, 'cooking')">Cooking</button>
                    <button class="ready-order-btn" onclick="updateOrderStatus(${order.id}, 'ready')">Ready</button>
                    ${order.status === 'ready' ? 
                    `<button class="deliver-order-btn" onclick="markAsDelivered(${order.id})">Mark as Delivered</button>` : ''}
                    <button class="confirm-order-btn" onclick="confirmOrder(${order.id})">Confirm Order</button>
                    <button class="delete-order-btn" onclick="deleteOrder(${order.id})">Delete Order</button>
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

async function confirmOrder(orderId) {
    try {
        const orderID = orderId;
        const response = await fetch('/api/CurrentOrders/confirmOrder', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ orderID })
        });
        if (!response.ok) {
            throw new Error('Failed to fetch orders');
        }

    } catch (error) {
        console.error('Error fetching orders:', error);
        document.getElementById('orders-container').innerHTML = '<p>Error loading orders.</p>';
    }
    console.log("WOO");

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

        const response2 = await fetch('/api/items');
        if (!response2.ok) {
            throw new Error('Failed to fetch items');
        }

        const data = await response2.json();
        let allItems = [];
        allItems = data;
        let j = 0;
        let itemsToAdd = [];
        allItems.forEach(item => {
            itemsToAdd[j] = allItems[j].title;
            j++;
        });

        const editOrderModal = document.getElementById('edit-order-select');
        const select = document.createElement('select');


        console.log(itemsToAdd);
        itemsToAdd.forEach(item => {
            const itemElement = document.createElement('option');
            itemElement.value = item;
            itemElement.textContent = item;
            select.appendChild(itemElement);
        });
        editOrderModal.innerHTML = `
        <p>Order ID: ${orderId}</p>
        <p>Items Currently In Cart:<p>
        <p>${formatOrderItems(orders[orderIdIndex].items)}</p>
        <button class="add-item-btn" onclick="addToActiveOrder(${orders[orderIdIndex].id})">Add To Order</button>
        <button class="delete-item-btn" onclick="removeFromActiveOrder(${orders[orderIdIndex].id})">Remove From Order</button>
        `;
        editOrderModal.appendChild(select);
        const closeButton = document.createElement('button');
        closeButton.textContent = 'Close';
        closeButton.onclick = function () {
            closeEditModal();
        };
        editOrderModal.appendChild(closeButton);
        document.getElementById('edit-order-modal').style.display = 'block';

    } catch (error) {
        console.error('Error fetching orders:', error);
        document.getElementById('orders-container').innerHTML = '<p>Error loading orders.</p>';
    }

}

async function addToActiveOrder(orderId) {
    try {
        selectedItem = document.querySelector('select');
        const orderID = orderId;
        const itemName = selectedItem.options[selectedItem.selectedIndex].value;
        console.log(orderID);
        console.log(itemName);
        const response = await fetch('/api/CurrentOrders/addItem', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ orderID, itemName })
        });
        if (!response.ok) {
            throw new Error('Failed to fetch orders');
        }
        alert("You have successfully added an item!");
        location.reload();

    } catch (error) {
        console.error('Error fetching orders:', error);
        alert('An error has occurred while attempting to add:', itemName);
    }
}

async function removeFromActiveOrder(orderId) {
    try {
        selectedItem = document.querySelector('select');
        const orderID = orderId;
        const itemName = selectedItem.options[selectedItem.selectedIndex].value;
        console.log(orderID);
        console.log(itemName);
        const response = await fetch('/api/CurrentOrders/removeItem', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ orderID, itemName })
        });
        if (!response.ok) {
            throw new Error('Failed to fetch orders');
        }
        alert("You have successfully removed an item!");
        location.reload();

    } catch (error) {
        console.error('Error fetching orders:', error);
        alert('An error has occurred while attempting to remove:', itemName);
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
            const needAssistance = await fetchAssistance(i);
            tableDiv.innerHTML = `
                <div class="table-header">
                    <span class="table-name">Table ${i}</span>
                    <span class="status-dot ${isInUse ? 'dot-red' : 'dot-green'}"></span>
                    <span class ="help-symbol">${needAssistance ? `Table Requires Assistance! <button onclick="removeHelp(${i})">Remove assistance</button>` : ''}</span>
                </div>
                <div class="table-details hidden">
                    <p>Status: <span class="${isInUse ? 'occupied' : 'available'}">
                        ${isInUse ? 'Occupied' : 'Available'}
                    </span></p>
                    <p>Assigned Waiter: <span class="waiter-name">${assignedWaiter}</span></p>
                    ${assignedWaiter === 'None' && isInUse ? `<button class="assign-btn" onclick="openWaiterAssignment(${i})">Assign Waiter</button>` : ''}
                    ${assignedWaiter !== 'None' ? `<button class="unassign-btn" onclick="unassignWaiter(${i})">Unassign Waiter</button>` : ''}
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

async function unassignWaiter(tableNumber) {
    if (!confirm(`Are you sure you want to unassign the waiter from Table ${tableNumber}?`)) {
        return;
    }

    try {
        const response = await fetch('/api/tableAssignments/unassign', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: new URLSearchParams({ tableNumber })
        });

        if (!response.ok) {
            throw new Error(await response.text());
        }

        alert(`Waiter unassigned from Table ${tableNumber}`);
        generateTablesOverview();  // Refresh tables

    } catch (error) {
        console.error('Error unassigning waiter:', error);
        alert('Failed to unassign waiter.');
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

// MARCUS ASSISTANCE BUTTON STUFF // 
async function fetchAssistance(tableNo) {
    try {
        const response = await fetch(`/api/tableAssignments/assistance`);
        if (!response.ok) throw new Error('Failed to fetch assistance');

        const assist = await response.json();

        return assist.some(entry => entry.table === tableNo && entry.assistance === true);

    } catch (error) {
        console.error('Error fetching assistance:', error);
        return false;
    }
}

async function removeHelp(tableN) {

    try {
        const response = await fetch('/api/tableAssignments/removeAssistance', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ tableN })
        });

        if (!response.ok) {
            throw new Error('Failed to remove assistance');
        }

        alert(`assistance  deleted successfully!`);

    } catch (error) {
        console.error('Failed to remove assistance:', error);
        alert('Failed to remove assistance');
    }
}

//end of marcus assistance button stuff//

// Function to mark an order as delivered
async function markAsDelivered(orderId) {
    try {
        if (!confirm(`Are you sure you want to mark Order #${orderId} as delivered?`)) {
            return;
        }

        const response = await fetch(`/api/orders/${orderId}/status`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ status: 'delivered' })
        });

        if (!response.ok) {
            throw new Error('Failed to update order status');
        }

        alert(`Order #${orderId} has been marked as delivered!`);
        fetchOrders();
    } catch (error) {
        console.error('Error updating order status:', error);
        alert('Failed to mark order as delivered.');
    }
}