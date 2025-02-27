package com.mr.rebujito.grupo1.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mr.rebujito.grupo1.repository.TownHallRepository;

import jakarta.transaction.Transactional;

import com.mr.rebujito.grupo1.entity.Admin;
import com.mr.rebujito.grupo1.entity.License;
import com.mr.rebujito.grupo1.entity.LicenseStatus;
import com.mr.rebujito.grupo1.entity.Stand;
import com.mr.rebujito.grupo1.entity.TownHall;

@Service
public class TownHallService {

    @Autowired
    private TownHallRepository townhallRepository;

    @Transactional
    public List<TownHall> findAll() {
        return (List<TownHall>) townhallRepository.findAll();
    }
    
    public void save(TownHall townHall) {
		this.townhallRepository.save(townHall);
	}
    
    public Optional<TownHall> findByUsername(String username) {
		return this.townhallRepository.findByUsername(username);
	}
    
}


    

