package com.miiList.product.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.miiList.product.domain.model.Product;
import com.miiList.product.domain.port.in.IProductService;

import com.miiList.product.api.contract.IProductApi;
import com.miiList.product.api.dto.ProductRequest;
import com.miiList.product.api.dto.ProductResponse;
import com.miiList.product.api.dto.ProductMapper;


@RestController
public class ProductController implements IProductApi {
	
	private final IProductService productService;
	private final ProductMapper productMapper;

	public ProductController(IProductService productService, ProductMapper productMapper) {
	    this.productService = productService;
	    this.productMapper = productMapper;
	}	
	
	
	@Override
	public 	List<ProductResponse> products(String filter) {
		return productMapper.toResponseList(productService.products(Optional.ofNullable(filter)));
	}
	
	@Override
	public ResponseEntity<Void> deleteProduct(Long id) {
		productService.deleteProduct(id); 
		return ResponseEntity.noContent().build();
	}
	
	@Override
	public ResponseEntity<ProductResponse> addProduct(ProductRequest product) {
		Product newProduct = productService.addProduct(productMapper.toDomain(product));
		return ResponseEntity.ok(productMapper.toResponse(newProduct));
	}
}