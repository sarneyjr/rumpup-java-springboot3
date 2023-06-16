package com.rumpup.demo.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpup.demo.entities.Address;
import com.rumpup.demo.entities.Customer;
import com.rumpup.demo.entities.Order;
import com.rumpup.demo.entities.OrderItem;
import com.rumpup.demo.entities.ProductOffering;

public class OrderDTO implements Serializable {
	private static final long serialVersionUID = 1L;


	private Integer id;
	private Integer customerId;
	private Integer productId;
	private Integer addressId;
	
	private Instant instant;
	
	@JsonIgnore
	private Customer customer;
	private Address address;
	
	private Double discount;
	private Integer quantity;
	
	
	private Set<OrderItem> items = new HashSet<>();
	
	
	
	public OrderDTO() {
	}
 
	public OrderDTO(Order obj) {
		id = obj.getId();
		instant = obj.getInstant();
		customer = obj.getCustomer();
		address = obj.getAddress();
		items = obj.getItems();
		
	
		if (customer != null) {
			customerId = customer.getId();
		}
		
		if (address != null) {
		    addressId = address.getId();
		}	
		
		if (obj.getItems() != null && !obj.getItems().isEmpty()) {
			OrderItem orderItem = obj.getItems().iterator().next();
			ProductOffering productOffering = orderItem.getProductOffering();
			if (productOffering != null) {
				productId = productOffering.getId();
			}
			discount = orderItem.getDiscount(); 
	        quantity = orderItem.getQuantity(); 
		}	
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
	@JsonIgnore
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
	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
