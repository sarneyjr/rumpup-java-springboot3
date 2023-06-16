package com.rumpup.demo.services.exceptions;

public class UpdateObjectException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UpdateObjectException(String msg , Throwable cause) {
		super(msg);
	}
	
}
