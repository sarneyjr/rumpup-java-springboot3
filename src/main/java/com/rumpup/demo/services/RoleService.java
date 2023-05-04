package com.rumpup.demo.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rumpup.demo.entities.Role;
import com.rumpup.demo.repositories.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository repository;

	public List<Role> findAll() {
		return repository.findAll();
	}
	public Role findById(Integer id) {
		Optional<Role> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(obj, "Objeto não encontrado"));
	}
}
