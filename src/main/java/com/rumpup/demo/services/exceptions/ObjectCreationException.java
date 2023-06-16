package com.rumpup.demo.services.exceptions;

public class ObjectCreationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectCreationException(String msg , Throwable cause) {
		super(msg);
	}
}
