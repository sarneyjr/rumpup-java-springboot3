package com.rumpup.demo.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

//Serializable is an interface used to object being transfered at network, or to be record in files..
@Entity
@Table(name = "tb_user")
@SQLDelete(sql = "UPDATE tb_user SET deleted = 1 WHERE id=?")
//@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
//@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
@Where(clause = "deleted=false")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 
	
	 @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b",
	            message = "Invalid Email")
	private String 	email;
	
	@NonNull
	private String password;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private boolean deleted = Boolean.FALSE; // Nova propriedade para indicar se o registro está excluído
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(Integer id, String email, String password) {

		this.id = 		id;
		this.email = 	email;
		this.password = password;
	}

	// getters and setters

	// FOR SECURITY (CHANGE THE AUTHORITIES OF AN USER TO A STRING LIST)
	@JsonIgnore
	public String[] getRolesinString() {

		String[] rolesId = new String[roles.size()];

		Integer index = 0;

		for (Role i : roles) {
			rolesId[index++] = i.getAuthorityinString();
		}

		return rolesId;
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

	public void addRole(Role role) {
		roles.add(role);
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
}
