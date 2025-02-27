package com.mr.rebujito.grupo1.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mr.rebujito.grupo1.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

}
