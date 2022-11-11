package com.azienda.ecommerce.model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Prodotto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nomeProdotto;
	private Double prezzo;
	private Integer quantita;
	private Boolean disponibile;
	private Blob foto ;
	private String descrizione;
	
	@ManyToMany(mappedBy = "prodotti")
	private List<Carrello> carrelli=new ArrayList<>();
	@OneToMany(mappedBy = "prodotto")
	private List<Ordine> ordini = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="id_categoria")
	private Categoria categoria;
	
	public Prodotto(Categoria categoria, String nomeProdotto, String descrizione, Blob foto, Double prezzo, Integer quantita, Boolean disponibile) {
		super();
		this.categoria = categoria;
		this.descrizione = descrizione;
		this.quantita = quantita;
		this.nomeProdotto = nomeProdotto;
		this.prezzo = prezzo;
		this.foto=foto;
		this.setDisponibile(disponibile);
		
	}

	

	public Categoria getCategoria() {
		return categoria;
	}



	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}



	public Blob getBlob() {
		return foto;
	}



	public void setBlob(Blob blob) {
		this.foto = blob;
	}



	public Prodotto() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeProdotto() {
		return nomeProdotto;
	}

	public void setNomeProdotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}


	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public List<Carrello> getCarrelli() {
		return carrelli;
	}

	public void setCarrelli(List<Carrello> carrelli) {
		this.carrelli = carrelli;
	}

	@Override
	public String toString() {
		return "Prodotto [id=" + id + ", nomeProdotto=" + nomeProdotto + ", prezzo=" + prezzo + "]";
	}

	public List<Ordine> getOrdini() {
		return ordini;
	}

	public void setOrdini(List<Ordine> ordini) {
		this.ordini = ordini;
	}

	public Integer getQuantita() {
		return quantita;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public Boolean getDisponibile() {
		return disponibile;
	}

	public void setDisponibile(Boolean disponibile) {
		this.disponibile = disponibile;
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
		Prodotto other = (Prodotto) obj;
		return Objects.equals(id, other.id);
	}



	public String getDescrizione() {
		return descrizione;
	}



	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
	
}
