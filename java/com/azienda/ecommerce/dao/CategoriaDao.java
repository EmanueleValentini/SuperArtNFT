package com.azienda.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.azienda.ecommerce.model.Carrello;
import com.azienda.ecommerce.model.Categoria;
import com.azienda.ecommerce.model.Utente;

public class CategoriaDao implements DaoInterface <Categoria>{
	private EntityManager em;

	public CategoriaDao(EntityManager em) {
		super();
		this.em = em;
	}



	@Override
	public Categoria create(Categoria t) {
		em.persist(t);
		return t;
	}

	@Override
	public List<Categoria> retrieve() {

		return em.createQuery("select c from Categoria c ",Categoria.class).getResultList();
	}

	@Override
	public Categoria update(Categoria t) {

		return em.merge(t);
	}

	@Override
	public void delete(Categoria t) {
		em.remove(t);

	}
	public List<Categoria> findById(Integer idCategoria) {

		return em.createQuery("select c from Categoria c where c.id = :idCategoria", Categoria.class).setParameter("idCategoria", idCategoria)
				.getResultList();

	}
	
	public List<Categoria> findByName(String nomeCategoria) {

		return em.createQuery("select c from Categoria c where c.nomeCategoria = :parNome", Categoria.class).setParameter("parNome", nomeCategoria)
				.getResultList();

	}
}