<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="controller.*"%>
<%@ page import="manager.*"%>
<%@ page import="entities.*"%>
<!DOCTYPE html>
<html lang="it">

<jsp:include page="../fragments/head.jsp">
	<jsp:param name="Ordini" value="Ordine" />
</jsp:include>

<body>

	<jsp:include page="../fragments/header.jsp"></jsp:include>
	<jsp:include page="../fragments/message.jsp"></jsp:include>

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
				
				<form class="px-4 py-3" action="EditAdminOrder" method="post">
					<input name="id" type="hidden" value="<% out.print(order.getId()); %>" style="display: none;">
					<div class="form-group">
					<label for="state">Stato</label>
						<select id="state" class="form-control" name="state" >
							<% 
							System.out.println(state);
								String[] options = new String[5];
								options[0] = "<option value=\"Attesa\">Attesa</option>";
								options[1] = "<option value=\"Completo\">Consegnato</option>";
								options[2] = "<option value=\"Archiviato\">Archiviato</option>";
								options[3] = "<option value=\"Attesa Di Spedizione\">Attesa Di Spedizione</option>";
								options[4] = "<option value=\"Spedito\">In Transito</option>";
							
								if (options[0].toLowerCase().contains(state.toLowerCase())) {
									
									out.print(options[0]);
									for (int i = 0 ; i < 5; i++ ) {
										if( i != 0 ) {
											out.print(options[i]);
										}
									}
									
								} else if (options[1].toLowerCase().contains(state.toLowerCase())) {
									out.print(options[1]);
									for (int i = 0 ; i < 5; i++ ) {
										if( i != 1 ) {
											out.print(options[i]);
										}
									}
									
								} else  if (options[2].toLowerCase().contains(state.toLowerCase())) {
									
									out.print(options[2]);
									for (int i = 0 ; i < 5; i++ ) {
										if( i != 2 ) {
											out.print(options[i]);
										}
									}
									
								} else if (options[3].toLowerCase().contains(state.toLowerCase())) {
									
									out.print(options[3]);
									for (int i = 0 ; i < 5; i++ ) {
										if( i != 3 ) {
											out.print(options[i]);
										}
									}
									
								} else if (options[4].toLowerCase().contains(state.toLowerCase())) {
									
									out.print(options[4]);
									for (int i = 0 ; i < 5; i++ ) {
										if( i != 4 ) {
											out.print(options[i]);
										}
									}
									
								}
							%>
					  			
							</select>
					</div>
						<button class="btn btn-outline-primary" type="submit">Modifica</button>
				</form>
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

	<jsp:include page="../fragments/footer.jsp"></jsp:include>

	<jsp:include page="../fragments/defaultScripts.jsp"></jsp:include>
</body>
</html>