package com.azienda.ecommerce.ui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.UtenteInesistenteException;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/password")
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public UpdatePasswordServlet() {
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
			
			String pssw=request.getParameter(Costanti.KEY_PASSWORD);

			Utente u =(Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
			
			if (u==null) {
				
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
				
			}else {
				
				String vecchiaPssw= request.getParameter(Costanti.KEY_VECCHIA_PASSWORD);
				bl.updatePassword(u.getNomeUtente(), pssw, vecchiaPssw);
				messaggioErrore="Password cambiata con successo!";
			}


		} catch (CampiMancantiException e) {

			e.printStackTrace();
			messaggioErrore=e.getMessage();

		}catch (UtenteInesistenteException e) {

			e.printStackTrace();
			messaggioErrore=e.getMessage();

		}catch (Exception e) {

			e.printStackTrace();
			messaggioErrore="ERRORE INASPETTATO CONTATTARE L'ASSISTENZA";

		}finally {

			request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, messaggioErrore);
			request.getRequestDispatcher("/ordini").forward(request, response);
		}
	}

}
