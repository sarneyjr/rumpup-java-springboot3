package com.rumpup.demo.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpup.demo.entities.enums.CustomerType;

@Entity
@Table(name = "tb_customer", uniqueConstraints = @UniqueConstraint(columnNames = {"documentNumber"}))
@SQLDelete(sql = "UPDATE tb_customer SET deleted = 1 WHERE id=?")
//@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
//@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
@Where(clause = "deleted=false")
public class Customer implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private String 	customerName;

	@Column(nullable = false)
	private Integer documentNumber;
	
	private String 	customerStatus;
	private String 	creditScore;
	
	@JsonIgnore
	private String	password;
	
	private CustomerType customerType;
	
	@JsonIgnore
	@OneToOne
	private User user;
	
	private boolean deleted = Boolean.FALSE; // Nova propriedade para indicar se o registro está excluído
	
	@JsonIgnore
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private Set<Address> address = new HashSet<>();

	@JsonIgnore
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private Set<Order> orders = new HashSet<>();
	
	//constructors
	public Customer() {
	}

	public Customer(Integer id, String customerName, Integer documentNumber, String customerStatus, String creditScore,
			String password, CustomerType customerType, User user) {

		this.id = 				id;
		this.customerName = 	customerName;
		this.documentNumber = 	documentNumber;
		this.customerStatus = 	customerStatus;
		this.creditScore = 		creditScore;
		this.password = 		password;
		this.customerType = 	customerType;
		this.user = user;
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
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public void setAddress(Address address2) {
	    if (address2 != null) {
	        this.address.add(address2);
	    }
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public boolean getDeleted() {
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
		Customer other = (Customer) obj;
		return Objects.equals(id, other.id);
	}
}
