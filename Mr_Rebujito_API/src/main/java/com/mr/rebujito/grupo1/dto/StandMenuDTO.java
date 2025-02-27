package com.mr.rebujito.grupo1.dto;

import java.util.ArrayList;
import java.util.List;

import com.mr.rebujito.grupo1.entity.Product;
import com.mr.rebujito.grupo1.entity.Stand;

public class StandMenuDTO {

	private String businessName;
	private List<ProductDTO> food_menus;
	
	public StandMenuDTO(String businessName, List<Product> food_menus) {
		super();
		this.businessName = businessName;
		this.food_menus = new ArrayList<ProductDTO>();
		
		for (Product product : food_menus) {
			this.food_menus.add(product.toDTO());
		}
	}

	public StandMenuDTO(Stand stand) {
		super();
		this.businessName = stand.getBusinessName();
		this.food_menus = new ArrayList<ProductDTO>();
		
		for (Product product : stand.getFood_menus()) {
			this.food_menus.add(product.toDTO());
		}
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public List<ProductDTO> getFood_menus() {
		return food_menus;
	}

	public void setFood_menus(List<ProductDTO> food_menus) {
		this.food_menus = food_menus;
	}
}
