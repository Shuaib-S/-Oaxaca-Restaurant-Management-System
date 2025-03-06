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

  // Navigation scroll effect
  window.addEventListener('scroll', () => {
    if (window.scrollY > 100) {
      nav.classList.add('scrolled');
    } else {
      nav.classList.remove('scrolled');
    }
  });
});
