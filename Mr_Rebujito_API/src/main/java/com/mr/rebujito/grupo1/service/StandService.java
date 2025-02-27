package com.mr.rebujito.grupo1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mr.rebujito.grupo1.entity.Stand;
import com.mr.rebujito.grupo1.repository.StandRepository;

@Service
public class StandService {
	
	@Autowired
	private StandRepository standRepository;
	
	public Optional<Stand> findByUsername(String username) {
		return this.standRepository.findByUsername(username);
	}
	
	public void save(Stand stand) {
		this.standRepository.save(stand);
	}

	public List<Stand> findAll() {
		return standRepository.findAll();
	}
}
