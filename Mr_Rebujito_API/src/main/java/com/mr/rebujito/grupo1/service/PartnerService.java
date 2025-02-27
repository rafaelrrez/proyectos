package com.mr.rebujito.grupo1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mr.rebujito.grupo1.entity.Partner;
import com.mr.rebujito.grupo1.entity.Stand;
import com.mr.rebujito.grupo1.repository.PartnerRepository;

@Service
public class PartnerService {
	@Autowired
	private PartnerRepository partnerRepository;

	public Optional<Partner> findByUsername(String username) {
		return this.partnerRepository.findByUsername(username);
	}
	
	public void save(Partner partner) {
		this.partnerRepository.save(partner);
	}

	public List<Partner> findAll() {
		return partnerRepository.findAll();
	}

}
