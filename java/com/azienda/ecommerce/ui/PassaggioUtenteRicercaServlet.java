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


@WebServlet("/passaggioutente")
public class PassaggioUtenteRicercaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public PassaggioUtenteRicercaServlet() {
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
				
				
				String email = request.getParameter(Costanti.KEY_EMAIL);
				
				Utente u = bl.searchByEmail(email);
				List<Ordine> ordini = bl.searchOrdineByUtenteId(u.getId());
				List<Prodotto> prodotti = new ArrayList<>();
				Double somma = 0.0;
				for(Ordine o : ordini) {
					prodotti.add(o.getProdotto());
					somma += o.getProdotto().getPrezzo();
				}
				
				request.setAttribute(Costanti.KEY_PASSAGGIO_UTENTE,u);
				request.setAttribute(Costanti.KEY_ORDINE,ordini);
				request.setAttribute(Costanti.KEY_PASSAGGIO_PRODOTTO, somma);

				String uploadPath = getServletContext().getRealPath("") + File.separator + Costanti.UPLOAD_PATH;

				bl.aggiungiImmagini(request, prodotti, uploadPath);

				
				
				request.getRequestDispatcher("/html/profiloadmin.jsp").forward(request, response);
				return;
			}else {
				
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}
			
		}catch (Exception e) {

			e.printStackTrace();
			messaggioErrore="ERRORE INASPETTATO CONTATTARE L'ASSISTENZA";

		}finally {

			request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, messaggioErrore);
			
		}
		request.getRequestDispatcher("/html/pannellocontrollo.jsp").forward(request, response);

		
	}

}
