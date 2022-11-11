package com.azienda.ecommerce.bl;

import java.io.File;
import java.net.http.HttpRequest;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import com.azienda.ecommerce.dao.CarrelloDao;
import com.azienda.ecommerce.dao.CategoriaDao;
import com.azienda.ecommerce.dao.NewsletterDao;
import com.azienda.ecommerce.dao.OrdineDao;
import com.azienda.ecommerce.dao.ProdottoDao;
import com.azienda.ecommerce.dao.RuoloDao;
import com.azienda.ecommerce.dao.UtenteDao;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.CarrelloNonTrovatoException;
import com.azienda.ecommerce.exceptions.LunghezzaPasswordException;
import com.azienda.ecommerce.exceptions.OrdineNonEsistenteException;
import com.azienda.ecommerce.exceptions.PasswordErrataException;
import com.azienda.ecommerce.exceptions.ProdottoEsistenteException;
import com.azienda.ecommerce.exceptions.ProdottoNonDisponibileException;
import com.azienda.ecommerce.exceptions.ProdottoNonTrovatoException;
import com.azienda.ecommerce.exceptions.RuoloEsistenteException;
import com.azienda.ecommerce.exceptions.RuoloInesistenteException;
import com.azienda.ecommerce.exceptions.UtenteEsistenteException;
import com.azienda.ecommerce.exceptions.UtenteInesistenteException;
import com.azienda.ecommerce.model.Carrello;
import com.azienda.ecommerce.model.Categoria;
import com.azienda.ecommerce.model.Newsletter;
import com.azienda.ecommerce.model.Ordine;
import com.azienda.ecommerce.model.Prodotto;
import com.azienda.ecommerce.model.Ruolo;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.BlobConverter;
import com.azienda.ecommerce.utilities.Costanti;
import com.azienda.ecommerce.utilities.EmailUtils;

public class BusinessLogic {

	private EntityManager em;
	private CarrelloDao carrelloDao;
	private OrdineDao ordineDao;
	private UtenteDao utenteDao;
	private RuoloDao ruoloDao;
	private ProdottoDao prodottoDao;
	private CategoriaDao categoriaDao;
	private NewsletterDao newsletterDao;
	//MANCA SOLO IL LOGIN, METODI OK(2022/10/07)


	public BusinessLogic(EntityManager em, CarrelloDao carrelloDao, OrdineDao ordineDao, UtenteDao utenteDao,
			RuoloDao ruoloDao, ProdottoDao prodottoDao, CategoriaDao categoriaDao, NewsletterDao newsletterDao) {
		super();

		this.em = em;
		this.carrelloDao = carrelloDao;
		this.ordineDao = ordineDao;
		this.utenteDao = utenteDao;
		this.ruoloDao = ruoloDao;
		this.prodottoDao = prodottoDao;
		this.categoriaDao = categoriaDao;
		this.newsletterDao = newsletterDao;
	}

	public void inizializzazione() throws Exception {
		


		try {

			em.getTransaction().begin();

			try {

				searchByNomeRuoloPrivato(Costanti.ADMIN);

			} catch (RuoloInesistenteException e) {

				Ruolo admin = insertRuoloPrivato(Costanti.ADMIN);

				Utente u = new Utente("ADMIN","admin@admin.admin","1",false);

				u.setRuolo(admin);
				utenteDao.create(u);

				Carrello c = new Carrello(u);
				carrelloDao.create(c);

			}

			try {

				searchByNomeRuoloPrivato(Costanti.UTENTE);

			} catch (RuoloInesistenteException e) {

				insertRuoloPrivato(Costanti.UTENTE);
			}

			em.getTransaction().commit();

		} catch (Exception e) {

			em.getTransaction().rollback();
			throw e;
		}
		
	}

