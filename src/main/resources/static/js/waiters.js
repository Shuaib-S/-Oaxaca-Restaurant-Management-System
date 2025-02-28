
document.querySelector('.table').forEach(table => {
    table.addEventListener('click', function() {
        window.location.href = `/table.html?tableId=${this.id}`;
    });
});
