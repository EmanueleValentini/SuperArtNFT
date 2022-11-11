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
    <title>Cerca ordine</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
    <main>
        <div class="container">
            <div class="row">
                <div class="col-12" id="titolo-login">
                    <h2>
                        CERCA ORDINE
                    </h2>
                </div>
                
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
                
                
                
                
                <div class="col-12" style="height: 49.3vh;" id="corpo-pannello">
                    <div class="contenitore-form-cerca">
                        <form class="cerca-form" action="<%=request.getContextPath() + "/ordiniadminid"%>" method="post">
                          
                                <label for="nome">Numero ordine</label>
                                <input type="number" name="<%=Costanti.KEY_ID_ORDINE %>" id="nome-ordine">
                                <button type="submit" class="cerca-opz" >Cerca</button> 
                                
                        </form>
                        <br><br><br>
                        <form class="cerca-form" action="<%=request.getContextPath() + "/ordiniadmindataa"%>" method="post">
                        	
                                <label for="nome">Data ordine</label>
                                <input type="date" name="<%=Costanti.KEY_DATA_ORDINE %>" id="data-ordine">
                                <button type="submit" class="cerca-opz" >Cerca</button>                 
                            
                        </form>
                        <button type="submit" class="cerca-opz"><a href="<%=request.getContextPath()%>/html/pannellocontrollo.jsp">Pannello di controllo</a></button>
                    </div>  
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>