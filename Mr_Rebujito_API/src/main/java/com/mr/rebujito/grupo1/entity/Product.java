package com.mr.rebujito.grupo1.entity;

import com.mr.rebujito.grupo1.dto.ProductDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
	@Version
	private int version;

	@ManyToOne
	@JoinColumn(name = "stand_id", referencedColumnName = "id" , nullable = false)
    private Stand stand_menus;

    @NotBlank
    private String name;

    @NotNull
    private double price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    public Product() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
 
	public Stand getStand_menus() {
		return stand_menus;
	}

	public void setStand_menus(Stand stand_menus) {
		this.stand_menus = stand_menus;
	}

	public ProductDTO toDTO() {
		return new ProductDTO(this);
	}
}
