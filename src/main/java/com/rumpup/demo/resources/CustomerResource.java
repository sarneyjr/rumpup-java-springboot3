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

import com.rumpup.demo.dto.AddressDTO;
import com.rumpup.demo.dto.CustomerDTO;
import com.rumpup.demo.entities.Address;
import com.rumpup.demo.entities.Customer;
import com.rumpup.demo.services.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {
	
	@Autowired
	private CustomerService service;

	@GetMapping 
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Iterable<CustomerDTO>> findAll() {
	    Iterable<Customer> list = service.findAll();
	    Stream<Customer> stream = StreamSupport.stream(list.spliterator(), true);
	    Iterable<CustomerDTO> listDto = stream.map(x -> new CustomerDTO(x)).collect(Collectors.toList());
	    return ResponseEntity.ok(listDto);
	}
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('1') || authentication.principal == @customerRepository.findById(#id).get().getUser().getEmail()")	
	public ResponseEntity<CustomerDTO> findById(@PathVariable Integer id) {
		Customer obj = service.findById(id);
		return ResponseEntity.ok().body(new CustomerDTO(obj, obj.getAddress(), obj.getOrders()));
	}

	@PostMapping
	@PreAuthorize("hasRole('OPERATOR')")	
	public ResponseEntity<CustomerDTO> insert(@RequestBody CustomerDTO objDto) {
	    Integer userId = objDto.getUserId();
		Customer obj = service.fromDto(objDto);
	    obj = service.insert(obj, userId ); // O ID será gerado automaticamente pelo banco de dados
	    CustomerDTO a = new CustomerDTO(obj);
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	    return ResponseEntity.created(uri).body(a);
	}

	
	@PostMapping("/{customerId}/addresses")
	@PreAuthorize("hasRole('1') || authentication.principal == @customerRepository.findById(#customerId).get().getUser().getEmail()")
	public ResponseEntity<CustomerDTO> insertAddress(@PathVariable Integer customerId, @RequestBody AddressDTO addressDto) {
	    Customer customer = service.findById(customerId);
	    
	    // Crie um objeto Address com base nos dados do AddressDTO
	    Address address = new Address();
	    address.setStreet(addressDto.getStreet());
	    address.setHouseNumber(addressDto.getHouseNumber());
	    address.setCountry(addressDto.getCountry());
	    address.setZipCode(addressDto.getZipCode());
	    address.setNeigborhood(addressDto.getNeighborhood());
	    
	    // Adicione o endereço ao cliente
	    customer.getAddress().add(address);
	    address.setCustomer(customer);
	    
	    // Atualize o cliente no banco de dados
	    Customer updatedCustomer = service.update(customer);
	    
	    // Crie a resposta com o DTO atualizado do cliente
	    CustomerDTO responseDto = new CustomerDTO(updatedCustomer, updatedCustomer.getAddress(), updatedCustomer.getOrders());
	    
	    return ResponseEntity.ok(responseDto);
	}
	
	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('1') || authentication.principal == @customerRepository.findById(#id).get().getUser().getEmail()")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping(value = "/{id}")
	@PreAuthorize("hasRole('1') || authentication.principal == @customerRepository.findById(#id).get().getUser().getEmail() ")
	public ResponseEntity<CustomerDTO> update(@RequestBody CustomerDTO objDto, @PathVariable Integer id) {
	    Customer obj = service.fromDto(objDto);
	    obj.setId(id);
	    obj = service.update(obj);
	    CustomerDTO a = new CustomerDTO(obj);
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getId()).toUri();
	    return ResponseEntity.created(uri).body(a);
	}
}
