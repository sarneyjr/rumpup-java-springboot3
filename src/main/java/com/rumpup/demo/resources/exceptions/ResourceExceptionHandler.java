package com.rumpup.demo.resources.exceptions;

import java.util.NoSuchElementException;

import javax.management.relation.RoleNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rumpup.demo.services.exceptions.DatabaseException;
import com.rumpup.demo.services.exceptions.InsertObjectException;
import com.rumpup.demo.services.exceptions.ObjectCreationException;
import com.rumpup.demo.services.exceptions.UpdateObjectException;

@ControllerAdvice //indica que a classe é responsável por tratar possiveis erros nas requisicoes
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
	    String error = "Object Not Found";
	    HttpStatus status = HttpStatus.NOT_FOUND;
	    StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), request.getRequestURI());
	    return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
	    String error = "Database error";
	    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
	    StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), request.getRequestURI());
	    return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(InsertObjectException.class)
	public ResponseEntity<StandardError> insertObject(InsertObjectException e, HttpServletRequest request) {
	    String error = "An error occurred while inserting the object";
	    HttpStatus status = HttpStatus.BAD_REQUEST;
	    StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), request.getRequestURI());
	    return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(UpdateObjectException.class)
	public ResponseEntity<StandardError> updateObject(UpdateObjectException e, HttpServletRequest request) {
	    String error = "An error occurred while updating the user";
	    HttpStatus status = HttpStatus.BAD_REQUEST;
	    StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), request.getRequestURI());
	    return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(ObjectCreationException.class)
	public ResponseEntity<StandardError> objectCreation(ObjectCreationException e, HttpServletRequest request) {
	    String error = "An error occurred while creating the object with the used role";
	    HttpStatus status = HttpStatus.BAD_REQUEST;
	    StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), request.getRequestURI());
	    return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<StandardError> roleNotFound(RoleNotFoundException e, HttpServletRequest request) {
	    String error = "The role was not found in the database";
	    HttpStatus status = HttpStatus.NOT_FOUND;
	    StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), request.getRequestURI());
	    return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<StandardError> noSuchElement(NoSuchElementException e, HttpServletRequest request) {
	    String error = "Object Not Found";
	    HttpStatus status = HttpStatus.NOT_FOUND;
	    StandardError err = new StandardError(System.currentTimeMillis(), status.value(), error, e.getMessage(), request.getRequestURI());
	    return ResponseEntity.status(status).body(err);
	}
}