
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
                    category: "Tacos"
                },
                {
                    id: 2,
                    title: "Enchiladas Rojas",
                    description: "Three chicken enchiladas covered in red chile sauce, topped with queso fresco.",
                    price: 15.99,
                    category: "Enchiladas"
                },
                {
                    id: 3,
                    title: "Guacamole Fresco",
                    description: "Fresh avocados mixed with tomatoes, onions, cilantro, and lime juice.",
                    price: 8.99,
                    category: "Appetizers"
                },
                {
                    id: 4,
                    title: "Chiles Rellenos",
                    description: "Poblano peppers stuffed with cheese, battered and fried. Served with rice and beans.",
                    price: 16.99,
                    category: "Specialties"
                },
                {
                    id: 5,
                    title: "Mole Poblano",
                    description: "Chicken in traditional mole sauce, served with rice and tortillas.",
                    price: 18.99,
                    category: "Specialties"
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

        return testData.content;
    } catch (error) {
        console.error('Error fetching items: ', error);
        return [];
    }

}

function createCardElement(item) {
    const div = document.createElement('div');
    div.className = 'card';
    div.innerHTML = `
        <h1>${item.title}</h1>
        <p>${item.description}</p>
        <p>${item.price}</p>
        <img src="images/menu_placeholder.jpg" alt=${item.title} style="max-height: 200px; max-width: 200px;">
    `;

    return div;
}

const grid = document.getElementById('grid');

async function loadItems(){
    const items = await fetchItems();

    console.log(grid)

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
