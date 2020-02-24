<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.User"%>
<%@ page import="entities.Product"%>
<%@ page import="java.util.ArrayList"%>
<!doctype html>
<html lang="it">
<jsp:include page="fragments/head.jsp">
	<jsp:param name="I Nostri Prodotti" value="Categoria" />
</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>

	<section class="card welcome_section">
		<div class="card-body">
			<h1>
				Lista dei prodotti della categoria
				<%
				String categoryName = (String) request.getAttribute("categoryName");
				out.print(categoryName);
			%>
			</h1>
		</div>
	</section>

	<section class="card product_section">

		<%
			@SuppressWarnings("unchecked")
			ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");
			if (products.size() == 0) {
				out.print("<section class=\"card welcome_section\"><div class=\"card-body\"><h2 style=\"color: red\">"
						+ "Non ci sono prodotti per questa categoria" + "</h2></div></section>");
			} else if (products != null) {
				for (Product product : products) {
		%>

		<div class="card product_div">
			<div class="card-body">
				<h1>
					<%
						out.print(product.getName());
					%>
				</h1>
				<img src="<%out.print(product.getImagePath());%>" alt="">
				<p>
					<%
						out.print(product.getPrice());
					%>
				</p>
				<p>
					<%
						out.print(product.getShippingPrice());
					%>
				</p>
				<a href="ProductView?id=<%out.print(product.getId());%>"><button
						class="btn btn-outline-primary">Visualizza</button></a>
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
				href="Products?page=<%out.print((pag) - 1);%>&min=<%out.print(((Integer) request.getAttribute("min")) - 20);%>">
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
				href="Products?page=<%out.print((pag) + 1);%>&min=<%out.print(((Integer) request.getAttribute("min")));%>">
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