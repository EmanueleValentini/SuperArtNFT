<%@page import="com.azienda.ecommerce.model.Utente"%>
<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modifica il prodotto</title>
</head>
<body>
<%
	if(session.getAttribute(Costanti.KEY_UTENTE_LOGGATO)==null){
		if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
%>
<p><%= request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)%> </p>
<%}
	}
		Utente u = (Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
		if (u.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)) {
%>
<form action="<%=request.getContextPath()+"/updateprodotto"%>" method="post">
	<label>id</label>
	<input type="text"  name="<%=Costanti.KEY_ID_PRODOTTO%>" value="<%=request.getParameter(Costanti.KEY_ID_PRODOTTO)%>" readonly>
	<label>nome prodotto</label>
	<input type="text" name="<%=Costanti.KEY_NOME_PRODOTTO%>" value="<%=request.getParameter(Costanti.KEY_NOME_PRODOTTO)%>">
	<label>disponibile</label>
	<input type="text" name="<%=Costanti.KEY_BOOLEAN_PRODOTTO%>" value="<%=request.getParameter(Costanti.KEY_BOOLEAN_PRODOTTO)%>">
	<label>quantità</label>
	<input type="number" name="<%=Costanti.KEY_QNT_PRODOTTO%>" value="<%=request.getParameter(Costanti.KEY_QNT_PRODOTTO)%>">
	<label>path</label>
	<input type="text" name="<%=Costanti.KEY_PATH_PRODOTTO%>" value="<%=request.getParameter(Costanti.KEY_PATH_PRODOTTO)%>">
	<label>prezzo</label>
	<input type="number" name="<%=Costanti.KEY_PREZZO_PRODOTTO%>" value="<%=request.getParameter(Costanti.KEY_PREZZO_PRODOTTO)%>">
	<input type="submit" value="invia">
</form>

<%}else{
	response.sendRedirect(request.getContextPath()+"/jsp/LoginForm.jsp");
%>

<%}%>
</body>
</html>