
<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%= request.getContextPath()%>/css/style.css">
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div class="col-12" id="titolo-login">
				<h2>LOGIN</h2>
			</div>
			<div class="col-12" id="form-login-pagina">
				<div class="form-login">
					<%
			if(session.getAttribute(Costanti.KEY_UTENTE_LOGGATO)==null){
				if(request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)!=null){
				%>
					<p><%= request.getAttribute(Costanti.KEY_MESSAGIO_EXCEPTION)%>
					</p>
					<%} %>
					<form class="login" action="<%=request.getContextPath()+"/login"%>"
						method="post">
						<label for="email" class="campi">Email</label> <input id="email"
							name="<%=Costanti.KEY_EMAIL %>" type="email"
							placeholder="mariorossi@gmail.com" class="campi" required><br>
						<br>
						<br> <label for="password" class="campi">Password</label> <input
							name="<%=Costanti.KEY_PASSWORD %>" id="password" type="password"
							class="campi" required><br>
						<button type="submit" class="pulsanti">Accedi</button>
						<label for="registrazione" style="margin-left: 13%;">Non
						sei ancora registrato?</label>

					
						<a href="<%= request.getContextPath()%>/html/registrati.jsp">Registrati</a>
					</form>
					
					

				</div>
			</div>
		</div>
	</div>
	<%}else{
   			response.sendRedirect(request.getContextPath()+"/html/index.jsp" );
   			}%>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>