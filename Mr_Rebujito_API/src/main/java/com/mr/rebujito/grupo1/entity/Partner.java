package com.mr.rebujito.grupo1.entity;

import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Partner extends Member {

	@NotBlank
	private String firstSurname;

	@Nullable
	private String secondSurname;

    @ManyToMany(mappedBy = "partners")
    private List<Stand> stands;

	public Partner() {

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

	public List<Stand> getStands() {
		return stands;
	}

	public void setStands(List<Stand> stands) {
		this.stands = stands;
	}
}
