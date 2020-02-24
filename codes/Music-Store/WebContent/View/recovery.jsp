<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!doctype html>
<html lang="it">
	<jsp:include page="fragments/head.jsp">
		<jsp:param name="titlePage" value="Recupera Account" />
	</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>
	

	<section class="card product_section card_login">
  <form class="px-4 py-3" action="Recovery" onsubmit="return submitControl()" method="post">
  	<img class="logo-login" src="img/logo.png">
  	
    <div class="form-group">
      <label for="exampleDropdownFormEmail1">Email</label>
      <input type="email" name="email" class="form-control" id="email" placeholder="email@example.com">
    </div>
    
    <button type="submit" class="btn btn-outline-primary">Recupera</button>
  </form>
  <div class="dropdown-divider"></div>

		
	</section>
	
	
	<jsp:include page="fragments/footer.jsp"></jsp:include>
	

	<jsp:include page="fragments/defaultScripts.jsp"></jsp:include>
	
	<script type="text/javascript">
	
	function submitControl() {
		var email = document.getElementById("email");
		if (isEmail(email) ) {	
			return true;
		} else {
			return false;
		}
		
	}
	
	</script>
</body>
</html>