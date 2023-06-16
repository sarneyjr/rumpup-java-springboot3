package com.rumpup.demo.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpup.demo.entities.Address;
import com.rumpup.demo.entities.Customer;
import com.rumpup.demo.entities.Order;
import com.rumpup.demo.entities.User;
import com.rumpup.demo.entities.enums.CustomerType;

public class CustomerDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer userId;
	private String 	customerName;
	private Integer documentNumber;
	private String 	customerStatus;
	private String 	creditScore;
	@JsonIgnore
	private String	password;

	private Set<Address> address = new HashSet<>();
	private Set<Order> orders = new HashSet<>();
	
	private User user;
	
	private CustomerType customerType;
	
	//constructors
	public CustomerDTO() {
	}

	public CustomerDTO(Customer obj) {
		id = obj.getId();
		customerName = obj.getCustomerName();
		documentNumber = obj.getDocumentNumber();
		customerStatus = obj.getCustomerStatus();
		creditScore = obj.getCreditScore();
		password = obj.getPassword();
		user = obj.getUser();
		userId = user.getId();
	}
	
	public CustomerDTO(Customer obj, Set<Address> address, Set<Order> orders) {
		id = obj.getId();
		customerName = obj.getCustomerName();
		documentNumber = obj.getDocumentNumber();
		customerStatus = obj.getCustomerStatus();
		creditScore = obj.getCreditScore();
		password = obj.getPassword();
		this.address = address;
		this.orders = orders;
	}
	
	//getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(Integer documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}

	public String getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(String creditScore) {
		this.creditScore = creditScore;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	//hashcode and equals
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerDTO other = (CustomerDTO) obj;
		return Objects.equals(id, other.id);
	}
}
