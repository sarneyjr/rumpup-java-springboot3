package com.rumpup.demo.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.rumpup.demo.entities.Customer;
import com.rumpup.demo.entities.Role;
import com.rumpup.demo.entities.User;

public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String email;
	private String password;
	
	private Customer customer;
	
	private Set<Role> roles = new HashSet<>();

	public UserDTO() {
	}

	public UserDTO(User obj) {
		id = obj.getId();
		email = obj.getEmail();
		password = obj.getPassword();
	}

	public UserDTO(User obj, Customer customer, Set<Role> roles) {
		id = obj.getId();
		email = obj.getEmail();
		password = obj.getPassword();
		this.customer = customer;
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
