<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Password</title>
</head>
<body>
<%
	if(session.getAttribute(Costanti.KEY_UTENTE_LOGGATO)!=null){
		if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
%>
<p><%= request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)%> </p>
<%} %>
<form action="<%=request.getContextPath()+"/password"%>" method="post">
	
	<label>Vecchia password</label>
	<input type="text" name="<%=Costanti.KEY_VECCHIA_PASSWORD%>">
	<label>Password</label>
	<input type="password" name="<%=Costanti.KEY_PASSWORD%>">
	<input type="submit" value="invia">
	<a href="<%=request.getContextPath() + "/jsp/LoginForm.jsp"%>"> torna alla Home Page</a>
</form>

<%}else{
	response.sendRedirect(request.getContextPath()+"/jsp/LoginForm.jsp");
%>

<%}%>
</body>
</html>