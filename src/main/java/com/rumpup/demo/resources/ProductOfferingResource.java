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

import com.rumpup.demo.dto.ProductOfferingDTO;
import com.rumpup.demo.entities.ProductOffering;
import com.rumpup.demo.services.ProductOfferingService;

@RestController
@RequestMapping(value = "/product")
public class ProductOfferingResource {
	
	@Autowired
	private ProductOfferingService service;


	@GetMapping
	@PreAuthorize("hasAnyRole('OPERATOR', 'ADMIN')")
	public ResponseEntity<Iterable<ProductOfferingDTO>> findAll() {
	    Iterable<ProductOffering> list = service.findAll();
	    Stream<ProductOffering> stream = StreamSupport.stream(list.spliterator(), true);
	    Iterable<ProductOfferingDTO> listDto = stream.map(x -> new ProductOfferingDTO(x)).collect(Collectors.toList());
	    return ResponseEntity.ok(listDto);
	}
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")	
	public ResponseEntity<ProductOfferingDTO> findById(@PathVariable Integer id) {
		ProductOffering obj = service.findById(id);
		return ResponseEntity.ok().body(new ProductOfferingDTO(obj));
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductOfferingDTO> insert(@RequestBody ProductOfferingDTO objDto) {
		ProductOffering obj = service.fromDto(objDto);
		obj = service.insert(obj);
		ProductOfferingDTO a = new ProductOfferingDTO(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(a);}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	} 
	 
	@PatchMapping(value = "/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductOfferingDTO> update(@RequestBody ProductOfferingDTO objDto, @PathVariable Integer id) {
	    ProductOffering obj = service.fromDto(objDto);
	    obj.setId(id);
	    obj = service.update(obj);
	    ProductOfferingDTO a = new ProductOfferingDTO(obj);
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getId()).toUri();
	    return ResponseEntity.created(uri).body(a);
	}
}
