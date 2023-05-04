package com.rumpup.demo.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumpup.demo.entities.ProductOffering;
import com.rumpup.demo.repositories.ProductOfferingRepository;

@Service
public class ProductOfferingService {
	
	@Autowired
	private ProductOfferingRepository repository;

	public List<ProductOffering> findAll() {
		return repository.findAll();
	}
	public ProductOffering findById(Integer id) {
		Optional<ProductOffering> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(obj, "Objeto não encontrado"));
	}
}
