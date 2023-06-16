package com.rumpup.demo.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_address")
@SQLDelete(sql = "UPDATE tb_address SET deleted = 1 WHERE id=?")
//@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
//@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
@Where(clause = "deleted=false")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String 	street;
	private Integer houseNumber;
	private String 	neigborhood;
	
	@NotNull
	private Integer zipCode;
	@NotNull
	private String 	country;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "custumer_id")
	private Customer customer;
	
	private boolean deleted = Boolean.FALSE; // Nova propriedade para indicar se o registro está excluído
	
	public Address() {
	}
	
	public Address(Integer id, String street, Integer houseNumber, String neigborhood, Integer zipCode, String country,
			Customer customer) {

		this.id = id;
		this.street = street;
		this.houseNumber = houseNumber;
		this.neigborhood = neigborhood;
		this.zipCode = zipCode;
		this.country = country;
		this.customer = customer;
	}

	//getters and setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getNeigborhood() {
		return neigborhood;
	}

	public void setNeigborhood(String neigborhood) {
		this.neigborhood = neigborhood;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
		Address other = (Address) obj;
		return Objects.equals(id, other.id);
	}

}
