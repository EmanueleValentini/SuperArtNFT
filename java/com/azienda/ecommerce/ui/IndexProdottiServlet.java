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
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.OrdineNonEsistenteException;
import com.azienda.ecommerce.model.Ordine;
import com.azienda.ecommerce.model.Prodotto;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/prodottiindex")
public class IndexProdottiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public IndexProdottiServlet() {
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
			
				List<Prodotto> prodotti = bl.searchProdottoLimit();
				if(prodotti == null) {
					prodotti = new ArrayList<>();
				}
				
				
				String uploadPath = getServletContext().getRealPath("") + File.separator + Costanti.UPLOAD_PATH;
				
				bl.aggiungiImmagini(request, prodotti, uploadPath);
				
				
				
				
				
				
				
				
				request.setAttribute(Costanti.KEY_PASSAGGIO_PRODOTTO, prodotti);
				request.getRequestDispatcher("/html/index.jsp").forward(request, response);
			
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

	}
}


