<%@page import="java.util.Map"%>
<%@page import="com.azienda.ecommerce.model.Prodotto"%>
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
    <title>Pagina prodotto</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
	<%
List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute(Costanti.KEY_VALORE_RICERCA_PRODOTTI);
Map <Integer,String> img = (Map<Integer,String>)request.getAttribute(Costanti.MAP_IMG);
%>
    <main>
        <div class="container">
            <div class="row">
                <div class="col-12" id="titolo-login">
                    <h2>
                        DETTAGLIO
                    </h2>
                </div>
                <div class="col-12 tabella-animata-prodotto" id="corpo-pannello-dettaglio">
                    <form action="<%=request.getContextPath() + "/insert"%>" method="post" id="contenitore-form-agg">
                        <table style="text-align: center;">
                            <input type="hidden" name="<%= Costanti.KEY_INSERT_TO_CARRELLO %>" value="<%= prodotti.get(0).getNomeProdotto() %>"></input>
							
                            <tr>
                                <td colspan="10"><img width="500px" height="500px" src="<%= img.get(prodotti.get(0).getId())%>" alt="asdasd"></td>
                                
                            </tr>
                            <tr style="border-radius: 12px;">
                            	<td></td>
                            	<td></td>
                            	<td></td>
                            	<td></td>
                            	<th style="">Categoria:</th>
                                <th style=""><%= prodotti.get(0).getCategoria().getNomeCategoria() %></th>
                            	
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr style="border-radius: 12px;"> 
                            	<td></td>
                            	<td></td>
                            	<td></td>
                            	<td></td>
                            	<th style="">Nome Prodotto:</th>
                                <th style=""><%= prodotti.get(0).getNomeProdotto() %></th>
                            	
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr style="border-radius: 12px;"> 
                            	<td></td>
                            	<td></td>
                            	<td></td>
                            	<td></td>
                            	<th style="">Descrizione:</th>
                                <th style=""><%= prodotti.get(0).getDescrizione() %></th>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr style="border-radius: 12px;"> 
                            	<td></td>
                            	<td></td>
                            	<td></td>
                            	<td></td>
                            	<th style="">Prezzo:</th>
                                <th style=""><%= prodotti.get(0).getPrezzo()+" ETH" %></th>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            
                            
                            
                            <%
                    		Utente u = (Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);

				if (prodotti.get(0).getDisponibile() == true && prodotti.get(0).getQuantita() > 0 && u != null) {
				%>
                            <tr>
                                <td colspan="10"><button type="submit" class="btn-cart">AGGIUNGI AL CARRELLO</button></td>
                            </tr>
                            
                            
                            <%} %>
                            
                        </table>
                                            </form>  
                        		<%
        Prodotto p = prodotti.get(0);
		if (u != null && u.getRuolo().getNomeRuolo().equals(Costanti.ADMIN) ) {
		%>
		<div class="col-12" style="display: flex; justify-content: center;">
		<table>
		<tr>
		<td>
		<form action="<%=request.getContextPath() + "/removeadmin"%>" method="post">
			<input type="hidden" name="<%=Costanti.KEY_REMOVE_PRODOTTO%>" value="<%=p.getNomeProdotto()%>">
			<button type="submit" class="agg-opz">Rimuovi</button>
		</form>
		</td>
		<td>
		<form action="<%=request.getContextPath() + "/updatecampi"%>" method="post">
			
			<input type="hidden" name="<%=Costanti.KEY_NOME_PRODOTTO%>" value="<%=p.getNomeProdotto()%>">
			
			<input type="hidden" name="<%=Costanti.KEY_BOOLEAN_PRODOTTO%>"value="<%=p.getDisponibile()%>">
			
			<input type="hidden" name="<%=Costanti.KEY_QNT_PRODOTTO%>"value="<%=p.getQuantita()%>">
			
			
			<input type="hidden" name="<%=Costanti.KEY_PREZZO_PRODOTTO%>"value="<%=p.getPrezzo()%>">
			
			<input type="hidden" name="<%=Costanti.KEY_ID_PRODOTTO%>" value="<%=p.getId()%>">
			<input type="hidden" name="<%=Costanti.KEY_DESC_PRODOTTO%>" value="<%=p.getDescrizione()%>">
			<input type="hidden" name="<%=Costanti.CATEGORIA_NOME%>" value="<%=p.getCategoria().getNomeCategoria()%>">

			<button type="submit" class="agg-opz" name="<%=Costanti.KEY_REMOVE_PRODOTTO%>">Modifica</button>
		</form>
		</td>
		</tr>
		</table>
		</div>


                    		<%
		}else {
			
			
		}
		%>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>