package com.rumpup.demo.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumpup.demo.entities.Address;
import com.rumpup.demo.repositories.AddressRepository;

@Service
public class AddressService {
	
	@Autowired
	private AddressRepository repository;

	public List<Address> findAll() {
		return repository.findAll();
	}
	public Address findById(Integer id) {
		Optional<Address> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(obj, "Objeto não encontrado"));
	}
}
