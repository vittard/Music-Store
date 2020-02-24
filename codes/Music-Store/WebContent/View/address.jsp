<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.Address"%>
<%@ page import="entities.User"%>
<!DOCTYPE html>
<html>
<jsp:include page="fragments/head.jsp">
	<jsp:param name="titlePage" value="Indirizzo" />
</jsp:include>
<jsp:include page="fragments/header.jsp"></jsp:include>
<jsp:include page="fragments/message.jsp"></jsp:include>
<body>
	<section class="card welcome_section">
		<div class="card-body">
			<h1>Indirizzo</h1>

			<%
				User user = (User) session.getAttribute("user");
				if (user != null) {
					Address address = (Address) request.getAttribute("address");
			%>
		</div>
	</section>
	<section>
		<form class="px-4 py-3"
			action="<% if (address!=null) out.print("EditAddress"); else out.print("NewAddress");  %>" onsubmit="return submitControl()"
			method="post">
			<div class="form-group">
				<label for="name">Nome</label> <input type="text" name="name" onfocus="clean(this)"
					class="form-control" id="name" placeholder="Nome"
					<% if (address!=null) out.print("value=\"" + address.getName() + "\""); %>
					required>
			</div>
			<div class="form-group">
				<label for="surname">Cognome</label> <input type="text" onfocus="clean(this)"
					name="surname" class="form-control" id="surname"
					placeholder="Cognome"
					<% if (address!=null) out.print("value=\"" + address.getSurname() + "\""); %>
					required>
			</div>
			<div class="form-group">
				<label for="street">Via</label> <input type="text" name="street" onfocus="clean(this)"
					class="form-control" id="street" placeholder="Via Esempio, 1" <% if (address!=null) out.print("value=\"" + address.getStreet() + "\""); %>
					required>
			</div>
			<div class="form-group">
				<label for="city">Città</label> <input type="text" name="city" onfocus="clean(this)"
					class="form-control" id="city" placeholder="Città" <% if (address!=null) out.print("value=\"" + address.getCity() + "\""); %> required>
			</div>
			<div class="form-group">
				<label for="province">Provincia</label> <input type="text" onfocus="clean(this)"
					name="province" class="form-control" id="province"
					placeholder="Provincia" <% if (address!=null) out.print("value=\"" + address.getProvince() + "\""); %> required>
			</div>
			<div class="form-group">
				<label for="zipCode">CAP</label> <input type="text" name="zipCode" onfocus="clean(this)"
					class="form-control" id="zipCode" placeholder="000000" <% if (address!=null) out.print("value=\"" + address.getZipCode() + "\""); %> required>
			</div>
			<div class="form-group">
				<label for="state">Stato</label> <input type="text" name="state" onfocus="clean(this)"
					class="form-control" id="state" placeholder="Italia" <% if (address!=null) out.print("value=\"" + address.getState() + "\""); %> required>
			</div>
			<div class="form-group">
				<label for="phone">Telefono</label> <input type="text" name="phone" onfocus="clean(this)"
					class="form-control" id="phone" placeholder="+39 0000000000" <% if (address!=null) out.print("value=\"" + address.getPhone() + "\""); %>>
			</div>
			<input type="text" name="userId" class="form-control" required
				style="display: none;" value="<% out.print(user.getId()); %>">
				<% if (address!=null) { %><input type="text" name="id" class="form-control" required
				style="display: none;" value="<% out.print(address.getId()); %>"> <% } %>
			<button type="submit" class="btn btn-outline-primary">
				<% if (address!=null) out.print("Salva modifiche"); else out.print("Aggiungi indirizzo"); %>
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
		var street = document.getElementById("street");
		var city = document.getElementById("city");
		var province = document.getElementById("province");
		var zipCode = document.getElementById("zipCode");
		var state = document.getElementById("state");
		var phone = document.getElementById("phone");
		
		
		if ( isName(name) && isName(surname) && isStreet(street) && isLetteral(city) && isLetteral(province) && isZipCode(zipCode) && isLetteral(state) && isNumberPhone(phone) ) {	
			return true;
		} else {
			return false;
		}
		
	}
	</script>
</body>
</html>