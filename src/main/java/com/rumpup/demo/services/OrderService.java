package com.rumpup.demo.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumpup.demo.entities.Order;
import com.rumpup.demo.repositories.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;

	public List<Order> findAll() {
		return repository.findAll();
	}
	public Order findById(Integer id) {
		Optional<Order> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(obj, "Objeto não encontrado"));
	}
}
