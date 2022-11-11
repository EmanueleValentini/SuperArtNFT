<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modifica Password</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
        integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
	<main>
        <div class="container">
            <div class="row">
                <div class="col-12" id="titolo-login">
                    <h2>
                        MODIFICA PASSWORD
                    </h2>
                </div>
                <div class="col-12" id="corpo-pannello-agg" style="height: 49.3vh;">
                    <div id="contenitore-form-agg" style="display: flex; flex-direction: column; align-items: center;" >
                        <form action="<%=request.getContextPath()%>/password" method="post"  style="display: flex; flex-direction: column;" id="aggiungi-form-immagine" >

                            <label for="password-old">Vecchia password</label>
                            <input type="password" name="<%=Costanti.KEY_VECCHIA_PASSWORD%>">             
                            <label for="password-new">Nuova password</label>
                            <input type="password" name="<%=Costanti.KEY_PASSWORD%>">                
                            <button type="submit" class="cerca-opz">Conferma modifica</button>
                            
                        </form>
                        <button class="cerca-opz"><a href="<%=request.getContextPath()%>/html/profilo.jsp">Profilo</a></button>
                            

                    </div>  
                </div>
            </div>
        </div>
    </main>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>