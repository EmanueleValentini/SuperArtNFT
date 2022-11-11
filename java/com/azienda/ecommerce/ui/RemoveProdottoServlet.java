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

@WebServlet("/removeadmin")
public class RemoveProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public RemoveProdottoServlet() {
		super();
		// TODO Auto-generated constructor stub
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

			if (utente.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {
				bl.removeProdotto(request.getParameter(Costanti.KEY_REMOVE_PRODOTTO));
			}

		}catch (CampiMancantiException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		}
		catch (ProdottoNonTrovatoException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		}catch (ProdottoEsistenteException e) {
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
