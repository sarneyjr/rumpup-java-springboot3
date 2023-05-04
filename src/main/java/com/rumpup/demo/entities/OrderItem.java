package com.rumpup.demo.entities;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rumpup.demo.entities.pk.OrderItemPK;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_order_item")
public class OrderItem implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private OrderItemPK id = new OrderItemPK();
	private Double discount;
	private Integer quantity;
	private Double totalPrice;
	
	public OrderItem() {
	}

	public OrderItem(Order order, ProductOffering productOffering ,Double discount, Integer quantity) {
		super();
		id.setOrder(order);
		id.setProductOffering(productOffering);
		this.discount = discount;
		this.quantity = quantity;
	}
	
	public Double getTotalPrice() {
		totalPrice =  getProductOffering().getUnitPrice()*quantity*discount;
		return totalPrice;
	}

	/*public Double getTotalPrice() {
		double sum = 0.0;
		for (OrderItem x : order.getItems()) {
			sum+= x.getSubTotalPrice();
		}
		return sum;
	}*/
	
	@JsonIgnore
	public Order getOrder() {
		return id.getOrder();
	}
	
	public void setOrder(Order order) {
		id.setOrder(order);
	}
	
	public ProductOffering getProductOffering() {
		return id.getProductOffering();
	}
	
	public void setProductOffering(ProductOffering productOffering) {
		id.setProductOffering(productOffering);
	}
	
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
		OrderItem other = (OrderItem) obj;
		return Objects.equals(id, other.id);
	}
}
