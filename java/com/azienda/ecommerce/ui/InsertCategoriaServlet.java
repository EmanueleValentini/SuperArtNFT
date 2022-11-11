package com.azienda.ecommerce.ui;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.ProdottoEsistenteException;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.BlobConverter;
import com.azienda.ecommerce.utilities.Costanti;

@WebServlet("/insertcategoria")
public class InsertCategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//DA FARE I CATCH!!! + TEST
	public InsertCategoriaServlet() {
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
		
			String categoria = request.getParameter(Costanti.CATEGORIA_NOME);


			if (utente.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {

				bl.insertCategoria(categoria);
				messaggioErrore="Categoria aggiunta correttamente!";
	
			}

		}catch (CampiMancantiException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		}catch (NumberFormatException e) {
			messaggioErrore="inserisci un numero,non una lettera!";
			e.printStackTrace();

		}catch (ProdottoEsistenteException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		} catch (Exception e) {
			messaggioErrore="Problema tecnico, contattare l'assistenza.";
			e.printStackTrace();
		}finally {
			request.setAttribute(Costanti.KEY_ERRORE_PRODOTTO, messaggioErrore);
			request.getRequestDispatcher("/html/aggiungicategoria.jsp").forward(request, response);

		} 
	}

}
