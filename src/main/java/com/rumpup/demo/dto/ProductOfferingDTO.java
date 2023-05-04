package com.rumpup.demo.dto;

import java.io.Serializable;

import com.rumpup.demo.entities.ProductOffering;
import com.rumpup.demo.entities.enums.POState;


public class ProductOfferingDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String 	productName;
	private Double 	unitPrice;
	private Boolean sellIndicator;
	private POState state;
	
	
	public ProductOfferingDTO(ProductOffering obj) {
		id = obj.getId();
		productName = obj.getProductName();
		unitPrice = obj.getUnitPrice();
		sellIndicator = obj.getSellIndicator();
		state = obj.getState();
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
}
