<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.Category"%>
<%@ page import="entities.User"%>
<!DOCTYPE html>
<html>
<jsp:include page="../fragments/head.jsp">
	<jsp:param name="titlePage" value="Categoria" />
</jsp:include>
<jsp:include page="../fragments/header.jsp"></jsp:include>
<jsp:include page="../fragments/message.jsp"></jsp:include>
<body>
	<section class="card welcome_section">
		<div class="card-body">

			<%
				User user = (User) session.getAttribute("user");
				if (user != null) {
					Category category = (Category) request.getAttribute("category");
			%>
			<h1><% if (category!=null) out.print("Modifica "); else out.print("Aggiungi nuova "); %> Categoria</h1>
		</div>
	</section>
	<section>
		<form class="px-4 py-3" onsubmit="return submitControl()"
			action="<% if (category!=null) out.print("EditAdminCategory"); else out.print("NewAdminCategory");  %>"
			method="post">
			<div class="form-group">
				<label for="name">Nome</label> <input type="text" name="name"
					class="form-control" id="name" placeholder="Nome"
					<% if (category!=null) out.print("value=\"" + category.getName() + "\""); %>
					required onfocus="clean(this)">
			</div>
			<% if (category!=null) { %><input type="text" name="id" class="form-control" required
				style="display: none;" value="<% out.print(category.getId()); %>"> <% } %>
			<button type="submit" class="btn btn-outline-primary">
				<% if (category!=null) out.print("Salva modifiche"); else out.print("Aggiungi categoria"); %>
			</button>
		</form>

		<% } else {
					String errorMessage = "Non puoi visualizzare questa pagina se non hai effettuato l'accesso";
					out.print("<h2>" + errorMessage + "</h2>");
				} %>

	</section>
	
		<jsp:include page="../fragments/footer.jsp"></jsp:include>
	<jsp:include page="../fragments/defaultScripts.jsp"></jsp:include>
	<script type="text/javascript">
	
	function submitControl() {
		var name = document.getElementById("name");
				
		if (isName(name)) {
			return true;
			
		} else {
			return false;
		}
		
	}
	
	</script>
</body>
</html>