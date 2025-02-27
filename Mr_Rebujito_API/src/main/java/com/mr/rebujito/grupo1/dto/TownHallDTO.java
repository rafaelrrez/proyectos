package com.mr.rebujito.grupo1.dto;

import com.mr.rebujito.grupo1.entity.TownHall;


public class TownHallDTO {
    private String name;
    private String address;
    
    public TownHallDTO(String name, String address) {
    	this.name = name;
    	this.address = address;
    }

    public TownHallDTO(TownHall townHall) {
    	this.name = townHall.getName();
    	this.address = townHall.getAddress();
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}