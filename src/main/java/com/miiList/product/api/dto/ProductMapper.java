package com.miiList.product.api.dto;

import java.util.List;

import org.springframework.stereotype.Component;
import com.miiList.product.domain.model.Product;

   
@Component
public class ProductMapper {

	public Product toDomain(ProductRequest dto) {
		return new Product(null, dto.name(), dto.price(), dto.category());
	}

	public ProductResponse toResponse(Product product) {
		return new ProductResponse(product.id(), product.name(), product.price(), product.category());
	}
    public List<ProductResponse> toResponseList(List<Product> products) {
    	return products.stream()
			.map(this::toResponse)
			.toList();
    }
}