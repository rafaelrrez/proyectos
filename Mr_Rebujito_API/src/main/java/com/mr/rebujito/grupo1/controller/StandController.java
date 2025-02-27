package com.mr.rebujito.grupo1.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mr.rebujito.grupo1.dto.LicenseDTO;
import com.mr.rebujito.grupo1.dto.ProductDTO;
import com.mr.rebujito.grupo1.dto.StandDTO;
import com.mr.rebujito.grupo1.dto.StandMenuDTO;
import com.mr.rebujito.grupo1.entity.License;
import com.mr.rebujito.grupo1.entity.LicenseStatus;
import com.mr.rebujito.grupo1.entity.Partner;
import com.mr.rebujito.grupo1.entity.Product;
import com.mr.rebujito.grupo1.entity.Rol;
import com.mr.rebujito.grupo1.entity.Stand;
import com.mr.rebujito.grupo1.entity.TownHall;
import com.mr.rebujito.grupo1.service.LicenseService;
import com.mr.rebujito.grupo1.service.MemberService;
import com.mr.rebujito.grupo1.service.PartnerService;
import com.mr.rebujito.grupo1.service.ProductService;
import com.mr.rebujito.grupo1.service.StandService;
import com.mr.rebujito.grupo1.service.TownHallService;

@RestController
@RequestMapping("/stand")
public class StandController {

	@Autowired
	private StandService standService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private ProductService productService;

	@Autowired
	private LicenseService licenseService;

	@Autowired
	private TownHallService townHallService;

	@Autowired
	private PartnerService partnerService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("")
	public ResponseEntity<List<StandDTO>> findAll() {
		List<Stand> stands = standService.findAll();

		if (stands.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}

		List<StandDTO> standDTOs = stands.stream().map(Stand::toDTO).collect(Collectors.toList());
		return ResponseEntity.ok(standDTOs);
	}

	@GetMapping("/{standUsername}")
	public ResponseEntity<List<StandDTO>> findStand(@PathVariable String standUsername) {
		Optional<Stand> standOptional = standService.findByUsername(standUsername);

		if (standOptional.isPresent()) {
			Stand stand = standOptional.get();
			List<StandDTO> standDTOs = Collections.singletonList(stand.toDTO());
			return ResponseEntity.ok(standDTOs);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}
	}

	@GetMapping("/menus")
	public ResponseEntity<List<StandMenuDTO>> findAllMenus() {
		List<Stand> stands = standService.findAll();

		if (stands.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}

		List<StandMenuDTO> standMenuDTO = stands.stream().map(Stand::toMenuDTO).collect(Collectors.toList());
		return ResponseEntity.ok(standMenuDTO);
	}

	@GetMapping("/menu/{standUsername}")
	public ResponseEntity<List<StandMenuDTO>> findMenuStand(@PathVariable String standUsername) {
		Optional<Stand> standOptional = standService.findByUsername(standUsername);

		if (standOptional.isPresent()) {
			Stand stand = standOptional.get();
			List<StandMenuDTO> standMenuDTO = Collections.singletonList(stand.toMenuDTO());
			return ResponseEntity.ok(standMenuDTO);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}
	}

