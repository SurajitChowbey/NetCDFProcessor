package com.nc.exception;

/**
 * The class {@code VariablesNotFoundException} and its subclasses are a form of
 * {@code Exception} that indicates conditions that a reasonable application
 * might want to catch.
 * 
 * @author Surajit
 *
 */
public class VariablesNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public VariablesNotFoundException() {
		super();
	}

	public VariablesNotFoundException(String message) {
		super(message);
	}

}
