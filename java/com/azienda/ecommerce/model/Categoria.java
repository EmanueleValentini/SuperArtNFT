package com.azienda.ecommerce.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nomeCategoria;
	@OneToMany(mappedBy = "categoria")
	private List<Prodotto> p;
	private Boolean rimosso;
	
	public Categoria() {
		super();
	}
	
	
	public Categoria(String nomeCategoria) {
		super();
		this.nomeCategoria = nomeCategoria;
		setRimosso(false);
	}
	
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	public List<Prodotto> getP() {
		return p;
	}
	public void setP(List<Prodotto> p) {
		this.p = p;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Boolean getRimosso() {
		return rimosso;
	}


	public void setRimosso(Boolean rimosso) {
		this.rimosso = rimosso;
	}
	
	
	
}
