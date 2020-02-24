<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="controller.*"%>
<%@ page import="manager.*"%>
<%@ page import="entities.*"%>
<!DOCTYPE html>
<html lang="it">

<jsp:include page="fragments/head.jsp">
	<jsp:param name="Ordini" value="Ordine" />
</jsp:include>

<body>

	<jsp:include page="fragments/header.jsp"></jsp:include>
	<jsp:include page="fragments/message.jsp"></jsp:include>

	<%
		Order order = (Order) request.getAttribute("order");
			Address address = (Address) request.getAttribute("address");
			@SuppressWarnings("unchecked")
			HashMap<Product, Integer> products = (HashMap<Product, Integer>) request.getAttribute("products");

			String state = order.getState();
			Date date = order.getDate();
			int userId = order.getUserId();
			int addressId = order.getAddressId();

			String id_name = address.getName();
	%>

	<div style="float: left" class="card">
		<div class="card-body">
			<h1>
				<%
					out.print("Music-Store");
				%>
			</h1>
			<p>
				<%
					out.print("Order ID: " + order.getId());
				%>
			</p>
			<p>
				<%
					out.print("Totale: ");
				%>
			</p>
			<p>
				<%
					out.print("Stato ordine : " + order.getState());
				%>
			</p>
		</div>


	</div>

	<div class="card">
		<div class="card-body">

			<p>
				<%
					out.print(" Nome: " + address.getName());
				%>
			</p>
			<p>
				<%
					out.print(" Cognome: " + address.getSurname());
				%>
			</p>
			<p>
				<%
					out.print(" Numero telefonico: " + address.getPhone());
				%>
			</p>
			<p>
				<%
					out.print("Strada: " + address.getStreet() + " CAP:  " + address.getZipCode() + " Provincia di: "
							+ address.getProvince() + " (" + address.getState() + ")");
				%>
			</p>
		</div>


	</div>
	<section class="card">
		<div>
			<table class="table admin_table">
				<thead>
					<tr class="table-primary">
						<th scope="col">ID Prodotto</th>
						<th scope="col">Nome</th>
						<th scope="col">Prezzo</th>
						<th scope="col">Prezzo Spedizione</th>
						<th scope="col">Quantità</th>
						<th scope="col">Totale</th>
					</tr>
				</thead>
				<tbody>
					<%
						double priceSum = 0;
						double shippingSum = 0;
						if (products.size() == 0) {
							out.print("<table class=\"table admin_table\"><th>Non ha effettuato nessun ordine</th></table>");
						} else {
							Iterator<Map.Entry<Product, Integer>> it = products.entrySet().iterator();
							while (it.hasNext()) {
								Map.Entry<Product, Integer> e = it.next();
								Product product = e.getKey();
								Integer quantity = e.getValue();
					%>

					<tr>
						<th scope="row">
							<%
								out.print(product.getId());
							%>
						</th>
						<td>
							<%
								out.print(product.getName());
							%>
						</td>
						<td>
							<%
								out.print(product.getPrice());
										priceSum = priceSum + product.getPrice() * quantity;
							%>
						</td>
						<td>
							<%
								out.print(product.getShippingPrice());
										shippingSum = shippingSum + product.getShippingPrice() * quantity;
							%>
						</td>
						<td>
							<%
								out.print(quantity);
							%>
						</td>
						<td>
							<%
								double totalLine = (product.getPrice() * quantity) + (product.getShippingPrice() * quantity);
										out.print(totalLine);
							%>
						</td>
					</tr>

					<%
						}
						}
					%>

				</tbody>
			</table>
			<table class="table admin_table"
				style="margin-top: 10px; border-top: 5px solid red">
				<thead>
					<tr class="table-primary">
						<th scope="col">Totali</th>
						<th scope="col">Totale Prodotti</th>
						<th scope="col">Totale Spedizioni</th>
						<th scope="col">Totale finale</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td></td>
						<td>
							<%
								out.print(priceSum);
							%>
						</td>
						<td>
							<%
								out.print(shippingSum);
							%>
						</td>
						<td>
							<%
								out.print((priceSum + shippingSum));
							%>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</section>

	<jsp:include page="fragments/footer.jsp"></jsp:include>

	<jsp:include page="fragments/defaultScripts.jsp"></jsp:include>
</body>
</html>