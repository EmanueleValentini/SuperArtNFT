package com.azienda.ecommerce.ui;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.dao.CarrelloDao;
import com.azienda.ecommerce.dao.OrdineDao;
import com.azienda.ecommerce.dao.ProdottoDao;
import com.azienda.ecommerce.dao.RuoloDao;
import com.azienda.ecommerce.dao.UtenteDao;
import com.azienda.ecommerce.model.Carrello;
import com.azienda.ecommerce.model.Ordine;
import com.azienda.ecommerce.model.Prodotto;
import com.azienda.ecommerce.model.Utente;

public class RunTest {

	public static void main(String[] args) {
		EntityManager em = null;


		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("Progetto");
			em = emf.createEntityManager();
			CarrelloDao carrelloDao = new CarrelloDao(em);
			OrdineDao ordineDao = new OrdineDao(em);
			ProdottoDao prodottoDao = new ProdottoDao(em);
			RuoloDao ruoloDao = new RuoloDao(em);
			UtenteDao utenteDao = new UtenteDao(em);
			BusinessLogic businessLogic = new BusinessLogic(em, carrelloDao, ordineDao, utenteDao, ruoloDao, prodottoDao);

			businessLogic.insertRuolo("Utente");
			Prodotto p = businessLogic.insertProdotto(1, "Pippo", "/dada/adad", 10.99, true);
			Prodotto p1 = businessLogic.insertProdotto(100, "Pipp13o", "/dada/adad", 40.99, true);
			Prodotto p2 = businessLogic.insertProdotto(50, "Pippo213", "/dada/adad", 30.99, true);
			Prodotto p3 = businessLogic.insertProdotto(30, "Pipp213123o", "/dada/adad", 1000.99, true);

			businessLogic.insertUtente("pippa", "pippa@email.com", "popopopo");

			Utente u1 = businessLogic.login("pippa@email.com", "popopopo");

			System.out.println(u1.getNomeUtente());

			businessLogic.updateCompletoProdotto(1, "Pluto", 360, 9999.00, true);
			
			businessLogic.updatePassword("pippa", "asdasdasd", "popopopo");
			
			System.out.println(u1.getPassword());
			
			businessLogic.insertOrdine("Pluto", "pippa", LocalDate.now());
			
			businessLogic.insertProdottoToCarrello("Pluto", 1);
			   
			businessLogic.removeProdotto("Pluto");
			

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(em != null) {
				em.close();
			}

			System.exit(0);

		}

	}

}
