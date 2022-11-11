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
import com.azienda.ecommerce.model.Categoria;
import com.azienda.ecommerce.model.Prodotto;

import com.azienda.ecommerce.utilities.Costanti;





@WebServlet("/ricerca")
public class RicercaProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public RicercaProdottoServlet() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}




	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		String messaggioErrore = (String) request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO);
		if(messaggioErrore == null) {
			messaggioErrore = (String) request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION);
		}
		try {


			String parametroRicerca = request.getParameter(Costanti.KEY_VALORE_RICERCA);
			//String parametroRicercaAttributo = (String) request.getAttribute(Costanti.KEY_VALORE_RICERCA);

			String prezzoMin=request.getParameter(Costanti.KEY_PREZZOMIN);
			String prezzoMax=request.getParameter(Costanti.KEY_PREZZOMAX);


			BusinessLogic bl=(BusinessLogic)getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);
			//parametroRicercaAttributo= parametroRicerca == null? "" : parametroRicerca;
			parametroRicerca= parametroRicerca == null? "" : parametroRicerca;
			prezzoMin = prezzoMin == null ? "" : prezzoMin;
			prezzoMax = prezzoMax == null ? "" : prezzoMax;

			List<Categoria> categorie = bl.searchCategorie();

			request.setAttribute(Costanti.PASSO_CATEGORIA, categorie);


			Double prezzoMinDouble =  (prezzoMin.isBlank()? 0.00 : Double.valueOf(prezzoMin));
			Double prezzoMaxDouble =  (prezzoMax.isBlank() ? Double.MAX_VALUE : Double.valueOf(prezzoMax));

			String uploadPath = getServletContext().getRealPath("") + File.separator + Costanti.UPLOAD_PATH;

			List<Prodotto> prodotti = bl.searchByFasciaPrezzo(prezzoMinDouble,	prezzoMaxDouble);
			bl.aggiungiImmagini(request, prodotti, uploadPath);
			if((parametroRicerca!=null&&!parametroRicerca.isBlank())){
				prodotti= bl.searchByNomeAndFasciaPrezzo(parametroRicerca,prezzoMinDouble,	prezzoMaxDouble);
				bl.aggiungiImmagini(request, prodotti, uploadPath);

			}

			request.setAttribute(Costanti.KEY_VALORE_RICERCA_PRODOTTI, prodotti);




		} catch (NumberFormatException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		} catch (CampiMancantiException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();

		}catch (ProdottoNonTrovatoException e) {
			messaggioErrore="Prodotto non trovato";
			e.printStackTrace();

		} catch (Exception e) {
			messaggioErrore="Problema tecnico, contattare l'assistenza.";
			e.printStackTrace();
		}finally {

			request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, messaggioErrore);
			request.getRequestDispatcher("/html/shop.jsp").forward(request, response);

		}
	}
}
