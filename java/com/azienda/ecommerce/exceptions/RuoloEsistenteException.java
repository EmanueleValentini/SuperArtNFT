package com.azienda.ecommerce.exceptions;

public class RuoloEsistenteException extends Exception{
	
	
	public RuoloEsistenteException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RuoloEsistenteException(String message) {
		super(message);
	}
	
}
