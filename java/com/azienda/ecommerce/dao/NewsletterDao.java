package com.azienda.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;


import com.azienda.ecommerce.model.Newsletter;


public class NewsletterDao implements DaoInterface <Newsletter>{
	private EntityManager em;

	public NewsletterDao(EntityManager em) {
		super();
		this.em = em;
	}



	@Override
	public Newsletter create(Newsletter t) {
		em.persist(t);
		return t;
	}

	@Override
	public List<Newsletter> retrieve() {

		return em.createQuery("select c from Newsletter c ",Newsletter.class).getResultList();
	}

	@Override
	public Newsletter update(Newsletter t) {

		return em.merge(t);
	}

	@Override
	public void delete(Newsletter t) {
		em.remove(t);

	}
	
	public List<Newsletter> findByEmail(String email) {

		return em.createQuery("select c from Newsletter c where c.email = :parEmai",Newsletter.class).setParameter("parEmai", email).getResultList();
	}
	
	public List<Newsletter> findByCode(String code) {

		return em.createQuery("select c from Newsletter c where c.codiceSegreto = :parEmai",Newsletter.class).setParameter("parEmai", code).getResultList();
	}

}