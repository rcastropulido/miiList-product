document.addEventListener('DOMContentLoaded', function() {
    let filterTimeout;

    // Event listener para el filtro
    document.getElementById('filterInput').addEventListener('input', function(e) {
        clearTimeout(filterTimeout);
        filterTimeout = setTimeout(() => {
            loadProducts(e.target.value);
        }, 300);        
    });
    
    // Event listener para el botón de agregar
    document.getElementById('addProductButton').addEventListener('click', function() {
        addProduct();
    });
    
    
    // Auto load products on startup`
    loadProducts();
});