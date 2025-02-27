package com.mr.rebujito.grupo1.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mr.rebujito.grupo1.entity.License;

@Repository
public interface LicenseRepository extends CrudRepository<License, Integer>{
	List<License> findAll();
}
