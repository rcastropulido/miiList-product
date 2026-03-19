package com.miiList.product.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.miiList.product.domain.model.Product;


public interface IProductRepository {
	
	Product save(Product product);
	
	List<Product> findAll();
	List<Product> findByFilter(String filter);
	
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    
    void deleteById(Number id);
}