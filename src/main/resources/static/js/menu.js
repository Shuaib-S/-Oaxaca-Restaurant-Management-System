// Global Variables
let currentCategory = 'all';
let allItems = [];
let filteredItems = [];
const quantItems = new Map(); // Map to store cart items
const cartUpdate = new CustomEvent('cartUpdated');
let totalPrice = 0;
let currentAllergen = 'null';

// DOM Elements
const grid = document.getElementById('grid');
const nav = document.querySelector('.nav');
const mainPage = document.querySelector('.main-page');

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

// Format price to GBP
function formatPrice(price) {
    return new Intl.NumberFormat('en-GB', {
        style: 'currency',
        currency: 'GBP',
        minimumFractionDigits: 2
    }).format(price);
}

// Filter items by category and allergen
function filterItems(items) {
    let filtered = items;

    if (currentCategory !== 'all') {
        filtered = filtered.filter(item => item.category === currentCategory);
    }

    if (currentAllergen !== 'null') {
        filtered = filtered.filter(item =>
            !item.allergens || !item.allergens.toLowerCase().includes(currentAllergen)
        );
    }

    return filtered;
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
                <span class="nutrition-info">Contains: ${item.allergens || 'No allergens'}</span>
            </div>
            <button class="add-to-order" data-item-id="${index}">Add to Order</button>
        </div>
    `;

    element.querySelector('.add-to-order').addEventListener('click', (e) => {
        const itemId = parseInt(e.target.dataset.itemId);
        let itemData = filteredItems[itemId];
        
        // Add to cart
        quantItems.set(itemData, (quantItems.get(itemData) || 0) + 1);
        
        // Dispatch event to update cart
        document.dispatchEvent(cartUpdate);
        
        // Visual feedback
        const button = e.target;
        button.classList.add('added');
        button.textContent = "Added!";
        
        setTimeout(() => {
            button.classList.remove('added');
            button.textContent = "Add to Order";
        }, 1000);
    });

    return element;
}

// Load and display menu items
async function loadItems() {
    try {
        grid.innerHTML = '<div class="loading">Loading menu items...</div>';
        const items = await fetchItems();

        if (items.length === 0) {
            grid.innerHTML = '<div class="no-items">No menu items available</div>';
            return;
        }

        grid.innerHTML = '';
        const fragment = document.createDocumentFragment();

        filteredItems = filterItems(items);
        filteredItems.forEach((item, index) => {
            const element = createMenuItemElement(item, index);
            element.style.animationDelay = `${index * 100}ms`;
            fragment.appendChild(element);
        });

        grid.appendChild(fragment);
        
        // Make grid visible with animation
        grid.style.opacity = '1';
        grid.style.transform = 'translateY(0)';

    } catch (error) {
        console.error('Failed to load items:', error);
        grid.innerHTML = '<div class="error">Failed to load menu items. Please try again later.</div>';
    }
}

// Cart functionality
function setupCart() {
    const cart = document.querySelector('.cart');
    const cartToggle = document.getElementById('cart-toggle');
    const mobileCartToggle = document.getElementById('mobile-cart-toggle');

    function openCart() {
        cart.classList.add('open');
        mainPage.classList.add('cart-open');
    }

    function closeCart() {
        cart.classList.remove('open');
        mainPage.classList.remove('cart-open');
    }

    // Toggle cart visibility
    cartToggle.addEventListener('click', () => {
        if (cart.classList.contains('open')) {
            closeCart();
        } else {
            openCart();
        }
    });

    mobileCartToggle.addEventListener('click', () => {
        openCart();
    });

    // Update cart when items are added/removed
    document.addEventListener('cartUpdated', () => {
        updateCartDisplay();
        
        // Auto-open cart when items are added
        const totalItems = getTotalItems();
        if (totalItems > 0 && window.innerWidth >= 992) {
            openCart();
        }
        
        // Update mobile cart button count
        const itemCountElement = document.querySelector('.cart-item-count');
        itemCountElement.textContent = totalItems;
        
        if (totalItems === 0) {
            closeCart();
        }
    });
    
    // Close cart when clicking outside (for mobile)
    document.addEventListener('click', (e) => {
        if (window.innerWidth < 992 && 
            cart.classList.contains('open') && 
            !cart.contains(e.target) && 
            !mobileCartToggle.contains(e.target)) {
            closeCart();
        }
    });
}

// Get total number of items in cart
function getTotalItems() {
    let count = 0;
    quantItems.forEach((quantity) => {
        count += quantity;
    });
    return count;
}

// Update cart display
function updateCartDisplay() {
    const cartContainer = document.querySelector('.cart-items');
    cartContainer.innerHTML = '';
    totalPrice = 0;

    if (quantItems.size === 0) {
        cartContainer.innerHTML = '<div class="empty-cart">Your cart is empty</div>';
        updateTotals(0);
        return;
    }

    quantItems.forEach((quantity, item) => {
        const element = document.createElement('div');
        element.className = 'cart-item';
        
        element.innerHTML = `
            <div class="cart-item-info">
                <div class="cart-item-title">${item.title}</div>
                <div class="cart-item-price">${formatPrice(item.price)} × ${quantity}</div>
            </div>
            <button class="remove-from-order" aria-label="Remove item">
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
        totalPrice += item.price * quantity;
    });

    updateTotals(totalPrice);
}

