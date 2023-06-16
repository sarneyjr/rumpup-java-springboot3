package com.rumpup.demo.resources;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rumpup.demo.dto.UserDTO;
import com.rumpup.demo.entities.User;
import com.rumpup.demo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/signup")
	public ResponseEntity<UserDTO> signUp(@RequestBody UserDTO userDto) {
	    User user = service.createUserWithOperatorRole(userDto); // Lógica para criar usuário com a função "OPERATOR"
	    UserDTO createdUserDto = new UserDTO(user);
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
	    return ResponseEntity.created(uri).body(createdUserDto);
	}

	@PostMapping("/users")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<UserDTO> createUserByAdmin(@RequestBody UserDTO userDto) {
	    User user = service.createUserWithAdminRole(userDto); // Lógica para criar usuário com a função "ADMIN"
	    UserDTO createdUserDto = new UserDTO(user);
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
	    return ResponseEntity.created(uri).body(createdUserDto);
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Iterable<UserDTO>> findAll() {
	    Iterable<User> list = service.findAll();
	    Stream<User> stream = StreamSupport.stream(list.spliterator(), true);
	    Iterable<UserDTO> listDto = stream.map(x -> new UserDTO(x)).collect(Collectors.toList());
	    return ResponseEntity.ok(listDto);
	}
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("authentication.principal =="
			+ " @userRepository.findById(#id).get().getEmail()") // Apenas usuários com as roles "ADMIN" ou "Operator" podem acessar este endpoint
	public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj, obj.getCustomer(), obj.getRoles()));
	}

	@PatchMapping(value = "/{id}")
	@PreAuthorize("authentication.principal =="
	        + " @userRepository.findById(#id).get().getEmail()")
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO objDto, @PathVariable Integer id) {
	    User obj = service.fromDto(objDto);
	    obj.setId(id);
	    obj = service.update(obj);
	    UserDTO a = new UserDTO(obj);
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getId()).toUri();
	    return ResponseEntity.created(uri).body(a);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("authentication.principal =="
			+ " @userRepository.findById(#id).get().getEmail()")// Apenas usuários com a role "ADMIN" podem acessar este endpoint
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	/*
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')") // Apenas usuários com a role "ADMIN" podem acessar este endpoint
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO objDto) {
		User obj = service.fromDto(objDto);
		obj = service.insert(obj);
		UserDTO a = new UserDTO(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(a);
	}
	 */
}
