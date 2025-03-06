document.addEventListener("DOMContentLoaded", function () {
    const url = new URLSearchParams(window.location.search);
    const tableId = url.get("tableId");

    if (tableId){
        document.getElementById("table-number").textContent = tableId;
        document.title = `Table ${tableId}`
    }

    async function fetchOrder() {
        // CODE TO BE ADDED HERE WHEN THE BACKEND EXISTS
    }
})
