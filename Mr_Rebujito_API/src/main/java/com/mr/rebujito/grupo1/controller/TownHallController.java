package com.mr.rebujito.grupo1.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mr.rebujito.grupo1.dto.LicenseDTO;
import com.mr.rebujito.grupo1.dto.StandDTO;
import com.mr.rebujito.grupo1.dto.TownHallDTO;
import com.mr.rebujito.grupo1.entity.License;
import com.mr.rebujito.grupo1.entity.LicenseStatus;
import com.mr.rebujito.grupo1.entity.Stand;
import com.mr.rebujito.grupo1.entity.TownHall;
import com.mr.rebujito.grupo1.service.LicenseService;
import com.mr.rebujito.grupo1.service.MemberService;
import com.mr.rebujito.grupo1.service.StandService;
import com.mr.rebujito.grupo1.service.TownHallService;

@RestController
@RequestMapping("/townhall")
public class TownHallController {

	@Autowired
	private TownHallService townhallService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private StandService standService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private LicenseService licenseService;

	@GetMapping("")
	public ResponseEntity<List<TownHallDTO>> findAll() {
		try {
			List<TownHall> townhall = townhallService.findAll();

			if (townhall != null) {

				List<TownHallDTO> townhallDTOs = townhall.stream().map(TownHall::toDTO).collect(Collectors.toList());
				return new ResponseEntity<>(townhallDTOs, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/licenses/approved/stands")
	public ResponseEntity<List<StandDTO>> findStands() {
		TownHall townHall = memberService.memberLogin();

		if (townHall == null) {
			return ResponseEntity.notFound().build();
		}

		List<License> licenses = townHall.getLicenses();

		if (licenses == null || licenses.isEmpty()) {
			return ResponseEntity.ok(Collections.emptyList());
		}

		List<StandDTO> standsDTO = licenses.stream()
				.filter(l -> l.getLicenseStatus() == LicenseStatus.APPROVED && l.getStand() != null)
				.map(l -> new StandDTO(l.getStand().getBusinessName(), l.getStand().getMaximumCapacity(),
						l.getStand().getStandType()))
				.collect(Collectors.toList());

		return ResponseEntity.ok(standsDTO);
	}
	
	@GetMapping("/license/approved/stand/{standUsername}")
	public ResponseEntity<List<StandDTO>> findStand(@PathVariable String standUsername) {
		TownHall townHall = memberService.memberLogin();

		if (townHall == null) {
			return ResponseEntity.notFound().build();
		}

		List<License> licenses = townHall.getLicenses();

		if (licenses == null || licenses.isEmpty()) {
			return ResponseEntity.ok(Collections.emptyList());
		}

		List<StandDTO> standsDTO = licenses.stream()
				.filter(l -> l.getLicenseStatus() == LicenseStatus.APPROVED && l.getStand() != null && l.getStand().getUsername().equals(standUsername))
				.map(l -> new StandDTO(l.getStand().getBusinessName(), l.getStand().getMaximumCapacity(),
						l.getStand().getStandType()))
				.collect(Collectors.toList());

		return ResponseEntity.ok(standsDTO);
	}

	@GetMapping("/licenses")
	public ResponseEntity<List<LicenseDTO>> findLicenseTown() {
		TownHall townHall = memberService.memberLogin();

		if (townHall == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}

		List<License> licenses = townHall.getLicenses();

		if (licenses != null && !licenses.isEmpty()) {
			List<LicenseDTO> licenseDTOs = licenses.stream().map(license -> new LicenseDTO(license.getTownHall(),
					license.getStand_licenses(), license.getLicenseStatus())).collect(Collectors.toList());
			return ResponseEntity.ok(licenseDTOs);
		} else {
			return ResponseEntity.ok(Collections.emptyList());
		}
	}


	@GetMapping("/license/{standUsername}")
	public ResponseEntity<List<LicenseDTO>> findLicenseStand(@PathVariable String standUsername) {
		TownHall townHall = memberService.memberLogin();

		List<License> licenses = townHall.getLicenses();

		if (licenses != null) {
			List<License> filteredLicenses = licenses.stream()
					.filter(license -> license.getStand().getUsername().equals(standUsername))
					.collect(Collectors.toList());

			if (!filteredLicenses.isEmpty()) {
				List<LicenseDTO> licenseDTOList = filteredLicenses.stream()
						.map(license -> new LicenseDTO(license.getTownHall(), license.getStand(),
								license.getLicenseStatus()))
						.collect(Collectors.toList());

				return ResponseEntity.ok(licenseDTOList);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping("/license/update/{standUsername}")
	public ResponseEntity<String> updateLicenseStand(@PathVariable String standUsername,
			@RequestBody License updatedLicense) {

		TownHall townHall = memberService.memberLogin();
		Optional<Stand> standOptional = standService.findByUsername(standUsername);

		if (standOptional.isPresent()) {
			Stand standEntity = standOptional.get();
			License pendingLicense = null;

			for (License license : townHall.getLicenses()) {
				if (license.getStand().equals(standEntity) && license.getLicenseStatus() == LicenseStatus.PENDING) {
					pendingLicense = license;
					break;
				}
			}

			if (pendingLicense != null) {
				if (pendingLicense.getLicenseStatus() != LicenseStatus.APPROVED) {
					if (townHall.getLicenses().stream()
							.filter(license -> license.getLicenseStatus() == LicenseStatus.APPROVED)
							.count() <= townHall.getNumberLicenses()) {

						pendingLicense.setLicenseStatus(updatedLicense.getLicenseStatus());
						licenseService.save(pendingLicense);
						return ResponseEntity.ok("License status updated successfully.");

					} else {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								.body("Exceeded the maximum number of approved licenses for the town hall.");
					}
				} else {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body("Cannot update the status of an already approved license.");
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Pending license not found for the specified stand and town hall.");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stand not found.");
		}
	}
	
	@PutMapping("/update/profile")
	public ResponseEntity<String> updateProfile(@RequestBody TownHall t) {
		TownHall townhall = memberService.memberLogin();

		if (townhall != null) {

			townhall.setName(t.getName());
			townhall.setPassword(passwordEncoder.encode(t.getPassword()));
			townhall.setEmail(t.getEmail());
			townhall.setPhoto(t.getPhoto());
			townhall.setAddress(t.getAddress());
			townhall.setNumberLicenses(t.getNumberLicenses());
			

			townhallService.save(townhall);

			return ResponseEntity.ok("Profile updated successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profile.");
		}
	}

}