	//OK
	public Utente updatePassword(String nomeUtente,String nuovaPassword,String vecchiaPassword) throws Exception {

		try {

			em.getTransaction().begin();

			if (nomeUtente==null||nomeUtente.isBlank()) {
				throw new CampiMancantiException("nome utente mancante/nulla");
			}

			if (nuovaPassword==null||nuovaPassword.isBlank()) {
				throw new CampiMancantiException("Nuova password mancante/nulla");
			}

			if (vecchiaPassword==null||vecchiaPassword.isBlank()) {
				throw new CampiMancantiException(" Vecchia password mancante/nulla");
			}

			Utente utente=searchByNomeUtentePrivato(nomeUtente);

			if (!utente.getPassword().equals(vecchiaPassword)) {
				throw new CampiMancantiException("La vecchia password non corrisponde!!!");
			}

			utente.setPassword(nuovaPassword);

			em.getTransaction().commit();
			return utente;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}

	}
	//OK	
	public Prodotto updateProdotto(String nuovoNome,String vecchioNome) throws Exception {

		try {
			em.getTransaction().begin();

			if (nuovoNome==null||nuovoNome.isBlank()) {

				throw new CampiMancantiException("nuovoNome mancante/nulla");
			}

			if (vecchioNome==null||vecchioNome.isBlank()) {

				throw new CampiMancantiException("vecchioNome mancante/nulla");
			}

			try {

				List<Prodotto> prodotti = getProdottoByNome(nuovoNome);

			} catch (ProdottoNonTrovatoException e) {

				System.out.println("non esiste un prodotto con "+ nuovoNome +" lo aggiungo.");
			}

			Prodotto prodotto = getProdottoByNome(vecchioNome).get(0);
			prodotto.setNomeProdotto(nuovoNome);

			em.getTransaction().commit();
			return prodotto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK	
	public List<Newsletter> searchByCode(String code) throws Exception {

		try {
			em.getTransaction().begin();

			List<Newsletter> news = newsletterDao.findByCode(code);
			em.getTransaction().commit();
			return news;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	
	//OK	
	public Prodotto updateBlob(String nomeProdotto,Blob nuovoBlob) throws Exception {

		try {
			em.getTransaction().begin();

			if (nomeProdotto==null||nomeProdotto.isBlank()) {

				throw new CampiMancantiException("nomeProdotto mancante/nullo");
			}

			if (nuovoBlob==null) {

				throw new CampiMancantiException("nuovoBlob mancante/nullo");
			}

			Prodotto prodotto = getProdottoByNome(nomeProdotto).get(0);
			prodotto.setBlob(nuovoBlob);

			em.getTransaction().commit();
			return prodotto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}



	public Prodotto updateCompletoProdotto(String nomeCategoria, Integer idProdotto, String descrizione, Blob blob ,String nomeProdotto, Integer quantita, Double prezzo, Boolean disponibile) throws Exception {

		try {
			em.getTransaction().begin();

			if(nomeProdotto.isBlank() || nomeCategoria.isBlank() || nomeCategoria == null){

				throw new CampiMancantiException("Manca il nome nell'update");
			}
			List<Prodotto> pds = prodottoDao.findByNome(nomeProdotto);

			if (pds.size()>0 && pds.get(0).getId() != idProdotto) {
				
				throw new ProdottoEsistenteException("Esiste gia un prodotto con il nome inserito.");
			}

			Prodotto prodotto = getProdottoById(idProdotto).get(0);
			
			if(blob!=null) {
				prodotto.setBlob(blob);

			}
			
			List<Categoria> categoria =  categoriaDao.findByName(nomeCategoria);
			if(categoria.size() == 0 ) {
				throw new CampiMancantiException("Categoria non esistente");
			}
			
			prodotto.setCategoria(categoria.get(0));
			
			prodotto.setNomeProdotto(nomeProdotto);

			prodotto.setQuantita(quantita);

			prodotto.setPrezzo(prezzo);
			prodotto.setDescrizione(descrizione);

			prodotto.setDisponibile(disponibile);


			em.getTransaction().commit();
			return prodotto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK	
	public Prodotto updatePrezzo(String nomeProdotto,Double nuovoPrezzo) throws Exception {

		try {
			em.getTransaction().begin();

			if (nomeProdotto==null||nomeProdotto.isBlank()) {

				throw new CampiMancantiException("nomeProdotto mancante/nullo");
			}

			if (nuovoPrezzo==null||nuovoPrezzo==0) {

				throw new CampiMancantiException("nuovoPrezzo mancante/nullo");
			}

			Prodotto prodotto = getProdottoByNome(nomeProdotto).get(0);
			prodotto.setPrezzo(nuovoPrezzo);

			em.getTransaction().commit();
			return prodotto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK	
	public Prodotto updateQuantita(String nomeProdotto,Integer quantita) throws Exception {

		try {
			em.getTransaction().begin();

			if (nomeProdotto==null||nomeProdotto.isBlank()) {
				throw new CampiMancantiException("nomeProdotto mancante/nullo");
			}

			if (quantita==null) {
				throw new CampiMancantiException("quantita nulla");
			}

			Prodotto prodotto = getProdottoByNome(nomeProdotto).get(0);
			prodotto.setQuantita(quantita);

			em.getTransaction().commit();
			return prodotto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK
	public Prodotto updateDisponibile(String nomeProdotto,Boolean disponibile) throws Exception {

		try {
			em.getTransaction().begin();

			if (nomeProdotto==null||nomeProdotto.isBlank()) {
				throw new CampiMancantiException("nomeProdotto mancante/nullo");
			}

			if (disponibile==null) {
				throw new CampiMancantiException("disponibile null");
			}

			Prodotto prodotto = getProdottoByNome(nomeProdotto).get(0);
			prodotto.setDisponibile(disponibile);

			em.getTransaction().commit();
			return prodotto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	//OK
	public void removeCategoria(String categoriaNome) throws Exception {
		try {
			em.getTransaction().begin();

			if (
					categoriaNome == null || categoriaNome.isBlank()) {
					
				throw new CampiMancantiException("Campi Mancanti,categoria non aggiunta.");
			}
			
			List<Categoria> categorie = categoriaDao.findByName(categoriaNome);
			if(categorie.size() == 0) {
				throw new CampiMancantiException("Categoria non esistente");
			}
			
			
			
			categorie.get(0).setRimosso(true);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	//OK
	public void ripristinaCategoria(String categoriaNome) throws Exception {
		try {
			em.getTransaction().begin();

			if (
					categoriaNome == null || categoriaNome.isBlank()) {
					
				throw new CampiMancantiException("Campi Mancanti,categoria non aggiunta.");
			}
			
			List<Categoria> categorie = categoriaDao.findByName(categoriaNome);
			if(categorie.size() == 0) {
				throw new CampiMancantiException("Categoria non esistente");
			}
			
			
			
			categorie.get(0).setRimosso(false);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	//OK
	public Newsletter insertEmailInNewsletter(String email) throws Exception {
		try {
			em.getTransaction().begin();

			if (
					email == null || email.isBlank()) {
					
				throw new CampiMancantiException("Campi Mancanti,newsletter non aggiunta.");
			}
			
			List<Newsletter> emails = newsletterDao.findByEmail(email);
			if(emails.size() > 0) {
				throw new CampiMancantiException("Sei già iscritto alla newsletter");
			}
			
			Newsletter n = new Newsletter(email);
			newsletterDao.create(n);
			
			em.getTransaction().commit();
			return n;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	
	//OK
	public void sendEmailToAll(String oggetto, String testo) throws Exception {
		try {
			em.getTransaction().begin();

			if (
					oggetto == null || oggetto.isBlank() 
					|| testo == null || testo.isBlank()) {
					
				throw new CampiMancantiException("Campi Mancanti,newsletter non aggiunta.");
			}
			
			List<Newsletter> ns = newsletterDao.retrieve();

			for(Newsletter n : ns) {
				
				EmailUtils.sendGenericEmail(oggetto, testo, n.getEmail(), n.getCodiceSegreto());
				
			}
			
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	
	//OK
	public Categoria insertCategoria(String categoriaNome) throws Exception {
		try {
			em.getTransaction().begin();

			if (
					categoriaNome == null || categoriaNome.isBlank()) {
					
				throw new CampiMancantiException("Campi Mancanti,categoria non aggiunta.");
			}
			
			List<Categoria> categorie = categoriaDao.findByName(categoriaNome);
			if(categorie.size() > 0) {
				throw new CampiMancantiException("Categoria esistente");
			}
			
			
			Categoria c = new Categoria(categoriaNome);
			categoriaDao.create(c);

			em.getTransaction().commit();
			return c;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	//OK
	public Prodotto insertProdotto(String categoriaNome, Integer quantita,String nomeProdotto,String descrizione, Blob foto,Double prezzo,Boolean isDisponibile) throws Exception {
		try {
			em.getTransaction().begin();

			if (
					quantita==null||					
					nomeProdotto.isBlank()||nomeProdotto==null||
					foto==null|| prezzo==0||prezzo==null||isDisponibile==null|| categoriaNome.isBlank() || categoriaNome == null) {
					
				throw new CampiMancantiException("Campi Mancanti,prodotto non aggiunto.");
			}

			if (prodottoDao.findByNome(nomeProdotto).size()!=0) {

				throw new ProdottoEsistenteException("Prodotto già esistente nel database.");
			}
			
			List<Categoria> categorie = categoriaDao.findByName(categoriaNome);
			if(categorie.size() == 0) {
				throw new CampiMancantiException("Categoria non esistente");
			}
			
			
			Prodotto prodotto= new Prodotto(categorie.get(0),nomeProdotto, descrizione, foto, prezzo, quantita, isDisponibile);
			prodottoDao.create(prodotto);

			em.getTransaction().commit();
			return prodotto;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK
	public void insertProdottoToCarrello (String nomeProdotto,Integer idUtente) throws Exception {
		try {
			em.getTransaction().begin();

			Prodotto prodotto=getProdottoByNomeUguale(nomeProdotto).get(0);
			Carrello carrello=getCarrelloByIdUtente(idUtente);

			if (prodotto==null||carrello==null) {
				throw new CampiMancantiException("Campi Mancanti, prodotto non inserito nel carrello");
			}
			if (carrello.getProdotti().contains(prodotto)) {
				throw new ProdottoEsistenteException("Prodotto già presente nel carrello");
			}
			if(prodotto.getDisponibile() == false) {
				throw new ProdottoNonDisponibileException("Il prodotto non è disponibile");
			}
			if(prodotto.getQuantita() == 0) {
				throw new ProdottoNonDisponibileException("Il prodotto non è disponibile, quantità non sufficiente");
			}

			carrello.getProdotti().add(prodotto);

			em.getTransaction().commit();			
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	
	//OK
	public List<Prodotto> searchByNomeProdottoUguale (String nomeProdotto) throws Exception {
		try {
			em.getTransaction().begin();

			List<Prodotto> prodotti =getProdottoByNomeUguale(nomeProdotto);

			em.getTransaction().commit();
			return prodotti;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	//OK
	public List<Prodotto> searchByNomeProdotto (String nomeProdotto) throws Exception {
		try {
			em.getTransaction().begin();

			List<Prodotto> prodotti =getProdottoByNome(nomeProdotto);

			em.getTransaction().commit();
			return prodotti;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	
	//OK
		public List<Categoria> searchCategorie() throws Exception {
			try {
				em.getTransaction().begin();


				List<Categoria> categorie = categoriaDao.retrieve();
			
				
				
				
				
				
				
				em.getTransaction().commit();
				return categorie;
			} catch (Exception e) {
				em.getTransaction().rollback();
				throw e;
			}
		}
	//OK
		public List<Categoria> searchCategoria(String nomeCategoria) throws Exception {
			try {
				em.getTransaction().begin();

				if(nomeCategoria == null || nomeCategoria.isBlank()) {
					throw new CampiMancantiException("Nome Categoria vuoto");
				}
				
				List<Categoria> categorie = categoriaDao.findByName(nomeCategoria);
				
				if(categorie.size() == 0) {
					throw new ProdottoNonDisponibileException("Non ci sono categorie con questo nome");
					
				}
				
				
				
				
				
				
				em.getTransaction().commit();
				return categorie;
			} catch (Exception e) {
				em.getTransaction().rollback();
				throw e;
			}
		}
	
	//OK
	public List<Prodotto> searchByCategoria(String nomeCategoria) throws Exception {
		try {
			em.getTransaction().begin();

			if(nomeCategoria == null || nomeCategoria.isBlank()) {
				throw new CampiMancantiException("Nome Categoria vuoto");
			}
			
			List<Categoria> categorie = categoriaDao.findByName(nomeCategoria);
			
			if(categorie.size() == 0) {
				throw new ProdottoNonDisponibileException("Non ci sono categorie con questo nome");
				
			}
			
			
			List<Prodotto> prodotti = prodottoDao.findByIdCategoria(categorie.get(0).getId());
			if(prodotti.size() == 0) {
				throw new ProdottoNonDisponibileException("Non ci sono prodotti in questa categoria");
			}
			
			
			
			
			em.getTransaction().commit();
			return prodotti;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}


	//OK
	public List<Prodotto> searchByNomeAndFasciaPrezzo (String nomeProdotto,Double prezzoMin,Double prezzoMax) throws Exception {
		try {			
			em.getTransaction().begin();

			List<Prodotto>prodotti=getNomeProdottobyFasciaPrezzo(nomeProdotto, prezzoMin, prezzoMax);

			em.getTransaction().commit();
			return prodotti;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}



	//OK
	public List<Prodotto> searchByFasciaPrezzo (Double prezzoMin,Double prezzoMax) throws Exception {
		try {
			em.getTransaction().begin();

			List<Prodotto>prodotti=getFasciaPrezzo(prezzoMin, prezzoMax);

			em.getTransaction().commit();
			return prodotti;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	//OK
	public void removeProdotto (String nomeProdotto) throws Exception {

		try {
			em.getTransaction().begin();

			Prodotto prodotto=getProdottoByNomeUguale(nomeProdotto).get(0);

			if (prodotto==null) {
				throw new CampiMancantiException("Campo prodotto Mancante");
			}

			try {

				removeProdottoFromCarrelliPrivato(prodotto);

			} catch (CarrelloNonTrovatoException e) {

				System.out.println("Carrelli non trovati per il prodotto, rimuovo solamente il prodotto!");
			}

			try {

				removeProdottoFromOrdiniPrivato(prodotto);				

			} catch (CarrelloNonTrovatoException e) {
				System.out.println("ordini non trovati per il prodotto, rimuovo solamente il prodotto dal carrello!");
			}
			prodottoDao.delete(prodotto);		
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	//OK
	public void removeProdottoFromCarrello (String nomeProdotto,Integer idUtente) throws Exception {
		try {
			em.getTransaction().begin();

			Prodotto prodotto =getProdottoByNome(nomeProdotto).get(0);
			Carrello carrello=getCarrelloByIdUtente(idUtente);

			if (prodotto==null||carrello==null) {
				throw new CampiMancantiException("Campi: prodotto,carrello = null");
			}

			if (!carrello.getProdotti().contains(prodotto)) {
				throw new ProdottoEsistenteException("Prodotto non presente nel carrello,impossibile rimuovere");
			}

			carrello.getProdotti().remove(prodotto);

			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	//OK
	public void removeProdottoFromCarrelli (String nomeProdotto) throws Exception {
		try {
			em.getTransaction().begin();

			Prodotto prodotto=getProdottoByNome(nomeProdotto).get(0);

			removeProdottoFromCarrelliPrivato(prodotto);

			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	//OK
	public Carrello searchByUtenteId(Integer id) throws Exception {
		try {
			em.getTransaction().begin();

			Carrello carrello=getCarrelloByIdUtente(id);

			em.getTransaction().commit();
			return carrello;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	//OK
	public List<Utente> searchUtente() throws Exception {
		try {
			em.getTransaction().begin();
			
			List<Utente> utenti = utenteDao.retrieve();

			em.getTransaction().commit();
			return utenti;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK
	public Utente insertUtente(String nomeUtente, String email, String password) throws Exception{
		try {
			em.getTransaction().begin();

			if(nomeUtente == null || nomeUtente.isBlank()) {
				throw new CampiMancantiException("Non hai inserito lo username");
			} else if(email == null || email.isBlank()) {

				throw new CampiMancantiException("Non hai inserito l'email");
			} else if(password == null || password.isBlank()) {
				throw new CampiMancantiException("Non hai inserito la password");
			}

			if(password.length()<8) {
				throw new LunghezzaPasswordException("La password deve contenere almeno 8 caratteri");
			}else if (password.length()>21) {
				throw new LunghezzaPasswordException("La password puo contenere massimo 20 caratteri");
			}

			List<Utente> utenti = utenteDao.ricercaPerNomeUtenteAndEmail(nomeUtente, email);
			if (utenti.size()>0) {
				throw new UtenteEsistenteException("Utente gia presente nel DATABASE");
			}
			Ruolo ruolo=searchByNomeRuoloPrivato(Costanti.UTENTE);

			Utente utente = new Utente(nomeUtente, email, password, false);

			utenteDao.create(utente);

			Carrello carrello = new Carrello(utente);

			carrelloDao.create(carrello);

			utente.setRuolo(ruolo);
			em.getTransaction().commit();
			return utente;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}

	}
	//OK
	public void insertRuolo(String nomeRuolo) throws Exception {
		try {
			em.getTransaction().begin();
			List<Ruolo> ruoli = ruoloDao.findRuoloByNomeRuolo(nomeRuolo);
			if (ruoli.size()>0) {
				throw new RuoloEsistenteException("Ruolo gia presente nel DATABASE");
			}

			Ruolo ruolo = new Ruolo(nomeRuolo);
			ruoloDao.create(ruolo);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK
	public Utente searchByNomeUtente(String nomeUtente) throws Exception {
		try {
			em.getTransaction().begin();
			
			if(nomeUtente == null || nomeUtente.isBlank()) {
				throw new CampiMancantiException("Campo mancante");
			}
			
			List<Utente> utenti = utenteDao.ricercaPerNomeUtente(nomeUtente);
			if(utenti.size() == 0) {
				throw new UtenteInesistenteException("L'username che stai cercando non esiste");
			}
			em.getTransaction().commit();
			return utenti.get(0);
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK
	public Utente searchByEmail(String email) throws Exception {
		try {
			em.getTransaction().begin();
			
			if(email == null || email.isBlank()) {
				throw new CampiMancantiException("Campo 'email' mancante!");
			}
			
			
			List<Utente> utenti = utenteDao.ricercaPerEmail(email);
			if(utenti.size() == 0) {
				throw new UtenteInesistenteException("Non esiste alcun utente registrato con questa mail");
			}
			em.getTransaction().commit();
			return utenti.get(0);
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK
	public Ruolo searchByNomeRuolo(String nomeRuolo) throws Exception {
		try {
			em.getTransaction().begin();
			List<Ruolo> ruoli = ruoloDao.findRuoloByNomeRuolo(nomeRuolo);
			if (ruoli.size()==0) {
				throw new RuoloInesistenteException("Ruolo con nome "+nomeRuolo+" non trovato");
			}
			em.getTransaction().commit();
			return ruoli.get(0);
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	//	public Ruolo searchByRuolo(Ruolo ruolo) throws Exception {
	//		try {
	//			em.getTransaction().begin();
	//			List<Ruolo> ruoli = ruoloDao.retrieve();
	//			if(ruoli.size() == 0) {
	//				throw new RuoloInesistenteException("Questo ruolo non esiste");
	//			}
	//			em.getTransaction().commit();
	//			return ruoli.get(0);
	//		} catch (Exception e) {
	//			em.getTransaction().rollback();
	//			throw e;
	//		}
	//	}

	//OK
	public void removeCarrelloFromUtente(String nomeUtente) throws Exception {
		try {
			em.getTransaction().begin();
			List<Utente> utenti = utenteDao.ricercaPerNomeUtente(nomeUtente);
			if(utenti.size() == 0) {
				throw new UtenteInesistenteException("L'username che stai cercando non esiste");
			}

			Utente u = utenti.get(0);
			List<Carrello> carrelli = carrelloDao.findByIdUtente(u.getId());

			if(carrelli.size() == 0) {
				throw new CarrelloNonTrovatoException("Carrello non esistente");
			}
			Carrello carrello = carrelli.get(0);
			carrelloDao.delete(carrello);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK
	public void ripristinaByEmail(String email) throws Exception {
		try {
			em.getTransaction().begin();
			List<Utente> utenti = utenteDao.ricercaPerEmail(email);
			if(utenti.size() == 0) {
				throw new UtenteInesistenteException("L'username che stai cercando non esiste");
			}

			utenti.get(0).setRimosso(false);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	//OK
	public void ripristinaByNomeUtente(String nomeUtente) throws Exception {
		try {
			em.getTransaction().begin();
			List<Utente> utenti = utenteDao.ricercaPerNomeUtente(nomeUtente);
			if(utenti.size() == 0) {
				throw new UtenteInesistenteException("L'username che stai cercando non esiste");
			}

			utenti.get(0).setRimosso(false);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	
	public void removeByNomeUtente(String nomeUtente) throws Exception {
		try {
			em.getTransaction().begin();
			List<Utente> utenti = utenteDao.ricercaPerNomeUtente(nomeUtente);
			if(utenti.size() == 0) {
				throw new UtenteInesistenteException("L'username che stai cercando non esiste");
			}

			utenti.get(0).setRimosso(true);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK
	public void removeByEmail(String email) throws Exception {
		try {
			em.getTransaction().begin();
			List<Utente> utenti = utenteDao.ricercaPerEmail(email);
			if(utenti.size() == 0) {
				throw new UtenteInesistenteException("Non esiste nessun utente associato a questa email");
			}
			utenti.get(0).setRimosso(true);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	//	public void removeByUtente(Utente utente) throws Exception {
	//		try {
	//			em.getTransaction().begin();
	//			List<Utente> utenti = utenteDao.listaUtenti();
	//			if(!utenti.contains(utente)) {
	//				throw new UtenteInesistenteException("Questo utente non esiste");
	//			}
	//			Utente u = utenti.get(0);
	//			u.setCarrello(null);
	//
	//
	//			utenteDao.delete(utenti.get(0));
	//			em.getTransaction().commit();
	//		} catch (Exception e) {
	//			em.getTransaction().rollback();
	//			throw e;
	//		}
	//	}
	
	public void updateCat(Integer id, String cat) throws Exception {
		try {
			em.getTransaction().begin();
			List<Categoria> categorie = categoriaDao.findById(id);
			if (categorie.size()==0) {
				throw new RuoloInesistenteException("Categoria non trovata");
			}
			
			List<Categoria> catDisp = categoriaDao.findByName(cat);
			if(catDisp.size() != 0 && catDisp.get(0).getId() != id) {
				throw new CampiMancantiException("C'è già una categoria con questo nome");
			}
			
			categorie.get(0).setNomeCategoria(cat);
			
			
			em.getTransaction().commit();
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	//OK
	public void removeByRuolo(String nomeRuolo) throws Exception {
		try {
			em.getTransaction().begin();
			List<Ruolo> ruoli = ruoloDao.findRuoloByNomeRuolo(nomeRuolo);
			if (ruoli.size()==0) {
				throw new RuoloInesistenteException("Ruolo con nome "+nomeRuolo+" non trovato");
			}
			ruoloDao.delete(ruoli.get(0));
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK
	public Ordine insertOrdine(String nomeProdotto,String nomeUtente, LocalDate dataEffettuato) throws Exception {
		try {
			em.getTransaction().begin();
			Prodotto prodotto = getProdottoByNome(nomeProdotto).get(0);
			if(prodotto.getDisponibile() == false) {
				throw new ProdottoNonDisponibileException("Prodotto non disponibile");
			}

			if(prodotto.getQuantita() == 0) {
				throw new ProdottoNonDisponibileException("Quantita non sufficiente");
			}

			if(prodotto==null || dataEffettuato==null) {
				throw new CampiMancantiException("Campi mancanti");
			}


			Utente utente =searchByNomeUtentePrivato(nomeUtente);
			if(utente == null) {
				throw new UtenteInesistenteException("Utente mancante");

			}

			Ordine ordine=new Ordine(prodotto, utente, dataEffettuato);
			prodotto.setQuantita(prodotto.getQuantita()-1);

			Carrello c = getCarrelloByIdUtente(utente.getId());
			c.getProdotti().remove(prodotto);

			ordineDao.create(ordine);
			em.getTransaction().commit();
			return ordine;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}

	}
	
	public List<Prodotto> searchProdottoLimit() throws Exception {
		try {
			em.getTransaction().begin();
			List<Prodotto> prodotti= prodottoDao.findLimitHome();	
			em.getTransaction().commit();
			return prodotti;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	//OK
	public Ordine searchById(Integer id) throws Exception {
		try {
			em.getTransaction().begin();
			if(id==null) {
				throw new CampiMancantiException("Id non esistente");
			}
			List<Ordine> ordini= ordineDao.findById(id);	
			if(ordini.size() == 0) {
				throw new OrdineNonEsistenteException("Ordine non esistente.");
			}
			Ordine ordine=ordini.get(0);
			em.getTransaction().commit();
			return ordine;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	
	
	//OK
	public void removeNewsletter(String codice) throws Exception {
		try {
			em.getTransaction().begin();
			if(codice == null || codice.isBlank()) {
				throw new CampiMancantiException("Codice non esistente");
			}
			
			Newsletter nes = newsletterDao.findByCode(codice).get(0);
			
			if(nes == null) {
				throw new CampiMancantiException("");
			}
			
			EmailUtils.sendGenericEmail("Disicrizione", "Ti sei disiscritto con successo alla mail", nes.getEmail(), nes.getCodiceSegreto());
			
			newsletterDao.delete(nes);
			
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK
	public List<Ordine> searchOrdineByUtenteId(Integer id) throws Exception {
		try {
			em.getTransaction().begin();
			if(id==null) {
				throw new CampiMancantiException("Id non esistente");
			}
			List<Ordine> ordini= ordineDao.findByUtente(id);

			em.getTransaction().commit();
			return ordini;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}

	//OK
	public List<Ordine> searchByDataEffettuato(LocalDate dataEffettuato) throws Exception {
		try {
			em.getTransaction().begin();
			if(dataEffettuato==null) {
				throw new CampiMancantiException("Data non esistente");
			}
			List<Ordine> ordini= ordineDao.findByDataEffettuato(dataEffettuato);
			if(ordini.size() == 0) {
				throw new OrdineNonEsistenteException("Ordine non esistente.");
			}
			em.getTransaction().commit();
			return ordini;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}
	}
	//OK
	public void removeOrdine(Integer idOrdine) throws Exception {
		try {
			em.getTransaction().begin();
			if(idOrdine==null) {
				throw new CampiMancantiException("Campi mancanti");
			}
			List<Ordine> ordini= ordineDao.findById(idOrdine);
			if(ordini.size() == 0) {
				throw new OrdineNonEsistenteException("Ordine con id " +idOrdine + " non trovato");
			}
			ordineDao.delete(ordini.get(0));
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}

	}

	public Utente login(String email, String password) throws Exception {
		try {
			em.getTransaction().begin();

			if (email.isBlank()||password.isBlank()) {
				throw new CampiMancantiException("email o password non inseriti");
			}

			List<Utente> utenti = utenteDao.ricercaPerEmail(email);

			if(utenti.size() == 0) {
				throw new UtenteInesistenteException("email non associata ad alcun utente");
			}else {
				if (utenti.get(0).getRimosso()==true) {
					throw new UtenteInesistenteException("Account inesistente");
				}
				if(!password.equals(utenti.get(0).getPassword())) {
					throw new PasswordErrataException("la password inserita non è corretta");
				}
			}

			em.getTransaction().commit();

			return utenti.get(0);
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		}

	}

	//OK
	private List<Prodotto> getProdottoByNomeUguale (String nomeProdotto) throws Exception {
		if (nomeProdotto.isBlank()||nomeProdotto==null) {
			throw new CampiMancantiException("nome prodotto mancante.");
		}
		List<Prodotto> prodotti= prodottoDao.findByNome(nomeProdotto);

		if(prodotti.size() == 0) {
			throw new ProdottoNonTrovatoException("Prodotto non trovato");
		}

		return prodotti;
	}
	
	//OK
	private List<Prodotto> getProdottoByNome (String nomeProdotto) throws Exception {
		if (nomeProdotto.isBlank()||nomeProdotto==null) {
			throw new CampiMancantiException("nome prodotto mancante.");
		}
		List<Prodotto> prodotti= prodottoDao.findByNomeContains(nomeProdotto);

		if(prodotti.size() == 0) {
			throw new ProdottoNonTrovatoException("Prodotto non trovato");
		}

		return prodotti;
	}

	private List<Prodotto> getProdottoById (Integer idProdotto) throws Exception {
		if (idProdotto==null) {
			throw new CampiMancantiException("id prodotto mancante.");
		}
		List<Prodotto> prodotti= prodottoDao.findById(idProdotto);

		if(prodotti.size() == 0) {
			throw new ProdottoNonTrovatoException("Prodotto non trovato");
		}

		return prodotti;
	}

	//OK
	private List<Prodotto> getProdottoByNomeSpecifico (String nomeProdotto) throws Exception { // CON PRODOTTO.QUANTITA > 0 
		if (nomeProdotto.isBlank()||nomeProdotto==null) {
			throw new CampiMancantiException("nome prodotto mancante.");
		}
		List<Prodotto> prodotti= prodottoDao.findByNomeContainsSpecifico(nomeProdotto);

		if(prodotti.size() == 0) {
			throw new ProdottoNonTrovatoException("Prodotto non trovato");
		}

		return prodotti;
	}
	//OK
	private List<Prodotto> getFasciaPrezzo (Double prezzoMin,Double prezzoMax) throws Exception {

		if (prezzoMin==null||prezzoMax==null) {
			throw new CampiMancantiException("Prezzo/i non inserito/i.");
		}
		List<Prodotto> prodotti= prodottoDao.findByFasciaPrezzo(prezzoMin,prezzoMax);
		if (prodotti.size()==0) {
			throw new ProdottoNonTrovatoException("Prodotto con prezzo min "+prezzoMin+"e prezzo max "+prezzoMax+" non trovato");
		}		
		return prodotti;		
	}
	//OK
	private List<Prodotto> getNomeProdottobyFasciaPrezzo (String nomeProdotto,Double prezzoMin,Double prezzoMax) throws Exception {

		if (nomeProdotto.isBlank()||nomeProdotto==null) {
			throw new CampiMancantiException("nome prodotto mancante.");
		}
		if (prezzoMin==null||prezzoMax==null||prezzoMax<prezzoMin) {
			throw new CampiMancantiException("Prezzo/i non inserito/i O valori non validi.");
		}
		List<Prodotto> prodotti= prodottoDao.findByNomeAndFasciaPrezzo(nomeProdotto,prezzoMin,prezzoMax);
		if (prodotti.size()==0) {
			throw new ProdottoNonTrovatoException("Prodotto con prezzo min "+prezzoMin+"e prezzo max "+prezzoMax+" non trovato");
		}		
		return prodotti;		
	}
	//OK
	private Carrello getCarrelloByIdUtente(Integer idUtente) throws Exception {


		if (idUtente==null) {
			throw new CampiMancantiException("Parametro Utente non inserito.");
		}
		List<Carrello> carrello= carrelloDao.findByIdUtente(idUtente);
		if (carrello.size()==0) {
			throw new CarrelloNonTrovatoException("Carrello associato a non trovato");
		}
		return carrello.get(0);

	}
	//	private void getCarreloByNomeProdotto (String nomeProdotto) throws Exception {
	//
	//		if (nomeProdotto==null) {
	//			throw new CampiMancantiException("Nome prodotto non inserito");
	//		}
	//		Prodotto p =getProdottoByNome(nomeProdotto).get(0);
	//		List<Carrello> carrelli = carrelloDao.findCarrelloByProdottoid(p.getId());
	//
	//		if(carrelli.size() == 0) {
	//			throw new CampiMancantiException("Lista utenti vuota");
	//		}
	//
	//		if(carrelli == null) {
	//			throw new CampiMancantiException("Lista utenti vuota");
	//
	//		}
	//		for(Carrello c : carrelli) {
	//			c.getProdotti().remove(p);
	//		}
	//	}

	@SuppressWarnings("unused")
	private void removeProdottoFromCarrelliPrivato (Prodotto prodotto) throws Exception {

		if (prodotto==null) {
			throw new CampiMancantiException("Nome prodotto non inserito");
		}
		List<Carrello> carrelli = carrelloDao.findCarrelloByProdottoid(prodotto.getId());

		if(carrelli.size() == 0) {
			throw new CarrelloNonTrovatoException("Lista utenti vuota");
		}

		if(carrelli == null) {
			throw new CampiMancantiException("Lista utenti vuota");

		}
		for(Carrello c : carrelli) {
			c.getProdotti().remove(prodotto);
		}
	}

	private Ruolo searchByNomeRuoloPrivato(String nomeRuolo) throws Exception {

		List<Ruolo> ruoli = ruoloDao.findRuoloByNomeRuolo(nomeRuolo);
		if (ruoli.size()==0) {
			throw new RuoloInesistenteException("Ruolo con nome "+nomeRuolo+" non trovato");
		}
		return ruoli.get(0);

	}
	private Utente searchByNomeUtentePrivato(String nomeUtente) throws Exception {

		List<Utente> utenti = utenteDao.ricercaPerNomeUtente(nomeUtente);
		if (utenti.size()==0) {
			throw new UtenteInesistenteException("Utente con nome "+nomeUtente+" non trovato");
		}
		return utenti.get(0);		
	}


	@SuppressWarnings("unused")
	private Boolean getIfRimosso(Utente utente) throws Exception {

		return utente.getRimosso();
	}

	private void removeProdottoFromOrdiniPrivato (Prodotto prodotto) throws Exception {

		if (prodotto==null) {
			throw new CampiMancantiException("Nome prodotto non inserito");
		}
		List<Ordine> ordini = ordineDao.findByProdotto(prodotto);

		if(ordini.size() == 0) {
			throw new CarrelloNonTrovatoException("Lista utenti vuota");
		}
		
		for(Ordine o : ordini) {
			o.setProdotto(null);
			em.remove(o);
		}


	}

	private Ruolo insertRuoloPrivato (String nomeRuolo) throws RuoloEsistenteException {

		List<Ruolo> ruoli = ruoloDao.findRuoloByNomeRuolo(nomeRuolo);
		if (ruoli.size()>0) {
			throw new RuoloEsistenteException("Ruolo gia presente nel DATABASE");
		}

		Ruolo ruolo = new Ruolo(nomeRuolo);
		ruoloDao.create(ruolo);
		return ruolo;

	}
	
	public void aggiungiImmagini(HttpServletRequest request,List<Prodotto> listResult,String uploadPath) throws Exception{
		Map<Integer,String> imagesPath = new HashMap<Integer,String>();
		if ( listResult.size()>0 ) {
			//C:// <-- questo è il percorso
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			
			for(Prodotto p : listResult) {
				//localhost:8080ecc <--
				String baseHttpUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				Blob foto = p.getBlob();
				
				String filePath = uploadPath + File.separator + p.getId();
				BlobConverter.saveFile(foto, filePath);
				imagesPath.put(p.getId(), baseHttpUrl +"/"+Costanti.UPLOAD_PATH +"/"+ p.getId());
				
			}
			request.setAttribute(Costanti.MAP_IMG, imagesPath);
			
			
		}
	}
	
	
	public String aggiungiImmagineUno(Prodotto result,String uploadPath, HttpServletRequest request) throws Exception{
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			
				Blob foto = result.getBlob();
				
				String filePath = uploadPath + File.separator + result.getId();
				BlobConverter.saveFile(foto, filePath);
				return filePath;
				
			
			
			
		
	}


}
/*
 * prendere lista utenti
 * prende il carrello
 * togliere il prodotto
 * */