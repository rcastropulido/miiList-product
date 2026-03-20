package com.miiList.product.api.controller;

import com.miiList.common.domain.exception.NotFoundException;
import com.miiList.product.api.dto.ProductMapper;
import com.miiList.product.domain.model.Product;
import com.miiList.product.domain.port.in.IProductService;
import com.miiList.product.api.dto.ProductRequest;
import com.miiList.product.api.dto.ProductResponse;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks; 
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private IProductService productService;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController productController;

    private Product product;
    private ProductRequest productRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Test Product", 99.99, "Electronics");
        productRequest = new ProductRequest("Test Product", 99.99, "Electronics");
        productResponse = new ProductResponse(1L, "Test Product", 99.99, "Electronics");
    }

    
    // --- GET /api/products ---
    
    @Test
    void products_WithoutFilter_ReturnsAllProducts() {
        List<Product> productList = List.of(product);
        List<ProductResponse> responseList = List.of(productResponse);

        when(productService.products(Optional.empty())).thenReturn(productList);
        when(productMapper.toResponseList(productList)).thenReturn(responseList);

        List<ProductResponse> result = productController.products(null);

        assertEquals(1, result.size());
        assertEquals(productResponse, result.get(0));
        verify(productService).products(Optional.empty());
        verify(productMapper).toResponseList(productList);
    }

    @Test
    void products_WithFilter_ReturnsFilteredProducts() {
        String filter = "Electronics";
        List<Product> productList = List.of(product);
        List<ProductResponse> responseList = List.of(productResponse);

        when(productService.products(Optional.of(filter))).thenReturn(productList);
        when(productMapper.toResponseList(productList)).thenReturn(responseList);

        List<ProductResponse> result = productController.products(filter);

        assertEquals(1, result.size());
        verify(productService).products(Optional.of(filter));
        verify(productMapper).toResponseList(productList);
    }

    @Test
    void products_WithFilter_ReturnsEmptyList() {
    	String filter = "NonExistent";

        when(productService.products(Optional.of(filter))).thenReturn(List.of());
        when(productMapper.toResponseList(List.of())).thenReturn(List.of());

        List<ProductResponse> result = productController.products(filter);

        assertTrue(result.isEmpty());
        verify(productService).products(Optional.of(filter));
    }

    
    // --- POST /api/products ---

    @Test
    void addProduct_ValidRequest_ReturnsOkWithProductResponse() {
        when(productMapper.toDomain(productRequest)).thenReturn(product);
        when(productService.addProduct(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ResponseEntity<ProductResponse> response = productController.addProduct(productRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productMapper).toDomain(productRequest);
        verify(productService).addProduct(product);
        verify(productMapper).toResponse(product);
    }

    
    // --- DELETE /api/products/{id} ---

    @Test
    void deleteProduct_ExistingId_ReturnsNoContent() {
        doNothing().when(productService).deleteProduct(1L);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(productService).deleteProduct(1L);
    }

    @Test
    void deleteProduct_WhenServiceThrows_ExceptionPropagates() {
        doThrow(new NotFoundException("Product not found")).when(productService).deleteProduct(99L);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> productController.deleteProduct(99L));

        assertEquals("Product not found", exception.getMessage());
    }
}