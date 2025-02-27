package com.mr.rebujito.grupo1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mr.rebujito.grupo1.entity.Partner;
import com.mr.rebujito.grupo1.entity.Stand;

@Repository	
public interface PartnerRepository extends CrudRepository<Partner, Integer> {
	Optional<Partner> findByUsername(String username);
	List<Partner> findAll();
}
