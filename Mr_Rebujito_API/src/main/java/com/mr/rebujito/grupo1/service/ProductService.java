package com.mr.rebujito.grupo1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mr.rebujito.grupo1.entity.Product;
import com.mr.rebujito.grupo1.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public void save(Product product) {
		this.productRepository.save(product);
	}
	
    public Optional<Product> findById(int productId) {
        return productRepository.findById(productId);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }
}