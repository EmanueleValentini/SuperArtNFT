<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@page import="com.azienda.ecommerce.model.Carrello"%>
<%@page import="com.azienda.ecommerce.model.Prodotto"%>
<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrello</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<%
List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute(Costanti.KEY_CARRELLO);
Map <Integer,String> img = (Map<Integer,String>)request.getAttribute(Costanti.MAP_IMG);
%>
<body>
    <jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
   
    <div class="container">
        <div class="row" id="box-carrello" >
            <div class="col-12" id="titolo-login">
                <h2>IL TUO CARRELLO</h2>
            </div>
            <div id="tabella" class="table-responsive-lg tabella-carrello">
                
                <% if(prodotti == null){
                    prodotti = new ArrayList<>();
                }%>
                            <%
	if(session.getAttribute(Costanti.KEY_UTENTE_LOGGATO)!=null){
		if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
				%>
				<p><%= request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)%> </p>
					<%} %>
                
                <%
		for (Prodotto p : prodotti) {
		%>
		<form action="<%=request.getContextPath() + "/remove"%>" method="post">
		<table>
		<tr>
    
                        <td rowspan="3"><img style="height: 150px; width: 150px" src="<%=img.get(p.getId()) %>" alt="asdasd"></td>
                        <th colspan="2" style="width: 200px;"><%=p.getNomeProdotto()%></th>
                    </tr>
                    <tr>
                        <th colspan="2" style="width: 200px;"><%=p.getPrezzo()+" ETH"%></th>
                    </tr>
                    
                    <tr>
   				
				<input type="hidden"name="<%=Costanti.KEY_REMOVE_FROM_CARRELLO%>" value="<%=p.getNomeProdotto()%>">
				<td><button class="btn-cart"type="submit" name="" value="rimuovi dal carrello">Rimuovi</button></td>
				<td><button class="btn-cart" type="submit" name="<%=Costanti.KEY_COMPRA%>" value="Compra">Checkout</button></td>
				
			</tr>
			
			</table>
			</form>
		
		<%} %>

                
            </div>
        </div>
    </div>
    <%} else {
    	response.sendRedirect(request.getContextPath()+"/html/login.jsp");

    }%>
   <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>