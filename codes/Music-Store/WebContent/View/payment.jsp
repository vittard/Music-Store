<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.Payment"%>
<%@ page import="entities.User"%>
<!DOCTYPE html>
<html>
<jsp:include page="fragments/head.jsp">
	<jsp:param name="titlePage" value="Metodo di pagamento" />
</jsp:include>
<jsp:include page="fragments/header.jsp"></jsp:include>
<jsp:include page="fragments/message.jsp"></jsp:include>
<body>
	<section class="card welcome_section">
		<div class="card-body">
			<h1>Metodo di pagamento</h1>

			<%
				User user = (User) session.getAttribute("user");
				if (user != null) {
					Payment payment = (Payment) request.getAttribute("payment");
			%>
		</div>
	</section>
	<section>
		<form class="px-4 py-3" onsubmit="return submitControl()"
			action="<% if (payment!=null) out.print("EditPayment"); else out.print("NewPayment");  %>"
			method="post">
			<div class="form-group">
				<label for="name">Nome intestatario carta</label> <input type="text" name="name" onfocus="clean(this)"
					class="form-control" id="name" placeholder="Nome"
					<% if (payment!=null) out.print("value=\"" + payment.getName() + "\""); %>
					required>
			</div>
			<div class="form-group">
				<label for="surname">Cognome intestatario carta</label> <input type="text" onfocus="clean(this)"
					name="surname" class="form-control" id="surname"
					placeholder="Cognome"
					<% if (payment!=null) out.print("value=\"" + payment.getSurname() + "\""); %>
					required>
			</div>
			<div class="form-group">
				<label for="cardNumber">Numero Carta</label> <input type="text" name="cardNumber" onfocus="clean(this)"
					class="form-control" id="cardNumber" placeholder="0000000000000000" <% if (payment!=null) out.print("value=\"" + payment.getCardNumber() + "\""); %>
					required>
			</div>
			<div class="form-group">
				<label for="date">Data di scadenza</label> <input type="date" name="date" onfocus="clean(this)"
					class="form-control" id="date" placeholder="yyyy-mm-dd" <% if (payment!=null) out.print("value=\"" + payment.getExpiryDate() + "\""); %> required>
			</div>
			<div class="form-group">
				<label for="cvv">cvv</label> <input type="text" onfocus="clean(this)"
					name="cvv" class="form-control" id="cvv"
					placeholder="000" <% if (payment!=null) out.print("value=\"" + payment.getCvv() + "\""); %> required>
			</div>
			<input type="text" name="userId" class="form-control" required
				style="display: none;" value="<% out.print(user.getId()); %>">
				<% if (payment!=null) { %><input type="text" name="id" class="form-control" required
				style="display: none;" value="<% out.print(payment.getId()); %>"> <% } %>
			<button type="submit" class="btn btn-outline-primary">
				<% if (payment!=null) out.print("Salva modifiche"); else out.print("Aggiungi indirizzo"); %>
			</button>
		</form>

		<% } else {
					String errorMessage = "Non puoi visualizzare questa pagina se non hai effettuato l'accesso";
					out.print("<h2>" + errorMessage + "</h2>");
				} %>

	</section>
	
		<jsp:include page="fragments/footer.jsp"></jsp:include>
	<jsp:include page="fragments/defaultScripts.jsp"></jsp:include>
	<script type="text/javascript">
	function submitControl() {
		var name = document.getElementById("name");
		var surname = document.getElementById("surname");
		var cardNumber = document.getElementById("cardNumber");
		var date = document.getElementById("date");
		var cvv = document.getElementById("cvv");
		
		
		
		if ( isName(name) && isName(surname) && isCard(cardNumber) && isDate(date) && isCVV(cvv) ) {	
			return true;
		} else {
			return false;
		}
		
	}
	</script>
</body>
</html>