
let currentCategory = 'all';  //Current category as decided by the user to filter the menu by.
let allItems = [];

const grid = document.getElementById('grid');

function filterItems(items) {
    if (currentCategory === 'all') return items;
    return items.filter(item => item.category === currentCategory);  //This line here is what actually filters the menu.
}

async function fetchItems() {
    // UNCOMMENT THIS CODE WHEN THE BACKEND FOR IT IS IMPLEMENTED
    // DON'T FORGET TO REMOVE THE TEST CODE
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
    const div = document.createElement('div');  // Creates the card div on the webpage
    div.className = 'card';
    div.style.opacity = '0';  //Set to 0 to fade in the card - Makes the loading in less jarring to look at.

    // Adds HTML to the div to add the content required.
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
    img.onload = () => div.style.opacity = '1';  // When the image loads, the opacity is changed to 1.

    return div;
}

// async function to handle the promise of fetching the items from the backend.
async function loadItems(){
    const items = await fetchItems();

    // Loops through the items in the menu and creates cards for each one.
    items.forEach(item => {
        grid.appendChild(createCardElement(item));
    })
}

// Event listener for loading the content when the page has loaded.
document.addEventListener('DOMContentLoaded', async () => {
    try {
        await loadItems();
    } catch (error) {
        console.error('Failed to load items: ', error);
    }
});

// Event listener for the filter button clicks.
document.getElementById('filters').addEventListener('click', (e) => {
    const activeButton = document.querySelector('.filter-btn.active');
    if (activeButton) activeButton.classList.remove('active'); // Remove old active button.

    e.target.classList.add('active'); // Change newly clicked button to active filter.

    currentCategory = e.target.dataset.category;  // Change currentCategory to new category.
    grid.innerHTML = '';

    // Create the new elements for the filtered items.
    filterItems(allItems).forEach(item => {
        grid.appendChild(createCardElement(item));
    });
});
