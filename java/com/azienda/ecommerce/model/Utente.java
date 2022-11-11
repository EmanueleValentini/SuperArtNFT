package com.azienda.ecommerce.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nomeUtente;
	
	private String email;
	
	private String password;
	@ManyToOne
	@JoinColumn(name="PK_ruolo")
	private Ruolo ruolo;
	
	@OneToMany (mappedBy ="utente")
	private List<Ordine> ordini = new ArrayList<>();
	
	@OneToOne (mappedBy ="utente")
	private Carrello carrello;
	
	private Boolean rimosso;

	public Utente() {
		super();
	}

	public Utente(String nomeUtente, String email, String password, Boolean rimosso) {
		super();
		this.nomeUtente = nomeUtente;
		this.setEmail(email);
		this.password = password;
		this.rimosso = rimosso;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public List<Ordine> getOrdini() {
		return ordini;
	}

	public void setOrdini(List<Ordine> ordini) {
		this.ordini = ordini;
	}

	public Carrello getCarrello() {
		return carrello;
	}

	public void setCarrello(Carrello carrello) {
		this.carrello = carrello;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", nomeUtente=" + nomeUtente + ", email=" + email + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		return Objects.equals(id, other.id);
	}

	public Boolean getRimosso() {
		return rimosso;
	}

	public void setRimosso(Boolean rimosso) {
		this.rimosso = rimosso;
	}

	
	
}
