package com.azienda.ecommerce.ui;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.UtenteInesistenteException;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.BlobConverter;
import com.azienda.ecommerce.utilities.Costanti;

@WebServlet("/updatecat")
public class UpdateCategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public UpdateCategoriaServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String messaggioErrore = (String) request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO);
		if(messaggioErrore == null) {
			messaggioErrore = (String) request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION);
		}		Blob blob = null;
		
		try {
			
			BusinessLogic bl = (BusinessLogic) getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);

			Utente utente =(Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
			
			if (utente==null) {
				
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}

			if (utente.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {
				
				String nomeCat = request.getParameter(Costanti.CATEGORIA_NOME);
				Integer id = Integer.valueOf(request.getParameter(Costanti.CATEGORIA_PASSAGGIO));
				
				bl.updateCat(id, nomeCat);
				messaggioErrore = "Aggiornamento categoria effettuato con successo!";
	
				
			}

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
			request.getRequestDispatcher("/html/pannellocontrollo.jsp").forward(request, response);
		
	}

}
