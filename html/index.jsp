<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.azienda.ecommerce.model.Prodotto"%>
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
<title>SUPERARTNFT</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>

<body>
	<jsp:include page="header.jsp"></jsp:include>
	<jsp:include page="navbar.jsp"></jsp:include>
	<main>
		<div class="container-fluid" id="box-articoli" style="margin-top: 2%;">
			<div class="row" style="display: flex; justify-content: center; height: 70vh;">
				<div class="carosello" style="margin: 2%;">
					<div class="slide1" style="display: flex; flex-direction: column;">
					<h3 style="color: #4F2956;"><b>Binance NFT sta presentando la fase di preparazione della caccia al tesoro spettrale</b></h3>
						<div style="display: flex; align-items: center;">
						<img id="img-articolo" src="<%=request.getContextPath()%>/IMMAGINI/Articolo-1.jpg"
							height="300px" width="600px" style="margin-right: 4%;">
							<div style=" display: flex; flex-direction: column; align-items: center; justify-content: center;">
							
						<p id="paragrafo-articolo" style="margin-left: 4%;">Binance lancia un nuovo meccanismo di abbonamento per garantire che i possessori di BNB e NFT
possano impegnare i token in una vendita di Binance NFT in modo equo ed equo.
Ogni utente ha un limite di abbonamento e l'allocazione NFT finale verrà distribuita
in modo equo e uniforme. Tutti gli utenti di Binance che hanno completato gli NFT
possono acquistare NFT durante la vendita primaria di Binance NFT.Nell'ambito della
"Spooktacular Scavenger Hunt", gli utenti che raccolgono un set completo di tutti
e tre gli NFT SR "Spooktacular Scavenger Hunt" o l'SSR "Spooktacular Scavenger Hunt" 
Enchanted Owl NFT dalla vendita principale o dal mercato secondario, 
avranno diritto a vinci una quota di un montepremi di $ 10.000 in buoni token BUSD.</p>
						</div>
							</div>
					</div>
					<div class="slide1" style="display: flex; flex-direction: column;">
					<h3 style="color: #4F2956;"><b>Galaxy Digital: gli inventori degli NFT basati su Ethereum hanno ricevuto quasi $ 1,8 miliardi di royalty
</b></h3>
						<div style="display: flex; align-items: center;">
						<img id="img-articolo" src="<%=request.getContextPath()%>/IMMAGINI/Articolo-2.jpg"
							height="300px" width="600px" style="margin-right: 4%;">
							<div style=" display: flex; flex-direction: column; align-items: center; justify-content: center;">
							
						<p id="paragrafo-articolo" style="margin-left: 4%;">Secondo un nuovo rapporto di studio pubblicato oggi da Galaxy Digital di Mike Novogratz, 
i produttori di Ethereum NFT hanno ricevuto un totale di 1,8 miliardi di dollari di 
royalties dalle vendite secondarie su mercati come OpenSea.
Il rapporto ha anche evidenziato che nell'ultimo anno le royalties medie 
su OpenSea sono aumentate dal 3% al 6%. Questo è notevole, considerando che OpenSea rimane 
il più grande mercato NFT per volume di scambi, secondo DappRadar.
Secondo Galaxy Digital, otto aziende controllano il 27% dell'intero mercato dei pagamenti delle 
royalty. Yuga Labs, il creatore della collezione in edizione limitata Bored Ape Yacht Club, 
ha raccolto il maggior numero di royalties, per un totale di oltre 147 milioni di dollari fino ad oggi.
Secondo i numeri di Dune Analytics utilizzati nella ricerca, Nike, 
che ha acquistato la startup di collezionismo digitale RTFKT, ha ricevuto 91,6 milioni di 
dollari in royalties NFT consolidate.</p>
						</div>
							</div>
					</div>
					<div class="slide1" style="display: flex; flex-direction: column;">
					<h3 style="color: #4F2956;"><b>OneOf e i Latin GRAMMY Awards hanno annunciato la loro prima collaborazione NFT
