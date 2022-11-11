package com.azienda.ecommerce.ui;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.ProdottoEsistenteException;
import com.azienda.ecommerce.exceptions.ProdottoNonDisponibileException;
import com.azienda.ecommerce.model.Ordine;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;
import com.azienda.ecommerce.utilities.EmailUtils;


@WebServlet("/remove")
public class RemoveFromCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public RemoveFromCarrelloServlet() {
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
			Utente utente=(Utente)request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
			
			if (utente == null) {
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}

			String prodottoDaRimuovere= request.getParameter(Costanti.KEY_REMOVE_FROM_CARRELLO);
			
			if (request.getParameter(Costanti.KEY_COMPRA)!=null) {
				
				Ordine o = bl.insertOrdine(prodottoDaRimuovere,utente.getNomeUtente() ,LocalDate.now() );
				String uploadPath = getServletContext().getRealPath("") + File.separator + Costanti.UPLOAD_PATH;

				EmailUtils.sendEmail(utente.getEmail(), o, uploadPath, request, bl);
				messaggioErrore="Ordine inserito con successo!";

			}else{
				bl.removeProdottoFromCarrello(prodottoDaRimuovere, utente.getId());
				messaggioErrore="Prodotto rimosso correttamente!";

			} 

		}
		catch (CampiMancantiException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		}catch (ProdottoNonDisponibileException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		}
		catch (ProdottoEsistenteException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		}catch (NullPointerException e) {
			messaggioErrore="ERRORE NULL POINTER,CONTATTA ASSISTENZA.";
			e.printStackTrace();

		} catch (Exception e) {
			messaggioErrore="Problema tecnico, contattare l'assistenza.";
			e.printStackTrace();
		}
			
		request.setAttribute(Costanti.KEY_ERRORE_PRODOTTO, messaggioErrore);
		request.getRequestDispatcher("/carrelloUtente").forward(request, response);
		
	}
}
