package com.azienda.ecommerce.exceptions;

public class ProdottoEsistenteException extends Exception{
	
	
	public ProdottoEsistenteException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ProdottoEsistenteException(String message) {
		super(message);
	}
	
}