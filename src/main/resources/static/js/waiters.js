
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
