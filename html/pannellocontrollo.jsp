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
    <title>Pannello di controllo</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>

				<%
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
            <div class="row" style="display: flex; justify-content: center;">
                <div class="col-12" id="titolo-login" style="margin-bottom: 5%;">
                    <h2>
                        PANNELLO DI CONTROLLO
                    </h2>
                </div>
                
                <div>
                    
                <div class="col-12" id="corpo-pannello" style="display: flex; justify-content: center; width: 50vw; height: 50.3vh;">
                    <h2 id="opzioni" style="margin-bottom: 5%;">Opzioni</h2>
                		<% 
				if(request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO)!=null){
				%>
				<p style="display: flex; justify-content: center; align-items: center"><%= request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO)%> </p>
				<%	}%>
				                		<% 
				if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
				%>
				<p style="display: flex; justify-content: center; align-items: center"><%= request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)%> </p>
				<%	}%>
				
                    
                    <div class="op1" style="overflow-y: scroll; overflow-x: none;">
                        <div class="op" id="oper-prodotti">
                            <button class="controlli"><a href="<%=request.getContextPath()%>/passocategorie">Aggiungi prodotto</a></button>
                        <div class="op" id="operazioni">
                            <button class="controlli"><a href="<%=request.getContextPath()%>/html/cercautente.jsp">Cerca utente</a></button>
                            <button class="controlli"><a href="<%=request.getContextPath()%>/html/cercaordine.jsp">Cerca ordine</a></button>
                            <button class="controlli"><a href="<%=request.getContextPath()%>/passocategoriesearch">Cerca categoria</a></button>
                            <button class="controlli"><a href="<%=request.getContextPath()%>/html/aggiungicategoria.jsp">Aggiungi categoria</a></button>
                            <button class="controlli"><a href="<%=request.getContextPath()%>/html/invianewsletter.jsp">Invia newsletter</a></button>
                            <button class="controlli"><a href="<%=request.getContextPath()%>/cercautentealladmin">Lista Utenti</a></button>
                            
                        </div>
                        </div>
                    </div>  
                </div>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>