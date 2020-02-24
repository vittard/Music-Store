<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="it">
<jsp:include page="fragments/head.jsp">
	<jsp:param name="titlePage" value="Login" />
</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>


	<section class="card product_section card_login">
		<form class="px-4 py-3" action="Login" method="post">
			<img class="logo-login" src="img/logo.png">
			<div class="form-group">
				<label for="email">Email</label> <input
					type="email" name="email" class="form-control"
					id="email" placeholder="email@example.com" onfocus="clean()">
			</div>
			<div class="form-group">
				<label for="password">Password</label> <input
					type="password" name="password" class="form-control"
					id="password" placeholder="Password" onfocus="clean()">
			</div>
			<div class="form-group">
				<!--
      	<div class="form-check">
        
        <input type="checkbox" name="remember" class="form-check-input" id="dropdownCheck">
         <label class="form-check-label" for="dropdownCheck">
          Ricordami
       	 </label>
    		  </div>
      -->
			</div>
			<button type="submit" class="btn btn-outline-primary">Accedi</button>
		</form>
		<div class="dropdown-divider"></div>
		<a class="dropdown-item" href="SiginPage">Sei nuovo? Registrati.</a> <a
			class="dropdown-item" href="RecoveryPage">Password dimenticata?
			Recuperala.</a>


	</section>


	<jsp:include page="fragments/footer.jsp"></jsp:include>


	<jsp:include page="fragments/defaultScripts.jsp"></jsp:include>
	<script type="text/javascript">
	function submitControl() {
		var email = document.getElementById("email");
		var password = document.getElementById("password");
		
		if ( isEmail(email) && isPassword(password) ) {	
			return true;
		} else {
			return false;
		}
		
	}
	</script>
</body>
</html>