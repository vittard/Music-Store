<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="it">
	<jsp:include page="fragments/head.jsp">
		<jsp:param name="titlePage" value="Registrazione" />
	</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>
	

	<section class="card product_section card_login">
  <form class="px-4 py-3" onsubmit="return submitControl()" action="Sigin" method="post">
  	<img class="logo-login" src="img/logo.png">
  	<div class="form-group">
      <label for="name">Nome</label>
      <input type="text" name="name" class="form-control" id="name" placeholder="Nome" onfocus="clean(this)" >
    </div>
    <div class="form-group">
      <label for="surname">Cognome</label>
      <input type="text" name="surname" class="form-control" id="surname" placeholder="Cognome" onfocus="clean(this)">
    </div>
    <div class="form-group">
      <label for="email">Email</label>
      <input type="email" name="email" class="form-control" id="email" placeholder="email@example.com" onfocus="clean(this)">
    </div>
    <div class="form-group">
      <label for="password">Password</label>
      <input type="password" name="password" class="form-control" id="password" placeholder="Password" onfocus="clean(this)">
    </div>
    <div class="form-group">
      <label for="confirmPassword">Conferma Password</label>
      <input type="password" name="confirmPassword" class="form-control" id="confirmPassword" placeholder="Password" onfocus="clean(this)">
    </div>
    <button type="submit" class="btn btn-outline-primary">Registrati</button>
  </form>
  <div class="dropdown-divider"></div>
  <a class="dropdown-item" href="LoginPage">Possiedi un account? Accedi</a>

		
	</section>
	
	
	<jsp:include page="fragments/footer.jsp"></jsp:include>
	

	<jsp:include page="fragments/defaultScripts.jsp"></jsp:include>
	<script type="text/javascript">
	
	function submitControl() {
		var name = document.getElementById("name");
		var surname = document.getElementById("surname");
		var email = document.getElementById("email");
		var password = document.getElementById("password");
		var confirm = document.getElementById("confirmPassword");
		
		if (isName(name) && isName(surname) && isEmail(email) && isPassword(password) && isPassword(password)) {
			if (password.toString().localeCompare(confirm.toString())==0) {
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