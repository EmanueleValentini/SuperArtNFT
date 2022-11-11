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

@MultipartConfig(fileSizeThreshold = 1024 * 1024,maxFileSize = 1024 * 1024 * 10,maxRequestSize = 1024 * 1024 * 10 * 5)
@WebServlet("/insertprodotto")
public class InsertProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//DA FARE I CATCH!!! + TEST
	public InsertProdottoServlet() {
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
			Blob blob = null;
			Integer quantita = Integer.valueOf(request.getParameter(Costanti.KEY_QNT_PRODOTTO));
			String nomeProdotto = request.getParameter(Costanti.KEY_NOME_PRODOTTO);
			Double prezzo =Double.valueOf(request.getParameter(Costanti.KEY_PREZZO_PRODOTTO)) ;
			String descrizione = request.getParameter(Costanti.KEY_DESC_PRODOTTO);
			String categoria = request.getParameter(Costanti.KEY_CAT_PRODOTTO);

			Boolean isDisponibile = Boolean.valueOf(request.getParameter(Costanti.KEY_BOOLEAN_PRODOTTO)); // va presa o di base è disponibile?(se la qnt >0) 
			String uploadPath = getServletContext().getRealPath("") + File.separator + Costanti.UPLOAD_PATH;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			
			String filePath = null;
			for ( Part part : request.getParts() ) {
				String fileName = part.getSubmittedFileName();
				if ( fileName!=null && !fileName.isEmpty() ) {
					filePath = uploadPath + File.separator + fileName;
					part.write(filePath);
					 blob = BlobConverter.generateBlob(filePath);
					break;
				}
			}

			if (utente.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {

				bl.insertProdotto(categoria,quantita, nomeProdotto, descrizione, blob, prezzo, isDisponibile);
				messaggioErrore="Prodotto aggiunto correttamente!";
	
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
			request.getRequestDispatcher("/html/pannellocontrollo.jsp").forward(request, response);

		} 
	}

}
