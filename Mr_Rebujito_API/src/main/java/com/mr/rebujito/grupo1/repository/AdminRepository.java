package com.mr.rebujito.grupo1.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mr.rebujito.grupo1.entity.Admin;


@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer>{
	
	Optional<Admin> findByUsername(String username);
}
