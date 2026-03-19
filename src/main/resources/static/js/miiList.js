async function loadProducts(filter) {
    const miiList = document.getElementById("miiList");
    const url = filter 
        ? `/api/products?filter=${encodeURIComponent(filter)}`
        : '/api/products';
        
    try {
        const response = await fetch(url);
        await handleFetchError(response);
        
        const products = await response.json();
        miiList.innerHTML = products.map(product => newRow(product)).join('');
    } catch (error) {
        console.log("Error loading products:", error);
        miiList.innerHTML = '<tr><td colspan="5">Error loading products</td></tr>';
    }    
}

async function deleteProduct(id, name) {
    if (!confirm(`Do you want to delete the product ${name}?`)) {
        return;
    }

    try {
        const response = await fetch(`/api/products/${id}`, {
            method: 'DELETE'
        });
        await handleFetchError(response);
        
        const row = document.querySelector(`tr[data-product-id="${id}"]`);
        if (row) row.remove();
    } catch(error) {
        console.log('Error: ', error);
        alert(error);
    }
}

async function addProduct() {
    // Get product values
    const productData = getValidatedProductData();
    if (!productData) return;
    
    try {
        const response = await fetch('/api/products', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productData)
        });
        await handleFetchError(response);
    
        const product = await response.json();
        document.getElementById("miiList").insertAdjacentHTML('beforeend', newRow(product));
    } catch(error) {
        console.log('Error: ', error);
        alert(error);
    }
}


function newRow(product) {
    return `
        <tr data-product-id="${product.id}">
            <td data-label="ID" class="id">${product.id}</td>
            <td data-label="Name" class="name">${product.name}</td>
            <td data-label="Price" class="price">${product.price}€</td>
            <td data-label="Category" class="category">${product.category}</td>
            <td class="action"><button onclick="deleteProduct(${product.id}, '${product.name}')">🗑️</button></td>
        </tr>    
    `
}


function getValidatedProductData() {
    const name = prompt('Name:');
    if (name === null) return null; 

    const priceString = prompt('Price:');
    if (priceString === null) return null;

    const category = prompt('Category:');
    if (category === null) return null;

    // Validar todos los campos
    const validation = validateProductInputs(name, priceString, category);
    if (!validation.isValid) {
        alert(validation.errors.join('\n'));
        return null;
    }

    return {
        name: name.trim(),
        price: parseFloat(priceString),
        category: category.trim()
    };
}

function validateProductInputs(name, priceString, category) {
    const errors = [];

    // Validar nombre
    if (!name || !name.trim()) {
        errors.push('Name cannot be empty');
    }

    // Validar precio
    if (!priceString || !priceString.trim()) {
        errors.push('Price cannot be empty');
    } else {
        const price = parseFloat(priceString);
        if (isNaN(price)) {
            errors.push('Price must be numeric');
        } else if (price < 0) {
            errors.push('Price cannot be negative');
        }
    }

    // Validar categoría
    if (!category || !category.trim()) {
        errors.push('Category cannot be empty');
    }

    return {
        isValid: errors.length === 0,
        errors: errors
    };
}