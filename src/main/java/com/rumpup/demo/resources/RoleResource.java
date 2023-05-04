package com.rumpup.demo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpup.demo.entities.Role;
import com.rumpup.demo.services.RoleService;

@RestController
@RequestMapping(value = "/roles")
public class RoleResource {
	
	@Autowired
	private RoleService service;

	@GetMapping
	public ResponseEntity<List<Role>> findAll() {
		List<Role> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Role> findById(@PathVariable Integer id) {
		Role obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
}
