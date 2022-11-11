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
import com.azienda.ecommerce.exceptions.UtenteInesistenteException;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.BlobConverter;
import com.azienda.ecommerce.utilities.Costanti;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,maxFileSize = 1024 * 1024 * 10,maxRequestSize = 1024 * 1024 * 10 * 5)
@WebServlet("/updateprodotto")
public class UpdateProdottoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public UpdateProdottoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String messaggioErrore = (String) request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO);
		if(messaggioErrore == null) {
			messaggioErrore = (String) request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION);
		}		Blob blob = null;
		
		try {
			
			BusinessLogic bl = (BusinessLogic) getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);

			Utente utente =(Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
			
			if (utente==null) {
				
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}

			if (utente.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {
				
				Integer quantita = Integer.valueOf(request.getParameter(Costanti.KEY_QNT_PRODOTTO));
				String nomeProdotto = request.getParameter(Costanti.KEY_NOME_PRODOTTO);
				Double prezzo =Double.valueOf(request.getParameter(Costanti.KEY_PREZZO_PRODOTTO)) ;
				Boolean isDisponibile = Boolean.valueOf(request.getParameter(Costanti.KEY_BOOLEAN_PRODOTTO));
				String descrizione = request.getParameter(Costanti.KEY_DESC_PRODOTTO);
				String categoria = request.getParameter(Costanti.CATEGORIA_NOME);

				String test = request.getParameter(Costanti.KEY_ID_PRODOTTO);
				Integer id = Integer.parseInt(test);
				
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
				
				bl.updateCompletoProdotto(categoria,id, descrizione, blob, nomeProdotto, quantita, prezzo, isDisponibile);
			}

		} catch (CampiMancantiException e) {

			e.printStackTrace();
			messaggioErrore=e.getMessage();

		}catch (UtenteInesistenteException e) {

			e.printStackTrace();
			messaggioErrore=e.getMessage();

		}catch (NullPointerException e) {

			e.printStackTrace();
			messaggioErrore="Hai sbagliato a compilare uno dei campi, riprova!";

		}catch (NumberFormatException e) {

			e.printStackTrace();
			messaggioErrore="Hai sbagliato a compilare uno o più campi!";

		}catch (Exception e) {

			e.printStackTrace();
			messaggioErrore="ERRORE INASPETTATO CONTATTARE L'ASSISTENZA";

		}

			request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, messaggioErrore);
			request.getRequestDispatcher("/ricerca").forward(request, response);
		
	}

}
