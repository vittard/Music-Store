<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.*"%>
<%@ page import="java.util.ArrayList"%>

<!doctype html>
<html lang="it">
<jsp:include page="../fragments/head.jsp">
	<jsp:param name="titlePage" value="Pagina di rimozione" />
</jsp:include>
<body>

	<jsp:include page="../fragments/header.jsp"></jsp:include>
	<jsp:include page="../fragments/message.jsp"></jsp:include>

	<% 
		String objectClass = (String) request.getAttribute("objectClass");
		if ( objectClass!=null ) {
			String details = "";
			String id = "";
			if ( objectClass.equals("Category") ) {
				Category category = (Category) request.getAttribute("object");
				id = category.getName();
				details = "la categoria con i seguenti dati:<br>"
					+ "Nome Categoria: "
					+ id;
			} else if ( objectClass.equals("User") ) {
				
				User user = (User) request.getAttribute("object");
				id = String.valueOf(user.getId());
				details = "il tuo account '"
						+ user.getName() + " " + user.getSurname() + "'";
				
			} else if ( objectClass.equals("Payment") ) {
				Payment payment = (Payment) request.getAttribute("object");
				id = String.valueOf(payment.getId());
				details = "il metodo di pagamento con i seguenti dettagli:<br>"
						+ "Intestatario: "
						+ payment.getName() + " " + payment.getSurname()
						+ "Numero carta:<br>"
					    + payment.getCardNumber().substring(0, 4) + " **** **** " + payment.getCardNumber().substring( payment.getCardNumber().length()-5, payment.getCardNumber().length()-1) +"<br>"
					    + "Scadenza carta:<br>"
					    + payment.getExpiryDate();
			} else if ( objectClass.equals("Product") ) {
				Product product = (Product) request.getAttribute("object");
				id = String.valueOf(product.getId());
				details = "il metodo di pagamento con i seguenti dettagli:<br>"
						+ "Nome: "
						+ product.getName() 
						+ "<br>Descrizione: " + product.getDescription()
						+ "<br>Prezzo: " + product.getPrice()
						+ "<br>Prezzo di spedizione: " + product.getShippingPrice()
						+ "<br>DisponibilitÓ: " + product.getDisponibility();
			}
		
	%>

	<section class="card welcome_section">
		<div class="card-body">
			<h1>Sei sicuro di voler rimuovere in maniera definitiva <% out.print(details); %></h1>
			<a href="AdminRemoveYes?id=<% out.print(id); %>&type=<% out.print(objectClass); %>"><button class="btn btn-outline-primary">Si</button></a>
			<a href="<% String previousPage = (String) request.getAttribute("previous"); out.print(previousPage); %>"><button class="btn btn-outline-primary">No</button></a>
		</div>
	</section>

<% } %>

	<jsp:include page="../fragments/footer.jsp"></jsp:include>


	<jsp:include page="../fragments/defaultScripts.jsp"></jsp:include>
</body>
</html>