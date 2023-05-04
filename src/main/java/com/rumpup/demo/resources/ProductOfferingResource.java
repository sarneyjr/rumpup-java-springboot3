		package com.rumpup.demo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rumpup.demo.dto.ProductOfferingDTO;
import com.rumpup.demo.entities.ProductOffering;
import com.rumpup.demo.services.ProductOfferingService;

@RestController
@RequestMapping(value = "/product")
public class ProductOfferingResource {
	
	@Autowired
	private ProductOfferingService service;

	@GetMapping
	public ResponseEntity<List<ProductOffering>> findAll() {
		List<ProductOffering> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductOfferingDTO> findById(@PathVariable Integer id) {
		ProductOffering obj = service.findById(id);	
		return ResponseEntity.ok().body(new ProductOfferingDTO(obj));
	}
}
