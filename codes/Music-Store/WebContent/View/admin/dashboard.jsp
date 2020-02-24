<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="entities.User" %>
	<%@ page import="manager.LoginManager" %>
<!doctype html>
<html lang="it">
	<jsp:include page="../fragments/head.jsp">
		<jsp:param name="titlePage" value="Home Page" />
	</jsp:include>
<body>

	<jsp:include page="../fragments/header.jsp"></jsp:include>
	<jsp:include page="../fragments/message.jsp"></jsp:include>
	
	<section class="card welcome_section">
		<div class="card-body">
			<img src="img/logo.png" alt="">
			<h1>Benvenuto 
				<% 
					User user = (User) session.getAttribute("user");
	    			if (user != null ) {
	    				out.print(user.getName());
	    			} else {
	    				out.print("Ospite");
	    			}
	    		%>
    		</h1>
		</div>
	</section>

	<section class="card product_section">
	
	<% 
		LoginManager manager = new LoginManager(user); 
		if (manager.checkCatalogAdmin()) {
	%>
	
		<div class="card dash_card">
			<div class="card-body">
				<a href="AdminCategories"><button class="btn btn-outline-primary">Gestione Categorie</button></a>
			</div>
		</div>
		
		<div class="card dash_card">
			<div class="card-body">
				<a href="AdminProducts"><button class="btn btn-outline-primary">Gestione Prodotti</button></a>
			</div>
		</div>
		
	<% } else if (manager.checkOrderAdmin()) { %>
		
		<div class="card dash_card">
			<div class="card-body">
				<a href="AdminOrders"><button class="btn btn-outline-primary">Gestione Ordini</button></a>
			</div>
		</div>
		
	<% } else if(manager.checkUserAdmin()) { %>	
		
		<div class="card dash_card">
			<div class="card-body">
				<a href="AdminUsers"><button class="btn btn-outline-primary">Gestione Utenti</button></a>
			</div>
		</div>
		
		<% } %>
	
		
		
	</section>
	
	
	<jsp:include page="../fragments/footer.jsp"></jsp:include>
	

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</body>
</html>