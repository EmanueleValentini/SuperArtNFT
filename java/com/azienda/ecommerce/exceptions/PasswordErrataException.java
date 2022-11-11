package com.azienda.ecommerce.exceptions;

public class PasswordErrataException extends Exception{
	
	
	public PasswordErrataException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public PasswordErrataException(String message) {
		super(message);
	}
	
}
