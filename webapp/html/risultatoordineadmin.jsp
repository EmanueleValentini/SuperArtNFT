<%@page import="java.util.Map"%>
<%@page import="com.azienda.ecommerce.model.Ordine"%>
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
<title>Ordine</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
	<main>
		<%
    
	if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
%>
		</p>
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
	
	Ordine o=(Ordine)request.getAttribute(Costanti.KEY_ORDINE);
	Map <Integer,String> img = (Map<Integer,String>)request.getAttribute(Costanti.MAP_IMG);
	
        %>
		
		<div class="container">
			<div class="row">
				<div class="col-12" id="titolo-login">
					<h2>ORDINE<%=" "+o.getId() %></h2>
				</div>
				<div class="col-12" style="background-color: #C2A8B6;
    border-radius: 18px;
    margin-bottom: 5%;
    flex-direction: column;
    justify-content: center;
    overflow-y: scroll;
    overflow-x: hidden;
    height: 49.2vh;">
					<div id="contenitore-tab-risultati">
						<table style="text-align: center;" border="1">
							<tr>
								<th>Foto prodotto</th>
								<th>Nome prodotto</th>
								<th>Prezzo prodotto</th>
								<th>Username</th>
							</tr>
							<tr>
								<td><img height="250px" width="250px" src="<%= img.get(o.getProdotto().getId())%>" alt="foto utente"></td>
								<td><%=o.getProdotto().getNomeProdotto() %></td>
								<td><%=o.getProdotto().getPrezzo()+" ETH" %></td>
								<td><%=o.getUtente().getNomeUtente() %></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>