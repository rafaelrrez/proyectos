package com.mr.rebujito.grupo1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mr.rebujito.grupo1.entity.License;
import com.mr.rebujito.grupo1.entity.LicenseStatus;
import com.mr.rebujito.grupo1.entity.Stand;
import com.mr.rebujito.grupo1.entity.TownHall;
import com.mr.rebujito.grupo1.repository.LicenseRepository;

@Service
public class LicenseService {

	@Autowired
	private LicenseRepository licenseRepository;

	public List<License> getAllLicenses() {
		return licenseRepository.findAll();
	}

	public License getLicenseById(int id) {
		return licenseRepository.findById(id).orElse(null);
	}

	public License save(License license) {
		return licenseRepository.save(license);
	}

	public void deleteLicense(int id) {
		licenseRepository.deleteById(id);
	}

	public boolean hasPendingRequestOrActiveLicense(Stand stand, TownHall townHall) {
		List<License> licenses = licenseRepository.findAll();

		for (License license : licenses) {
			if (license.getStand().equals(stand) && license.getTownHall().equals(townHall)) {

				if (license.getLicenseStatus() == LicenseStatus.PENDING
						|| license.getLicenseStatus() == LicenseStatus.APPROVED) {
					return true;
				}
			}
		}

		return false;
	}

}
