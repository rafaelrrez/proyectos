package com.mr.rebujito.grupo1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mr.rebujito.grupo1.entity.Stand;

public interface StandRepository extends CrudRepository<Stand, Integer> {
	Optional<Stand> findByUsername(String username);
	List<Stand> findAll();
}
