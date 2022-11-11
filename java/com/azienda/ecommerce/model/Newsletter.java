package com.azienda.ecommerce.model;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Newsletter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String email;
	private String codiceSegreto;
	
	public Newsletter() {
		// TODO Auto-generated constructor stub
	}

	public Newsletter(String email) {
		super(); //http://ecommerce/disiscriviti?codice=codiceSegreto
		this.setEmail(email);

	   codiceSegreto = UUID.randomUUID().toString().substring(0, 7);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodiceSegreto() {
		return codiceSegreto;
	}

	public void setCodiceSegreto(String codiceSegreto) {
		this.codiceSegreto = codiceSegreto;
	}
	
	
	
	
}
