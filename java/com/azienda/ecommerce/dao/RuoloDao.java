package com.azienda.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.azienda.ecommerce.model.Ruolo;
import com.azienda.ecommerce.model.Utente;

public class RuoloDao implements DaoInterface<Ruolo> {
	
	private EntityManager em;
	

	public RuoloDao(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Ruolo create(Ruolo t) {
		em.persist(t);
		return t;
	}

	@Override
	public List<Ruolo> retrieve() {
		
		return em.createQuery("select r from Ruolo r",Ruolo.class).getResultList();
	}

	@Override
	public Ruolo update(Ruolo t) {
		
		return em.merge(t);
	}

	@Override
	public void delete(Ruolo t) {
		em.remove(t);
	}
	
	public List<Utente> elencoUtenti(){
		
		return em.createQuery("select u from Utente u where u.ruolo= :parRuolo",Utente.class)
				.setParameter("parRuolo", "Utente").getResultList();
	}
	public List<Ruolo> findRuoloByNomeRuolo(String nomeRuolo){
		return em.createQuery("select r from Ruolo r where r.nomeRuolo = :parNomeRuolo",Ruolo.class)
				.setParameter("parNomeRuolo", nomeRuolo).getResultList();
		
	}

}
