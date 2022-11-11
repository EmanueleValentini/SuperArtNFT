package com.azienda.ecommerce.ui;


import java.io.IOException;


import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.UtenteInesistenteException;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/sendnewsletter")
public class SendEmailNewsletterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public SendEmailNewsletterServlet() {
		super();
	
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String messaggioErrore = (String) request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO);
		
		
		try {
			Utente utente=(Utente)request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);

			if (utente == null) {
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}
			
			if(!utente.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}
			
			
			BusinessLogic bl = (BusinessLogic) getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);
			
			String oggetto = request.getParameter(Costanti.OGGETTO_EMAIL);
			String testo = request.getParameter(Costanti.TESTO_EMAIL);
			
			bl.sendEmailToAll(oggetto, testo);
			messaggioErrore = "News mandata con successo a tutti gli iscritti!";

		} catch (CampiMancantiException e) {

			e.printStackTrace();
			messaggioErrore=e.getMessage();

		}catch (UtenteInesistenteException e) {

			e.printStackTrace();
			messaggioErrore=e.getMessage();

		}catch (NullPointerException e) {

			e.printStackTrace();
			messaggioErrore="Hai sbagliato a compilare uno dei campi, riprova!";

		}catch (NumberFormatException e) {

			e.printStackTrace();
			messaggioErrore="Hai sbagliato a compilare uno o più campi!";

		}catch (Exception e) {

			e.printStackTrace();
			messaggioErrore="ERRORE INASPETTATO CONTATTARE L'ASSISTENZA";

		}

			request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, messaggioErrore);
			request.getRequestDispatcher("/html/invianewsletter.jsp").forward(request, response);
		
	}

}
