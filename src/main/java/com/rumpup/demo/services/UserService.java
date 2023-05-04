package com.rumpup.demo.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rumpup.demo.dto.UserDTO;
import com.rumpup.demo.entities.User;
import com.rumpup.demo.repositories.UserRepository;
import com.rumpup.demo.services.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}
	
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(obj, "Objeto não encontrado"));
	}
	
	public User insert(User obj) {
		return repository.save(obj);
	}
	
	public void updateData(User newObj, User obj) {
		newObj.setCustomer(obj.getCustomer());
	}
	
	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {	
			throw new ObjectNotFoundException(id, "Objeto não encontrado");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public User update(User obj) {
		try {
			User newObj = repository.findById(obj.getId()).get();
			updateData(newObj,obj);
			return repository.save(newObj);
		} catch (EntityNotFoundException e) {
			throw new ObjectNotFoundException(obj, "Objeto não encontrado");
		}
	}

	public User fromDto(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getEmail(), objDto.getPassword());
	}
}
