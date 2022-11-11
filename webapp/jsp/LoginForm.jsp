<%@page import="com.azienda.ecommerce.model.Utente"%>
<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
<%
	if(session.getAttribute(Costanti.KEY_UTENTE_LOGGATO)==null){
		if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
%>
<p><%= request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)%> </p>
<%} %>
<form action="<%=request.getContextPath()+"/login"%>" method="post">
	<label>Email</label>
	<input type="text" name="<%=Costanti.KEY_EMAIL%>">
	<label>Password</label>
	<input type="password" name="<%=Costanti.KEY_PASSWORD%>">
	<input type="submit" value="invia">
	<a href="<%=request.getContextPath()+"/jsp/Registrazione.jsp"%>">Registrati</a>
</form>
<%
}else{
	
	Utente u = (Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
	if (u.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {
	
%>
<a href="<%=request.getContextPath()+"/jsp/InsertProdotto.jsp"%>"> aggiungi prodotto</a>
<%} %>
<a href="<%=request.getContextPath()+"/logout"%>"> logout</a>
<a href="<%=request.getContextPath()+"/carrelloUtente"%>"> vai al carrello</a>
<a href="<%=request.getContextPath()+"/ordini"%>"> Ordini effettuati</a>
<a href="<%=request.getContextPath()+"/jsp/Password.jsp"%>">Cambia Password</a>
<form action="<%=request.getContextPath()+"/ricerca"%>" method="post">
<label>barra di ricerca</label>
<input type="text" name="<%=Costanti.KEY_VALORE_RICERCA%>">
<label>prezzo massimo</label>
<input type="number" name="<%=Costanti.KEY_PREZZOMAX%>">
<label>prezzo minimo</label>
<input type="number" name="<%=Costanti.KEY_PREZZOMIN%>">
<input type="submit" value="invia">
</form>
<%}%>
</body>
</html>