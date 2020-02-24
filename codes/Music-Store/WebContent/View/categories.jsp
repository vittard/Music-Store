<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.User"%>
<%@ page import="entities.Category"%>
<%@ page import="java.util.ArrayList"%>
<!doctype html>
<html lang="it">
<jsp:include page="fragments/head.jsp">
	<jsp:param name="Categorie" value="Categorie" />
</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>

	<section class="card welcome_section">
		<div class="card-body">
			<h1>Categorie</h1>
		</div>
	</section>

	<section class="card product_section">

		<%
			@SuppressWarnings("unchecked")
			ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("category");
			if (categories != null) {
				for (Category category : categories) {
		%>

		<div class="card product_div">
			<div class="card-body">
				<h1>
					<%
						out.print(category.getName());
					%>
				</h1>

				<a href="CategoryView?name=<%out.print(category.getName());%>"><button
						class="btn btn-outline-primary">Visualizza Prodotti</button></a>
			</div>
		</div>

		<%
			}
			}
		%>


	</section>

	<section class="card product_section">
		<div>
			<%
				int pag = (Integer) request.getAttribute("page");
				if (pag > 1) {
			%>
			<a
				href="Categories?page=<%out.print((pag) - 1);%>&min=<%out.print(((Integer) request.getAttribute("min")) - 20);%>">
				<button>
					<i class="fas fa-arrow-circle-left"></i>
				</button>
			</a>
			<%
				}
			%>
			<input type="number" value="<%out.print(pag);%>"
				style="text-align: center" min="1" max="10000" />


			<%
				if (request.getAttribute("next") != null) {
			%>
			<a
				href="Categories?page=<%out.print((pag) + 1);%>&min=<%out.print(((Integer) request.getAttribute("min")));%>">
				<button>
					<i class="fas fa-arrow-circle-right"></i>
				</button>
			</a>
			<%
				}
			%>

		</div>
	</section>

	<jsp:include page="fragments/footer.jsp"></jsp:include>


	<jsp:include page="fragments/defaultScripts.jsp"></jsp:include>
</body>
</html>