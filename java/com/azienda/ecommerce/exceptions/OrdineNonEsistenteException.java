package com.azienda.ecommerce.exceptions;

public class OrdineNonEsistenteException extends Exception{
	public OrdineNonEsistenteException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public OrdineNonEsistenteException(String message) {
		super(message);
		
	}
}
