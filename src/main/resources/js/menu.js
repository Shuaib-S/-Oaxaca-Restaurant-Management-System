
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
