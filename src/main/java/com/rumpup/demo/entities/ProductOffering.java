package com.rumpup.demo.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpup.demo.entities.enums.POState;

@Entity
@Table(name = "tb_product", uniqueConstraints = @UniqueConstraint(columnNames = {"productName"}))
@SQLDelete(sql = "UPDATE tb_product SET deleted = 1 WHERE id=?")
//@FilterDef(name = "deletedUserFilter", parameters = @ParamDef(name = "isDeleted", type = "boolean"))
//@Filter(name = "deletedProductFilter", condition = "deleted = :isDeleted")
@Where(clause = "deleted=false")
public class ProductOffering implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String 	productName;
	
	@NotNull
	private Double 	unitPrice;
	private Boolean sellIndicator;
	private POState state;
	
	@JsonIgnore
	@OneToMany(mappedBy = "id.productOffering")
	private Set<OrderItem> items = new HashSet<>();
	
	private boolean deleted = Boolean.FALSE; // Nova propriedade para indicar se o registro está excluído
	
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
	
	

	public Set<OrderItem> getItems() {
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
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
		ProductOffering other = (ProductOffering) obj;
		return Objects.equals(id, other.id);
	}
}
