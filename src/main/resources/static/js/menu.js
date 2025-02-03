
let currentCategory = 'all';
let allItems = [];

const grid = document.getElementById('grid');

function filterItems(items) {
    if (currentCategory === 'all') return items;
    return items.filter(item => item.category === currentCategory);
}

async function fetchItems() {
    // try {
    //     const response = await fetch('api/items?sort=id,desc', {
    //         headers: {
    //             'Accept': 'application/json'
    //         }
    //     });
    //
    //     const data = await response.json();
    //
    //     return data.content;
    // } catch (error) {
    //     console.error('Error fetching items: ', error);
    //     return [];
    // }

    try {
        const testData = {
            content: [
                {
                    id: 1,
                    title: "Carne Asada Tacos",
                    description: "Grilled marinated beef tacos with onions, cilantro, and lime. Served with salsa verde.",
                    price: 12.99,
                    category: "Main"
                },
                {
                    id: 2,
                    title: "Enchiladas Rojas",
                    description: "Three chicken enchiladas covered in red chile sauce, topped with queso fresco.",
                    price: 15.99,
                    category: "Main"
                },
                {
                    id: 3,
                    title: "Guacamole Fresco",
                    description: "Fresh avocados mixed with tomatoes, onions, cilantro, and lime juice.",
                    price: 8.99,
                    category: "Starter"
                },
                {
                    id: 4,
                    title: "Chiles Rellenos",
                    description: "Poblano peppers stuffed with cheese, battered and fried. Served with rice and beans.",
                    price: 16.99,
                    category: "Main"
                },
                {
                    id: 5,
                    title: "Mole Poblano",
                    description: "Chicken in traditional mole sauce, served with rice and tortillas.",
                    price: 18.99,
                    category: "Main"
                },
                {
                    id: 6,
                    title: "Churros",
                    description: "Traditional Mexican pastry dusted with cinnamon sugar, served with chocolate sauce.",
                    price: 7.99,
                    category: "Desserts"
                }
            ]
        };

        allItems = testData.content;
        return testData.content;
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
