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
    <title>Modifica categoria</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
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
                        MODIFICA CATEGORIA
                    </h2>
                </div>
                <div class="col-12" id="corpo-pannello-agg" style="height: 49.3vh;">
                    <div id="contenitore-form-agg" style="display: flex; flex-direction: column; align-items: center;" >
                        <form action="<%=request.getContextPath()%>/updatecat" method="post"  style="display: flex; flex-direction: column;" id="aggiungi-form-immagine" >

                            <label for="categoria">Categoria</label>
                            <input type="hidden" value="<%=request.getAttribute(Costanti.CATEGORIA_PASSAGGIO)%>" name="<%=Costanti.CATEGORIA_PASSAGGIO%>">             
                        
                            <input type="text" value="<%=request.getAttribute(Costanti.CATEGORIA_NOME) %>" name="<%= Costanti.CATEGORIA_NOME%>">             
                            <button type="submit" class="cerca-opz">Applica modifiche</button>
                            
                        </form>
                        <button class="cerca-opz"><a href="<%=request.getContextPath()%>/html/pannellocontrollo.jsp">Pannello di controllo</a></button>
                            

                    </div>  
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>