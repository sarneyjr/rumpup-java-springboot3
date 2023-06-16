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

import com.rumpup.demo.dto.OrderDTO;
import com.rumpup.demo.entities.Order;
import com.rumpup.demo.services.OrderService;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {
	
	@Autowired
	private OrderService service;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Iterable<OrderDTO>> findAll() {
	    Iterable<Order> list = service.findAll();
	    Stream<Order> stream = StreamSupport.stream(list.spliterator(), true);
	    Iterable<OrderDTO> listDto = stream.map(x -> new OrderDTO(x)).collect(Collectors.toList());
	    return ResponseEntity.ok(listDto);
	}
	
	@GetMapping(value = "/{id}")
	@PreAuthorize("hasRole('1') || authentication.principal == @orderRepository.findById(#id).get().getCustomer().getUser().getEmail()")	
	public ResponseEntity<OrderDTO> findById(@PathVariable Integer id) {
		Order obj = service.findById(id);
		return ResponseEntity.ok().body(new OrderDTO(obj, obj.getCustomer(), obj.getAddress(), obj.getItems()));
	}
	
	@PostMapping
	@PreAuthorize("hasRole('OPERATOR')")
	public ResponseEntity<OrderDTO> insert(@RequestBody OrderDTO objDto) {
	    Integer customerId = objDto.getCustomerId();
	    Integer addressId = objDto.getAddressId();
	    Integer productId = objDto.getProductId();
	    Double discount = objDto.getDiscount(); // Retrieve the discount from the OrderDTO
	    Integer quantity = objDto.getQuantity(); // Retrieve the quantity from the OrderDTO

	    Order obj = service.fromDto(objDto);
	    obj = service.insert(obj, customerId, addressId, productId, discount, quantity); // Pass the discount and quantity to the insert method

	    OrderDTO orderDto = new OrderDTO(obj);
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	    return ResponseEntity.created(uri).body(orderDto);
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasRole('1') || authentication.principal == @orderRepository.findById(#id).get().getCustomer().getUser().getEmail()")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping(value = "/{id}")
	@PreAuthorize("hasRole('1') || authentication.principal == @orderRepository.findById(#id).get().getCustomer().getUser().getEmail()")
	public ResponseEntity<OrderDTO> update(@RequestBody OrderDTO objDto, @PathVariable Integer id) {
	    Order obj = service.fromDto(objDto);
	    obj.setId(id);
	    obj = service.update(obj);
	    OrderDTO a = new OrderDTO(obj);
	    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(a.getId()).toUri();
	    return ResponseEntity.created(uri).body(a);
	}
}
