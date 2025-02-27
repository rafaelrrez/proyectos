package com.mr.rebujito.grupo1.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mr.rebujito.grupo1.dto.StandDTO;
import com.mr.rebujito.grupo1.entity.Partner;
import com.mr.rebujito.grupo1.entity.Rol;
import com.mr.rebujito.grupo1.entity.Stand;
import com.mr.rebujito.grupo1.service.MemberService;
import com.mr.rebujito.grupo1.service.PartnerService;

@RestController
@RequestMapping("/partner")
public class PartnerController {

	@Autowired
	private PartnerService partnerService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Partner p) {

		if (partnerService.findByUsername(p.getUsername()).isPresent()) {
			return new ResponseEntity<String>("The member already exists", HttpStatus.BAD_REQUEST);
		}

		Partner partner = new Partner();
		partner.setName(p.getName());
		partner.setUsername(p.getUsername());
		partner.setPassword(passwordEncoder.encode(p.getPassword()));
		partner.setEmail(p.getEmail());
		partner.setPhoto(p.getPhoto());
		partner.setAddress(p.getAddress());
		partner.setFirstSurname(p.getFirstSurname());
		partner.setSecondSurname(p.getSecondSurname());
		partner.setRol(Rol.PARTNER);

		partnerService.save(partner);
		return new ResponseEntity<String>("Partner successfully created", HttpStatus.OK);
	}

	@GetMapping("/enrolledstands")
	public ResponseEntity<List<StandDTO>> findEnrolledStands() {
	    Partner partner = memberService.memberLogin();

	    if (partner == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
	    }

	    List<Stand> stands = partner.getStands();

	    if (stands != null && !stands.isEmpty()) {
	        List<StandDTO> standsDTO = stands.stream()
	                .map(stand -> new StandDTO(stand.getBusinessName(), stand.getMaximumCapacity(), stand.getStandType()))
	                .collect(Collectors.toList());
	        return ResponseEntity.ok(standsDTO);
	    } else {
	        return ResponseEntity.ok(Collections.emptyList());
	    }
	}
	
	@GetMapping("/enrolledstand/{standUsername}")
	public ResponseEntity<List<StandDTO>> findEnrolledStand(@PathVariable String standUsername) {
	    Partner partner = memberService.memberLogin();
	    
	    if (partner == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
	    }

	    List<Stand> stands = partner.getStands();
	    List<StandDTO> standDTO = new ArrayList<>();

	    if (stands != null && !stands.isEmpty()) {
	        for (Stand stand : stands) {
	            if (stand.getUsername().equals(standUsername)) {
	                standDTO.add(stand.toDTO());
	            }
	        }

	        if (!standDTO.isEmpty()) {
	            return ResponseEntity.ok(standDTO);
	        }
	    }

	    return ResponseEntity.ok(Collections.emptyList());
	}

	@PutMapping("/update/profile")
	public ResponseEntity<String> updatePartner(@RequestBody Partner updatedPartner) {
		Partner partner = memberService.memberLogin();

		if (partner != null) {
			partner.setName(updatedPartner.getName());
			partner.setPassword(passwordEncoder.encode(updatedPartner.getPassword()));
			partner.setEmail(updatedPartner.getEmail());
			partner.setPhoto(updatedPartner.getPhoto());
			partner.setAddress(updatedPartner.getAddress());
			partner.setFirstSurname(updatedPartner.getFirstSurname());
			partner.setSecondSurname(updatedPartner.getSecondSurname());

			partnerService.save(partner);

			return ResponseEntity.ok("Profile updated successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profile.");
		}

	}

}