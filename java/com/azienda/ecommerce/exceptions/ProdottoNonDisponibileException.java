package com.azienda.ecommerce.exceptions;

public class ProdottoNonDisponibileException extends Exception {
	
	public ProdottoNonDisponibileException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ProdottoNonDisponibileException(String message) {
		super(message);
	}
}
