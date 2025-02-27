package com.mr.rebujito.grupo1.dto;

import com.mr.rebujito.grupo1.entity.Stand;
import com.mr.rebujito.grupo1.entity.StandType;

public class StandDTO {

	private String businessName;
	private int maximumCapacity;
	private StandType standType;
	
	public StandDTO(String businessName, int maximumCapacity, StandType standType) {
		super();
		this.businessName = businessName;
		this.maximumCapacity = maximumCapacity;
		this.standType = standType;
	}

	public StandDTO(Stand stand) {
		super();
		this.businessName = stand.getBusinessName();
		this.maximumCapacity = stand.getMaximumCapacity();
		this.standType = stand.getStandType();
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
}
