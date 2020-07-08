package com.nc.exception;

/**
 * The class {@code LatLonOrderNotFoundException} and its subclasses are a form of
 * {@code Exception} that indicates conditions that a reasonable application
 * might want to catch.
 * 
 * @author Surajit
 *
 */
public class LatLonOrderNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public LatLonOrderNotFoundException() {
		super();
	}

	public LatLonOrderNotFoundException(String message) {
		super(message);
	}

}
