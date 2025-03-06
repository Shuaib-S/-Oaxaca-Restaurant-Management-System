const numTables = 12;
const tableMap = document.getElementById("table-map");

for (let i = 1; i <= numTables; i++) {
  const table = document.createElement("div");
  table.classList.add("table");
  table.textContent = i;
  table.dataset.tableId = i;

  table.addEventListener("click", function() {
    window.location.href = `/table.html?tableId=${this.dataset.tableId}`;
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
});

// Load teh items for each tab
async function loadMenuItems() {
    const activeResponse = await fetch('/api/items?active=true');
    const activeItems = await activeResponse.json();
    const inactiveResponse = await fetch('/api/items?active=false');
    const inactiveItems = await inactiveResponse.json();
    
    renderItems(activeItems, '#removeTab', true);
    renderItems(inactiveItems, '#addTab', false);
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
    itemDiv.innerHTML = `
      <img src="images/menu_placeholder.jpg" alt="${item.title}" class="item-image" loading="lazy" />
      <div class="item-content">
        <h3 class="item-title">${item.title}</h3>
        <p class="item-description">${item.description}</p>
        <div class="item-price">£${item.price}</div>
        <div class="item-meta">
          <span class="calories">${item.calories} cal</span>
        </div>
        <button class="toggle-visibility-btn">
          ${isActive ? 'Remove' : 'Add'}
        </button>
      </div>
    `;

    itemDiv.querySelector('.toggle-visibility-btn').addEventListener('click', () => {
      toggleActiveStatus(item.id, !isActive);
    });
    
    container.appendChild(itemDiv);
  });
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
