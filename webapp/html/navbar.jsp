    <%@page import="com.azienda.ecommerce.model.Utente"%>
<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<nav style="background: linear-gradient(#373737 0%, #454545 50%, #565656 100%);">
        <div class="container-fluid" id="nav-cont">
            <div class="row">
                <div class="col-lg-4 col-md-8 col-12" id="ricerca-form">
                    <form action="<%=request.getContextPath() + "/ricerca"%>" method="post">
                            <input id="ricerca" name="<%=Costanti.KEY_VALORE_RICERCA%>" type="text" placeholder="Ricerca nel sito<%=request.getContextPath()%>" required>                            
                            <button type="submit"><img src="<%=request.getContextPath()%>/IMMAGINI/lente.jpg" alt="Cerca" class="img-fluid" id="img-lente"></button>
                    </form> 
                </div>
                <div class="col-lg-2 col-md-4 col-12" id="chi">
                    <a href="<%=request.getContextPath()%>/html/chisiamo.jsp" class="pagine">Chi siamo</a>
                </div>
                <div class="col-lg-2 col-md-4 col-12" id="shop">
                    <a href="<%=request.getContextPath()%>/ricerca" class="pagine">Shop</a>
                </div>
                 <%
	            		if(session.getAttribute(Costanti.KEY_UTENTE_LOGGATO)==null){
	            			            	
	            %>
	           	
                <div class="col-lg-2 col-md-4 col-12" id="logout">
               
                    <button  type="submit" style="display:none;"> <a href="<%=request.getContextPath()%>/logout"><img src="<%=request.getContextPath()%>/IMMAGINI/LOGOUT-1.png" alt="logout" class="img-fluid" id="img-logout"></button>
                </div>
                <%} else {%>
                                <div class="col-lg-2 col-md-4 col-12" id="logout">
               
                    <button  type="submit"> <a href="<%=request.getContextPath()%>/logout"><img src="<%=request.getContextPath()%>/IMMAGINI/LOGOUT-1.png" alt="logout" class="img-fluid" id="img-logout"></button>
                </div>
                <% }%>
                
                <div class="col-lg-2 col-md-4 col-12" id="butt">
                    <div class="col-lg-6 col-md-6 col-12" id="butt-login">
                        
                        <% 
                        
                        if(request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO) != null){
                            Utente u = (Utente) request.getSession().getAttribute(Costanti.KEY_UTENTE_LOGGATO);
                            if(u.getRuolo().getNomeRuolo().equals(Costanti.ADMIN)){
                                
                            
                            
                             %>
                         <button class="icone" id="login"><a href="<%=request.getContextPath()%>/html/pannellocontrollo.jsp"><img src="<%=request.getContextPath()%>/IMMAGINI/utente.png" alt="utente" class="img-fluid" id="img-utente"></a></button>
						<% } else { %>
						<button class="icone" id="login"><a href="<%=request.getContextPath()%>/ordini"><img src="<%=request.getContextPath()%>/IMMAGINI/utente.png" alt="utente" class="img-fluid" id="img-utente"></a></button>
						<%}
						
						}else{ %>
						
						<button class="icone" id="login"><a href="<%=request.getContextPath()%>/html/login.jsp"><img src="<%=request.getContextPath()%>/IMMAGINI/utente.png" alt="utente" class="img-fluid" id="img-utente"></a></button>

                        <% } %>
                        
                        
                        

                        
                    </div>
                    <div class="col-lg-6 col-md-6 col-12" id="butt-carrello">
                        <button class="icone" id="carrello"><a href="<%=request.getContextPath()%>/carrelloUtente"><img src="<%=request.getContextPath()%>/IMMAGINI/Carrello.png" alt="Carrello" class="img-fluid" id="img-carrello"></a></button>
                    </div>
                </div>
            </div>
        </div>
    </nav>