package com.azienda.ecommerce.ui;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.dao.CarrelloDao;
import com.azienda.ecommerce.dao.CategoriaDao;
import com.azienda.ecommerce.dao.NewsletterDao;
import com.azienda.ecommerce.dao.OrdineDao;
import com.azienda.ecommerce.dao.ProdottoDao;
import com.azienda.ecommerce.dao.RuoloDao;
import com.azienda.ecommerce.dao.UtenteDao;
import com.azienda.ecommerce.utilities.Costanti;

@WebServlet(value="/init",loadOnStartup = 1)
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EntityManager em = null;
	// servlet di iniziallizaione , crea tutti i dai e la BL, e chiama il metodo bl.inizializzaione.
	@Override
	public void init() throws ServletException {
		
		
		
		try {
			
			EntityManagerFactory emf= Persistence.createEntityManagerFactory("Progetto");
			em=emf.createEntityManager();
			
			UtenteDao utenteDao= new UtenteDao(em);
			CarrelloDao carrelloDao= new CarrelloDao(em);
			RuoloDao ruoloDao= new RuoloDao(em);
			ProdottoDao prodottoDao= new ProdottoDao(em);
			OrdineDao OrdineDao= new OrdineDao(em);
			CategoriaDao categoriaDao = new CategoriaDao(em);
			NewsletterDao newsletterDao = new NewsletterDao(em);
			
			BusinessLogic bl =new BusinessLogic(em, carrelloDao, OrdineDao, utenteDao, ruoloDao, prodottoDao, categoriaDao, newsletterDao);
			
			getServletContext().setAttribute(Costanti.BUSINESS_LOGIC, bl);// abbiamo la bl in tutte le servelt
			
			bl.inizializzazione();
		} catch (Exception e) {
			e.printStackTrace();
			
		}	
	}
	@Override
	public void destroy() {
		//super.destroy();
		if (em!=null) {
			em.close();
		}
		System.exit(0);
	}

}







