package com.azienda.ecommerce.ui;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/cercautentealladmin")
public class CercaUtenteAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public CercaUtenteAdminServlet() {
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
				
				List<Utente> utenti = bl.searchUtente();
				
				request.setAttribute(Costanti.LISTA_ALL_UTENTI, utenti);
				
				request.getRequestDispatcher("/html/listautentiadmin.jsp").forward(request, response);
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
