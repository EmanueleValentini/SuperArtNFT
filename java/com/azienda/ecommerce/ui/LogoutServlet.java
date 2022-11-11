package com.azienda.ecommerce.ui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public LogoutServlet() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Utente utenteLoggato= (Utente)request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
		
		if (utenteLoggato!=null) {
			request.getSession().removeAttribute(Costanti.KEY_UTENTE_LOGGATO);
		}
		request.getRequestDispatcher("/html/login.jsp").forward(request, response);	
	}

}
