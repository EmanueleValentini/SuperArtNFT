package com.azienda.ecommerce.exceptions;

public class UtenteEsistenteException extends Exception{
	
	
	public UtenteEsistenteException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UtenteEsistenteException(String message) {
		super(message);
	}
	
}
