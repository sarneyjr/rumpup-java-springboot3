package com.rumpup.demo.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumpup.demo.entities.Customer;
import com.rumpup.demo.repositories.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	public List<Customer> findAll() {
		return repository.findAll();
	}
	public Customer findById(Integer id) {
		Optional<Customer> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(obj, "Objeto não encontrado"));
	}
}