// Update totals in cart
function updateTotals(total) {
    const subtotal = total * 0.8; // 80% of total
    const VAT = total * 0.2; // 20% VAT
    
    document.getElementById("subtotal-amount").innerHTML = formatPrice(subtotal);
    document.getElementById("tax-amount").innerHTML = formatPrice(VAT);
    document.getElementById("order-total").innerHTML = formatPrice(total);
}

// Populate table numbers
function populateTableNumbers() {
    const tableSelect = document.getElementById("table-number");
    tableSelect.innerHTML = '';
    
    for (let i = 1; i <= 12; i++) {
        const option = document.createElement("option");
        option.value = i;
        option.textContent = `Table ${i}`;
        tableSelect.appendChild(option);
    }
}

// Submit order
function setupOrderSubmission() {
    const submitOrderBtn = document.getElementById("submit-order");
    
    submitOrderBtn.addEventListener("click", function() {
        if (quantItems.size === 0) {
            alert("Your cart is empty!");
            return;
        }

        const tableNumberSelect = document.getElementById("table-number");
        const quantItemsArray = Array.from(quantItems, ([item, quantity]) => ({ item, quantity }));
        
        const orderData = {
            tableNumber: parseInt(tableNumberSelect.value),
            itemList: quantItemsArray
        };

        fetch('/api/orders', {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(orderData)
        })
        .then(response => {
            if (!response.ok) throw new Error("Failed to submit order");
            return response.json();
        })
        .then(data => {
            alert("Order placed successfully!");
            quantItems.clear();
            document.dispatchEvent(cartUpdate);
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error placing order. Please try again.");
        });
    });
}

// Setup category filter buttons
function setupFilters() {
    document.getElementById('filters').addEventListener('click', (e) => {
        const button = e.target.closest('.filter-btn');
        if (!button) return;

        // Update active button
        document.querySelector('.filter-btn.active')?.classList.remove('active');
        button.classList.add('active');

        // Update category and reload items
        currentCategory = button.dataset.category;
        
        // Animation for smooth transition
        grid.style.opacity = '0';
        grid.style.transform = 'translateY(10px)';

        setTimeout(() => {
            loadItems();
        }, 300);
    });
    
    // Allergen filter event handler
    document.getElementById('allergen-filters').addEventListener('click', (e) => {
        const button = e.target.closest('.allergen-btn');
        if (!button) return;
        
        document.querySelector('.allergen-btn.active')?.classList.remove('active');
        button.classList.add('active');
        
        currentAllergen = button.dataset.allergen;
        
        // Animation for smooth transition
        grid.style.opacity = '0';
        grid.style.transform = 'translateY(10px)';

        setTimeout(() => {
            loadItems();
        }, 300);
    });
}

// Mobile menu button functionality
function setupMobileMenu() {
    const mobileMenuButton = document.querySelector('.mobile-menu-button');
    const mobileMenu = document.querySelector('.mobile-menu');

    mobileMenuButton?.addEventListener('click', () => {
        mobileMenu.classList.toggle('open');
        mobileMenuButton.querySelector('i').classList.toggle('fa-bars');
        mobileMenuButton.querySelector('i').classList.toggle('fa-times');
    });
}

// Navigation scroll effect
function setupNavScroll() {
    window.addEventListener('scroll', () => {
        if (window.scrollY > 20) {
            nav.classList.add('scrolled');
        } else {
            nav.classList.remove('scrolled');
        }
    });
}

// Initialize everything when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    setupNavScroll();
    setupMobileMenu();
    setupFilters();
    setupCart();
    populateTableNumbers();
    setupOrderSubmission();
    
    // Initial load of menu items
    loadItems();
    
    // Initialize cart display
    updateCartDisplay();
});