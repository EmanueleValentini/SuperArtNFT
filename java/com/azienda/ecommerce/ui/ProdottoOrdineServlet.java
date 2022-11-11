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
import com.azienda.ecommerce.model.Ordine;
import com.azienda.ecommerce.model.Prodotto;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/prodottoordine")
public class ProdottoOrdineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public ProdottoOrdineServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String messaggioErrore=null;
		
		try {
			
			BusinessLogic bl = (BusinessLogic) getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);
			

			Utente utente =(Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
			
			if (utente==null) {
				
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}

			if (utente.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {
				
				Integer id = Integer.valueOf(request.getParameter(Costanti.KEY_NUMERO_ORDINE));
				Ordine ordine = bl.searchById(id);
				List<Prodotto> prodotto = new ArrayList<>();
				prodotto.add(ordine.getProdotto());
				String uploadPath = getServletContext().getRealPath("") + File.separator + Costanti.UPLOAD_PATH;
				bl.aggiungiImmagini(request, prodotto, uploadPath);
				request.setAttribute(Costanti.KEY_ORDINE, ordine);
				
				
				request.getRequestDispatcher("/html/risultatoordineadmin.jsp").forward(request, response);
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
