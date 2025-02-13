// Global Variables
let currentCategory = 'all';
let allItems = [];
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
        </div>
    `;


    element.querySelector('.add-to-order').addEventListener('click', (e) => {
        console.log("button");
        const itemId = e.target.dataset.itemId;
        const itemData = allItems[itemId];
        // const quantItems = allItems.map(item => ({
        //     itemData, quantity : 0
        // }));
        // quantity = quantItems.get(itemData);
        console.log(cartArray);
        quantItems.set(itemData, 1);
        // console.log(quantArray);
        cartArray.push(itemData);
        document.dispatchEvent(cartUpdate)
    })

    return element;
}

const cartUpdate = new CustomEvent('cartUpdated');

// Adds items to an order
document.addEventListener('cartUpdated', () => {
    const element = document.createElement('div');
    element.innerHTML = ``;
    cartArray.forEach(item => {
        element.className = 'order-item';
        element.innerHTML = `
        <div class="order-content">
            <p> ${item.title}: ${formatPrice(item.price)}    </p>
            <button class="remove-from-order"> Bin </button>
        </div>`;
        document.querySelector('.cart-items').appendChild(element);
    });
});



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

        const filteredItems = filterItems(items);
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
