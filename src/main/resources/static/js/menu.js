
let currentCategory = 'all';
let allItems = [];

const grid = document.getElementById('grid');

function filterItems(items) {
    if (currentCategory === 'all') return items;
    return items.filter(item => item.category === currentCategory);
}

async function fetchItems() {
    try {
        const response = await fetch('api/items', {
            headers: {
                'Accept': 'application/json'
            }
        });

        const data = await response.json();

        allItems = data;
        return data;
    } catch (error) {
        console.error('Error fetching items: ', error);
        return [];
    }
}

function createCardElement(item) {
    const div = document.createElement('div');
    div.className = 'card';
    div.style.opacity = '0';

    div.innerHTML = `
        <div style="width: 200px; height: 200px;">
            <img src="images/menu_placeholder.jpg" alt=${item.title} class="images">
        </div>
        <div style="display: flex; flex-direction: column; justify-content: center;">
            <h2>${item.title}</h2>
            <p>${item.description}</p>
            <p>${item.price}</p>
        </div>
    `;

    const img = div.querySelector('img');
    img.onload = () => div.style.opacity = '1';

    return div;
}

async function loadItems(){
    const items = await fetchItems();

    items.forEach(item => {
        grid.appendChild(createCardElement(item));
    })
}

document.addEventListener('DOMContentLoaded', async () => {
    try {
        await loadItems();
    } catch (error) {
        console.error('Failed to load items: ', error);
    }
});

document.getElementById('filters').addEventListener('click', (e) => {
    const activeButton = document.querySelector('.filter-btn.active');
    if (activeButton) activeButton.classList.remove('active');
    e.target.classList.add('active');

    currentCategory = e.target.dataset.category;
    grid.innerHTML = '';


    filterItems(allItems).forEach(item => {
        grid.appendChild(createCardElement(item));
    });
});
