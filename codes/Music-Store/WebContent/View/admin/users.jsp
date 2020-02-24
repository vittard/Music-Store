<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.User"%>
<%@ page import="java.util.ArrayList"%>

<!doctype html>
<html lang="it">
<jsp:include page="../fragments/head.jsp">
	<jsp:param name="titlePage" value="Lista Utenti" />
</jsp:include>
<body>

	<jsp:include page="../fragments/header.jsp"></jsp:include>
	<jsp:include page="../fragments/message.jsp"></jsp:include>

	<section class="card welcome_section">
		<div class="card-body">
			<h1>Elenco utenti</h1>
		</div>
	</section>

	<section class="card">
		<form class="form-inline my-2 my-lg-0 mr-auto" action="AdminUsers" method="post">
			<input class="form-control mr-sm-2" name="search" id="search" type="search"
				placeholder="Search" aria-label="Search" onkeyup="showCustomer(this.value)">
			<button class="btn btn-outline-primary my-2 my-sm-0" type="submit">Search</button>
		</form>
		<a hreF="AdminUser" style="margin-top: 5px; display: block;"><button class="btn btn-outline-primary">
				<i class="fas fa-plus-circle"></i> Aggiungi nuovo utente
			</button></a>
	</section>
	
	<section class="card" id="result">
	</section>

	<section class="card">
		<table class="table admin_table">
			<thead>
				<tr class="table-primary">
					<th scope="col">ID</th>
					<th scope="col">Nome</th>
					<th scope="col">Cognome</th>
					<th scope="col">Tipo</th>
					<th scope="col">Operazioni</th>
				</tr>
			</thead>
			<%
				@SuppressWarnings("unchecked")
				ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
				if (users != null) {
					for (User u : users) {
			%>


			<tbody>
				<tr>
					<th scope="row">
						<%
							out.print(u.getId());
						%>
					</th>
					<td>
						<%
							out.print(u.getName());
						%>
					</td>
					<td>
						<%
							out.print(u.getSurname());
						%>
					</td>
					<td>
						<%
							out.print(u.getRole());
						%>
					</td>
					<td>
						<a href="AdminUser?id=<% out.print(u.getId()); %>">
						<button class="btn btn-outline-primary">
							<i class="fas fa-eye"></i>
						</button>
						</a>
						<a href="AdminUser?id=<% out.print(u.getId()); %>">
						<button class="btn btn-outline-primary">
							<i class="fas fa-edit"></i>
						</button>
						</a>
						<form style="display: contents;" action="AdminRemove" method="post">
							<input type="hidden" style="display:none;" name="type" value="User"/>
							<input type="hidden" style="display:none;" name="id" value="<% out.print(u.getId());%>"/>
							<input type="hidden" style="display:none;" name="prev" value="AdminUsers?page=<%out.print(request.getParameter("page")); String min = request.getParameter("min"); if (min!=null) out.print("&min="+min);%>" />
							<button type="submit" class="btn btn-outline-primary">
							<i class="fas fa-trash-alt"></i>
							</button>
						</form>
					</td>
				</tr>

				<%
					}
					}
				%>

			</tbody>
		</table>

		<div style="text-align: center;">
			<%
				int pag = (Integer) request.getAttribute("page");
				if (pag > 1) {
			%>
			<a
				href="AdminUsers?page=<%out.print((pag) - 1);%>&min=<%out.print(((Integer) request.getAttribute("min")) - 20);%>">
				<button class="btn btn-outline-primary">
					<i class="fas fa-arrow-circle-left"></i>
				</button>
			</a>
			<%
				}
			%>
			<p style="display: contents;"><%out.print(pag);%></p>


			<%
				if (request.getAttribute("next") != null) {
			%>
			<a
				href="AdminUsers?page=<%out.print((pag) + 1);%>&min=<%out.print(((Integer) request.getAttribute("min")));%>">
				<button class="btn btn-outline-primary">
					<i class="fas fa-arrow-circle-right"></i>
				</button>
			</a>
			<%
				}
			%>

		</div>


	</section>


	<jsp:include page="../fragments/footer.jsp"></jsp:include>


	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
		
	<script>
	function showCustomer(str) {
		  var xhttp;
		  if (str == "") {
		    document.getElementById("result").innerHTML = "";
		    return;
		  }
		  xhttp = new XMLHttpRequest();
		  xhttp.onreadystatechange = function() {
		    if (this.readyState == 4 && this.status == 200) {
		    	document.getElementById("result").innerHTML = this.responseText;
		    }
		  };
		  xhttp.open("GET", "SearchUsers?q="+str, true);
		  xhttp.send();
		}
	function selectResultInSearch(obj) {
		searchNav = document.getElementById("search"); //searches for and detects the input element from the 'myButton' id
		var x = obj.textContent; 
		searchNav.value = x; //changes the value
	}
	</script>
</body>
</html>