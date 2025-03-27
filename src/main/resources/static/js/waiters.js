const numTables = 12;
const tableMap = document.getElementById("table-map");

for (let i = 1; i <= numTables; i++) {
  const table = document.createElement("div");
  table.classList.add("table");
  table.textContent = i;
  table.dataset.tableId = i;

  table.addEventListener("click", function() {
    window.location.href = `/protected/table.html?tableId=${this.dataset.tableId}`;
  });

  tableMap.appendChild(table);
}

document.addEventListener('DOMContentLoaded', () => {
  const tabs = document.querySelectorAll('.tab');
  const tabContents = document.querySelectorAll('.tab-content');
  const nav = document.querySelector('.nav');

  // removes the old tab from the page and then displays the new one clicked
  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      tabs.forEach(t => t.classList.remove('active'));
      tabContents.forEach(tc => tc.classList.remove('active'));

      tab.classList.add('active');
      const target = tab.getAttribute('data-target');
      document.querySelector(target).classList.add('active');
    });
  });
  loadMenuItems();
  
  // Navigation scroll effect
  window.addEventListener('scroll', () => {
    if (window.scrollY > 100) {
      nav.classList.add('scrolled');
    } else {
      nav.classList.remove('scrolled');
    }
  });

  // Form submission handler
  document.getElementById('editItemForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    
    const itemId = document.getElementById('editItemId').value;
    const updatedItem = {
      title: document.getElementById('editTitle').value,
      description: document.getElementById('editDescription').value,
      price: parseFloat(document.getElementById('editPrice').value),
      category: document.getElementById('editCategory').value,
      calories: parseInt(document.getElementById('editCalories').value),
      allergens: document.getElementById('editAllergens').value
    };
    
    await updateMenuItem(itemId, updatedItem);
  });

  // Cancel button handler
  document.getElementById('cancelEdit').addEventListener('click', closeEditForm);
});

// Load the items for each tab
async function loadMenuItems() {
    const activeResponse = await fetch('/api/items?active=true');
    const activeItems = await activeResponse.json();
    const inactiveResponse = await fetch('/api/items?active=false');
    const inactiveItems = await inactiveResponse.json();
    
    renderItems(activeItems, '#removeTab', true);
    renderItems(inactiveItems, '#addTab', false);
    renderItems(activeItems, '#editTab', true);
}

// The function that renders the items under the appropriate tab
function renderItems(items, tabSelector, isActive) {
  const tab = document.querySelector(tabSelector);
  const container = tab.querySelector('.menu-grid');
  container.innerHTML = '';
  
  items.forEach(item => {
    const itemDiv = document.createElement('div');
    itemDiv.className = `menu-item ${isActive ? 'active' : 'inactive'}`;
    itemDiv.setAttribute('data-item-id', item.id);
    
    // Modify the button display based on which tab we're in (Can be optimised & improved, however works godo for now)
    let buttonHtml = '';
    if (tabSelector === '#editTab') {
      buttonHtml = `<button class="edit-item-btn" data-item-id="${item.id}">Edit</button>`;
    } else {
      buttonHtml = `<button class="toggle-visibility-btn">
        ${isActive ? 'Remove' : 'Add'}
      </button>`;
      if (isActive) {
        buttonHtml += `<button class="edit-item-btn" data-item-id="${item.id}">Edit</button>`;
      }
    }
    
    itemDiv.innerHTML = `
      <img src="../images/${item.id}.png" alt="${item.title}" class="item-image" loading="lazy">
      <div class="item-content">
        <h3 class="item-title">${item.title}</h3>
        <p class="item-description">${item.description}</p>
        <div class="item-price">£${item.price}</div>
        <div class="item-meta">
          <span class="calories">${item.calories} cal</span>
        </div>
        ${buttonHtml}
      </div>
    `;

    if (tabSelector !== '#editTab') {
      itemDiv.querySelector('.toggle-visibility-btn').addEventListener('click', () => {
        toggleActiveStatus(item.id, !isActive);
      });
    }
    
    const editButton = itemDiv.querySelector('.edit-item-btn');
    if (editButton) {
      editButton.addEventListener('click', (event) => {
        event.stopPropagation();
        openEditForm(item);
      });
    }
    
    container.appendChild(itemDiv);
  });
}

// Function to open the edit form and populate it with item data
async function openEditForm(item) {
  document.getElementById('editItemId').value = item.id;
  document.getElementById('editTitle').value = item.title;
  document.getElementById('editDescription').value = item.description;
  document.getElementById('editPrice').value = item.price;
  document.getElementById('editCategory').value = item.category;
  document.getElementById('editCalories').value = item.calories;
  document.getElementById('editAllergens').value = item.allergens || '';

  // Display the form
  document.getElementById('editFormContainer').classList.remove('hidden');
}

// Function to close the edit form
function closeEditForm() {
  document.getElementById('editFormContainer').classList.add('hidden');
  document.getElementById('editItemForm').reset();
}

// Function to update an item in the database
async function updateMenuItem(itemId, updatedData) {
  try {
    const response = await fetch(`/api/items/${itemId}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(updatedData)
    });

    if (!response.ok) {
      throw new Error('Failed to update item');
    }

    // Refresh the menu to update it
    loadMenuItems();
    closeEditForm();
    alert('Item updated successfully');

  } catch (error) {
    console.error('Error updating item:', error);
    alert('Failed to update item. Please try again.');
  }
}

// Function to toggle the active status of an item
async function toggleActiveStatus(itemId, newStatus) {
  await fetch(`/api/items/${itemId}/active`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ active: newStatus })
  });
  loadMenuItems();
}