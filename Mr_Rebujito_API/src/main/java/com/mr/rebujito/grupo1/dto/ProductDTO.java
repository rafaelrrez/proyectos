package com.mr.rebujito.grupo1.dto;

import com.mr.rebujito.grupo1.entity.Product;
import com.mr.rebujito.grupo1.entity.ProductType;

public class ProductDTO {
    private String name;
    private double price;
    private ProductType productType;
    
	public ProductDTO(Product product) {
		super();
		this.name = product.getName();
		this.price = product.getPrice();
		this.productType = product.getProductType();
	}
	
    
	public ProductDTO(String name, double price, ProductType productType) {
		super();
		this.name = name;
		this.price = price;
		this.productType = productType;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public ProductType getProductType() {
		return productType;
	}
	public void setProductType(ProductType productType) {
		this.productType = productType;
	}
}
