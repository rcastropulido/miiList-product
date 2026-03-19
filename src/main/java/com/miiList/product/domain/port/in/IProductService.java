package com.miiList.product.domain.port.in;

import com.miiList.product.domain.model.Product;

import java.util.List;
import java.util.Optional;


public interface IProductService {
	
	Product addProduct(Product product);
	
    List<Product> products(Optional<String> filter);
    
    void deleteProduct(Long id);
}