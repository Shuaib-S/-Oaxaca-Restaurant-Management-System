document.addEventListener("DOMContentLoaded", function () {
    const url = new URLSearchParams(window.location.search);
    const tableId = url.get("tableId");

    if (tableId){
        document.getElementById("table-number").textContent = tableId;
        document.title = `Table ${tableId}`
    }

    async function fetchOrder() {
        try {
        const response = await fetch(`/api/CurrentOrders/table/${tableId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch orders');
            }

        const order = await response.json();

        const orderList = document.getElementById("list-grid");
        orderList.innerHTML = "";

        order[0].itemList.forEach((item, index) => {
            const element = createItemElement(item, index);
            element.style.animationDelay = `${index * 100}ms`;
            orderList.appendChild(element);
        })


        console.log(order);

        createItemElement(order);

        } catch (error) {
            console.error('Error fetching order:', error);
        }
    }

    fetchOrder();
})

function createItemElement(item, index) {
    const element = document.createElement('div');
    element.className = 'menu-item';
    element.dataset.itemId = index;

    element.innerHTML = `
        <img src="images/menu_placeholder.jpg" 
             alt="${item.title}" 
             class="item-image"
             loading="lazy">
        <div class="item-content">
            <h3 class="item-title">${item.title}</h3>
            <p class="item-description">${item.description}</p>
            <div class="item-price">${formatPrice(item.price)}</div>
            <div class="item-meta">
                <span class="calories">${item.calories} cal</span>
                ${item.allergens ? `<span class="nutrition-info">Contains: ${item.allergens}</span>` : `Contains: No allergens`}
            </div>
            <div class="button-container">
            <button class="remove-item" data-item-id="${index}">
                <i class='fas fa-minus'></i>
            </button>
            <button class="add-note" data-item-id="${index}">
                <i class='fas fa-sticky-note'></i>
            </button>
            <button class="add-item" data-item-id="${index}">
                <i class='fas fa-plus'></i>
            </button>
            
            </div>
        </div>
    `;

    return element;
}

// Format price to GBP
function formatPrice(price) {
    return new Intl.NumberFormat('en-GB', {
        style: 'currency',
        currency: 'GBP',
        minimumFractionDigits: 2
    }).format(price);
}
