package com.azienda.ecommerce.ui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.LunghezzaPasswordException;
import com.azienda.ecommerce.exceptions.RuoloInesistenteException;
import com.azienda.ecommerce.exceptions.UtenteEsistenteException;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public RegistrazioneServlet() {
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
			
			String username =request.getParameter(Costanti.KEY_USERNAME);
			String email=request.getParameter(Costanti.KEY_EMAIL);
			String pssw=request.getParameter(Costanti.KEY_PASSWORD);
			
			Utente utente1=(Utente)request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);

			if (utente1!=null) { // se utente gia loggato non ti registri
				request.getRequestDispatcher("/html/index.jsp").forward(request, response);
				return;
			}
			
			Utente utente = bl.insertUtente(username, email, pssw);
			
			request.getSession().setAttribute(Costanti.KEY_UTENTE_LOGGATO, utente);
			request.getRequestDispatcher("/html/index.jsp").forward(request, response);
			return;
			
		} catch (CampiMancantiException e) {
			
			e.printStackTrace();
			 messaggioErrore=e.getMessage();
			 
		}catch (LunghezzaPasswordException e) {
			
			e.printStackTrace();
			messaggioErrore=e.getMessage();
			
		}catch (RuoloInesistenteException e) {
			
			e.printStackTrace();
			messaggioErrore=e.getMessage();
			
		}catch (UtenteEsistenteException e) {
			
			e.printStackTrace();
			messaggioErrore=e.getMessage();
			
		}catch (Exception e) {
			
			e.printStackTrace();
			messaggioErrore="ERRORE INASPETTATO CONTATTARE L'ASSISTENZA";
			
		}
			
			request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, messaggioErrore);
			request.getRequestDispatcher("/html/registrati.jsp").forward(request, response);
		
	}

}
