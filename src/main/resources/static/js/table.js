document.addEventListener("DOMContentLoaded", function () {
    const url = new URLSearchParams(window.location.search);
    const tableId = url.get("tableId");

    if (tableId){
        document.getElementById("table-number").textContent = tableId;
        document.title = `Table ${tableId}`
    }

    async function fetchOrder() {
        try {
        const response = await fetch(`/api/CurrentOrders/table/${tableId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch orders');
            }

        const order = await response.json();

        const orderList = document.getElementById("order-list");
        orderList.innerHTML = "";

        console.log(order);

        order[0].itemList.forEach(item => {
            const li = document.createElement("li");
            li.textContent = `ITEM: ${item.title} - PRICE: ${item.price}`;
            orderList.appendChild(li);
        });

        } catch (error) {
            console.error('Error fetching order:', error);
        }
    }

    fetchOrder();
})
