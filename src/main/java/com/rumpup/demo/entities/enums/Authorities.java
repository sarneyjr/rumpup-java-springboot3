package com.rumpup.demo.entities.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Authorities {
	
	@JsonProperty("ADMIN") ADMIN(1),
	@JsonProperty("OPERATOR") OPERATOR(2);
	
	private int code;
	
	private Authorities(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static Authorities valueOf(int code) {
		for (Authorities value : Authorities.values()) {
			if (value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid AddressType Code");
	}
}
