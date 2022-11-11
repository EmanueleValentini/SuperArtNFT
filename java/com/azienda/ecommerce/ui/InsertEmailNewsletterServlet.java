package com.azienda.ecommerce.ui;


import java.io.IOException;


import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.ProdottoEsistenteException;
import com.azienda.ecommerce.model.Newsletter;
import com.azienda.ecommerce.utilities.Costanti;
import com.azienda.ecommerce.utilities.EmailUtils;

@WebServlet("/newsemail")
public class InsertEmailNewsletterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public InsertEmailNewsletterServlet() {
		super();
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				String messaggioErrore=null;

		try {
			

			BusinessLogic bl=(BusinessLogic)getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);

			String email= request.getParameter(Costanti.KEY_PASSAGGIO_EMAIL);
			
			Newsletter n = bl.insertEmailInNewsletter(email);
			EmailUtils.sendGenericEmail("Benvenuto nella nostra newsletter!", "Ciao! questa è la conferma che ti sei iscritto alla nostra newsletter!", email, n.getCodiceSegreto());


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
			request.getRequestDispatcher("/html/index.jsp").forward(request, response);

		} 
	}

}
