package com.miiList.product.application.service;

import com.miiList.common.domain.exception.AlreadyExistsException;
import com.miiList.common.domain.exception.NotFoundException;
import com.miiList.product.domain.model.Product;
import com.miiList.product.domain.port.out.IProductRepository;

import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks; 
import org.mockito.Mock; 
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock 
	private IProductRepository productRepository;
	@InjectMocks 
	private ProductService productService;
	
	private Product testProduct;
	
	
	@BeforeEach
	void setUp() {
	    testProduct = new Product(1L, "Test Product", 99.99, "Electronics");
	}
	
	
	@Test
	void testAddProduct_Success() {
	    when(productRepository.findByName(testProduct.name())).thenReturn(Optional.empty());
	    when(productRepository.save(testProduct)).thenReturn(testProduct);

	    Product result = productService.addProduct(testProduct);

	    assertEquals(testProduct, result);
	    verify(productRepository, times(1)).findByName(testProduct.name());
	    verify(productRepository, times(1)).save(testProduct);
	}

	@Test
	void testAddProduct_AlreadyExists() {
	    when(productRepository.findByName(testProduct.name())).thenReturn(Optional.of(testProduct));

	    assertThrows(AlreadyExistsException.class, () -> productService.addProduct(testProduct));

	    verify(productRepository, times(1)).findByName(testProduct.name());
	    verify(productRepository, never()).save(any());
	}

	@Test
	void testDeleteProduct_Success() {
	    when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
	    doNothing().when(productRepository).deleteById(1L);

	    assertDoesNotThrow(() -> productService.deleteProduct(1L));

	    verify(productRepository, times(1)).findById(1L);
	    verify(productRepository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteProduct_NotFound() {
	    when(productRepository.findById(1L)).thenReturn(Optional.empty());

	    assertThrows(NotFoundException.class, () -> {
	        productService.deleteProduct(1L);
	    });

	    verify(productRepository, times(1)).findById(1L);
	    verify(productRepository, never()).deleteById(anyLong());
	}
	
	@Test
	void products_WithFilter_ReturnsFilteredProducts() {
	    when(productRepository.findByFilter("Electronics"))
	        .thenReturn(List.of(testProduct));

	    List<Product> result = productService.products(Optional.of("Electronics"));

	    assertEquals(1, result.size());
	    verify(productRepository).findByFilter("Electronics");
	}

	@Test
	void products_WithoutFilter_ReturnsAllProducts() {
	    when(productRepository.findAll()).thenReturn(List.of(testProduct));

	    List<Product> result = productService.products(Optional.empty());

	    assertEquals(1, result.size());
	    verify(productRepository).findAll();
	}

	@Test
	void addProduct_WithValidProduct_SavesAndReturnsProduct() {
	    when(productRepository.save(testProduct)).thenReturn(testProduct);

	    Product result = productService.addProduct(testProduct);

	    assertNotNull(result);
	    verify(productRepository).save(testProduct);
	}

	@Test
	void deleteProduct_WithValidId_DeletesProduct() {
	    when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
	    doNothing().when(productRepository).deleteById(1L);

	    productService.deleteProduct(1L);

	    verify(productRepository).findById(1L);
	    verify(productRepository).deleteById(1L);
	}	
}