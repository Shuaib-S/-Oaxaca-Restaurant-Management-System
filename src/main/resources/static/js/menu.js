// Global Variables
let currentCategory = 'all';
let allItems = [];
let filteredItems = [];
const cartArray = [];
const quantArray = [];
const quantItems = new Map();
let totalPrice = 0;

// DOM Elements
const grid = document.getElementById('grid');
const nav = document.querySelector('.nav');

// Navigation scroll effect
window.addEventListener('scroll', () => {
    if (window.scrollY > 100) {
        nav.classList.add('scrolled');
    } else {
        nav.classList.remove('scrolled');
    }
});

// Fetch menu items from API
async function fetchItems() {
    try {
        const response = await fetch('api/items', {
            headers: {
                'Accept': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json();
        allItems = data;
        return data;
    } catch (error) {
        console.error('Error fetching items:', error);
        return [];
    }
}

// Navbar functionality
document.addEventListener('DOMContentLoaded', function() {
    const mobileMenuButton = document.querySelector('.mobile-menu-button');
    const mobileMenu = document.querySelector('.mobile-menu');

    // Mobile menu toggle
    mobileMenuButton.addEventListener('click', () => {
        mobileMenu.classList.toggle('open');
        mobileMenuButton.querySelector('i').classList.toggle('fa-bars');
        mobileMenuButton.querySelector('i').classList.toggle('fa-times');
    });
});

// Cart functionality
document.addEventListener('DOMContentLoaded', function() {
    const cart = document.querySelector('.cart');
    const cartToggle = document.getElementById('cart-toggle');
    const mobileCartToggle = document.getElementById('mobile-cart-toggle');

    function openCart() {
        cart.classList.add('open');
    }

    function closeCart() {
        cart.classList.remove('open');
    }

    cartToggle?.addEventListener('click', () => {
        cart.classList.toggle('open');
    });

    mobileCartToggle?.addEventListener('click', () => {
        cart.classList.toggle('open');
    });
});

// Filter items by category
function filterItems(items) {
    if (currentCategory === 'all') return items;
    return items.filter(item => item.category === currentCategory);
}

// Format price to GBP
function formatPrice(price) {
    return new Intl.NumberFormat('en-GB', {
        style: 'currency',
        currency: 'GBP',
        minimumFractionDigits: 2
    }).format(price);
}

// Create HTML for a menu item
function createMenuItemElement(item, index) {
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
                ${item.allergens ? `<span class="allergens">Contains: ${item.allergens}</span>` : ''}
            </div>
            <button class="add-to-order" data-item-id="${index}">Add to Order</button>
            <div class="popup" onclick="ShowNutriInfo(${index})">i
                <span class="popuptext" id="popup-${index}">Contains: ${item.allergens}</span>
            </div>
        </div>
    `;

    element.querySelector('.add-to-order').addEventListener('click', (e) => {
        const itemId = e.target.dataset.itemId;
        let itemData = currentCategory === "all" ? allItems[itemId] : filteredItems[itemId];
        quantItems.set(itemData, (quantItems.get(itemData) || 0) + 1);
        document.dispatchEvent(cartUpdate);
    });

    return element;
}

const cartUpdate = new CustomEvent('cartUpdated');
document.addEventListener('cartUpdated', () => {
    const cartContainer = document.querySelector('.cart-items');
    cartContainer.innerHTML = '';

    quantItems.forEach((quantity, item) => {
        const element = document.createElement('div');
        element.className = 'cart-item';
        element.innerHTML = `
            <div class="cart-item-info">
                <div class="cart-item-title">${item.title}</div>
                <div class="cart-item-price">${formatPrice(item.price)} × ${quantity}</div>
            </div>
            <button class="remove-from-order">
                <i class="fas fa-trash"></i>
            </button>
        `;

        element.querySelector('.remove-from-order').addEventListener('click', () => {
            if (quantity > 1) {
                quantItems.set(item, quantity - 1);
            } else {
                quantItems.delete(item);
            }
            document.dispatchEvent(cartUpdate);
        });

        cartContainer.appendChild(element);
    });
});

// Load and display menu items
async function loadItems() {
    grid.innerHTML = '<div class="loading">Loading menu items...</div>';
    const items = await fetchItems();
    if (items.length === 0) {
        grid.innerHTML = '<div class="no-items">No menu items available</div>';
        return;
    }
    grid.innerHTML = '';
    filteredItems = filterItems(items);
    filteredItems.forEach((item, index) => {
        grid.appendChild(createMenuItemElement(item, index));
    });
}

document.addEventListener('DOMContentLoaded', () => loadItems());

// Submit order to API
document.getElementById("submit-order").addEventListener("click", function () {
    if (quantItems.size === 0) {
        alert("Your cart is empty!");
        return;
    }
    const orderData = { tableNumber: 12, itemList: Array.from(quantItems, ([item, quantity]) => ({ item, quantity })) };
    fetch('http://localhost:8080/api/orders', {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(orderData)
    }).then(response => {
        if (!response.ok) throw new Error("Failed to submit order");
        return response.json();
    }).then(() => {
        alert("Order placed successfully!");
        quantItems.clear();
        document.querySelector(".cart-items").innerHTML = "";
    }).catch(error => {
        console.error("Error:", error);
        alert("Error placing order. Please try again.");
    });
});

// toggle whether the popup is visible
function ShowNutriInfo(index) {
    var popup = document.getElementById(`popup-${index}`);
    popup.classList.toggle("show");
}
