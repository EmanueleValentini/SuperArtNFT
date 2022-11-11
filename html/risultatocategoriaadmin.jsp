<%@page import="com.azienda.ecommerce.model.Categoria"%>
<%@page import="org.eclipse.jdt.internal.compiler.ast.IfStatement"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.azienda.ecommerce.model.Ordine"%>
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
    <title>Lista categorie</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
    <main>   
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
                    	
                    	Categoria o = (Categoria) request.getAttribute(Costanti.CATEGORIA_PASSAGGIO);
                    	
                            %>
        <div class="container">
            <div class="row">
                <div class="col-12" id="titolo-login">
                    <h2>
                        RISULTATO RICERCA CATEGORIA
                    </h2>
                </div>
                <div class="col-12" style="background-color: #C2A8B6;
    border-radius: 18px;
    margin-bottom: 5%;
    padding: 40px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    overflow-y: scroll;
    overflow-x: hidden;
    height: 49.3vh;">
                <%
                if(o!=null){
					
                %>
                    <div id="contenitore-tab-risultati">
                        <table style="text-align: center;" border="1">
                            <tr>
                                <th>Nome Categoria:</th>
                                <th colspan="2">Opzioni:</th>
                            </tr>
                            <tr>
                                <td><%=o.getNomeCategoria()%></td>
                                <td>
                                <% if(!o.getRimosso()){ %>
                                <form action="<%=request.getContextPath()+"/removeadmincat"%>" method="post">
                                	<input type="hidden" name="<%= Costanti.CATEGORIA_NOME%>" value="<%=o.getNomeCategoria()%>">
                                
                               		<button type="submit">Rimuovi</button>
                                </form>
                                <%} else { %>
                                 <form action="<%=request.getContextPath()+"/ripristinacat"%>" method="post">
                                	<input type="hidden" name="<%= Costanti.CATEGORIA_NOME%>" value="<%=o.getNomeCategoria()%>">
                                
                               		<button type="submit">Ripristina</button>
                                </form>
                                <%} %>
                                </td>
                                <td>
                               <form action="<%=request.getContextPath()+"/updatecampicat"%>" method="post">
                               <input type="hidden" name="<%= Costanti.CATEGORIA_NOME%>" value="<%=o.getNomeCategoria()%>">
                                <input type="hidden" name="<%= Costanti.CATEGORIA_PASSAGGIO%>" value="<%=o.getId()%>">
                               
                               <button type="submit">Modifica</button>
                                </form>
                                </td>
                            </tr>                                                   
                        </table>
                    </div>  
                    <%}
                		else{
                    			%>
                    		<p>Non esiste categoria con questo nome</p>
                    		<%} %>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>