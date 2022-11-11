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


@WebServlet("/updatecampicat")
public class UpdateCampiCategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public UpdateCampiCategoriaServlet() {
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
				
				String nomeCat = request.getParameter(Costanti.CATEGORIA_NOME);
				String id = request.getParameter(Costanti.CATEGORIA_PASSAGGIO);
				Integer idn = Integer.valueOf(id);
				
				request.setAttribute(Costanti.CATEGORIA_NOME, nomeCat);
				request.setAttribute(Costanti.CATEGORIA_PASSAGGIO, idn);

				request.getRequestDispatcher("/html/modificacategoria.jsp").forward(request, response);
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
