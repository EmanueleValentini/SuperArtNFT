package com.azienda.ecommerce.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.exceptions.CampiMancantiException;
import com.azienda.ecommerce.utilities.Costanti;

@WebServlet("/disiscriviti")
public class DisiscriviServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			
			BusinessLogic bl=(BusinessLogic)getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);
			String code = req.getParameter(Costanti.KEY_CODICE_SEGRETO);
			bl.removeNewsletter(code);
			
			
			
		}catch (CampiMancantiException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			req.getRequestDispatcher("/html/index.jsp").forward(req, resp);

		}
		
		
	}
	
}
