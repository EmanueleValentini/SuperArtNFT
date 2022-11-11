<%@page import="com.azienda.ecommerce.model.Utente"%>
<%@page import="java.util.List"%>
<%@page import="com.azienda.ecommerce.model.Prodotto"%>
<%@page import="com.azienda.ecommerce.model.Carrello"%>
<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Risultati ricerca</title>
</head>
<%
List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute(Costanti.KEY_VALORE_RICERCA_PRODOTTI);
%>
<body>
	<table>
		<tr>
			<th>nome prodotto</th>
			<th>prezzo</th>
		</tr>
		<%
		if (request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION) == null) {
		%>
		<%=request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO) == null ? ""
		: request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO)%>
		<%
		for (Prodotto p : prodotti) {
		%>
		<form action="<%=request.getContextPath() + "/insert"%>" method="post">
			<tr>
				<th><%=p.getNomeProdotto()%></th>
				<th><%=p.getPrezzo()%></th>
				<th><img alt="prodotto"
					src="<%=request.getContextPath() + p.getPath()%>"></th>
				<%
				if (p.getDisponibile() == true && p.getQuantita() > 0) {
				%>
				<th><input type="submit" name="" value="aggiungi al carrello">
				</th>
				<%
				}
				%>
				<input type="hidden" name="<%=Costanti.KEY_INSERT_TO_CARRELLO%>" value="<%=p.getNomeProdotto()%>">
			
		</form>
		<%
		Utente u = (Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
		if (u.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {
		%>
		<th>
		<form action="<%=request.getContextPath() + "/removeadmin"%>" method="post">
			<button type="submit" name="<%=Costanti.KEY_REMOVE_PRODOTTO%>" value="<%=p.getNomeProdotto()%>">rimuovi</button>
		</form>
		<form action="<%=request.getContextPath() + "/updatecampi"%>" method="post">
			
			<input type="hidden" name="<%=Costanti.KEY_NOME_PRODOTTO%>" value="<%=p.getNomeProdotto()%>">
			
			<input type="hidden" name="<%=Costanti.KEY_BOOLEAN_PRODOTTO%>"value="<%=p.getDisponibile()%>">
			
			<input type="hidden" name="<%=Costanti.KEY_QNT_PRODOTTO%>"value="<%=p.getQuantita()%>">
			
			<input type="hidden" name="<%=Costanti.KEY_PATH_PRODOTTO%>"value="<%=p.getPath()%>">
			
			<input type="hidden" name="<%=Costanti.KEY_PREZZO_PRODOTTO%>"value="<%=p.getPrezzo()%>">
			
			<input type="hidden" name="<%=Costanti.KEY_ID_PRODOTTO%>"value="<%=p.getId()%>">

			<button type="submit" name="<%=Costanti.KEY_REMOVE_PRODOTTO%>">modifica</button>
		</form>
		</th>
		</tr>
		<%
		}
		}
		} else {
		%>
		<p>
			<%=request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)%></p>
		<%
		}
		%>
	</table>
	<a href="<%=request.getContextPath() + "/carrelloUtente"%>"> vai al carrello</a>
	<a href="<%=request.getContextPath() + "/jsp/LoginForm.jsp"%>">torna alla Home Page</a>
</body>
</html>