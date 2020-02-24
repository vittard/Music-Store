<%@page import="manager.LoginManager"%>
<%@ page import="entities.User"%>
<%@ page import="manager.LoginManager"%>
<header>
	<% 
		User user = (User) session.getAttribute("user");
	%>
	<nav class="navbar navbar-expand-lg navbar-light nav-background">
		<a class="navbar-brand" href="#"><img src="img/logo.png"
			width="50" height="50" class="d-inline-block align-top" alt=""
			style="height: 50;">
			<% out.print("<p style=\"float: right; margin: 10px;\">Music-Store</p>");%> </a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="Home">Home
						Page<span class="sr-only">(current)</span>
				</a></li>
				<%
					if (user==null) {
				%>
				<li class="nav-item"><a class="nav-link" href="Categories">Categorie</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="Products">Prodotti</a>
				</li>
				<% } else {
					LoginManager manager = new LoginManager(user);
					if (manager.checkClient()) { %>
				<li class="nav-item"><a class="nav-link" href="Categories">Categorie</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="Products">Prodotti</a>
				</li>
				<% } else if (manager.checkCatalogAdmin()) { %>
				<li class="nav-item"><a class="nav-link" href="AdminCategories">Categorie</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="AdminProducts">Prodotti</a>
				</li>
				<% } else if (manager.checkUserAdmin()) { %>
				<li class="nav-item"><a class="nav-link" href="AdminUsers">Utenti</a>
				</li>
				<% } else if (manager.checkOrderAdmin()) { %>
				<li class="nav-item"><a class="nav-link" href="AdminOrders">Ordini</a>
				</li>
				<% }
				}%>
			</ul>
			<form class="form-inline my-2 my-lg-0 mr-auto" action="Products" method="post">
				<input class="form-control mr-sm-2" type="search" name="searchNav"
					placeholder="Search" aria-label="Search" id="searchNav" onkeyup="showNavResult(this.value)">
				<button class="btn btn-outline-primary my-2 my-sm-0" type="submit">Search</button>
			</form>

			<ul class="navbar-nav">
				<%
		    		if (user == null ) { 
	    		%>
				<li class="nav-item"><a href="SiginPage" class="nav-link">Registrati</a>
				</li>
				<li class="nav-item"><a href="LoginPage" class="nav-link">Accedi</a>
				</li>
				<% 
	    			} else {
	    				String username = user.getName();
	    				if (user.getType()==0) {
	    		%>
				<li class="nav-item"><a href="Cart" class="nav-link"><i
						class="fas fa-shopping-cart"></i> Carrello</a></li>
				<% } %>
				<li class="nav-item dropdown"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> Benvenuto <% out.print(username); %>
				</a>
				<%
				if (user!=null) {
					LoginManager manager = new LoginManager(user);
				if (manager.checkClient()) { %>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="MyAccount">Il mio profilo</a> <a
							class="dropdown-item" href="MyAddresses">I miei indirizzi</a> <a
							class="dropdown-item" href="MyPayments">Metodi di pagamento</a> <a
							class="dropdown-item" href="MyOrders">I miei ordini</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="Logout">Esci</a>
					</div>
				<% } else if (manager.checkUserAdmin()) { %>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="MyAccount">Il mio profilo</a>
						<a class="dropdown-item" href="AdminUsers">Gestione Utenti</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="Logout">Esci</a>
					</div>
				<% } else if(manager.checkCatalogAdmin()) { %>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="MyAccount">Il mio profilo</a> <a
							class="dropdown-item" href="AdminCategories">Gestione Categorie</a>
						<a class="dropdown-item" href="AdminProducts">Gestione prodotti</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="Logout">Esci</a>
					</div>
				<% } else if (manager.checkOrderAdmin()) { %>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="MyAccount">Il mio profilo</a> <a
							class="dropdown-item" href="AdminOrders">Gestore Ordini</a>
						<div class="dropdown-divider"></div>
						<a class="dropdown-item" href="Logout">Esci</a>
					</div>
				<% } 
				} %>
				</li>
				<% } %>
			</ul>

		</div>
	</nav>
</header>
<div id="resultNav"></div>