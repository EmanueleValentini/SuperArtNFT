<%@page import="java.util.List"%>
<%@page import="com.azienda.ecommerce.model.Ordine"%>
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
	List<Ordine> ordini = (List<Ordine>) request.getAttribute(Costanti.KEY_ORDINE);
	if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)==null){
	
%>
<body>
	<table>
		<tr>
		
			<th>
			nome prodotto
			</th>
			
			<th>
			prezzo
			</th>
			
			<th>
			data ordine
			</th>
			
		</tr>
		<%
		for (Ordine o : ordini) {
		%>
		
		
			<tr>
				<th>
				<%=o.getProdotto().getNomeProdotto()%>
				</th>
				
				<th>
				<%=o.getProdotto().getPrezzo()%>
				</th>
				
				<th>
				<%=o.getDataEffettuato()%>
				</th>
				
				<th>
				<img alt="prodotto"src="<%=request.getContextPath() + o.getProdotto().getPath()%>">
				</th>
			</tr>
		<%
		}
	}else{	
		%>
		<p> <%=request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION) %></p>
		<%
	}
		%>
	</table>
	<a href="<%=request.getContextPath() + "/jsp/LoginForm.jsp"%>"> torna alla Home Page</a>
</body>
</html>