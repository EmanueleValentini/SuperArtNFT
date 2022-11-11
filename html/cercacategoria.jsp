<%@page import="java.util.ArrayList"%>
<%@page import="com.azienda.ecommerce.model.Categoria"%>
<%@page import="java.util.List"%>
<%@page import="com.azienda.ecommerce.model.Utente"%>
<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Cerca Categoria</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<%	
List<Categoria> categorie = (List<Categoria>) request.getAttribute(Costanti.PASSO_CATEGORIA);
						if(categorie == null){
							categorie = new ArrayList<>();
						}


	            		if(session.getAttribute(Costanti.KEY_UTENTE_LOGGATO)==null){
	            			response.sendRedirect(request.getContextPath()+"/html/login.jsp");
	            			return;
	            		}
	            %>
<%
						Utente u = (Utente) session.getAttribute(Costanti.KEY_UTENTE_LOGGATO);
                    	if(!u.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)){
                    		response.sendRedirect(request.getContextPath()+"/html/index.jsp");
                    		return;
                    	}
                %>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
	<main>
		<div class="container">
			<div class="row">
				<div class="col-12" id="titolo-login">
					<h2>CERCA CATEGORIA</h2>
				</div>
				<div class="col-12" id="corpo-pannello">
					<div class="contenitore-form-cerca">

						<form class="cerca-form"
							action="<%=request.getContextPath()%>/ricercacatserv"
							method="post">
							<label for="categoria">Nome categoria</label> 
							<input list="categorie" name="<%= Costanti.CATEGORIA_NOME %>" id="categoria">
							<datalist id="categorie">
								<% for(Categoria c : categorie){ %>
								<option value="<%= c.getNomeCategoria() %>"><%=c.getNomeCategoria() %></optio>
								<%} %>
							</datalist>
							<button type="submit" class="cerca-opz">Cerca</button>
						</form>
						<button type="submit" class="cerca-opz">
							<a href="<%=request.getContextPath()%>/html/pannellocontrollo.jsp">Pannello
								di controllo</a>
						</button>
					</div>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>