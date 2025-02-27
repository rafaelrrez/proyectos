package com.mr.rebujito.grupo1.entity;

import java.util.List;

import com.mr.rebujito.grupo1.dto.StandDTO;
import com.mr.rebujito.grupo1.dto.StandMenuDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Stand extends Member {

	@NotBlank
	private String businessName;

	@NotNull
	private int maximumCapacity;

	@NotNull
	@Enumerated(EnumType.STRING)
	private StandType standType;

	@OneToMany(mappedBy = "stand_licenses")
	private List<License> licenses;

	@OneToMany(mappedBy = "stand_menus")
	private List<Product> food_menus;
	@ManyToMany
	@JoinTable(name = "stand_partner", joinColumns = @JoinColumn(name = "stand_id"), inverseJoinColumns = @JoinColumn(name = "partner_id"))
	private List<Partner> partners;

	public Stand() {

	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public int getMaximumCapacity() {
		return maximumCapacity;
	}

	public void setMaximumCapacity(int maximumCapacity) {
		this.maximumCapacity = maximumCapacity;
	}

	public StandType getStandType() {
		return standType;
	}

	public void setStandType(StandType standType) {
		this.standType = standType;
	}

	public List<License> getLicenses() {
		return licenses;
	}

	public void setLicenses(List<License> licenses) {
		this.licenses = licenses;
	}

	public List<Product> getFood_menus() {
		return food_menus;
	}

	public void setFood_menus(List<Product> food_menus) {
		this.food_menus = food_menus;
	}

	public List<Partner> getPartners() {
		return partners;
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}

	public StandDTO toDTO() {
		return new StandDTO(this);
	}
	
	public StandMenuDTO toMenuDTO() {
		return new StandMenuDTO(this);
	}
}