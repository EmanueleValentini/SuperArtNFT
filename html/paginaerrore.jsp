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
    <title>Errore generico</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
    <main>
        <div class="container">
            <div class="row" style="display: flex; justify-content: center;">
                
                <div class="col-12" id="titolo-login">
                    <img src="<%=request.getContextPath()%>/IMMAGINI/pulp-fiction-john-travolta.gif" alt="gif-travolta">
                </div>
                <div>
                    <h2 style="color:white;">Si è verificato un errore imprevisto</h2>
                </div>
            </div>
        </div>
    </main>
    <div style="height: 5.5vh;"></div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>