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


@WebServlet("/cercautenteusername")
public class CercaUtenteUsernameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public CercaUtenteUsernameServlet() {
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
	
			Utente utente =(Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
			
			if (utente==null) {
				
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}

			if (utente.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {
				
				String username = request.getParameter(Costanti.KEY_USERNAME);
				
				request.setAttribute(Costanti.UTENTE, bl.searchByNomeUtente(username));
				
				request.getRequestDispatcher("/html/listarisultatiutente.jsp").forward(request, response);
				return;
			}else {
				
				request.getRequestDispatcher("/html/login.jsp").forward(request, response);
				return;
			}
			
		}catch (NumberFormatException e) {

			e.printStackTrace();
			messaggioErrore="Hai inserito parametri errati";

		}catch (CampiMancantiException e) {

			e.printStackTrace();
			messaggioErrore=e.getMessage();

		}catch (NullPointerException e) {

			e.printStackTrace();
			messaggioErrore="Uno o più campi vuoti";

		}catch (UtenteInesistenteException e) {
			
			e.printStackTrace();
			messaggioErrore=e.getMessage();
		
		}catch (Exception e) {

			e.printStackTrace();
			messaggioErrore="ERRORE INASPETTATO CONTATTARE L'ASSISTENZA";

		}finally {

			request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, messaggioErrore);
			
		}
		
		
		request.getRequestDispatcher("/html/cercautente.jsp").forward(request, response);
		
	}

}