</b></h3>
						<div style="display: flex; align-items: center;">
						<img id="img-articolo" src="<%=request.getContextPath()%>/IMMAGINI/Articolo-3.jpg"
							height="300px" width="600px" style="margin-right: 4%;">
							<div style=" display: flex; flex-direction: column; align-items: center; justify-content: center;">
							
						<p id="paragrafo-articolo" style="margin-left: 4%;">I Latin GRAMMY seguono le orme dei primi GRAMMY NFT drop per commemorare il 64° anniversario
dell'evento. Il 4 ottobre, i Latin GRAMMY Awards hanno annunciato il loro debutto ufficiale
NFT attraverso una partnership triennale con la piattaforma musicale leader nel web3 OneOf.OneOf è una piattaforma NFT leader che crea esperienze rispettose dell'ambiente, creatori e fan. Adam Fell, co-fondatore di OneOf e presidente di Quincy Jones Productions, ha dichiarato:
"Gli Annual Latin GRAMMY Awards sono la notte più importante dell'anno per la musica latina e 
questa partnership avvicinerà i fan alla cerimonia come mai prima d'ora".
Giovedì 17 novembre 2022, alle 20, i Latin GRAMMYs andranno in onda in diretta su Univision. 19:00 CT
ET/PT (19:00 CT). Le date per la trasmissione internazionale devono ancora essere annunciate.</p>
						</div>
							</div>
					</div>
					<a class="prec" onclick="cambiaSlide(-1, 0)">&#10094; </a> <a
						class="succ" onclick="cambiaSlide(1, 0)">&#10095; </a>       
				</div>
				<script>
					var slideTab = [ 1, 1 ];
					var slideId = []
					var slides = document.getElementsByClassName("carosello");
					for (var e = 1; e <= slides.length; e++) {
						slideId.push("slide" + e);
					}
					creaSlide(1, 0);
					creaSlide(1, 1);
					function cambiaSlide(n, no) {
						creaSlide(slideTab[no] += n, no);
					}
					function creaSlide(n, no) {
						var i;
						var x = document.getElementsByClassName(slideId[no]);
						if (n > x.length) {
							slideTab[no] = 1
						}
						if (n < 1) {
							slideTab[no] = x.length
						}
						for (i = 0; i < x.length; i++) {
							x[i].style.display = "none";
						}
						x[slideTab[no] - 1].style.display = "flex";
					}
				</script>
			</div>
		</div>
		<%
		List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute(Costanti.KEY_PASSAGGIO_PRODOTTO);
		Map<Integer, String> mappa = (Map<Integer, String>) request.getAttribute(Costanti.MAP_IMG);
		if (prodotti == null) {
			response.sendRedirect(request.getContextPath() + "/prodottiindex");
			return;
		}
		%>




		<div class="container-fluid" id="box-arrivi">
			<div class="row">
				<div class="col-lg-12 col-md-12 col-12" id="ultimi">
					<p>ULTIMI ARRIVI</p>
				</div>
				<div class="tabella-shop-i">
					<%
					for (Prodotto p : prodotti) {
					%>
					<form method="post"
						action="<%=request.getContextPath() + "/infoprodotto"%>">
						<div class="hovering-index">
							<table class="tabella-prod-shop">
								<tr>
									<input type="hidden" value="<%=p.getNomeProdotto()%>"
										name="<%=Costanti.KEY_PASSAGGIO_PRODOTTO%>">
									<td rowspan="3"><button type="submit">
											<img height="150px" width="150px"
												src="<%=mappa.get(p.getId())%>" alt="Immagine Prodotto">
										</button></td>
									<th colspan="2"><%=p.getNomeProdotto()%></th>
								</tr>
								<tr>
									<th colspan="2"><%=p.getPrezzo() + " ETH"%></th>
								</tr>
							</table>
						</div>
					</form>
					<%
					}
					%>
				</div>
			</div>
		</div>
		<jsp:include page="boxbottom.jsp"></jsp:include>
	</main>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>