<%@page import="com.azienda.ecommerce.utilities.Costanti"%>
<div class="container-fluid" id="box-bottom" style="margin-bottom: 5%;">
	<div class="row">
		<div class="col-lg-3 col-md-3 col-12" id="part-div">
			<h3 id="partners">Puoi trovarci anche su</h3>
		</div>
		<div class="col-lg-1 col-md-1 col-12" id="part-im1">
			<a href="https://opensea.io/"><img
				src="<%=request.getContextPath()%>/IMMAGINI/OpenSea.jpg"
				alt="logo-openSea" class="img-fluid" id="im1"></a>
		</div>
		<div class="col-lg-1 col-md-1 col-12" id="part-im2">
			<a href="https://superrare.com/"><img
				src="<%=request.getContextPath()%>/IMMAGINI/SuperRare.jpg"
				alt="logo-superRare" class="img-fluid" id="im2"></a>
		</div>
		<div class="col-lg-1 col-md-1 col-12" id="part-im3">
			<a href="https://rarible.com/"><img
				src="<%=request.getContextPath()%>/IMMAGINI/Rarible_logo.jpg"
				alt="logo-rarible" class="img-fluid" id="im3"></a>
		</div>
		<div class="col-lg-1 col-md-1 col-12" id="part-im4">
			<a href="https://mintable.app/"><img
				src="<%=request.getContextPath()%>/IMMAGINI/mintable.png"
				alt="logo-rarible" class="img-fluid" id="im4"></a>
		</div>
		<div class="col-lg-1 col-md-1" id="vuoto"></div>
		<div class="col-lg-3 col-md-3 col-12" id="newsletter">
			<h3>Iscriviti alla newsletter</h3>
			<p>Non perderti le prossime uscite e le ultime offerte</p>
			<form method="post" action="<%=request.getContextPath()%>/newsemail">
				<input id="email-newsletter" type="email"
					placeholder="mariorossi@gmail.com" required
					name="<%=Costanti.KEY_PASSAGGIO_EMAIL%>">
				<button type="submit" id="iscriviti">Iscriviti</button>
			</form>
		</div>
	</div>
</div>