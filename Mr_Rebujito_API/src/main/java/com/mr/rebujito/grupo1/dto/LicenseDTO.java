package com.mr.rebujito.grupo1.dto;

import com.mr.rebujito.grupo1.entity.LicenseStatus;
import com.mr.rebujito.grupo1.entity.Stand;
import com.mr.rebujito.grupo1.entity.TownHall;

public class LicenseDTO {

	private TownHallDTO townHall;
	private StandDTO stand;
	private LicenseStatus licenseStatus;

    public LicenseDTO(TownHall townHall, Stand stand, LicenseStatus licenseStatus) {
    	this.townHall = townHall.toDTO();
        this.stand = stand.toDTO();
        this.licenseStatus = licenseStatus;
    }

	public TownHallDTO getTownHall() {
		return townHall;
	}

	public void setTownHall(TownHallDTO townHall) {
		this.townHall = townHall;
	}

	public LicenseStatus getLicenseStatus() {
		return licenseStatus;
	}

	public void setLicenseStatus(LicenseStatus licenseStatus) {
		this.licenseStatus = licenseStatus;
	}

	public StandDTO getStand() {
		return stand;
	}

	public void setStand(StandDTO stand) {
		this.stand = stand;
	}
	
}
