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
    <title>Modifica prodotto</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<%
	List<Categoria> categorie = (List<Categoria>)request.getAttribute(Costanti.PASSO_CATEGORIA);

	if(categorie == null){
		request.setAttribute(Costanti.KEY_MESSAGIO_EXCEPTION, "ATTENZIONE, NON CI SONO CATEGORIE");
		categorie = new ArrayList<>();
	}


%>
    <jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
		            <%
						if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
				%>
				<p><%= request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)%> </p>
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
    <main>
        <div class="container">
            <div class="row">
                <div class="col-12" id="titolo-login">
                    <h2>
                        MODIFICA PRODOTTO
                    </h2>
                </div>
                <div class="col-12" id="corpo-pannello-agg">
                    <div id="contenitore-form-agg" style="display: flex; justify-content: center;" >
                        <form action="<%=request.getContextPath()%>/updateprodotto" method="post"  enctype="multipart/form-data" style="display: flex; flex-direction: column;" id="aggiungi-form-immagine" >
                            <label for="immagine">Immagine prodotto</label><br>
                            <input type="file" name="immagine" id="img-prodotto">
                            <input type="hidden" name="<%=Costanti.KEY_ID_PRODOTTO%>" value="<%=request.getAttribute(Costanti.KEY_ID_PRODOTTO) %>">                               
                            <label>Nome prodotto</label>
                            <input type="text" name="<%=Costanti.KEY_NOME_PRODOTTO %>" value="<%=request.getAttribute(Costanti.KEY_NOME_PRODOTTO)%>"id="nome-prodotto">
                            <label for="categoria">Categoria</label>
                            <select name="<%=Costanti.CATEGORIA_NOME %>">
                            	<%for(Categoria c : categorie){ 
                            	
                            		String cat = (String) request.getAttribute(Costanti.CATEGORIA_NOME);
                            	
                            	if(c.getNomeCategoria().equals(cat)){
                            	%>
                            	<option selected="selected" value="<%= c.getNomeCategoria() %>"><%=c.getNomeCategoria() %></option>
                            	
                            	<%}else{ %>
                            	<option value="<%= c.getNomeCategoria() %>"><%=c.getNomeCategoria() %></option>
                            	
                            	<%} %>
                            	
                            <%} %>
                            
                            </select>
                            <label for="descrizione">Descrizione prodotto</label>
                            <textarea form="aggiungi-form-immagine" name="<%=Costanti.KEY_DESC_PRODOTTO%>" id="nome-prodotto" maxlength="255"><%=request.getAttribute(Costanti.KEY_DESC_PRODOTTO)%></textarea>
                            
                            <label>Prezzo prodotto</label>
                            <input type="number" name="<%=Costanti.KEY_PREZZO_PRODOTTO %>" value="<%=request.getAttribute(Costanti.KEY_PREZZO_PRODOTTO)%>" id="prezzo-prodotto">
                            <label>Quantità</label>
                            <input type="number" name="<%=Costanti.KEY_QNT_PRODOTTO %>" value="<%=request.getAttribute(Costanti.KEY_QNT_PRODOTTO)%>" id="quantita-prodotto">
                           	Disponibile<select id="selects" name="<%=Costanti.KEY_BOOLEAN_PRODOTTO %>">
                             	<option value="true">true</option>
                            	<option value="false">false</option>               
                            </select><br><br>
                            <button type="submit" class="agg-opz">Modifica prodotto</button>
                            <button class="agg-opz"><a href="<%=request.getContextPath()%>/html/pannellocontrollo.jsp">Pannello di controllo</a></button>
                        </form>
                            

                    </div>  
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>