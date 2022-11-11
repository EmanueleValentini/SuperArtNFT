<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.azienda.ecommerce.model.Prodotto"%>
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
    <title>Il tuo profilo</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css">
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
    	Utente utente = (Utente)session.getAttribute(Costanti.KEY_UTENTE_LOGGATO);
    	List<Ordine> ordini = (List<Ordine>) request.getAttribute(Costanti.KEY_ORDINE);
    	Double somma = (Double) request.getAttribute(Costanti.KEY_PASSAGGIO_PRODOTTO);
    	Map<Integer,String> img = (Map<Integer,String>)request.getAttribute(Costanti.MAP_IMG);

    	if(ordini==null){
    		ordini=new ArrayList<Ordine>();
    	}
    	
	
    %>
        <div class="container">
            <div class="row">
                <div class="col-12" id="titolo-login">
                    <h2>
                        Profilo Utente
                    </h2>
                </div>
                <div class="col-12" id="corpo-profilo" style="justify-content: center;">
                    <div class="contenuto-profilo">
                    <%
    
    if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
	  %>
	  <p> <%= request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION) %></p>
	  
	 <% } %>
                        <div class="intestazione-profilo">
                            <table id="tabella-profilo">
                                <tr>
                                    <td rowspan="4"><img style="border-radius: 100%;" height="150px" width="150px" src="<%= request.getContextPath()%>/IMMAGINI/utente.png" alt=""></td>
                                    <th colspan="2" style="font-size: x-large;"><%=utente.getNomeUtente()%></th>
                                </tr>
                                <tr>
                                    <th colspan="2" style="font-size: x-large;"><%=utente.getEmail()%></th>
                                </tr>
                            </table>
                        </div>
                        <br><br><br>
                        <h2 style="margin-bottom: 3%; text-align: center;">
                                Riepilogo Ordini
                            </h2>
                        <div style="overflow-y: scroll; overflow-x: none; height: 40vh; margin-bottom: 5%; display: flex;
    flex-direction: column;
    
    align-items: center;
    padding: 2%;">
                            
                             <% for(Ordine o : ordini){
    		
    						%>
    						<div style="margin: 3%;">
                            <table style="background-color: white; border-radius: 18px;">
                           
                                <tr>
                                    <td rowspan="2"><img height="200px" width="200px" src="<%= img.get(o.getProdotto().getId())%>" alt=""></td>
                                    <th style="font-size: large;"><%=o.getProdotto().getNomeProdotto()%></th>
                                </tr>
                                <tr>
                                    <th style="font-size: large;">Id ordine:<%=o.getId()%></th>
                                </tr>
                            </table>
                            </div>
                            <%} %>
                        </div>
                        <p style="text-align: center;">totale ordini <%=somma %> ETH</p>
                        <div class="btn-profilo-utente" style="display: flex; flex-direction: column; align-items: center;">
                        <form method="post" action="<%=request.getContextPath()+"/password"%>">
                        	<button style="margin-bottom: 20%;"> <a href="<%=request.getContextPath()+"/html/modificapassword.jsp" %>">Modifica password</a> </button>
                        </form>
                                                                      
                            <button> <a href="<%=request.getContextPath()+"/removeutente" %>">Cancella account</a></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>