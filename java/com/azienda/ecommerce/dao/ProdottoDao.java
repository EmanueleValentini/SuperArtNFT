package com.azienda.ecommerce.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.azienda.ecommerce.model.Ordine;
import com.azienda.ecommerce.model.Prodotto;

public class ProdottoDao implements DaoInterface<Prodotto>{
	private EntityManager em;


	public ProdottoDao(EntityManager em) {
		super();
		this.em = em;
	}

	@Override
	public Prodotto create(Prodotto t) {
		em.persist(t);
		return t;
	}

	@Override
	public List<Prodotto> retrieve() {

		return em.createQuery("select p from Prodotto p",Prodotto.class).getResultList();
	}

	@Override
	public Prodotto update(Prodotto t) {
		// TODO Auto-generated method stub
		return em.merge(t);
	}

	@Override
	public void delete(Prodotto t) {
		em.remove(t);
	}
	public List<Prodotto> findByNome(String nome){

		return em.createQuery("select p from Prodotto p where p.nomeProdotto = :parNome",Prodotto.class)
				.setParameter("parNome", nome).getResultList();
	}
	public List<Prodotto> findByNomeContains(String nome){

		return em.createQuery("select p from Prodotto p where p.nomeProdotto like :parNome",Prodotto.class)
				.setParameter("parNome","%"+nome+"%").getResultList();
	}
	public List<Prodotto> findByNomeContainsSpecifico(String nome){

		return em.createQuery("select p from Prodotto p where p.nomeProdotto = :parNome and p.quantita > 0 and p.disponibile=true",Prodotto.class)
				.setParameter("parNome","%"+nome+"%").getResultList();
	}
	
	public List<Prodotto> findByIdCategoria(Integer id){
		return em.createQuery("select p from Prodotto p where p.categoria.id = :parCat", Prodotto.class)
				.setParameter("parCat", id).getResultList();
	}
	
	public List<Prodotto> findByPrezzoMaggiore(Double prezzo){

		return em.createQuery("select p from Prodotto p where p.prezzo > :parPrezzo",Prodotto.class)
				.setParameter("parPrezzo", prezzo).getResultList();
	}
	public List<Prodotto> findByPrezzoMinore(Double prezzo){

		return em.createQuery("select p from Prodotto p where p.prezzo < :parPrezzo",Prodotto.class)
				.setParameter("parPrezzo", prezzo).getResultList();
	}
	
	public List<Prodotto> findByFasciaPrezzo(Double prezzoMin,Double prezzoMax){

		return em.createQuery("select p from Prodotto p where p.prezzo > :parPrezzoMinore and p.prezzo < :parPrezzoMaggiore",Prodotto.class)
				.setParameter("parPrezzoMinore", prezzoMin).setParameter("parPrezzoMaggiore", prezzoMax).getResultList();
	}
	public List<Prodotto> findByNomeAndFasciaPrezzo(String nomeProdotto,Double prezzoMin,Double prezzoMax){

		return em.createQuery("select p from Prodotto p where p.prezzo > :parPrezzoMinore and p.prezzo < :parPrezzoMaggiore and p.nomeProdotto like :parNome",Prodotto.class)
				.setParameter("parPrezzoMinore", prezzoMin).setParameter("parPrezzoMaggiore", prezzoMax).setParameter("parNome", "%"+nomeProdotto+"%").getResultList();
	}
	
	public List<Prodotto> findById(Integer idProdotto){
		
		return em.createQuery("select p from Prodotto p where p.id= :paramId",Prodotto.class)
				.setParameter("paramId", idProdotto).getResultList();
	}
	
	public List<Prodotto> findLimitHome(){
		TypedQuery<Prodotto> query = em.createQuery("select p from Prodotto p where p.disponibile=true and p.quantita>0 order by p.id desc",Prodotto.class);
		query.setMaxResults(10);
		return query.getResultList();
	}
}
