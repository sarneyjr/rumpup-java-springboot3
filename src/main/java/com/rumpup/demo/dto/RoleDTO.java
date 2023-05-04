package com.rumpup.demo.dto;

import java.io.Serializable;

import com.rumpup.demo.entities.Role;
import com.rumpup.demo.entities.enums.Authorities;


public class RoleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer 	id;
	private Authorities authority;
	
	public RoleDTO() {
	}
	
	public RoleDTO(Role obj) {
		id = obj.getId();
		authority = obj.getAuthority();
	}
	
	//getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Authorities getAuthority() {
		return authority;
	}
	
	public void setAuthority(Authorities authority) {
		this.authority = authority;
	}
}