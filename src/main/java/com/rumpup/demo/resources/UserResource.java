package com.rumpup.demo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
	    List<User> list = service.findAll();
	    List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
	    return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj, obj.getCustomer(), obj.getRoles()));
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO objDto) {
		User obj = service.fromDto(objDto);
		obj = service.insert(obj);
		UserDTO a = new UserDTO(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(a);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO objDto, @PathVariable Integer id){
		User obj = service.fromDto(objDto);
		obj.setId(id);
		obj = service.update( obj);
		UserDTO a = new UserDTO(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getId()).toUri();
		return ResponseEntity.created(uri).body(a);
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
