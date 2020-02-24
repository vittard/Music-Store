<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="entities.User"%>
<%@ page import="entities.Product"%>
<%@ page import="java.util.ArrayList"%>
<!doctype html>
<html lang="it">
<jsp:include page="fragments/head.jsp">
	<jsp:param name="titlePage" value="Prodotto" />
</jsp:include>
<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>

	<section class="card welcome_section">
		<div class="card-body">
			<h1>
				<%
				Product product = null;
				String error = (String) request.getAttribute("error");
				if (error != null) {
					out.print(error);
				} else {
					product = (Product) request.getAttribute("product");
					out.print(product.getName());
				}
			%>
			</h1>
		</div>
	</section>

	<section class="card welcome_section">
		<% if ( product != null ) { %>

		<div class="card">
			<div class="card_product_view">
				<div class="view_image">
					<img src="<%out.print(product.getImagePath());%>" alt="">
				</div>
				<p class="view_price">
					&euro; 
					<%
						out.print( String.valueOf( product.getPrice() ).replace(".", ",") );
					%>
				</p>
				<p class="view_shipping">
					Prezzo di spedizione:  
					<br>
					<span>
						&euro;
						<%
							if (product.getShippingPrice() == 0) {
								out.print("GRATUITA");
							} else {
								out.print( String.valueOf( product.getShippingPrice() ).replace(".", ",") );
							}
						%>
					</span>
				</p>
				<p class="view_description">
					<%
						out.print(product.getDescription());
					%>
				</p>
				<form style="display: contents;" action="CartOp" method="post">
						<input type="hidden" style="display:none;" name="id" value="<% out.print(product.getId());%>"/>
						<input type="hidden" style="display:none;" name="operation" value="add" />
						<button type="submit" class="btn btn-outline-primary">
							<i class="fas fa-plus-circle"></i> Aggiungi al carrello
						</button>
				</form>
			</div>
		</div>

		<% } %>


	</section>


	<jsp:include page="fragments/footer.jsp"></jsp:include>


	<jsp:include page="fragments/defaultScripts.jsp"></jsp:include>
</body>
</html>