	@GetMapping("/menu")
	public ResponseEntity<List<ProductDTO>> findMenu() {
		Stand stand = memberService.memberLogin();

		if (stand != null) {
			List<Product> menus = stand.getFood_menus();

			if (menus != null) {
				List<ProductDTO> menuDTOs = menus.stream()
						.map(product -> new ProductDTO(product.getName(), product.getPrice(), product.getProductType()))
						.collect(Collectors.toList());

				return ResponseEntity.ok(menuDTOs);
			} else {
				return ResponseEntity.ok(Collections.emptyList());
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
		}
	}

	@GetMapping("/licenses")
	public List<LicenseDTO> findLicense() {
		Stand stand = memberService.memberLogin();
		List<License> licenses = stand.getLicenses();

		if (licenses != null) {
			return licenses.stream().map(
					license -> new LicenseDTO(license.getTownHall(), license.getStand(), license.getLicenseStatus()))
					.collect(Collectors.toList());
		} else {
			return Collections.emptyList();
		}
	}

	@GetMapping("/license/{townHallUsername}")
	public ResponseEntity<List<LicenseDTO>> findLicenseTownHall(@PathVariable String townHallUsername) {
		Stand stand = memberService.memberLogin();
		List<License> licenses = stand.getLicenses();

		if (licenses != null) {
			List<License> filteredLicenses = licenses.stream()
					.filter(license -> license.getTownHall().getUsername().equals(townHallUsername))
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

	@PutMapping("/update/profile")
	public ResponseEntity<String> updateProfile(@RequestBody Stand s) {
		Stand stand = memberService.memberLogin();

		if (stand != null) {

			stand.setName(s.getName());
			stand.setPassword(passwordEncoder.encode(s.getPassword()));
			stand.setEmail(s.getEmail());
			stand.setPhoto(s.getPhoto());
			stand.setAddress(s.getAddress());
			stand.setBusinessName(s.getBusinessName());
			stand.setMaximumCapacity(s.getMaximumCapacity());
			stand.setStandType(s.getStandType());

			System.out.println(s.getMaximumCapacity());
			
			standService.save(stand);

			return ResponseEntity.ok("Profile updated successfully.");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profile.");
		}
	}

	@PutMapping("/product/update/{productId}")
	public ResponseEntity<String> updateProduct(@PathVariable int productId, @RequestBody Product updatedProduct) {
		Optional<Product> optionalProduct = productService.findById(productId);
		Stand stand = memberService.memberLogin();

		if (optionalProduct.isPresent()) {
			Product existingProduct = optionalProduct.get();

			if (existingProduct.getStand_menus().equals(stand)) {
				existingProduct.setName(updatedProduct.getName());
				existingProduct.setPrice(updatedProduct.getPrice());
				existingProduct.setProductType(updatedProduct.getProductType());

				productService.save(existingProduct);

				return ResponseEntity.ok("Product updated successfully.");
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Product does not belong to this stand.");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Stand s) {

		if (memberService.findByUsername(s.getUsername()).isPresent()) {
			return new ResponseEntity<String>("The member already exists", HttpStatus.BAD_REQUEST);
		}

		Stand stand = new Stand();
		stand.setName(s.getName());
		stand.setUsername(s.getUsername());
		stand.setPassword(passwordEncoder.encode(s.getPassword()));
		stand.setEmail(s.getEmail());
		stand.setPhoto(s.getPhoto());
		stand.setAddress(s.getAddress());
		stand.setBusinessName(s.getBusinessName());
		stand.setMaximumCapacity(s.getMaximumCapacity());
		stand.setStandType(s.getStandType());
		stand.setRol(Rol.STAND);

		standService.save(stand);
		return new ResponseEntity<String>("Stand successfully created", HttpStatus.OK);
	}

	@PostMapping("/product/create")
	public ResponseEntity<String> createProduct(@RequestBody Product p) {
		Stand stand = memberService.memberLogin();

		Product product = new Product();
		product.setStand_menus(stand);
		product.setName(p.getName());
		product.setPrice(p.getPrice());
		product.setProductType(p.getProductType());

		stand.getFood_menus().add(product);
		productService.save(product);

		return ResponseEntity.ok("Product added to the menu successfully.");

	}

	@PostMapping("/partner/add/{partnerUsername}")
	public ResponseEntity<String> addPartnerToStand(@PathVariable String partnerUsername) {
		Stand stand = memberService.memberLogin();

		if (stand.getPartners().size() >= stand.getMaximumCapacity()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Cannot add more partners. Maximum capacity reached.");
		}

		Optional<Partner> partner = partnerService.findByUsername(partnerUsername);

		if (partner.isPresent()) {

			if (!stand.getPartners().contains(partner.get())) {

				stand.getPartners().add(partner.get());

				standService.save(stand);

				return ResponseEntity.ok("Partner added successfully to the stand.");

			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Partner is already associated with the stand.");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partner not found.");
		}
	}

	@PostMapping("/license/request/{townHallUsername}")
	public ResponseEntity<String> createLicense(@PathVariable String townHallUsername) {
		Stand stand = memberService.memberLogin();

		Optional<TownHall> townHallOptional = townHallService.findByUsername(townHallUsername);

		if (townHallOptional.isPresent()) {
			TownHall townHall = townHallOptional.get();

			if (!licenseService.hasPendingRequestOrActiveLicense(stand, townHall)) {
				License license = new License();
				license.setStand(stand);
				license.setTownHall(townHall);

				licenseService.save(license);

				return ResponseEntity.ok("License request created successfully.");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
						"This stand already has a pending request or an active license associated with the specified Town Hall.");
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Town Hall not found.");
		}
	}

	@DeleteMapping("/product/delete/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable int productId) {
		Optional<Product> optionalProduct = productService.findById(productId);
		Stand stand = memberService.memberLogin();

		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			if (product.getStand_menus().equals(stand)) {
				productService.delete(product);
				return ResponseEntity.ok("Product deleted successfully.");
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Product does not belong to this stand.");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
		}
	}

	@DeleteMapping("/partner/delete/{partnerUsername}")
	public ResponseEntity<String> deletePartnerFromStand(@PathVariable String partnerUsername) {
		Stand stand = memberService.memberLogin();

		Optional<Partner> partner = partnerService.findByUsername(partnerUsername);

		if (partner.isPresent()) {

			if (stand.getPartners().contains(partner.get())) {

				stand.getPartners().remove(partner.get());

				standService.save(stand);

				return ResponseEntity.ok("Partner removed successfully from the stand.");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Partner is not associated with the stand.");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Partner not found.");
		}
	}

	@DeleteMapping("/license/delete/{townHallUsername}")
	public ResponseEntity<String> deleteLicenseFromStand(@PathVariable String townHallUsername) {
		Stand stand = memberService.memberLogin();

		Optional<License> licenseToDelete = stand.getLicenses().stream()
				.filter(license -> license.getTownHall().getUsername().equals(townHallUsername)).findFirst();

		if (licenseToDelete.isPresent() && LicenseStatus.PENDING.equals(licenseToDelete.get().getLicenseStatus())) {

			licenseService.deleteLicense(licenseToDelete.get().getId());

			return ResponseEntity.ok("License removed successfully from the stand and database.");
		} else if (licenseToDelete.isPresent()
				&& !LicenseStatus.PENDING.equals(licenseToDelete.get().getLicenseStatus())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("License cannot be removed because the status is not PENDING.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("License not found for the specified TownHall associated with Stand.");
		}
	}
}
