package com.azienda.ecommerce.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.OrdineNonEsistenteException;
import com.azienda.ecommerce.model.Categoria;
import com.azienda.ecommerce.model.Ordine;
import com.azienda.ecommerce.model.Prodotto;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/ricercacatserv")
public class RicercaCategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public RicercaCategoriaServlet() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		@SuppressWarnings("unused")
		String messaggioErrore=null;
		
		try {	
			
			BusinessLogic bl=(BusinessLogic)getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);
			Utente u =(Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
			
			if (u==null||!u.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {
				
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
				
			}else {
				String nomeCategoria = request.getParameter(Costanti.CATEGORIA_NOME);
				List<Categoria>	categorie = bl.searchCategoria(nomeCategoria);
				request.setAttribute(Costanti.CATEGORIA_PASSAGGIO, categorie.get(0));
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
		request.getRequestDispatcher("/html/risultatocategoriaadmin.jsp").forward(request, response);
	}

	
}


