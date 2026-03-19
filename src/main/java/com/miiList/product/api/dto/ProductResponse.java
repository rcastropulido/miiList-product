package com.miiList.product.api.dto;


public record ProductResponse(
	Long id,
    String name,
    Double price,
    String category
) {}