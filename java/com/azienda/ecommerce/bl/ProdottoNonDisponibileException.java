package com.azienda.ecommerce.bl;

public class ProdottoNonDisponibileException extends Exception {
	
	public ProdottoNonDisponibileException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ProdottoNonDisponibileException(String message) {
		super(message);
	}
}
