package com.azienda.ecommerce.ui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.exceptions.PasswordErrataException;

import com.azienda.ecommerce.exceptions.UtenteInesistenteException;
import com.azienda.ecommerce.model.Utente;
import com.azienda.ecommerce.utilities.Costanti;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//servelt di login, prende email e pssw dal form e chiama il metodo login, poi manda in sessione l'utente.
	public LoginServlet() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email=request.getParameter(Costanti.KEY_EMAIL);
		String pssw=request.getParameter(Costanti.KEY_PASSWORD);		
		String messaggioErrore=null;

		try {
			
			BusinessLogic bl=(BusinessLogic)getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);
			Utente utente=(Utente)request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);

			if (utente!=null) {
				response.sendRedirect(request.getContextPath()+"/html/index.jsp");
				return;
			}
			
			Utente u = bl.login(email, pssw);

			request.getSession().setAttribute(Costanti.KEY_UTENTE_LOGGATO, u);
			response.sendRedirect(request.getContextPath()+"/html/index.jsp");

			return;

		} catch (CampiMancantiException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();


		} catch (PasswordErrataException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();


		}catch (UtenteInesistenteException e) {
			messaggioErrore=e.getMessage();
			e.printStackTrace();


		}catch (Exception e) {
			e.printStackTrace();


		}
		request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, messaggioErrore);
		request.getRequestDispatcher("/html/login.jsp").forward(request, response);

		
		

		
	}
}
