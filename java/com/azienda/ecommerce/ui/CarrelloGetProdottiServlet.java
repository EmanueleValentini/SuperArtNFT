package com.azienda.ecommerce.ui;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.model.Carrello;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/carrelloUtente")
public class CarrelloGetProdottiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//questa servlet prende il carrello dell utente e la manda alla jsp la quale fa vedere i prodotti nel carrello

	public CarrelloGetProdottiServlet() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String messaggioErrore = (String) request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO);
		if(messaggioErrore == null) {
			messaggioErrore = (String) request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION);
		}
		try {
			
			BusinessLogic bl=(BusinessLogic)getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);
			Utente u =(Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
			String uploadPath = getServletContext().getRealPath("") + File.separator + Costanti.UPLOAD_PATH;
			
			if (u==null) {
				
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}else {
				
				Carrello carrello=bl.searchByUtenteId(u.getId());
				request.setAttribute(Costanti.KEY_CARRELLO, carrello.getProdotti());
				bl.aggiungiImmagini(request, carrello.getProdotti(), uploadPath);
				request.setAttribute(Costanti.KEY_ERRORE_PRODOTTO, messaggioErrore);
				request.getRequestDispatcher("/html/carrello.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/html/ErroreImprevisto.jsp").forward(request, response);
		}

	}

}


