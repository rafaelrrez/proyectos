package com.mr.rebujito.grupo1;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mr.rebujito.grupo1.entity.Admin;
import com.mr.rebujito.grupo1.entity.Rol;
import com.mr.rebujito.grupo1.repository.AdminRepository;

@Component
public class DataLoader implements ApplicationRunner {

    private AdminRepository adminRepository;
    
	@Autowired
	private PasswordEncoder passwordEncoder;


    @Autowired
    public DataLoader(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void run(ApplicationArguments args) {

        Admin admin = new Admin();
        admin.setFirstSurname("firstsurname");
        admin.setSecondSurname("secondsurname");
        admin.setName("admin");
        admin.setEmail("admin@iescarrillo.es");
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123456"));
        admin.setAddress("C.Cervantes");
        admin.setPhoto("https://example.com/admin-photo.jpg");
        admin.setRol(Rol.ADMIN);

        Optional<Admin> existingAdmin = adminRepository.findByUsername(admin.getUsername());

        if (existingAdmin.isPresent()) {
            System.out.println("The administrator already exists. No additional actions will be taken.");
        } else {
            adminRepository.save(admin);
            System.out.println("Administrator created successfully.");
        }
    }
}