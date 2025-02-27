package com.mr.rebujito.grupo1.entity;

import com.mr.rebujito.grupo1.dto.LicenseDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;

@Entity
public class License {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Version
	private int version;

	@ManyToOne
	@JoinColumn(name = "stand_id", nullable = false)
	private Stand stand_licenses;

	@ManyToOne
	@JoinColumn(name = "townhall_id", nullable = false)
	private TownHall town_hall;

	@NotNull
	@Enumerated(EnumType.STRING)
	private LicenseStatus licenseStatus;

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

	public Stand getStand_licenses() {
		return stand_licenses;
	}

	public void setStand_licenses(Stand stand_licenses) {
		this.stand_licenses = stand_licenses;
	}

	public License() {
		this.licenseStatus = LicenseStatus.PENDING;
	}

	public LicenseStatus getLicenseStatus() {
		return licenseStatus;
	}

	public void setLicenseStatus(LicenseStatus licenseStatus) {
		this.licenseStatus = licenseStatus;
	}

	public Stand getStand() {
		return stand_licenses;
	}

	public void setStand(Stand stand) {
		this.stand_licenses = stand;
	}

	public TownHall getTownHall() {
		return town_hall;
	}

	public void setTownHall(TownHall townHall) {
		this.town_hall = townHall;
	}

	public LicenseDTO toDTO() {
		return new LicenseDTO(this.getTownHall(), this.getStand(), this.getLicenseStatus());
	}
}
