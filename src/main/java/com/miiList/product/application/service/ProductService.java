package com.miiList.product.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.miiList.common.domain.exception.AlreadyExistsException;
import com.miiList.common.domain.exception.NotFoundException;
import com.miiList.product.domain.model.Product;
import com.miiList.product.domain.port.in.IProductService;
import com.miiList.product.domain.port.out.IProductRepository;



@Service
public class ProductService implements IProductService {
	
	private final IProductRepository productRepository;

	public ProductService(IProductRepository productRepository) {
	    this.productRepository = productRepository;
	}
	
	
	@Override
	public List<Product> products(Optional<String> filter) {
		return filter
			.map(productRepository::findByFilter)
			.orElseGet(productRepository::findAll);
	}
	
	@Override
	public void deleteProduct(Long id) {
		if (productRepository.findById(id).isEmpty()) {
			throw new NotFoundException("Product " + id + " not found");
		}
		
		productRepository.deleteById(id);
	}
	
	@Override
	public Product addProduct(Product product) {
		if (productRepository.findByName(product.name()).isPresent()) {
			throw new AlreadyExistsException("Product " + product.name() + " already exists");
		}
		
		return productRepository.save(product);
	}
}