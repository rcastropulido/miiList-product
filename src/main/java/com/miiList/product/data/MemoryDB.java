package com.miiList.product.data;

import java.util.HashMap;
import java.util.Map;

import com.miiList.product.domain.model.Product;


public class MemoryDB {
	
	public static Long productID = 1L;	
	public static Map<Long, Product> products = new HashMap<Long, Product>();
	
}