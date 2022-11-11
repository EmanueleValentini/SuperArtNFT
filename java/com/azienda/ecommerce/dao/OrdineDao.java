package com.azienda.ecommerce.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import com.azienda.ecommerce.model.Ordine;
import com.azienda.ecommerce.model.Prodotto;
import com.azienda.ecommerce.model.Utente;

public class OrdineDao implements DaoInterface<Ordine>{
		private EntityManager em;


		public OrdineDao(EntityManager em) {
			super();
			this.em = em;
		}


		@Override
		public Ordine create(Ordine t) {
			em.persist(t);
			return (t);
		}


		@Override
		public List<Ordine> retrieve() {
			return em.createQuery("select o from Ordine o", Ordine.class)
					.getResultList();
		}


		@Override
		public Ordine update(Ordine t) {
			return em.merge(t);
		}


		@Override
		public void delete(Ordine t) {
			em.remove(t);
			
		}
		
		public List<Ordine> findByUtente(Integer id){
			return em.createQuery("select o from Ordine o where o.utente.id = :parUtente", Ordine.class)
					.setParameter("parUtente", id).getResultList();
		}
		
		public List<Ordine> findById(Integer id){
			return em.createQuery("select o from Ordine o where o.id= :parId", Ordine.class)
					.setParameter("parId", id).getResultList();
		}
		
		public List<Ordine> findByProdotto(Prodotto prodotto){
			return em.createQuery("select o from Ordine o where o.prodotto.id = :parProdotto", Ordine.class)
					.setParameter("parProdotto", prodotto.getId()).getResultList();
		}
		
		public List<Ordine> findByDataEffettuato(LocalDate dataEffettuato){
			return em.createQuery("select o from Ordine o where o.dataEffettuato= :parDataEffettuato", Ordine.class)
					.setParameter("parDataEffettuato", dataEffettuato).getResultList();
		}
}
