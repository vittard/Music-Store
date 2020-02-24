<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.Category"%>
<%@ page import="entities.User"%>
<%@ page import="entities.Product"%>

<!doctype html>
<html lang="it">
	<jsp:include page="../fragments/head.jsp">
		<jsp:param name="titlePage" value="Prodotto" />
	</jsp:include>
<body>

	<jsp:include page="../fragments/header.jsp"></jsp:include>
	<jsp:include page="../fragments/message.jsp"></jsp:include>
	
	<section class="card welcome_section">
		<div class="card-body">

			<%
				User user = (User) session.getAttribute("user");
				if (user != null) {
					Product product = (Product) request.getAttribute("product");
			%>
			<h1><% if (product!=null) out.print("Modifica "); else out.print("Aggiungi nuovo "); %> Prodotto</h1>
		</div>
	</section>

	<section class="card product_section card_login">
  <form class="px-4 py-3" action="<% if (product!=null) out.print("EditAdminProduct"); else out.print("NewAdminProduct");  %>" method="post" onsubmit="return submitControl()" enctype="multipart/form-data">
  	<img class="logo-login" src="img/logo.png">
  	<div class="form-group">
      <label for="name">Nome</label>
      <input type="text" name="name" class="form-control" id="name" placeholder="Nome" <% if (product!=null) out.print("value=\"" + product.getName() + "\""); %> onfocus="clean()" required>
    </div>
    <div class="form-group">
      <label for="description">Description</label>
      <input type="text" name="description" class="form-control" id="description" placeholder="Cognome" <% if (product!=null) out.print("value=\"" + product.getDescription() + "\""); %> onfocus="clean()" required>
    </div>
    <div class="form-group">
      <label for="price">Prezzo</label>
      <input type="text" name="price" class="form-control" id="price" placeholder="0.00" <% if (product!=null) out.print("value=\"" + product.getPrice() + "\""); %> onfocus="clean()" required>
    </div>
    <div class="form-group">
      <label for="shippingPrice">Prezzo spedizione</label>
      <input type="text" name="shippingPrice" class="form-control" id="shippingPrice" placeholder="0.00" <% if (product!=null) out.print("value=\"" + product.getShippingPrice() + "\""); %> onfocus="clean()" required>
    </div>
    <div class="form-group">
      <label for="disponibility">Disponibilità</label>
      <input type="text" name="disponibility" class="form-control" id="disponibility" placeholder="0" <% if (product!=null) out.print("value=\"" + product.getDisponibility() + "\""); %> onfocus="clean()" required>
    </div>
    <div class="form-group">
    <% if (product!=null) { %><img src="<% out.print(product.getImagePath()); %>" > <% } %>
      <label for="imagePath">Carica Immagine</label>
      <input type="file" name="imagePath" class="form-control" id="imagePath" multiple required>
    </div>
    <% if (product!=null) { %><input type="text" name="id" class="form-control" required
				style="display: none;" value="<% out.print(product.getId()); %>"> <% } %>
	<button type="submit" class="btn btn-outline-primary">
		<% if (product!=null) out.print("Salva modifiche"); else out.print("Aggiungi prodotto"); %>
	</button>
	</form>

		<% } else {
					String errorMessage = "Non puoi visualizzare questa pagina se non hai effettuato l'accesso";
					out.print("<h2>" + errorMessage + "</h2>");
				} %>
	</section>
	
	
	<jsp:include page="../fragments/footer.jsp"></jsp:include>
	

	<jsp:include page="../fragments/defaultScripts.jsp"></jsp:include>
	
	<script type="text/javascript">
	function submitControl() {
		var name = document.getElementById("name");
		var price = document.getElementById("price");
		var shippingPrice = document.getElementById("shippingPrice");
		var disponibility = document.getElementById("disponibility");
		
		
		if ( isName(name) && isPrice(price) && isPirce(shippingPrice) && isDisponibility(disponibility)) {	
			return true;
		} else {
			return false;
		}
		
	}
	</script>
</body>
</html>