package com.azienda.ecommerce.ui;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.azienda.ecommerce.bl.BusinessLogic;
import com.azienda.ecommerce.model.Categoria;
import com.azienda.ecommerce.utilities.Costanti;

@WebServlet("/passocategorie")
public class PassoCategorieServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			BusinessLogic bl = (BusinessLogic) req.getServletContext().getAttribute(Costanti.BUSINESS_LOGIC);
			List<Categoria> categorie = bl.searchCategorie();
			
			req.setAttribute(Costanti.PASSO_CATEGORIA, categorie);
			
			req.getRequestDispatcher("/html/aggiungiprodotto.jsp").forward(req, resp);
			
			
		} catch (Exception e) {
			req.getRequestDispatcher("/html/paginaerrore.html").forward(req, resp);

		}
		
	}
}
