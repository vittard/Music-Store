<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.User"%>
<%@ page import="entities.Product"%>
<%@ page import="java.util.ArrayList"%>
<!doctype html>
<html lang="it">
<jsp:include page="fragments/head.jsp">
	<jsp:param name="titlePage" value="Home Page" />
</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>

	<section class="card welcome_section">
		<div class="card-body">
			<img src="img/logo.png" alt="">
			<h1>
				Benvenuto
				<%
				User user = (User) session.getAttribute("user");
				if (user != null) {
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
			@SuppressWarnings("unchecked")
			ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");
			if (products != null) {
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


	<jsp:include page="fragments/footer.jsp"></jsp:include>


	<jsp:include page="fragments/defaultScripts.jsp"></jsp:include>
</body>
</html>