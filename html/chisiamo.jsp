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
<title>Chi Siamo</title>
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
	<main>
		<div class="container-fluid" id="box-articoli"
			style="margin-bottom: 3%; margin-top: 3%; padding: 1% 3%;">
			<div class="row">
				<div class="col-12">
					<h3 id="titolo-articolo" style="text-align: center; margin-bottom: 5%;">Chi
						siamo?</h3>
					<br>
					<div class="col-12"
						style="display: flex; justify-content: space-around; margin-bottom: 5%;">
						<div class="hovering-noi"
							style="display: flex; flex-direction: column; align-items: center;">
							<a href="https://www.linkedin.com/in/emanuele-valentini/"><img height="300px" width="230px" src="../IMMAGINI/terry.jpg" alt="E.Valentini"></a>
							<h5>Emanuele Valentini</h5>
						</div>
						<div class="hovering-noi"
							style="display: flex; flex-direction: column; align-items: center;">
							<a href="https://www.linkedin.com/in/giuliandrea-tortorici/"><img height="300px" width="210px" src="../IMMAGINI/andrea.jpg" alt="G.Tortorici"></a>
							<h5>Giuliandrea Tortorici</h5>
						</div>
						<div class="hovering-noi"
							style="display: flex; flex-direction: column; align-items: center;">
							<a href="https://www.linkedin.com/in/davide-cedoloni-9a6782243/"><img height="300px" width="230px" src="../IMMAGINI/cedola.jpg" alt="D.Cedoloni"></a>
							<h5>Davide Cedoloni</h5>
						</div>
					</div>
					<div class="col-12"
						style="display: flex; justify-content: space-around; margin-bottom: 5%">
						<div class="hovering-noi"
							style="display: flex; flex-direction: column; align-items: center;">
							<a href="https://www.linkedin.com/in/danieldei02/"><img height="300px" width="220px" src="../IMMAGINI/el deo.jpg" alt="D.Dei"></a>
							<h5>Daniel Dei</h5>
						</div>
						<div class="hovering-noi"
							style="display: flex; flex-direction: column; align-items: center;">
							<a href="https://www.linkedin.com/in/gabrydes/"><img height="300px" width="220px" src="../IMMAGINI/gab.jpg" alt="G.De Sanctis"></a>
							<h5>Gabriele De Sanctis</h5>
						</div>
						<div class="hovering-noi"
							style="display: flex; flex-direction: column; align-items: center;">
							<a href="https://www.linkedin.com/in/davide-maulella-892924182/"><img height="300px" width="230px" src="../IMMAGINI/papi.jpg" alt="D.Maulella"></a>
							<h5>Davide Maulella</h5>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>
	<jsp:include page="boxbottom.jsp"></jsp:include>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>