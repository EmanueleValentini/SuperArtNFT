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
    <title>Aggiungi Categoria</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
    <main>
        <div class="container">
            <div class="row">
                <div class="col-12" id="titolo-login">
                    <h2>
                        INVIA NEWSLETTER
                    </h2>
                </div>
                <div class="col-12" id="corpo-pannello-agg" style="height: 49.3vh;">
	            <%
			if(request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO)!=null){
				%>
				<p><%= request.getAttribute(Costanti.KEY_ERRORE_PRODOTTO)%> </p>
				<%
				}%>
				
				
					            <%
			if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
				%>
				<p><%= request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)%> </p>
				<%
				}%>
				
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
                
                    <div id="contenitore-form-agg" style="display: flex; flex-direction: column; align-items: center;">
                        <form id="aggiungi-form-1" action="<%=request.getContextPath()+"/sendnewsletter"%>" method="post">
                            <label>Oggetto</label>
                            <input type="text" name="<%=Costanti.OGGETTO_EMAIL%>">
                            <label>Testo</label>
                            
                            <textarea form="aggiungi-form-1" name="<%=Costanti.TESTO_EMAIL%>"> </textarea>
          
                            
                            <button type="submit" class="cerca-opz">Manda news</button>
                            
                        </form>
                        <button class="cerca-opz"><a href="<%=request.getContextPath()%>/html/pannellocontrollo.jsp">Pannello di
                                    controllo</a></button>
                    </div>
                </div>
            </div>
        </div>
    </main>
<jsp:include page="footer.jsp"></jsp:include>
</body>

</html>