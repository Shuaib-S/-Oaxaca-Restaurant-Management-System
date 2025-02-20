// Global Variables
let currentCategory = 'all';
let allItems = [];
let filteredItems = [];
const cartArray = [];
const quantArray = [];

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
    const nav = document.querySelector('.nav');
    const mobileMenuButton = document.querySelector('.mobile-menu-button');
    const mobileMenu = document.querySelector('.mobile-menu');

    // Scroll effect
    window.addEventListener('scroll', () => {
        if (window.scrollY > 20) {
            nav.classList.add('scrolled');
        } else {
            nav.classList.remove('scrolled');
        }
    });

    // Mobile menu toggle
    mobileMenuButton.addEventListener('click', () => {
        mobileMenu.classList.toggle('open');
        mobileMenuButton.querySelector('i').classList.toggle('fa-bars');
        mobileMenuButton.querySelector('i').classList.toggle('fa-times');
    });
});

// Cart functionality
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

    // Update cart item count and visibility
    function updateCartState() {
        const count = Array.from(quantItems.values()).reduce((a, b) => a + b, 0);
        const cartItemCount = document.querySelector('.cart-item-count');
        if (cartItemCount) {
            cartItemCount.textContent = count;
        }

        // Auto open/close based on items
        if (count > 0) {
            openCart();
        } else {
            closeCart();
        }
    }

    // Listen for cart updates
    document.addEventListener('cartUpdated', updateCartState);
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
                <div class="popup" onclick="ShowNutriInfo(${index})">i
                    <span class="popuptext" id="popup-${index}">Contains: ${item.allergens}</span>
                </div>
            </div>
            
            <button class="add-to-order" data-item-id="${index}">Add to Order</button>
        </div>
    `;

    element.querySelector('.add-to-order').addEventListener('click', (e) => {
        console.log("button");
        const itemId = e.target.dataset.itemId;

        let itemData = {};
        if (currentCategory === "all") {
            itemData = allItems[itemId];
        } else {
            itemData = filteredItems[itemId];
        }

        quantItems.set(itemData, (quantItems.get(itemData) || 0) + 1);
        console.log(quantItems);
        document.dispatchEvent(cartUpdate);
    });

    return element;
}

// Map to store cart items
const quantItems = new Map();
const cartUpdate = new CustomEvent('cartUpdated');
let totalPrice = 0;
document.getElementById("order-total").innerHTML = "Order Total: " + formatPrice(totalPrice);

// Adds items to an order | Removes items from an order
function orderSystem() {
    totalPrice = 0;
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

            // Check if cart is empty and close if it is
            if (quantItems.size === 0) {
                document.querySelector('.cart').classList.remove('open');
            }
        });

        cartContainer.appendChild(element);
    });

    quantItems.forEach((quantity, item) => {
        totalPrice = totalPrice + ((item.price) * quantity);
    });

    // Update totals
    document.getElementById("order-total").innerHTML = formatPrice(totalPrice);

    // Open cart when items are added
    if (quantItems.size > 0) {
        document.querySelector('.cart').classList.add('open');
    }
}

document.addEventListener('cartUpdated', orderSystem);

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

    } catch (error) {
        console.error('Failed to load items:', error);
        grid.innerHTML = '<div class="error">Failed to load menu items. Please try again later.</div>';
    }
}

// Filter button click handler
document.getElementById('filters').addEventListener('click', (e) => {
    e.preventDefault(); // Prevent default behavior

    const button = e.target.closest('.filter-btn');
    if (!button) return;

    // Update active button
    document.querySelector('.filter-btn.active')?.classList.remove('active');
    button.classList.add('active');

    // Update category and reload items
    currentCategory = button.dataset.category;

    const currentScrollPosition = window.scrollY; // Store current scroll position

    // Fade out current items
    grid.style.opacity = '0';
    grid.style.transform = 'translateY(10px)';

    // Load new items with a subtle animation
    setTimeout(() => {
        loadItems().then(() => {
            requestAnimationFrame(() => {
                grid.style.opacity = '1';
                grid.style.transform = 'translateY(0)';
                window.scrollTo(0, currentScrollPosition); // Maintain scroll position
            });
        });
    }, 200);
});

// Initialize menu on page load
document.addEventListener('DOMContentLoaded', () => {
    loadItems();
});

// Intersection Observer for animation
const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            entry.target.style.opacity = '1';
            entry.target.style.transform = 'translateY(0)';
            observer.unobserve(entry.target);
        }
    });
}, {
    threshold: 0.1
});

// Observe menu items for animation
document.querySelectorAll('.menu-item').forEach(item => {
    observer.observe(item);
});


// connect Submit order button to api/orders
document.addEventListener("DOMContentLoaded", function () {
    const submitOrderBtn = document.getElementById("submit-order");

    submitOrderBtn.addEventListener("click", function () {
        if (quantItems.size === 0) {
            alert("Your cart is empty!");
            return;
        }

        const quantItemsArray = Array.from(quantItems, ([item, quantity]) => ({ item, quantity: quantity }));

        const orderData = {
            tableNumber: 12,
            itemList: quantItemsArray
        };



        fetch('http://localhost:8080/api/orders', {
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
                totalPrice = 0;
                document.getElementById("order-total").innerHTML = "Order Total: " + formatPrice(totalPrice);
                document.querySelector(".cart-items").innerHTML = "";
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Error placing order. Please try again.");
            });
    });
});


// toggle whether the popup is visible
function ShowNutriInfo(index) {
    var popup = document.getElementById(`popup-${index}`);
    popup.classList.toggle("show");
}

