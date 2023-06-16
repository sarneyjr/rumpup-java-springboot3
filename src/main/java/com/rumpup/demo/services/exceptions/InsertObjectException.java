package com.rumpup.demo.services.exceptions;

public class InsertObjectException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InsertObjectException(String msg , Throwable cause) {
		super(msg);
	}
}
