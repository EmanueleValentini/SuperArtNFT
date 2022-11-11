
<%@page import="com.azienda.ecommerce.model.Categoria"%>
<%@page import="java.util.Map"%>
<%@page import="com.azienda.ecommerce.model.Prodotto"%>
<%@page import="java.util.List"%>
<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shop</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
  $(document).ready(function(){
    $("#menuButton").click(function(){
      $("#menu").slideToggle();
    }); 
  });

</script>
</head>
<body>
   	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
	
	<%
	List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute(Costanti.KEY_VALORE_RICERCA_PRODOTTI);
	Map <Integer,String> img = (Map<Integer,String>)request.getAttribute(Costanti.MAP_IMG);
	List<Categoria> categorie = (List<Categoria>) request.getAttribute(Costanti.PASSO_CATEGORIA);
        


%>
	
<main>	            
    <div class="container-fluid">
    <div class="row" style="display: flex; justify-content: center;">
       <div class="col-2" style="display: flex; flex-direction: column; justify-content: start; align-items: center; margin-top: 5%;">
           <button id="menuButton" style="text-align: center; padding:8px">Filtri</button>
			<div id="menu" style="display:none;">
            <form action="<%=request.getContextPath()+"/ricerca"%>" method="post" style="display: flex; flex-direction: column;" id="form-ricerca-filtrata">
            <!--<label for="ricerca">Nome Prodotto</label>-->
            <input type="text" name="<%=Costanti.KEY_VALORE_RICERCA %>" style="border-radius: 8px" placeholder="Nome Prodotto" class="filtri">
            <!--<label for="prezzo-min">Prezzo minimo</label>-->
            <input type="number" name="<%=Costanti.KEY_PREZZOMIN %>" style="border-radius: 8px" placeholder="Prezzo minimo" class="filtri">
            <!--<label for="prezzo-max">Prezzo massimo</label>-->
            <input type="number" name="<%=Costanti.KEY_PREZZOMAX %>" style="border-radius: 8px" placeholder="Prezzo massimo" class="filtri">
               <select name="<%= Costanti.CATEGORIA_NOME %>" style="border-radius: 8px" class="filtri">
            	<option value="all">Tutte</option>
            	<%for(Categoria c : categorie){ %>
            	<% if(c.getRimosso()){
            		continue;
            	}%>
            	<option value="<%= c.getNomeCategoria() %>"><%= c.getNomeCategoria() %></option>
            	<% } %>
            </select>
            <button type="submit" class="filtri">Cerca</button>
        </form>
       </div>
       </div>

    
    <div class="tabella-shop col-8" style="display: flex; justify-content: start; flex-wrap: wrap;">
        
        <%
		if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
				%>
				<p style="display: flex; justify-content: center; align-items: center"><%= request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)%> </p>
				<%
				}
	            		if(session.getAttribute(Costanti.KEY_UTENTE_LOGGATO)==null){
	            			response.sendRedirect(request.getContextPath()+"/html/login.jsp");
	            			return;
	            		}
	            %>
            
    
     <%
     if(prodotti != null){
    	 String categoriaCheck = request.getParameter(Costanti.CATEGORIA_NOME) == null? "all" : request.getParameter(Costanti.CATEGORIA_NOME);
         
     
	            for(int i = 0; i < prodotti.size(); i++){
	        		if(!categoriaCheck.equals("all")){
	        			String categoria = request.getParameter(Costanti.CATEGORIA_NOME);
	            		if(!prodotti.get(i).getCategoria().getNomeCategoria().equals(categoria)){
	            			continue;
	            		}
	        		}
                %>
                                                                      
	<div class="col-4 tabella-prodotti">
       	<form action="<%=request.getContextPath() + "/infoprodotto"%>" method="post">
            <table class="tabella-prod-shop hovering-shop">
                <tr>
                	<input  type="hidden" name="<%= Costanti.KEY_PASSAGGIO_PRODOTTO %>" value="<%= prodotti.get(i).getNomeProdotto() %>">
                    <td rowspan="2"> <button type="submit"><img width="150px" height="150px" src="<%=img.get(prodotti.get(i).getId()) %>" alt="asdasd"></button></td>
                    <th style="max-width: 150px; min-width: 150px; table-layout: auto;"><%= prodotti.get(i).getNomeProdotto() %></th>
                </tr>
                <tr>
                    <th style="max-width: 150px; min-width: 150px; table-layout: auto;">Prezzo <%= prodotti.get(i).getPrezzo()+" ETH" %></th> 
                </tr>
                
            </table>
       </form>
       </div>  
       <% }
       } else {%>
       
       <% } %>  
   </div>
       		       

    </div>
    </div>

    <jsp:include page="boxbottom.jsp"></jsp:include>
</main>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>