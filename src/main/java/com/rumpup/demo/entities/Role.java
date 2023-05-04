package com.rumpup.demo.entities;

import java.io.Serializable;
import java.util.Objects;

import com.rumpup.demo.entities.enums.Authorities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_roles")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer 	id;
	private Authorities authority;

	
	public Role() {
	}
	
	public Role(Integer id, Authorities authority) {
		this.id = id;
		this.authority = authority;
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
		Role other = (Role) obj;
		return Objects.equals(id, other.id);
	}
}