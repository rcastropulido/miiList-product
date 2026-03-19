package com.miiList.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.miiList.product.domain.model.Product;
import com.miiList.product.domain.port.in.IProductService;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class MiiListApplication {

    private final IProductService productService;

    public MiiListApplication(IProductService productService) {
        this.productService = productService;
    }
    
	
	public static void main(String[] args) {
		SpringApplication.run(MiiListApplication.class, args);
	}
	
	
	@PostConstruct
	public void init() {
	    productService.addProduct(new Product(null, "Notebook", 2D, "Office"));
	    productService.addProduct(new Product(null, "Laptop", 5D, "Electronics"));
	    productService.addProduct(new Product(null, "Mouse", 10D, "Electronics"));
	    productService.addProduct(new Product(null, "Desk", 3D, "Furniture"));
	}
}
