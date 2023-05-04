package com.rumpup.demo.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.rumpup.demo.entities.Address;
import com.rumpup.demo.entities.Customer;
import com.rumpup.demo.entities.Order;
import com.rumpup.demo.entities.OrderItem;

public class OrderDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;
	private Instant instant;
	
	private Customer customer;
	private Address address;
	private Set<OrderItem> items = new HashSet<>();
	
	
	public OrderDTO() {
	}

	public OrderDTO(Order obj) {
		id = obj.getId();
		instant = obj.getInstant();
	}

	public OrderDTO(Order obj, Customer customer, Address address, Set<OrderItem> items) {
		id = obj.getId();
		instant = obj.getInstant();
		this.customer = customer;
		this.address = address;
		this.items = items;
	}
	
	//Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Instant getInstant() {
		return instant;
	}

	public void setInstant(Instant instant) {
		this.instant = instant;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}
}
