package com.mr.rebujito.grupo1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mr.rebujito.grupo1.entity.Admin;
import com.mr.rebujito.grupo1.repository.AdminRepository;

@Service
public class AdminService {
	@Autowired
	private AdminRepository adminRepository;

	public void save(Admin admin) {
		this.adminRepository.save(admin);
	}

	public Optional<Admin> findByUsername(String username) {
		return this.adminRepository.findByUsername(username);
	}
}
