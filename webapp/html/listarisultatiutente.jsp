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
<title>Lista utenti</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
	<main>
		<%
    
	if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
%>
		<p><%= request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)%>
		</p>
		<%
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
	Utente utente= (Utente)request.getAttribute(Costanti.UTENTE); 
%>
		
		<div class="container">
			<div class="row">
				<div class="col-12" id="titolo-login">
					<h2>LISTA UTENTI</h2>
				</div>
				<div class="col-12" id="corpo-pannello">
					<div id="contenitore-tab-risultati">
						<table style="text-align: center;" border="1">
							<tr>
								<th>Nome Utente</th>
								<th>Email</th>
								<th>Rimosso?</th>
							</tr>
							<tr>
								<td><%=utente.getNomeUtente()%></td>
								<td><%=utente.getEmail()%></td>
								<td><%=utente.getRimosso()%> </td>
							</tr>
							<tr>
								<td colspan="3">
									<% if(!utente.getRimosso()){ %>
									<form action="<%=request.getContextPath()%>/removebyadminutente" method="post">
										<input type="hidden" value="<%=utente.getEmail() %>" name="<%=Costanti.KEY_EMAIL%>">
										<button type="submit">Rimuovi Utente</button>
										
									</form>
									<% } else { %>
									<form action="<%=request.getContextPath()%>/ripristinabyadminutente" method="post">
										<input type="hidden" value="<%=utente.getEmail() %>" name="<%=Costanti.KEY_EMAIL%>">
										<button type="submit">Ripristina Utente</button>
										
									</form>	
									<% } %>
									
									
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>