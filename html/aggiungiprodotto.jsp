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
    <title>Aggiungi prodotto</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<%  List<Categoria> categorie = (List<Categoria>) request.getAttribute(Costanti.PASSO_CATEGORIA);
	if(categorie == null){
		request.setAttribute(Costanti.KEY_ERRORE_PRODOTTO, "ATTENZIONE: Nessuna categoria esistente!");
	}


%>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
    <main>
        <div class="container">
            <div class="row">
                <div class="col-12" id="titolo-login">
                    <h2>
                        AGGIUNGI PRODOTTO
                    </h2>
                </div>
                <div class="col-12" id="corpo-pannello-agg">
	            <%
			if(request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO)!=null){
				%>
				<p><%= request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO)%> </p>
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
                %>
                
                    <div id="contenitore-form-agg">
                        <form id="aggiungi-form" action="<%=request.getContextPath()+"/insertprodotto"%>" method="post"  enctype="multipart/form-data">
                  			<label for="immagine">Immagine prodotto</label><br>
                            <input type="file" name="immagine" id="img-prodotto">
                            <label for="nome">Nome prodotto</label>
                            <input type="text" name="<%=  Costanti.KEY_NOME_PRODOTTO %>" id="nome-prodotto">
                            <label for="categoria">Categoria</label>
                            <select name="<%=Costanti.KEY_CAT_PRODOTTO %>">
                            <%for(Categoria c : categorie){
                            	%>
                            
                            <option value="<%= c.getNomeCategoria() %>"><%= c.getNomeCategoria() %></option>
                            
                            <% } %>
                            
                            </select>
                            <label for="descrizione">Descrizione prodotto</label>
                            <textarea form="aggiungi-form" name="<%= Costanti.KEY_DESC_PRODOTTO %>" id="nome-prodotto" maxlength="255"> </textarea>
                            <label for="prezzo">Prezzo prodotto</label>
                            <input type="number" name="<%=  Costanti.KEY_PREZZO_PRODOTTO %>" id="prezzo-prodotto">
                            <label>Disponibilità</label>
                            <select name="<%=Costanti.KEY_BOOLEAN_PRODOTTO %>">
                             	<option value="true">true</option>
                            	<option value="false">false</option>               
                            </select>
                            <label for="quantita">Quantità</label>
                            <input type="number" name="<%=  Costanti.KEY_QNT_PRODOTTO %>" id="quantita-prodotto">
                            
                            <button type="submit" class="agg-opz">Aggiungi prodotto</button>
                            <button type="submit" class="agg-opz"><a href="<%=request.getContextPath()%>/html/pannellocontrollo.jsp">Pannello di
                                    controllo</a></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"></jsp:include>
</body>

</html>