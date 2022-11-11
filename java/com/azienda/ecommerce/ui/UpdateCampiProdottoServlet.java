package com.azienda.ecommerce.ui;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.model.Categoria;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/updatecampi")
public class UpdateCampiProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public UpdateCampiProdottoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String messaggioErrore=null;
		
		try {
			

			Utente utente =(Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
			
			if (utente==null) {
				
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}

			if (utente.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {
				BusinessLogic bl = (BusinessLogic) request.getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);
				
				Integer id = Integer.valueOf(request.getParameter(Costanti.KEY_ID_PRODOTTO));
				Integer quantita = Integer.valueOf(request.getParameter(Costanti.KEY_QNT_PRODOTTO));
				String nomeProdotto = request.getParameter(Costanti.KEY_NOME_PRODOTTO);
				Double prezzo =Double.valueOf(request.getParameter(Costanti.KEY_PREZZO_PRODOTTO)) ;
				Boolean isDisponibile = Boolean.valueOf(request.getParameter(Costanti.KEY_BOOLEAN_PRODOTTO));
				String descrizione = request.getParameter(Costanti.KEY_DESC_PRODOTTO);
				String categoria = request.getParameter(Costanti.CATEGORIA_NOME);
				List<Categoria> categorie = bl.searchCategorie();

				
				request.setAttribute(Costanti.PASSO_CATEGORIA, categorie);
				request.setAttribute(Costanti.KEY_ID_PRODOTTO, id);
				request.setAttribute(Costanti.KEY_QNT_PRODOTTO, quantita);
				request.setAttribute(Costanti.KEY_NOME_PRODOTTO, nomeProdotto);
				request.setAttribute(Costanti.KEY_PREZZO_PRODOTTO, prezzo);
				request.setAttribute(Costanti.KEY_BOOLEAN_PRODOTTO, isDisponibile);
				request.setAttribute(Costanti.KEY_DESC_PRODOTTO, descrizione);
				request.setAttribute(Costanti.CATEGORIA_NOME, categoria);

				request.getRequestDispatcher("/html/modificaprodotto.jsp").forward(request, response);
				return;
			}else {
				
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
			}
			
		}catch (NumberFormatException e) {

			e.printStackTrace();
			messaggioErrore="Hai inserito parametri errati";

		}catch (Exception e) {

			e.printStackTrace();
			messaggioErrore="ERRORE INASPETTATO CONTATTARE L'ASSISTENZA";

		}finally {

			request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, messaggioErrore);
			
		}
		
	}

}
