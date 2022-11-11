package com.azienda.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.azienda.ecommerce.model.Ordine;
import com.azienda.ecommerce.model.Utente;

public class UtenteDao implements DaoInterface<Utente>{

	private EntityManager em;

	public UtenteDao(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Utente create(Utente t) {
		em.persist(t);
		return t;
	}

	@Override
	public List<Utente> retrieve() {

		return em.createQuery("select u from Utente u",Utente.class).getResultList();
	}

	@Override
	public Utente update(Utente t) {

		return em.merge(t);
	}

	@Override
	public void delete(Utente t) {
		em.remove(t);

	}

	public List<Utente> ricercaPerNomeUtente(String nomeUtente) {

		return em.createQuery("select u from Utente u where u.nomeUtente= :parNome",Utente.class)
				.setParameter("parNome", nomeUtente).getResultList();
	}

	public List<Utente> ricercaPerEmail(String email) {

		return em.createQuery("select u from Utente u where u.email= :parEmail",Utente.class)
				.setParameter("parEmail", email).getResultList();
	}

	public List<Ordine> ordiniUtente(Utente utente){

		return em.createQuery("select o from Ordine o where o.utente= :parUtente",Ordine.class)
				.setParameter("parUtente", utente).getResultList();

	}

	public List<Utente> listaUtenti(){

		return em.createQuery("select u from Utente u",Utente.class)
				.getResultList();
	}
	public List<Utente> ricercaPerNomeUtenteAndEmail(String nomeUtente,String email) {

		return em.createQuery("select u from Utente u where u.nomeUtente= :parNome or u.email= :parEmail",Utente.class)
				.setParameter("parNome", nomeUtente).setParameter("parEmail", email).getResultList();
	}

}
