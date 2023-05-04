package com.rumpup.demo.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpup.demo.entities.enums.POState;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_productOffering")
public class ProductOffering implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String 	productName;
	private Double 	unitPrice;
	private Boolean sellIndicator;
	private POState state;
	
	@JsonIgnore
	@OneToMany(mappedBy = "id.productOffering")
	private Set<OrderItem> items = new HashSet<>();
	
	public ProductOffering() {
	}
	
	public ProductOffering(Integer id, String productName, Double unitPrice, Boolean sellIndicator, POState state) {
		this.id = id;
		this.productName = productName;
		this.unitPrice = unitPrice;
		this.sellIndicator = sellIndicator;
		this.state = state;
	}

	@JsonIgnore
	public Set<Order> getOrders(){
		Set<Order> set = new HashSet<>();
		for (OrderItem x : items) {
		set.add(x.getOrder());
		}
		return set;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Boolean getSellIndicator() {
		return sellIndicator;
	}

	public void setSellIndicator(Boolean sellIndicator) {
		this.sellIndicator = sellIndicator;
	}

	public POState getState() {
		return state;
	}

	public void setState(POState state) {
		this.state = state;
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
		ProductOffering other = (ProductOffering) obj;
		return Objects.equals(id, other.id);
	}
}
