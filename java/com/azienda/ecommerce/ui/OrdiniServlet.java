package com.azienda.ecommerce.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.OrdineNonEsistenteException;
import com.azienda.ecommerce.model.Ordine;
import com.azienda.ecommerce.model.Prodotto;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/ordini")
public class OrdiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public OrdiniServlet() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String messaggioErrore=null;

		try {	

			BusinessLogic bl=(BusinessLogic)getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);
			Utente u =(Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);

			if (u==null) {

				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;

			}else {

				List<Ordine> ordini = bl.searchOrdineByUtenteId(u.getId());
				List<Prodotto> prodotti = new ArrayList<>();
				Double somma = 0.0;

				ordini.removeIf(o -> {
					if(o.getProdotto() == null) {
						return true;
					}
					return false;
				});

				for(Ordine o : ordini) {
					prodotti.add(o.getProdotto());
					somma += o.getProdotto().getPrezzo();

				}




				//				for(Ordine o : ordini) {
				//					if(o.getProdotto() != null) {
				//					
				//					prodotti.add(o.getProdotto());
				//					somma += o.getProdotto().getPrezzo();
				//					}
				//				}
				String uploadPath = getServletContext().getRealPath("") + File.separator + Costanti.UPLOAD_PATH;


				bl.aggiungiImmagini(request, prodotti, uploadPath);

				request.setAttribute(Costanti.KEY_ORDINE, ordini);
				request.setAttribute(Costanti.KEY_PASSAGGIO_PRODOTTO, somma);
				request.getRequestDispatcher("/html/profilo.jsp").forward(request, response);
				return;
			}
		}
		catch (CampiMancantiException e) {
			e.printStackTrace();
			messaggioErrore=e.getMessage();
		}catch (OrdineNonEsistenteException e) {
			e.printStackTrace();
			messaggioErrore=e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			messaggioErrore="ERRORE INASPETTATO CONTATTARE L'ASSISTENZA";
		}
		request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, messaggioErrore);
		request.getRequestDispatcher("/html/profilo.jsp").forward(request, response);
	}

}



