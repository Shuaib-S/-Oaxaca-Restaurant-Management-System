
async function fetchItems() {

    try {
        const response = await fetch('api/items?sort=id,desc', {
            headers: {
                'Accept': 'application/json'
            }
        });

        const data = await response.json();

        return data.content;
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
        <img src="images/menu_placholder.jpg" alt=${item.title}/>
    `
}
