package com.miiList.product.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record ProductRequest(
	@NotBlank(message = "NAME_EMPTY")
	String name,
	
	@NotNull(message = "PRICE_EMPTY")
	@Min(value = 0, message = "PRICE_NEGATIVE")
	Double price,
	
	@NotBlank(message = "CATEGORY_EMPTY")
	String category
) {}