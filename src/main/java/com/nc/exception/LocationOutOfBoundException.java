package com.nc.exception;

/**
 * The class {@code LocationOutOfBoundException} and its subclasses are a form of
 * {@code Exception} that indicates conditions that a reasonable application
 * might want to catch.
 * 
 * @author Surajit
 *
 */
public class LocationOutOfBoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public LocationOutOfBoundException() {
		super();
	}

	public LocationOutOfBoundException(String message) {
		super(message);
	}

	
}
