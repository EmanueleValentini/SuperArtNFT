<%@page import="com.azienda.ecommerce.model.Prodotto"%>
<%@page import="com.azienda.ecommerce.model.Carrello"%>
<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Carrello</title>
</head>
<%
Carrello carrello = (Carrello) request.getAttribute(Costanti.KEY_CARRELLO);
%>
<body>
	<table>
		<tr>
			<th>nome prodotto</th>
			<th>prezzo</th>
		</tr>
		<%
		if (request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO) != null) {
		%>
		<p><%=request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO)%>
		</p>
		<%
		}
		%>
		<%
		for (Prodotto p : carrello.getProdotti()) {
		%>
		<form action="<%=request.getContextPath() + "/remove"%>" method="post">
			<tr>
				<th>
				<%=p.getNomeProdotto()%>
				</th>
				
				<th>
				<%=p.getPrezzo()%>
				</th>
				
				<th>
				<img alt="prodotto"	src="<%=request.getContextPath() + p.getPath()%>">
				</th>
				
				<th>
				<input type="hidden"name="<%=Costanti.KEY_REMOVE_FROM_CARRELLO%>" value="<%=p.getNomeProdotto()%>">
				<input type="submit" name="" value="rimuovi dal carrello"> 
				<input type="submit" name="<%=Costanti.KEY_COMPRA%>" value="Compra">
				</th>
			</tr>
		</form>
		<%
		}
		%>
	</table>
	<a href="<%=request.getContextPath() + "/jsp/LoginForm.jsp"%>">torna alla Home Page</a>
</body>
</html>