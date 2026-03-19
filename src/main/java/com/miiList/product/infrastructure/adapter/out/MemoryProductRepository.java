package com.miiList.product.infrastructure.adapter.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.miiList.product.data.MemoryDB;
import com.miiList.product.domain.model.Product;
import com.miiList.product.domain.port.out.IProductRepository;


@Component
public class MemoryProductRepository implements IProductRepository {
	
	@Override
	public Product save(Product product) {
		Long productID = MemoryDB.productID++;
		Product newProduct = new Product(productID, product.name(), product.price(), product.category());
		
		MemoryDB.products.put(productID, newProduct);
		return newProduct;
	}
	
	@Override
	public List<Product> findAll() {
		return MemoryDB.products.values().stream().toList();
	}
	public List<Product> findByFilter(String filter) {
		return findAll().stream()
			.filter(product -> 
				product.name().toLowerCase().contains(filter.toLowerCase()) ||
				product.category().toLowerCase().contains(filter.toLowerCase())
			)
			.toList();
	}
	
	@Override
	public Optional<Product> findById(Long id) {
	    return Optional.ofNullable(MemoryDB.products.get(id));
	}
	@Override
	public Optional<Product> findByName(String name) {
		return findAll().stream()
			.filter(product -> product.name().toLowerCase().equals(name.toLowerCase()))
			.findFirst();
	}	
	
	@Override
	public void deleteById(Number id) {
		MemoryDB.products.remove(id);
	}
}