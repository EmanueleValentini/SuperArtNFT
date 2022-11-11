package com.azienda.ecommerce.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ordine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="PK_prodotto")
	private Prodotto prodotto;
	@ManyToOne
	@JoinColumn(name="PK_utente")
	private Utente utente;
	private LocalDate dataEffettuato;
	
	public Ordine() {
		super();
		
	}

	public Ordine(Prodotto prodotto, Utente utente, LocalDate dataEffettuato) {
		super();
		this.prodotto = prodotto;
		this.utente = utente;
		this.dataEffettuato = dataEffettuato;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Prodotto getProdotto() {
		return prodotto;
	}

	public void setProdotto(Prodotto prodotto) {
		this.prodotto = prodotto;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public LocalDate getDataEffettuato() {
		return dataEffettuato;
	}

	public void setData_effettuato(LocalDate dataEffettuato) {
		this.dataEffettuato = dataEffettuato;
	}

	@Override
	public String toString() {
		return "Ordine [id=" + id + ", data_effettuato=" + dataEffettuato + "]";
	}


	

}
