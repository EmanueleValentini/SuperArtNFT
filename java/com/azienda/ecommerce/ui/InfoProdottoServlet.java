package com.azienda.ecommerce.ui;

import java.io.File;
import java.io.IOException;

import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;

import com.azienda.ecommerce.exceptions.ProdottoNonTrovatoException;

import com.azienda.ecommerce.model.Prodotto;

import com.azienda.ecommerce.utilities.Costanti;





@WebServlet("/infoprodotto")
public class InfoProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public InfoProdottoServlet() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}
	



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		String messaggioErrore = (String) request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO);
		try {
			List<Prodotto> prodotti = null;
			
			String parametroRicerca = request.getParameter(Costanti.KEY_PASSAGGIO_PRODOTTO);
			//String parametroRicercaAttributo = (String) request.getAttribute(Costanti.KEY_VALORE_RICERCA);

			
			BusinessLogic bl=(BusinessLogic)getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);


			
			String uploadPath = getServletContext().getRealPath("") + File.separator + Costanti.UPLOAD_PATH;
		
				prodotti= bl.searchByNomeProdottoUguale(parametroRicerca);
				bl.aggiungiImmagini(request, prodotti, uploadPath);

			
			
			
			
			request.setAttribute(Costanti.KEY_VALORE_RICERCA_PRODOTTI, prodotti);

		} catch (NumberFormatException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		} catch (CampiMancantiException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		}catch (ProdottoNonTrovatoException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		} catch (Exception e) {
			messaggioErrore="Problema tecnico, contattare l'assistenza.";
			e.printStackTrace();
		}finally {
			request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, messaggioErrore);
			request.getRequestDispatcher("/html/paginaprodotto.jsp").forward(request, response);

		}
	}
}
