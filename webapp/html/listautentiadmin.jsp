<%@page import="java.util.List"%>
<%@page import="com.azienda.ecommerce.model.Utente"%>
<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lista Utenti</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="navbar.jsp"></jsp:include>
<main>
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
                  List<Utente> utenti = (List<Utente>) request.getAttribute(Costanti.LISTA_ALL_UTENTI);
                      
                 
                    	
                %>
    
<div class="container">
            <div class="row">
                <div class="col-12" id="titolo-login">
                    <h2>
                        LISTA UTENTI
                    </h2>
                </div>
                <div class="col-12" style="background-color: #C2A8B6;
    border-radius: 18px;
    margin-bottom: 5%;
    padding: 40px;
    flex-direction: column;
    justify-content: center;
    overflow-y: scroll;
    overflow-x: hidden;
    height: 49.3vh;">
                
                    <div id="contenitore-tab-risultati">

                        <table style="text-align: center;" border="1">

                            <tr>
                                <th>Username utente</th>
                                <th>Email</th>
                                <th>Ruolo</th>
                            </tr> 
                                                                          <%
                           for(Utente ue : utenti) {
                           %>
                            <tr>
                                <td><%=ue.getNomeUtente() %></td>
                                <td>
                                    <form action="<%=request.getContextPath()+"/passaggioutente"%>" method="post">
                                        <input type="submit" name="<%=Costanti.KEY_EMAIL %>" value="<%=ue.getEmail()%>">
                                    </form>
                                </td>
                                <td><%=ue.getRuolo().getNomeRuolo() %></td>        
                            </tr>
                                                                        <%}
                           %>                                                   
                        </table>

                    </div>  

                </div>
            </div>
        </div>
    </main>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>