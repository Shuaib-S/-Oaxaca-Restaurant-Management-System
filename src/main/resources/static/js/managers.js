// Global Variables
let currentCategory = 'all';
let allStock = [];
let filteredStock = [];

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
        return data;
    } catch (error) {
        console.error('Error fetching items:', error);
        return [];
    }
}

// Navbar functionality
document.addEventListener('DOMContentLoaded', function () {
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
});

// Filter ingredients by category
function filterStock() {
    let filtered = allStock;

    if (currentCategory !== 'all') {
        filtered = filtered.filter(ingredient => ingredient.category === currentCategory);
    }

    console.log(filtered);

    return filtered;
}

// Change quantity
async function updateQuantity(ingredient, newQuantity) {
  try {
    const response = await fetch(`api/stock/${ingredient.id}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ quantity: newQuantity })
    });
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const updatedIngredient = await response.json();
    ingredient.quantity = updatedIngredient.quantity;
    const quantityElement = document.getElementById(`quantity-${ingredient.id}`);
    if (quantityElement) {
      quantityElement.textContent = updatedIngredient.quantity;
    }
  } catch (error) {
    console.error('Error updating quantity:', error);
  }
}

// Create HTML for a Stock
function createIngredientElement(ingredient, index) {
    const element = document.createElement('div');
    element.className = 'stock-card';
    element.dataset.itemId = index;

    element.innerHTML = `
      <img src="../images/managers-images/${ingredient.id}.png" 
           alt="${ingredient.title}" 
           class="stock-image"
           loading="lazy">
      <div class="stock-content">
        <h3 class="stock-title">${ingredient.title}</h3>
        <div class="stock-quantity">Quantity: <strong id="quantity-${ingredient.id}">${ingredient.quantity}</strong></div>
        <div class="quantity-controls">
          <button class="decrease-btn" data-id="${ingredient.id}">–</button>
          <button class="increase-btn" data-id="${ingredient.id}">+</button>
        </div>
      </div>
    `;

    const increaseBtn = element.querySelector('.increase-btn');
    const decreaseBtn = element.querySelector('.decrease-btn');
    
    increaseBtn.addEventListener('click', () => {
      updateQuantity(ingredient, ingredient.quantity + 1);
    });

    decreaseBtn.addEventListener('click', () => {
      if (ingredient.quantity > 0) {
        updateQuantity(ingredient, ingredient.quantity - 1);
      }
    });

    return element;
  }

  async function loadIngredients() {
    try {
      grid.innerHTML = '<div class="loading">Loading Ingredients...</div>';
      const items = await fetchStock();

      if (items.length === 0) {
        grid.innerHTML = '<div class="no-items">No ingredients available</div>';
        return;
      }

      grid.innerHTML = '';
      const fragment = document.createDocumentFragment();

      filteredStock = filterStock();
      filteredStock.forEach((ingredient, index) => {
        const element = createIngredientElement(ingredient, index);
        element.style.animationDelay = `${index * 100}ms`;
        fragment.appendChild(element);
      });

      grid.appendChild(fragment);
    } catch (error) {
      console.error('Failed to load ingredients:', error);
      grid.innerHTML = '<div class="error">Failed to load ingredients. Please try again later.</div>';
    }
  }

  // filters
  document.getElementById('filters').addEventListener('click', (e) => {
    e.preventDefault();

    const button = e.target.closest('.filter-btn');
    if (!button) return;
    document.querySelector('.filter-btn.active')?.classList.remove('active');
    button.classList.add('active');
    currentCategory = button.dataset.category;

    const currentScrollPosition = window.scrollY;
    grid.style.opacity = '0';
    grid.style.transform = 'translateY(10px)';

    setTimeout(() => {
      loadIngredients().then(() => {
        requestAnimationFrame(() => {
          grid.style.opacity = '1';
          grid.style.transform = 'translateY(0)';
          window.scrollTo(0, currentScrollPosition);
        });
      });
    }, 200);
  });
  
  // Initialize stock on page load
  document.addEventListener('DOMContentLoaded', () => {
    loadIngredients();
  });

