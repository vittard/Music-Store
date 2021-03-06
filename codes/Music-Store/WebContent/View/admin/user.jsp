<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.User"%>
<!doctype html>
<html lang="it">
<jsp:include page="../fragments/head.jsp">
	<jsp:param name="titlePage" value="Utente" />
</jsp:include>
<body>

	<jsp:include page="../fragments/header.jsp"></jsp:include>
	<jsp:include page="../fragments/message.jsp"></jsp:include>

	<section class="card welcome_section">
		<div class="card-body">
			<h1>Modifica Utente</h1>
		</div>
	</section>

	<section class="card welcome_section profile_card">
		<section class="card product_section card_login">

			<%
				User u = (User) session.getAttribute("user");
				if (u != null) {
					User user = (User)request.getAttribute("UserDB");
					if (user!=null) {
			%>
			<form class="px-4 py-3" action="EditAdminUser" onsubmit="return submitControl()" method="post">

				<div class="form-group">
					<label for="name">Nome</label> <input type="text" name="name"
						class="form-control" id="name"
						value="<%out.print(user.getName());%>">
				</div>
				<div class="form-group">
					<label for="surname">Cognome</label> <input type="text"
						name="surname" class="form-control" id="surname"
						value="<%out.print(user.getSurname());%>">
				</div>
				<div class="form-group">
					<label for="email">Email</label> <input type="email" name="email"
						class="form-control" id="email"
						value="<%out.print(user.getEmail());%>">
				</div>
				<div class="form-group">
					<label for="password">Password Amministratore</label> <input type="password"
						name="password" class="form-control" id="password"
						placeholder="Password">
				</div>
				
				<div class="form-group">
				<label for="typeUser">Tipo utente</label>
				<% if (user.getType() == 0) { out.print(""
						+"<select id=\"typeUser\" class=\"form-control\" name=\"typeUser\" >"
							+"<option value=\"0\">Cliente</option>"
				  			+"<option value=\"1\">Gestore Ordini</option>"
				  			+"<option value=\"2\">Gestore Catalogo</option>"
				  			+"<option value=\"3\">Gestore Utente</option>"
						+"</select>"); 
				} else if ( user.getType() == 1 ) {
					out.print(""
							+"<select id=\"typeUser\" class=\"form-control\" name=\"typeUser\" >"
								+"<option value=\"1\">Gestore Ordini</option>"
								+"<option value=\"0\">Cliente</option>"
					  			+"<option value=\"2\">Gestore Catalogo</option>"
					  			+"<option value=\"3\">Gestore Utente</option>"
							+"</select>");
				} else if ( user.getType() == 2 ) {
					out.print(""
							+"<select id=\"typeUser\" class=\"form-control\" name=\"typeUser\" >"
								+"<option value=\"2\">Gestore Catalogo</option>"	
								+"<option value=\"0\">Cliente</option>"
								+"<option value=\"1\">Gestore Ordini</option>"
					  			+"<option value=\"3\">Gestore Utente</option>"
							+"</select>");
				} else if ( user.getType() == 3 ) {
					out.print(""
							+"<select id=\"typeUser\" class=\"form-control\" name=\"typeUser\" >"	
								+"<option value=\"3\">Gestore Utente</option>"
								+"<option value=\"0\">Cliente</option>"
								+"<option value=\"1\">Gestore Ordini</option>"
								+"<option value=\"2\">Gestore Catalogo</option>"
							+"</select>");
				}
				%>
				</div>
				
				
				<input type="hidden"
						name="idUser" class="form-control" value="<% out.print(user.getId()); %>" required style="display: none;">
				
				<input type="hidden"
						name="fun" class="form-control" value="account" required style="display: none;">

				<button type="submit" class="btn btn-outline-primary">Salva
					modifiche</button>
			</form>
			<div class="dropdown-divider"></div>
				<a
					href="Remove?id=<% out.print(user.getId()); %>&type=User&prev=MyAccount">
					<button class="btn btn-outline-primary">Elimina
						Account</button>
				</a>
			<div class="dropdown-divider"></div>


		</section>


		<section class="card product_section card_login">

			<form class="px-4 py-3" action="EditAdminUser" onsubmit="return submitControl()" method="post">
				<div class="form-group">
					<label for="oldPassword">Password Amministratore</label> <input
						type="password" name="oldPassword" class="form-control"
						id="oldPassword" placeholder="Password">
				</div>
				<div class="form-group">
					<label for="newPassword">Nuova password utente</label> <input
						type="password" name="newPassword" class="form-control"
						id="newPassword" placeholder="Password">
				</div>
				<div class="form-group">
					<label for="confirmPassword">Conferma nuova password utente</label> <input
						type="password" name="confirmPassword" class="form-control"
						id="confirmPassword" placeholder="Password">
				</div>
				
				<input type="hidden"
						name="idUser" class="form-control" value="<% out.print(user.getId()); %>" required style="display: none;">
				
				<input type="text"
						name="fun" class="form-control" value="password" required style="display: none;">
				
				<button type="submit" class="btn btn-outline-primary">Salva
					modifiche</button>
			</form>

		</section>
		
			<%
					}}
			%>
	</section>

	<jsp:include page="../fragments/footer.jsp"></jsp:include>


	<jsp:include page="../fragments/defaultScripts.jsp"></jsp:include>
	<script type="text/javascript">
	
	function submitControl() {
		var name = document.getElementById("name");
		var surname = document.getElementById("surname");
		var email = document.getElementById("email");
		var password = document.getElementById("password");
		
		if (isName(name) && isName(surname) && isEmail(email) && isPassword(password)) {
			if (password == confirm) {
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}
		
	}
	
	function submitControl2() {
		var oldPassword = document.getElementById("oldPassword");
		var newPassword = document.getElementById("newPassword");
		var confirmPassword = document.getElementById("confirmPassword");
		
		if (isPassword(oldPassword) && isPassword(newPassword) && isEmail(email) && isPassword(confirmPassword)) {
			if (password == confirm) {
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}
		
	}
	
	</script>
</body>
</html>