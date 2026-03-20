package com.miiList.product.api.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.miiList.product.domain.model.Product;


@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    private ProductMapper mapper;

	
    @BeforeEach
    void setUp() {
    	mapper = new ProductMapper();
    }
    

    @Test
    void toDomain_WithValidRequest_ReturnsProduct() {
        ProductRequest request = new ProductRequest("Laptop", 999.99, "Electronics");

        Product result = mapper.toDomain(request);

        assertNotNull(result);
        assertNull(result.id()); // El mapper debe asignar null al ID
        assertEquals("Laptop", result.name());
        assertEquals(999.99, result.price());
        assertEquals("Electronics", result.category());
    }

    @Test
    void toResponse_WithValidProduct_ReturnsProductResponse() {
        Product product = new Product(1L, "Mouse", 25.50, "Peripherals");

        ProductResponse result = mapper.toResponse(product);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Mouse", result.name());
        assertEquals(25.50, result.price());
        assertEquals("Peripherals", result.category());
    }

    @Test
    void toResponseList_WithMultipleProducts_ReturnsResponseList() {
        List<Product> products = List.of(
            new Product(1L, "Keyboard", 50.0, "Peripherals"),
            new Product(2L, "Monitor", 300.0, "Displays")
        );

        List<ProductResponse> result = mapper.toResponseList(products);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals("Keyboard", result.get(0).name());
        assertEquals(2L, result.get(1).id());
        assertEquals("Monitor", result.get(1).name());
    }

    @Test
    void toResponseList_WithEmptyList_ReturnsEmptyList() {
        List<Product> products = List.of();

        List<ProductResponse> result = mapper.toResponseList(products);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}