package com.azienda.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.azienda.ecommerce.model.Carrello;
import com.azienda.ecommerce.model.Utente;

public class CarrelloDao implements DaoInterface <Carrello>{
	private EntityManager em;

	public CarrelloDao(EntityManager em) {
		super();
		this.em = em;
	}



	@Override
	public Carrello create(Carrello t) {
		em.persist(t);
		return t;
	}

	@Override
	public List<Carrello> retrieve() {

		return em.createQuery("select c from Carrello c ",Carrello.class).getResultList();
	}

	@Override
	public Carrello update(Carrello t) {

		return em.merge(t);
	}

	@Override
	public void delete(Carrello t) {
		em.remove(t);

	}
	public List<Carrello> findByIdUtente(Integer id) {
		return em.createQuery("select c from Carrello c where c.utente.id = :parUtente", Carrello.class).setParameter("parUtente", id)
				.getResultList();

	}
	public List<Carrello> findCarrelloByProdottoid(Integer idProdotto) {

		return em.createQuery("select c from Carrello c join fetch c.prodotti cp where cp.id = :parUtente", Carrello.class).setParameter("parUtente", idProdotto)
				.getResultList();

	}
}