package com.mr.rebujito.grupo1.entity;

import java.util.List;

import com.mr.rebujito.grupo1.dto.TownHallDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class TownHall extends Member{
	
	@OneToMany(mappedBy = "town_hall")
	private List <License> licenses;
	
	@NotNull
	private int numberLicenses;

	public TownHall() {
		
	}
	public TownHallDTO toDTO() {
	        return new TownHallDTO(this);
	}

	public List<License> getLicenses() {
		return licenses;
	}

	public void setLicenses(List<License> licenses) {
		this.licenses = licenses;
	}

	public int getNumberLicenses() {
		return numberLicenses;
	}

	public void setNumberLicenses(int numberLicenses) {
		this.numberLicenses = numberLicenses;
	}
}
