package com.azienda.ecommerce.ui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.ProdottoEsistenteException;
import com.azienda.ecommerce.exceptions.ProdottoNonTrovatoException;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/insert")
public class InsertProdottoToCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

// 
	public InsertProdottoToCarrelloServlet() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String messaggioErrore=null;
		try {
			BusinessLogic bl=(BusinessLogic)getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);

			Utente utente=(Utente)request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
			if (utente == null) {
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}

			String prodottoDaInserire= request.getParameter(Costanti.KEY_INSERT_TO_CARRELLO);

			bl.insertProdottoToCarrello(prodottoDaInserire, utente.getId());


		}
		catch (CampiMancantiException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		}catch (ProdottoNonTrovatoException e) {
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
		}finally {
			
			request.setAttribute(Costanti.KEY_ERRORE_PRODOTTO, messaggioErrore);
			request.getRequestDispatcher("/ricerca").forward(request, response);
		}
	}
}
