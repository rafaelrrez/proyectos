package com.mr.rebujito.grupo1.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Admin extends Member {

	@NotBlank
	private String firstSurname;
	
	@Nullable
	private String secondSurname;

	public Admin() {

	}

	public String getFirstSurname() {
		return firstSurname;
	}

	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}
}
