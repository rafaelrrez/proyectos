package com.mr.rebujito.grupo1.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mr.rebujito.grupo1.entity.Admin;
import com.mr.rebujito.grupo1.entity.Product;
import com.mr.rebujito.grupo1.entity.Rol;
import com.mr.rebujito.grupo1.entity.Stand;
import com.mr.rebujito.grupo1.entity.TownHall;
import com.mr.rebujito.grupo1.service.AdminService;
import com.mr.rebujito.grupo1.service.TownHallService;
import com.mr.rebujito.grupo1.service.MemberService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminservice;
	
	@Autowired
	private TownHallService townHallService; 
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register/admin")
	public ResponseEntity<String> registerAdmin(@RequestBody Admin a) {

		if (memberService.findByUsername(a.getUsername()).isPresent()) {
			return new ResponseEntity<String>("The member already exists", HttpStatus.BAD_REQUEST);
		}

		Admin admin = new Admin();
		admin.setName(a.getName());
		admin.setUsername(a.getUsername());
		admin.setPassword(passwordEncoder.encode(a.getPassword()));
		admin.setEmail(a.getEmail());
		admin.setPhoto(a.getPhoto());
		admin.setAddress(a.getAddress());
		admin.setFirstSurname(a.getFirstSurname());
		admin.setSecondSurname(a.getSecondSurname());
		admin.setRol(Rol.ADMIN);

		adminservice.save(admin);
		return new ResponseEntity<String>("Admin created successfully", HttpStatus.OK);
	}

	@PostMapping("/register/townhall")
	public ResponseEntity<String> registerTownhall(@RequestBody TownHall t) {

		if (memberService.findByUsername(t.getUsername()).isPresent()) {
			return new ResponseEntity<String>("The member already exists", HttpStatus.BAD_REQUEST);
		}

		TownHall townhall = new TownHall();
		townhall.setName(t.getName());
		townhall.setUsername(t.getUsername());
		townhall.setPassword(passwordEncoder.encode(t.getPassword()));
		townhall.setEmail(t.getEmail());
		townhall.setPhoto(t.getPhoto());
		townhall.setAddress(t.getAddress());
		townhall.setNumberLicenses(t.getNumberLicenses());
		townhall.setRol(Rol.TOWNHALL);
		
		townHallService.save(townhall);
		return new ResponseEntity<String>("Townhall created successfully", HttpStatus.OK);
	}
	
	@PutMapping("/update/profile")
	public ResponseEntity<String> updateProfile(@RequestBody Admin a) {
		Admin admin = memberService.memberLogin();

		if (admin != null) {

			admin.setName(a.getName());
			admin.setPassword(passwordEncoder.encode(a.getPassword()));
			admin.setEmail(a.getEmail());
			admin.setPhoto(a.getPhoto());
			admin.setAddress(a.getAddress());
			admin.setFirstSurname(a.getFirstSurname());
			admin.setSecondSurname(a.getSecondSurname());
			

			adminservice.save(admin);

			return ResponseEntity.ok("Profile updated successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profile.");
		}
	}
	
}
