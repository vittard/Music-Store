<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.User"%>
<!doctype html>
<html lang="it">
<jsp:include page="../fragments/head.jsp">
	<jsp:param name="titlePage" value="Nuovo Utente" />
</jsp:include>
<body>

	<jsp:include page="../fragments/header.jsp"></jsp:include>
	<jsp:include page="../fragments/message.jsp"></jsp:include>

	<section class="card welcome_section">
		<div class="card-body">
			<h1>Nuovo Utente</h1>
		</div>
	</section>

	<section class="card welcome_section profile_card">
		<section class="card product_section card_login">

			<%
				User u = (User) session.getAttribute("user");
				if (u != null) {
			%>
			<form class="px-4 py-3" onsubmit="return submitControl()" action="NewAdminUser" method="post">

				<div class="form-group">
					<label for="name">Nome</label> <input type="text" name="name"
						class="form-control" id="name" onfocus="clean(this)">
				</div>
				<div class="form-group">
					<label for="surname">Cognome</label> <input type="text"
						name="surname" class="form-control" id="surname" onfocus="clean(this)">
				</div>
				<div class="form-group">
					<label for="email">Email</label> <input type="email" name="email"
						class="form-control" id="email" onfocus="clean(this)">
				</div>
				<div class="form-group">
					<label for="userPassword">Password Nuovo Utente</label> <input type="password"
						name="userPassword" class="form-control" id="userPassword"
						placeholder="Password" onfocus="clean(this)">
				</div>
				<div class="form-group">
					<label for="confirmUserPassword">Conferma Password Nuovo Utente</label> <input type="password"
						name="confirmUserPassword" class="form-control" id="confirmUserPassword"
						placeholder="Password" onfocus="clean(this)">
				</div>
				<div class="form-group">
					<label for="adminPassword">Password Amministratore</label> <input type="password"
						name="adminPassword" class="form-control" id="adminPassword"
						placeholder="Password" onfocus="clean(this)">
				</div>
				
				<div class="form-group">
				<label for="typeUser">Tipo utente</label>
					<select id="typeUser" class="form-control" name="typeUser" >
							<option value="0">Cliente</option>
				  			<option value="1">Gestore Ordini</option>
				  			<option value="2">Gestore Catalogo</option>
				  			<option value="3">Gestore Utenti</option>
						</select>
				</div>
				
				<input type="hidden"
						name="fun" class="form-control" value="account" required style="display: none;">

				<button type="submit" class="btn btn-outline-primary">Salva
					modifiche</button>
			</form>


		</section>
		
			<%
					}
			%>
	</section>

	<jsp:include page="../fragments/footer.jsp"></jsp:include>


	<jsp:include page="../fragments/defaultScripts.jsp"></jsp:include>
		<script type="text/javascript">
	
	function submitControl() {
		var name = document.getElementById("name");
		var surname = document.getElementById("surname");
		var email = document.getElementById("email");
		var userPassword = document.getElementById("userPassword");
		var confirmUserPassword = document.getElementById("confirmUserPassword");
		var adminPassword = document.getElementById("adminPassword");
		
		if (isName(name) && isSurname(surname) && isEmail(email) && isPassword(userPassword) && isPassword(confirmPassword) && isPassword(adminPassword)) {
			return true;
			
		} else {
			return false;
		}
		
	}
	
	</script>
</body>
</html>