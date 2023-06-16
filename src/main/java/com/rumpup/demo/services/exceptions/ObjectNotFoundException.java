package com.rumpup.demo.services.exceptions;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException( Object Id) {
		super("Objeto não encontrado");
	}

}
