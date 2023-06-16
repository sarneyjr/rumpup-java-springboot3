package com.rumpup.demo.services;

import java.util.Collections;
import java.util.Optional;

import javax.management.relation.RoleNotFoundException;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rumpup.demo.dto.UserDTO;
import com.rumpup.demo.entities.Role;
import com.rumpup.demo.entities.User;
import com.rumpup.demo.entities.enums.Authorities;
import com.rumpup.demo.repositories.RoleRepository;
import com.rumpup.demo.repositories.UserRepository;
import com.rumpup.demo.services.exceptions.DatabaseException;
import com.rumpup.demo.services.exceptions.EmailAlreadyExistsException;
import com.rumpup.demo.services.exceptions.ObjectCreationException;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	//Para a criptografia da senha
	
	public static BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	
	// Regras de negócio

	public Iterable<User> findAll() {
		return repository.findAll();
	}

	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(id,"User not found"));
	}

	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(e, "Object not found");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getLocalizedMessage());
		}
	}

	public User update(User updatedUser) {
		try {
			Optional<User> optionalUser = repository.findById(updatedUser.getId());

			if (optionalUser.isPresent()) {
				User existingUser = optionalUser.get();
				updateData(existingUser, updatedUser);
				return repository.save(existingUser);
			} else {
				throw new DatabaseException("Object not found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getLocalizedMessage());
		}
	}

	public void updateData(User existingUser, User updatedUser) {
		// Verificando cada campo do objeto updatedUser e atualize apenas os campos
		// relevantes em existingUser

		if (updatedUser.getEmail() != null) {
			existingUser.setEmail(updatedUser.getEmail());
		}

		if (updatedUser.getPassword() != null) {
			existingUser.setPassword(passwordEncoder().encode(updatedUser.getPassword()));
			existingUser.setPassword(updatedUser.getPassword());
		}
	}

	public User fromDto(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getEmail(), objDto.getPassword());
	} 

	// Security

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    User user = repository.findByEmail(email);
	    if (user == null) {
	        throw new UsernameNotFoundException("Login Not Found");
	    }

	    return org.springframework.security.core.userdetails.User.builder()
	            .username(user.getEmail())
	            .password(user.getPassword())
	            .roles(user.getRolesinString()) 
	            .build();
	}

	public User createUserWithOperatorRole(UserDTO userDto) {
	    try {
	        User existingUser = repository.findByEmail(userDto.getEmail());
	        if (existingUser != null) {
	            throw new EmailAlreadyExistsException("A user with the same email already exists.");
	        }
	        
	        User user = new User();
	        user.setEmail(userDto.getEmail());
	        user.setPassword(passwordEncoder().encode(userDto.getPassword()));

	        Role operatorRole = roleRepository.findByAuthority(Authorities.OPERATOR);

	        if (operatorRole == null) {
	            throw new RoleNotFoundException("An error occurred while creating the object with role 'OPERATOR'");
	        }

	        user.setRoles(Collections.singleton(operatorRole));

	        return repository.save(user);
	    } catch (Exception e) {
	        throw new ObjectCreationException(e.getLocalizedMessage(), e);
	    }
	}

	public User createUserWithAdminRole(UserDTO userDto) {
	    try {
	        User existingUser = repository.findByEmail(userDto.getEmail());
	        if (existingUser != null) {
	            throw new EmailAlreadyExistsException("A user with the same email already exists.");
	        }
	        
	        User user = new User();
	        user.setEmail(userDto.getEmail());
	        user.setPassword(passwordEncoder().encode(userDto.getPassword()));
	        Role adminRole = roleRepository.findByAuthority(Authorities.ADMIN);

	        if (adminRole == null) {
	            throw new RoleNotFoundException("An error occurred while creating the object with role 'OPERATOR'");
	        }

	        user.setRoles(Collections.singleton(adminRole));
	        return repository.save(user);
	    } catch (Exception e) {
	        throw new ObjectCreationException(e.getLocalizedMessage(), e);
	    }
	}


}
