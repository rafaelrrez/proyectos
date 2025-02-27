package com.mr.rebujito.grupo1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mr.rebujito.grupo1.entity.Admin;
import com.mr.rebujito.grupo1.entity.TownHall;
import org.springframework.stereotype.Repository;

@Repository
public interface TownHallRepository extends CrudRepository<TownHall, Integer> {

	List<TownHall> findAll();
	Optional<TownHall> findByUsername(String username);

}
