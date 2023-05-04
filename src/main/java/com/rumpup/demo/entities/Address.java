package com.rumpup.demo.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_address")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String 	street;
	private Integer houseNumber;
	private String 	neigborhood;
	private Integer zipCode;
	private String 	country;
	
	@ManyToOne
	@JoinColumn(name = "custumer_id")
	private Customer customer;
	
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
